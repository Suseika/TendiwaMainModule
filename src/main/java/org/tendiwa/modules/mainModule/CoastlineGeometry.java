package org.tendiwa.modules.mainModule;

import com.google.common.collect.ImmutableList;
import org.jgrapht.UndirectedGraph;
import org.tendiwa.demos.Demos;
import org.tendiwa.demos.settlements.CityDrawer;
import org.tendiwa.drawing.*;
import org.tendiwa.drawing.extensions.*;
import org.tendiwa.geometry.*;
import org.tendiwa.geometry.extensions.*;
import org.tendiwa.noise.Noise;
import org.tendiwa.noise.SimpleNoiseSource;
import org.tendiwa.pathfinding.astar.AStar;
import org.tendiwa.pathfinding.dijkstra.PathTable;
import org.tendiwa.settlements.*;
import org.tendiwa.settlements.utils.RectangularBuildingLots;
import org.tendiwa.settlements.utils.StreetsDetector;

import java.awt.Color;
import java.util.*;
import java.util.stream.Collectors;

import static java.awt.Color.*;

public class CoastlineGeometry implements Runnable {
	DrawableInto canvas;
	CellSet water;
	Collection<FiniteCellSet> shapeExitsSets;
	List<List<Cell>> pathsBetweenCities;
	Collection<CoastlineCityGeometry> cities = new HashSet<>();
	Rectangle worldSize = new Rectangle(0, 0, 600, 600);


	public static void main(String[] args) {
		Demos.run(
			CoastlineGeometry.class,
			new DrawingModule(),
			new LargerScaleCanvasModule()
		);
	}

	@Override
	public void run() {
		// Constants and most general shapes
		PieChartTimeProfiler chart = new PieChartTimeProfiler();
		int maxCityRadius = 40;
		int minDistanceFromCoastToCityBorder = 3;
		int minDistanceBetweenCityCenters = maxCityRadius * 7;
		int minDistanceFromCoastToCityCenter = 20;
		SimpleNoiseSource noise = (x, y) -> Noise.noise(
			((double) x + 0) / 50,
			((double) y + 0) / 40,
			7
		);
		water = (x, y) -> noise.noise(x, y) <= 100;
		chart.saveTime("Constants");

		// Find city centers
		CellSet reducingMask = (x, y) -> (x + y) % 20 == 0;
		ChebyshevDistanceBufferBorder cityCenterBorder = new ChebyshevDistanceBufferBorder(
			minDistanceFromCoastToCityCenter,
			(x, y) -> worldSize.contains(x, y) && water.contains(x, y)
		);
		FiniteCellSet borderWithCityCenters = new ScatteredCellSet(
			reducingMask.and(cityCenterBorder),
			worldSize
		);
		chart.saveTime("City centers");
		Rectangle cityCentersRectangle = worldSize.shrink(0);
		DistantCellsFinder cityCenters = new DistantCellsFinder(
			borderWithCityCenters,
			minDistanceBetweenCityCenters
		);
		chart.saveTime("Distant cells");

		// Finding cells that are close to water
		CachedCellSet cellsCloseToCoast = new CachedCellSet(
			new ChebyshevDistanceBuffer(
				minDistanceFromCoastToCityBorder,
				(x, y) -> worldSize.contains(x, y) && water.contains(x, y)
			),
			worldSize
		);
		chart.saveTime("Cells close to coast");


		DrawingAlgorithm<Cell> grassColor = DrawingCell.withColor(Color.GREEN);
		DrawingAlgorithm<Cell> waterColor = DrawingCell.withColor(BLUE);

		CityBoundsFactory boundsFactory = new CityBoundsFactory(water);
		Rectangle worldSizeStretchedBy1 = worldSize.stretch(1);
		canvas = new TestCanvas(1, worldSize.x + worldSize.getMaxX(), worldSize.y + worldSize.getMaxY());
//		canvas = new FakeCanvas();
		TestCanvas.canvas = canvas;
		canvas.draw(borderWithCityCenters, DrawingCellSet.withColor(Color.PINK));
		drawTerrain(worldSize, water, waterColor, grassColor);
		chart.saveTime("Draw terrain");
        canvas.draw(borderWithCityCenters, DrawingCellSet.withColor(Color.RED));
		shapeExitsSets = new HashSet<>();
		MutableCellSet citiesCells = new ScatteredMutableCellSet();
		for (Cell cell : cityCenters) {
			if (!cityCentersRectangle.contains(cell)) {
				continue;
			}
			CoastlineCityGeometry cityGeometry = new CoastlineCityGeometry();
			cities.add(cityGeometry);
			chart.saveTime("0");
			int maxCityRadiusModified = maxCityRadius + cell.x % 30 - 15;
			Rectangle cityBoundRec = Recs
				.rectangleByCenterPoint(cell, maxCityRadiusModified * 2 + 1, maxCityRadiusModified * 2 + 1)
				.intersectionWith(worldSize)
				.get();
			CachedCellSet coast = new CachedCellSet(
				new ChebyshevDistanceBufferBorder(minDistanceFromCoastToCityBorder, water),
				cityBoundRec
			);
			chart.saveTime("1");
			BoundedCellSet cityShape = new PathTable(
				cell.x,
				cell.y,
				(x, y) -> worldSizeStretchedBy1.contains(x, y) && !coast.contains(x, y),
				maxCityRadiusModified
			).computeFull();
			chart.saveTime("2");
            canvas.draw(cell, DrawingCell.withColorAndSize(Color.black, 6));
            canvas.draw(cityShape, DrawingCellSet.withColor(Color.BLACK));
			UndirectedGraph<Point2D, Segment2D> cityBounds = boundsFactory.create(
				cityShape,
				cell,
				maxCityRadiusModified
			);
			chart.saveTime("3");
            canvas.draw(cityBounds, DrawingGraph.withColorAndVertexSize(RED, 2));
			RoadsPlanarGraphModel roadsPlanarGraphModel = new CityGeometryBuilder(cityBounds)
				.withDefaults()
				.withRoadsFromPoint(4)
				.withDeviationAngle(Math.PI / 30)
				.withSecondaryRoadNetworkDeviationAngle(0.1)
				.withRoadSegmentLength(40)
				.withConnectivity(1)
				.withMaxStartPointsPerCycle(1)
				.build();
			chart.saveTime("4");
			citiesCells.addAll(ShapeFromOutline.from(roadsPlanarGraphModel.getLowLevelRoadGraph()));
			chart.saveTime("5");
			canvas.draw(roadsPlanarGraphModel, new CityDrawer());
			FiniteCellSet exitCells = null;
			try {
				exitCells = roadsPlanarGraphModel
					.getNetworks()
					.stream()
					.flatMap(c -> c
							.exitsOnCycles()
							.stream()
							.filter(p -> c
									.network()
									.edgeSet()
									.stream()
									.anyMatch(e -> e.start.equals(p) || e.end.equals(p))
							)
							.map(Point2D::toCell)
					)
					.collect(CellSet.toCellSet());
			} catch (Exception exc) {
				TestCanvas cvs = new TestCanvas(2, worldSize.x + worldSize.getMaxX(),
					worldSize.y + worldSize.getMaxY());
				for (NetworkWithinCycle net : roadsPlanarGraphModel.getNetworks()) {
					cvs.draw(net.cycle(), DrawingGraph.withColorAndAntialiasing(Color.BLACK));
				}
				throw new RuntimeException();
			}
			chart.saveTime("6");
			shapeExitsSets.add(exitCells);
			chart.saveTime("7");
			Set<RectangleWithNeighbors> buildingPlaces = RectangularBuildingLots.placeInside(roadsPlanarGraphModel);
			chart.saveTime("8: Find building places");
			cityGeometry.roadsPlanarGraphModel = roadsPlanarGraphModel;
			cityGeometry.buildingPlaces = buildingPlaces;
			canvas.drawAll(
				buildingPlaces,
				DrawingRectangleWithNeighbors.withColorAndDefaultBorder(Color.blue, Color.magenta)
			);
			cityGeometry.streets = StreetsDetector.detectStreets(roadsPlanarGraphModel.getFullRoadGraph());
			chart.saveTime("9: Detect streets");

			// End of city geometry
		}
		CellSet shapeExitsCombined = shapeExitsSets
			.stream()
			.map(a -> (CellSet) a)
			.reduce(CellSet.empty(), (a, b) -> a.or(b));
		chart.saveTime("Combined sets");

		CellSet spaceBetweenCities = new CachedCellSet(
			(x, y) ->
				worldSize.contains(x, y)
					&& (!water.contains(x, y)
					&& !citiesCells.contains(x, y)
					&& !cellsCloseToCoast.contains(x, y) || shapeExitsCombined.contains(x, y)),
			worldSize
		);
		chart.saveTime("Space between cities");

		IntershapeNetwork network = IntershapeNetwork
			.withShapeExits(shapeExitsSets)
			.withWalkableCells(spaceBetweenCities);
		chart.saveTime("Network");

		pathsBetweenCities = network.getGraph().edgeSet()
			.stream()
			.map(
				segment -> new AStar(
					(cell, neighbor) ->
						((spaceBetweenCities.contains(neighbor) ? 1 : 100000000) * cell.diagonalComponent(neighbor))
				).path(segment.start, segment.end)
			)
			.collect(Collectors.toList());
		chart.saveTime("Final drawing");
		canvas.draw(cellsCloseToCoast, DrawingCellSet.withColor(Color.PINK));
		chart.draw();

	}

	private void drawTerrain(
		Rectangle worldSize,
		CellSet water,
		DrawingAlgorithm<Cell> waterColor,
		DrawingAlgorithm<Cell> grassColor
	) {
		for (int i = worldSize.x; i <= worldSize.getMaxX(); i++) {
			for (int j = worldSize.y; j <= worldSize.getMaxY(); j++) {
				canvas.draw(new Cell(i, j), water.contains(i, j) ? waterColor : grassColor);
			}
		}
	}
}

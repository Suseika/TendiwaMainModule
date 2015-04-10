package org.tendiwa.modules.mainModule;

import org.jgrapht.UndirectedGraph;
import org.tendiwa.geometry.*;
import org.tendiwa.geometry.extensions.CachedCellSet;
import org.tendiwa.geometry.extensions.ChebyshovDistanceBufferBorder;
import org.tendiwa.geometry.smartMesh.NetworkWithinCycle;
import org.tendiwa.geometry.smartMesh.Segment2DSmartMesh;
import org.tendiwa.geometry.smartMesh.SegmentNetworkBuilder;
import org.tendiwa.pathfinding.dijkstra.PathTable;
import org.tendiwa.settlements.cityBounds.CityBounds;
import org.tendiwa.settlements.utils.RectangleWithNeighbors;
import org.tendiwa.settlements.utils.RectangularBuildingLots;
import org.tendiwa.settlements.utils.streetsDetector.DetectedStreets;

import java.util.Set;
import java.util.stream.Stream;

final class CoastlineCityGeometry {
	private final CoastlineGeometryConfig config;
	private final Cell citySeed;
	private final Rectangle worldSize;

	Segment2DSmartMesh mesh;
	Set<RectangleWithNeighbors> buildingPlaces;
	Stream<Chain2D> streets;
	FiniteCellSet exits;

	CoastlineCityGeometry(
		CoastlineGeometryConfig config,
		ProgressDrawing progress,
		Cell citySeed,
		Rectangle worldSize,
		CellSet water
	) {
		this.config = config;
		this.citySeed = citySeed;
		this.worldSize = worldSize;

		CachedCellSet coast = findNearbyCoast(water);

		BoundedCellSet cityShape = computeCityShape(coast);

		progress.drawCityBackground(citySeed, cityShape);
		UndirectedGraph<Point2D, Segment2D> cityBounds = CityBounds.create(
			cityShape,
			citySeed,
			cityRadiusModified()
		);
		progress.drawCityBounds(cityBounds);

		mesh = computeMesh(cityBounds);
//			canvas.draw(segment2DSmartMesh, new CityDrawer());
		exits = exitsSet();
		buildingPlaces = RectangularBuildingLots.placeInside(mesh);
			progress.drawLots(buildingPlaces);
		streets = DetectedStreets.toChain2DStream(mesh.getFullRoadGraph());
	}

	private Segment2DSmartMesh computeMesh(UndirectedGraph<Point2D, Segment2D> cityBounds) {
		return new SegmentNetworkBuilder(cityBounds)
			.withDefaults()
			.withRoadsFromPoint(2)
			.withDeviationAngle(Math.PI / 30)
			.withSecondaryRoadNetworkDeviationAngle(0.1)
			.withRoadSegmentLength(40)
			.withSnapSize(10)
			.withMaxStartPointsPerCycle(2)
			.build();
	}

	private BoundedCellSet computeCityShape(CachedCellSet coast) {
		return new PathTable(
			citySeed,
			worldSize.without(coast),
			cityRadiusModified()
		).computeFull();
	}

	private CachedCellSet findNearbyCoast(CellSet water) {
		return new CachedCellSet(
			new ChebyshovDistanceBufferBorder(
				config.minDistanceFromCoastToCityBorder,
				water
			),
			cityBoundRectangle()
		);
	}

	private Rectangle cityBoundRectangle() {
		return Recs
			.rectangleByCenterPoint(citySeed, cityRadiusModified() * 2 + 1, cityRadiusModified() * 2 + 1)
			.intersectionWith(worldSize)
			.get();
	}

	private int cityRadiusModified() {
		return config.maxCityRadius + citySeed.x % 30 - 15;
	}

	private FiniteCellSet exitsSet() {
		return mesh
			.networks()
			.stream()
			.flatMap(this::cellsAtCycleExits)
			.collect(CellSet.toCellSet());
	}

	private Stream<Cell> cellsAtCycleExits(NetworkWithinCycle network) {
		return network
			.exitsOnCycles()
			.stream()
			.filter(p -> network
					.network()
					.edgeSet()
					.stream()
					.anyMatch(e -> e.start.equals(p) || e.end.equals(p))
			)
			.map(Point2D::toCell)
			.distinct();
	}
}

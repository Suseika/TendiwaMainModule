package org.tendiwa.modules.mainModule;

import com.google.common.collect.ImmutableList;
import org.jgrapht.UndirectedGraph;
import org.tendiwa.core.Location;
import org.tendiwa.core.Tendiwa;
import org.tendiwa.core.World;
import org.tendiwa.drawing.TestCanvas;
import org.tendiwa.drawing.extensions.DrawingWorld;
import org.tendiwa.geometry.CellSegment;
import org.tendiwa.geometry.Point2D;
import org.tendiwa.geometry.Rectangle;
import org.tendiwa.geometry.Segment2D;
import org.tendiwa.geometry.extensions.CachedCellSet;
import org.tendiwa.groovy.Registry;
import org.tendiwa.settlements.RectangleWithNeighbors;
import org.tendiwa.settlements.buildings.BuildingsTouchingStreets;
import org.tendiwa.settlements.buildings.LotFacadeAssigner;
import org.tendiwa.settlements.buildings.UrbanPlanner;
import org.tendiwa.settlements.streets.Namer;
import org.tendiwa.settlements.utils.RoadRejector;
import org.tendiwa.settlements.utils.StreetsDetector;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

/**
 */
public class CoastlinePixmap implements Runnable {
	private final CoastlineGeometry geometry;

	public static void main(String[] args) {
//		Demos.run(CoastlinePixmap.class);

		new CoastlinePixmap().run();
	}

	public CoastlinePixmap() {
		this.geometry = new CoastlineGeometry();
		geometry.run();
	}

	@Override
	public void run() {
		Tendiwa.loadModules();
		TestCanvas canvas = new TestCanvas(1, 300, 300);
		World world = new World(300, 300);
		Location location = new Location(world.getDefaultPlane(), 0, 0, 300, 300);
		CachedCellSet waterCells = new CachedCellSet(
			geometry.water,
			world.asRectangle()
		);
		location.drawCellSet(waterCells, Registry.floorTypes.get("water"));
		location.drawCellSet(
			(x, y) -> !waterCells.contains(x, y),
			waterCells.getBounds(),
			Registry.floorTypes.get("grass")
		);
		geometry.pathsBetweenCities
			.stream()
			.flatMap(path -> path.stream())
			.forEach((cell) -> {
				if (world.asRectangle().contains(cell)) {
					location.place(Registry.floorTypes.get("ground"), cell);
				}
			});
		Consumer<Segment2D> drawRoad = segment ->
			new CellSegment(segment)
				.asList()
				.stream()
				.filter(c -> world.asRectangle().contains(c))
				.forEach(c -> location.place(Registry.floorTypes.get("ground"), c));
		for (
			CoastlineCityGeometry city
			: geometry.cities
			) {
			UndirectedGraph<Point2D, Segment2D> actualRoadGraph = RoadRejector.rejectPartOfNetworksBorders(
				city.roadsPlanarGraphModel.getFullRoadGraph(),
				city.roadsPlanarGraphModel,
				0.5,
				new Random(123445634)
			);
			Set<ImmutableList<Point2D>> streets = StreetsDetector.detectStreets(actualRoadGraph);
			BuildingsTouchingStreets b2s = new BuildingsTouchingStreets(streets, 3.3);
			Namer<List<Point2D>> streetNamer = (line) -> "Улица Говна";
//			LotFacadeAssigner facadeAssigner = (lot) -> b2s.getStreetsForLot(lot).iterator().next();
//			new UrbanPlanner(world.getDefaultPlane(), 3.3).addAvailableArchitecture();
			actualRoadGraph.edgeSet().forEach(drawRoad);
			city.roadsPlanarGraphModel.getNetworks().stream()
				.flatMap(cell -> cell.network().edgeSet().stream())
				.forEach(drawRoad);
			for (RectangleWithNeighbors buildingPlace : city.buildingPlaces) {
				location.fillRectangle(
					buildingPlace.rectangle.intersectionWith(world.asRectangle()).get(),
					Registry.wallTypes.get("wall_grey_stone")
				);
				for (Rectangle neighbor : buildingPlace.neighbors) {
					Optional<Rectangle> intersection = neighbor.intersectionWith(world.asRectangle());
					if (intersection.isPresent()) {
						location.fillRectangle(
							intersection.get(),
							Registry.wallTypes.get("wall_grey_stone")
						);
					}
				}
			}

		}

		canvas.draw(world, DrawingWorld.withColorMap(new MainPlaceableToColor()));
	}
}

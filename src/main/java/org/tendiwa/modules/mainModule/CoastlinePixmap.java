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
import org.tendiwa.settlements.buildings.*;
import org.tendiwa.settlements.streets.LotStreetAssigner;
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
			LotsTouchingStreets b2s = new LotsTouchingStreets(streets, 3.3);
			Namer<List<Point2D>> streetNamer = (line) -> "Улица Говна";
			LotFacadeAssigner facadeAssigner = FairLotFacadeAndStreetAssigner.create(b2s);
			LotStreetAssigner streetAssigner = (LotStreetAssigner) facadeAssigner;
			UrbanPlanner urbanPlanner = new UrbanPlanner(world.getDefaultPlane(), 3.3, new Random(565656565));
			urbanPlanner.addAvailableArchitecture(
				new House(),
				new ArchitecturePolicyBuilder().withMinInstancesNoGreaterThan(1).withMaxInstances(5).build()
			);
			urbanPlanner.addAvailableArchitecture(
				new DummyArchitecture(),
				new ArchitecturePolicyBuilder().withMinInstancesNoGreaterThan(1).withMaxInstances(7).build()
			);
			City.builder()
				.addLots(city.buildingPlaces)
				.placeBuildings(urbanPlanner);
			actualRoadGraph.edgeSet().forEach(drawRoad);
			city.roadsPlanarGraphModel.getNetworks().stream()
				.flatMap(cell -> cell.network().edgeSet().stream())
				.forEach(drawRoad);
//			drawShitInLots(world, location, city);

		}

		canvas.draw(world, DrawingWorld.withColorMap(new MainPlaceableToColor()));
	}

	private void drawShitInLots(World world, Location location, CoastlineCityGeometry city) {
		for (RectangleWithNeighbors buildingPlace : city.buildingPlaces) {
			location.fillRectangle(
				buildingPlace.rectangle.intersectionWith(world.asRectangle()).get(),
				Registry.floorTypes.get("water")
			);
			for (Rectangle neighbor : buildingPlace.neighbors) {
				Optional<Rectangle> intersection = neighbor.intersectionWith(world.asRectangle());
				if (intersection.isPresent()) {
					location.fillRectangle(
						intersection.get(),
						Registry.floorTypes.get("water")
					);
				}
			}
		}
	}
}

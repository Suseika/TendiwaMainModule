package org.tendiwa.modules.mainModule;

import org.jgrapht.UndirectedGraph;
import org.tendiwa.core.Location;
import org.tendiwa.core.Tendiwa;
import org.tendiwa.core.World;
import org.tendiwa.demos.Demos;
import org.tendiwa.drawing.TestCanvas;
import org.tendiwa.drawing.extensions.DrawingWorld;
import org.tendiwa.geometry.CellSegment;
import org.tendiwa.geometry.Point2D;
import org.tendiwa.geometry.Rectangle;
import org.tendiwa.geometry.Segment2D;
import org.tendiwa.geometry.extensions.CachedCellSet;
import org.tendiwa.groovy.Registry;
import org.tendiwa.settlements.RoadsPlanarGraphModel;
import org.tendiwa.settlements.RectangleWithNeighbors;
import org.tendiwa.settlements.utils.RoadRejector;

import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class CoastlinePixmap implements Runnable {
	private final CoastlineGeometry geometry;

	public static void main(String[] args) {
		Demos.run(CoastlinePixmap.class);
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
			RoadsPlanarGraphModel roadsPlanarGraphModel
			: geometry.buildingPlaces.keySet()
			) {
			UndirectedGraph<Point2D, Segment2D> graphToDraw = RoadRejector.rejectPartOfNetworksBorders(
				roadsPlanarGraphModel.getFullRoadGraph(),
				roadsPlanarGraphModel,
				0.5,
				123445634
			);
			graphToDraw.edgeSet().forEach(drawRoad);
			roadsPlanarGraphModel.getNetworks().stream().forEach(
				cell -> cell.network().edgeSet().stream().forEach(drawRoad)
			);
		}

		for (
			Set<RectangleWithNeighbors> rectangleWithNeighborses
			: geometry.buildingPlaces.values()
			) {
			for (RectangleWithNeighbors rectangleWithNeighbors : rectangleWithNeighborses) {
				location.fillRectangle(
					rectangleWithNeighbors.rectangle.intersectionWith(world.asRectangle()).get(),
					Registry.wallTypes.get("wall_grey_stone")
				);
				for (Rectangle neighbor : rectangleWithNeighbors.neighbors) {
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

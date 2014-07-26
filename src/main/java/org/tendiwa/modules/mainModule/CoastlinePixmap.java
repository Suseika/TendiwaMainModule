package org.tendiwa.modules.mainModule;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import org.tendiwa.core.Location;
import org.tendiwa.core.Tendiwa;
import org.tendiwa.core.World;
import org.tendiwa.demos.Demos;
import org.tendiwa.drawing.TestCanvas;
import org.tendiwa.drawing.extensions.DrawingWorld;
import org.tendiwa.geometry.*;
import org.tendiwa.geometry.extensions.CachedCellSet;
import org.tendiwa.groovy.Registry;
import org.tendiwa.settlements.City;
import org.tendiwa.settlements.RectangleWithNeighbors;

import java.util.List;
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
//		location.fillRectangle(new Rectangle(100, 100, 100, 100), Registry.wallTypes.get("wall_grey_stone"));
		System.out.println(geometry.water);
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
		for (List<Cell> pathsBetweenCity : geometry.pathsBetweenCities) {
			for (Cell cell : pathsBetweenCity) {
				if (world.asRectangle().contains(cell)) {
					location.place(Registry.floorTypes.get("ground"), cell);
				}
			}
		}
		Consumer<Segment2D> drawRoad = segment ->
			Lists.newArrayList(new CellSegment(
				new Cell(
					(int) segment.start.x,
					(int) segment.start.y
				),
				new Cell(
					(int) segment.end.x,
					(int) segment.end.y
				)
			)).forEach(c -> {
				if (world.asRectangle().contains(c)) {
					location.place(
						Registry.floorTypes.get("ground"),
						c
					);
				}
			});
		for (
			City city
			: geometry.buildingPlaces.keySet())

		{
			city.getLowLevelRoadGraph().edgeSet().forEach(drawRoad);
//			city.getHighLevelRoadGraph().edgeSet().forEach(drawRoad);
			city.getCells().stream().forEach(
				cell -> cell.network().edgeSet().stream().forEach(drawRoad)
			);
		}

		for (
			Set<RectangleWithNeighbors> rectangleWithNeighborses
			: geometry.buildingPlaces.values())

		{
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

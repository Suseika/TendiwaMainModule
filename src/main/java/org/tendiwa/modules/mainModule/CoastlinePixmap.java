package org.tendiwa.modules.mainModule;

import org.tendiwa.core.Location;
import org.tendiwa.core.Tendiwa;
import org.tendiwa.core.World;
import org.tendiwa.drawing.DrawableInto;
import org.tendiwa.drawing.TestCanvas;
import org.tendiwa.drawing.extensions.DrawingWorld;
import org.tendiwa.drawing.extensions.PieChartTimeProfiler;
import org.tendiwa.geometry.CellSegment;
import org.tendiwa.geometry.Segment2D;
import org.tendiwa.geometry.extensions.CachedCellSet;
import org.tendiwa.groovy.Registry;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.tendiwa.groovy.Registry.floorTypes;

/**
 */
public class CoastlinePixmap implements Runnable {
	private final CoastlineGeometry geometry;
	static PieChartTimeProfiler profiler = new PieChartTimeProfiler(400, TimeUnit.MILLISECONDS);

	public static void main(String[] args) {
//		Demos.run(CoastlinePixmap.class);

		new CoastlinePixmap().run();
	}

	public CoastlinePixmap() {

		profiler.saveTime("Just started");
		this.geometry = new CoastlineGeometry();
		geometry.run();
		profiler.saveTime("Geometry");
	}

	@Override
	public void run() {
		Tendiwa.loadModules();
		DrawableInto canvas = new TestCanvas(1, geometry.worldSize.width, geometry.worldSize.height);
//		DrawableInto canvas = new FakeCanvas();
		World world = new World(geometry.worldSize.width, geometry.worldSize.height);
		Location location = new Location(
			world.getDefaultPlane(),
			0,
			0,
			geometry.worldSize.width,
			geometry.worldSize.height
		);
		CachedCellSet waterCells = new CachedCellSet(
			geometry.water,
			world.asRectangle()
		);
		location.drawCellSet(waterCells, floorTypes.get("water"));
		location.drawCellSet(
			(x, y) -> !waterCells.contains(x, y),
			waterCells.getBounds(),
			floorTypes.get("grass")
		);
		geometry.pathsBetweenCities
			.stream()
			.flatMap(path -> path.stream())
			.forEach((cell) -> {
				if (world.asRectangle().contains(cell)) {
					location.place(floorTypes.get("ground"), cell);
				}
			});
		Consumer<Segment2D> drawRoad = segment ->
			new CellSegment(segment)
				.asList()
				.stream()
				.filter(c -> world.asRectangle().contains(c))
				.forEach(c -> location.place(floorTypes.get("ground"), c));
		for (
			CoastlineCityGeometry city
			: geometry.cities
			) {
			new CoastlineCity(world, drawRoad, city).invoke();
//			new ShitDrawer(world, location, city).invoke();
		}

		canvas.draw(world, DrawingWorld.withColorMap(new MainPlaceableToColor()));
		profiler.saveTime("Cities");
//		profiler.draw();
	}


}

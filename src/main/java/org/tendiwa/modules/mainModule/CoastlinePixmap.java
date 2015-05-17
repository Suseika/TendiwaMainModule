package org.tendiwa.modules.mainModule;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.tendiwa.core.Location;
import org.tendiwa.core.World;
import org.tendiwa.core.worlds.Genesis;
import org.tendiwa.drawing.TestCanvas;
import org.tendiwa.drawing.extensions.PieChartTimeProfiler;
import org.tendiwa.geometry.BoundedCellSet;
import org.tendiwa.geometry.FiniteCellSet;
import org.tendiwa.geometry.extensions.CachedCellSet;
import org.tendiwa.modules.mainModule.ontology.Grass;
import org.tendiwa.modules.mainModule.ontology.Ground;
import org.tendiwa.modules.mainModule.ontology.Water;

import java.util.Collection;
import java.util.Random;


/**
 */
public class CoastlinePixmap implements Genesis {
	private final CoastlineGeometry geometry;
	private final Random random;
	private final World world;
	private final Location location;

	public static void main(String[] args) {
		CoastlineGeometryConfig config = new CoastlineGeometryConfig();
		TestCanvas canvas = new TestCanvas(1, config.worldSize);
		ProgressDrawing progress = new ProgressDrawing(
			config,
			canvas,
			new PieChartTimeProfiler()
		);
		new CoastlinePixmap(
			new CoastlineGeometry(
				config,
				progress
			),
			progress,
			config,
			new Random(0)
		).world();
	}

	@Inject
	public CoastlinePixmap(
		CoastlineGeometry geometry,
		ProgressDrawing progress,
		CoastlineGeometryConfig config,
		@Named("genesis") Random random
	) {
		this.geometry = geometry;
		this.random = random;

		this.world = new World(config.worldSize);
		this.location = new Location(
			world.getDefaultPlane(),
			world.asRectangle()
		);
		BoundedCellSet waterCells = new CachedCellSet(
			geometry.water,
			world.asRectangle()
		);
		placeWater(waterCells);
		placeGrass(waterCells);
		placePathsBetweenCities();
		placeCities();

		progress.drawWorld(world);
	}

	private void placeCities() {
		geometry.cities.stream()
			.map(city -> new CoastlineCity(world, location, city, random))
			.forEach(CoastlineCity::placeContents);
	}

	private void placePathsBetweenCities() {
		this.geometry.pathsBetweenCities.paths()
			.stream()
			.flatMap(Collection::stream)
			.filter(world.asRectangle()::contains)
			.forEach(cell -> location.place(Ground.piece, cell));
	}

	private void placeGrass(BoundedCellSet waterCells) {
		location.drawCellSet(
			(x, y) -> !waterCells.contains(x, y),
			waterCells.getBounds(),
			Grass.piece
		);
	}

	private void placeWater(FiniteCellSet waterCells) {
		location.drawCellSet(waterCells, Water.piece);
	}

	@Override
	public World world() {
		return world;
	}
}

package org.tendiwa.modules.mainModule;

import com.google.inject.util.Modules;
import org.tendiwa.core.Location;
import org.tendiwa.core.Tendiwa;
import org.tendiwa.core.World;
import org.tendiwa.core.worlds.Genesis;
import org.tendiwa.demos.Demos;
import org.tendiwa.drawing.extensions.DrawingModule;
import org.tendiwa.geometry.BoundedCellSet;
import org.tendiwa.geometry.FiniteCellSet;
import org.tendiwa.geometry.extensions.CachedCellSet;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.Random;

import static org.tendiwa.groovy.Registry.floorTypes;

/**
 */
public class CoastlinePixmap implements Genesis {
	private final CoastlineGeometry geometry;
	private final Random random;
	private final World world;
	private final Location location;

	public static void main(String[] args) {
		Demos.genesis(
			CoastlinePixmap.class,
			Modules.override(new DrawingModule()).with(new CoastlineModule())
		);
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

		Tendiwa.loadModules();
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
			.forEach(cell -> location.place(floorTypes.get("ground"), cell));
	}

	private void placeGrass(BoundedCellSet waterCells) {
		location.drawCellSet(
			(x, y) -> !waterCells.contains(x, y),
			waterCells.getBounds(),
			floorTypes.get("grass")
		);
	}

	private void placeWater(FiniteCellSet waterCells) {
		location.drawCellSet(waterCells, floorTypes.get("water"));
	}

	@Override
	public World world() {
		return world;
	}
}

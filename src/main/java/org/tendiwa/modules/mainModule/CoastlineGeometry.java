package org.tendiwa.modules.mainModule;

import org.tendiwa.core.World;
import org.tendiwa.core.worlds.Genesis;
import org.tendiwa.drawing.TestCanvas;
import org.tendiwa.drawing.extensions.PieChartTimeProfiler;
import org.tendiwa.geometry.*;
import org.tendiwa.geometry.extensions.ChebyshovDistanceBufferBorder;
import org.tendiwa.geometry.extensions.ShapeFromOutline;

import java.util.Collection;
import java.util.LinkedHashSet;


public final class CoastlineGeometry implements Genesis {
	private final Rectangle worldSize;
	private final CoastlineGeometryConfig config;
	private final ProgressDrawing progress;

	CellSet water;
	PathNetwork pathsBetweenCities;
	Collection<CityAroundCell> cities = new LinkedHashSet<>();


	public static void main(String[] args) {
		CoastlineGeometryConfig config = new CoastlineGeometryConfig();
		new CoastlineGeometry(
			config,
			new ProgressDrawing(
				config,
				new TestCanvas(1, config.worldSize),
				new PieChartTimeProfiler()
			)
		).run();
	}

	CoastlineGeometry(
		CoastlineGeometryConfig config,
		ProgressDrawing progress
	) {
		this.config = config;
		this.progress = progress;
		this.worldSize = config.worldSize;
	}

	@Override
	public World world() {
		throw new RuntimeException();
	}

	private void run() {
		water = new Water();
		progress.drawTerrain(water);
		Rectangle cityCentersRectangle = config.worldSize.shrink(20);
		cityCenters()
			.stream()
			.filter(cityCentersRectangle::contains)
			.map(cell ->
					new CityAroundCell(
						cell,
						config,
						progress,
						worldSize,
						water
					)
			)
			.forEach(cities::add);
		CellSet citiesCells = cities.stream()
			.map(city -> city.network.outerHull())
			.map(ShapeFromOutline::from)
			.flatMap(BoundedCellSet::stream)
			.collect(CellSet.toCellSet());
		pathsBetweenCities = pathsBetweenCities(citiesCells);
	}

	private PathNetwork pathsBetweenCities(CellSet citiesCells) {
		return new PathNetwork(
			config,
			worldSize,
			citiesCells,
			water
		);
	}

	private DistantCellsFinder cityCenters() {
		return new DistantCellsFinder(
			borderWithCityCenters(),
			config.minDistanceBetweenCityCenters
		);
	}

	private FiniteCellSet borderWithCityCenters() {
		// Find city centers
		CellSet reducingMask = (x, y) -> (x + y) % 200 == 0;
		CellSet cityCenterBorder = new ChebyshovDistanceBufferBorder(
			config.minDistanceFromCoastToCityCenter,
			(x, y) -> worldSize.contains(x, y) && water.contains(x, y)
		);
		FiniteCellSet borderWithCityCenters = new ScatteredCellSet(
			reducingMask.and(cityCenterBorder),
			worldSize
		);
		progress.drawBorderWithCityCenters(borderWithCityCenters);
		return borderWithCityCenters;
	}
}

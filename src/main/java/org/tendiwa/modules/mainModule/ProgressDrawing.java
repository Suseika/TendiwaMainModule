package org.tendiwa.modules.mainModule;

import org.tendiwa.core.World;
import org.tendiwa.core.meta.Cell;
import org.tendiwa.demos.settlements.DrawableCellSet;
import org.tendiwa.drawing.DrawableInto;
import org.tendiwa.drawing.TestCanvas;
import org.tendiwa.drawing.extensions.*;
import org.tendiwa.geometry.BoundedCellSet;
import org.tendiwa.geometry.CellSet;
import org.tendiwa.geometry.FiniteCellSet;
import org.tendiwa.geometry.Rectangle;
import org.tendiwa.geometry.graphs2d.Cycle2D;
import org.tendiwa.settlements.utils.RectangleWithNeighbors;

import javax.inject.Inject;
import java.awt.Color;
import java.util.Set;

final class ProgressDrawing {
	private final CoastlineGeometryConfig config;
	private final DrawableInto canvas;
	private final TimeProfiler profiler;
	private final Color grassColor = Color.green;
	private final Color waterColor = Color.blue;

	@Inject
	ProgressDrawing(
		CoastlineGeometryConfig config,
		DrawableInto canvas,
		TimeProfiler profiler
	) {
		this.config = config;
		this.canvas = canvas;
		this.profiler = profiler;
		TestCanvas.canvas = canvas;
	}

	void drawTerrain(CellSet water) {
		Rectangle worldSize = config.worldSize;
		canvas.drawAll(
			worldSize,
			cell ->
				new DrawableCell(
					cell,
					water.contains(cell) ? waterColor : grassColor
				)
		);
		profiler.saveTime("Draw terrain");
	}

	void drawCityBackground(Cell citySeed, BoundedCellSet cityShape) {
		canvas.draw(
			new DrawableCell(citySeed, Color.black)
		);
		canvas.draw(
			new DrawableCellSet(
				cityShape,
				Color.black
			)
		);
	}

	void drawCityBounds(Cycle2D cityBounds) {
		canvas.draw(cityBounds.graph(), DrawingGraph.withColorAndAntialiasing(Color.red));
	}

	void drawLots(Set<RectangleWithNeighbors> buildingPlaces) {
		canvas.drawAll(
			buildingPlaces,
			DrawingRectangleWithNeighbors.withColorAndDefaultBorder(
				Color.blue,
				Color.magenta
			)
		);
	}

	void drawBorderWithCityCenters(FiniteCellSet borderWithCityCenters) {
		canvas.draw(
			new DrawableCellSet.Finite(
				borderWithCityCenters,
				Color.red
			)
		);
	}

	void drawWorld(World world) {
		canvas.draw(
			world,
			DrawingWorld.withColorMap(new MainPlaceableToColor())
		);
	}
}

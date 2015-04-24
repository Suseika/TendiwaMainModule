package org.tendiwa.modules.mainModule;

import org.tendiwa.core.World;
import org.tendiwa.core.meta.Cell;
import org.tendiwa.demos.settlements.DrawableCellSet;
import org.tendiwa.drawing.Canvas;
import org.tendiwa.drawing.DrawableRectangleWithNeighbors;
import org.tendiwa.drawing.DrawableWorld;
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
	private final Canvas canvas;
	private final TimeProfiler profiler;
	private final Color grassColor = Color.green;
	private final Color waterColor = Color.blue;

	@Inject
	ProgressDrawing(
		CoastlineGeometryConfig config,
		Canvas canvas,
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
		canvas.draw(
			new DrawableGraph2D.Thin(
				cityBounds.graph(),
				Color.red
			)
		);
	}

	void drawLots(Set<RectangleWithNeighbors> buildingPlaces) {
		canvas.drawAll(
			buildingPlaces,
			place -> new DrawableRectangleWithNeighbors(
				place,
				Color.blue,
				Color.blue.darker(),
				Color.magenta,
				Color.magenta.darker()
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
			new DrawableWorld(
				world,
				new MainPlaceableToColor()
			)
		);
	}
}

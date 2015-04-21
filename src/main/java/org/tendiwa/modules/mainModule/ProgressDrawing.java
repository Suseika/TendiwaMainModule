package org.tendiwa.modules.mainModule;

import org.tendiwa.core.World;
import org.tendiwa.drawing.DrawableInto;
import org.tendiwa.drawing.DrawingAlgorithm;
import org.tendiwa.drawing.TestCanvas;
import org.tendiwa.drawing.extensions.*;
import org.tendiwa.geometry.*;
import org.tendiwa.geometry.graphs2d.Cycle2D;
import org.tendiwa.settlements.utils.RectangleWithNeighbors;

import javax.inject.Inject;
import java.awt.Color;
import java.util.Set;

final class ProgressDrawing {
	private final CoastlineGeometryConfig config;
	private final DrawableInto canvas;
	private final TimeProfiler profiler;
	private final DrawingAlgorithm<BasicCell> grassColor = DrawingCell.withColor(Color.green);
	private final DrawingAlgorithm<BasicCell> waterColor = DrawingCell.withColor(Color.blue);

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
		for (BasicCell cell : worldSize) {
			canvas.draw(
				cell,
				water.contains(cell.x, cell.y) ? waterColor : grassColor
			);
		}
		profiler.saveTime("Draw terrain");
	}

	void drawCityBackground(BasicCell citySeed, BoundedCellSet cityShape) {
		canvas.draw(citySeed, DrawingCell.withColorAndSize(Color.black, 6));
		canvas.draw(cityShape, DrawingCellSet.withColor(Color.BLACK));
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
		canvas.draw(borderWithCityCenters, DrawingCellSet.withColor(Color.RED));
	}

	void drawWorld(World world) {
		canvas.draw(
			world,
			DrawingWorld.withColorMap(new MainPlaceableToColor())
		);
	}
}

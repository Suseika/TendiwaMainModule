package org.tendiwa.modules.mainModule;

import org.jgrapht.UndirectedGraph;
import org.tendiwa.core.World;
import org.tendiwa.drawing.DrawableInto;
import org.tendiwa.drawing.DrawingAlgorithm;
import org.tendiwa.drawing.TestCanvas;
import org.tendiwa.drawing.extensions.*;
import org.tendiwa.geometry.*;
import org.tendiwa.settlements.utils.RectangleWithNeighbors;

import javax.inject.Inject;
import java.awt.Color;
import java.util.Set;

final class ProgressDrawing {
	private final CoastlineGeometryConfig config;
	private final DrawableInto canvas;
	private final TimeProfiler profiler;
	private final DrawingAlgorithm<Cell> grassColor = DrawingCell.withColor(Color.green);
	private final DrawingAlgorithm<Cell> waterColor = DrawingCell.withColor(Color.blue);

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
		for (Cell cell : worldSize) {
			canvas.draw(
				cell,
				water.contains(cell.x, cell.y) ? waterColor : grassColor
			);
		}
		profiler.saveTime("Draw terrain");
	}

	void drawCityBackground(Cell citySeed, BoundedCellSet cityShape) {
		canvas.draw(citySeed, DrawingCell.withColorAndSize(Color.black, 6));
		canvas.draw(cityShape, DrawingCellSet.withColor(Color.BLACK));
	}

	void drawCityBounds(UndirectedGraph<Point2D, Segment2D> cityBounds) {
		canvas.draw(cityBounds, DrawingGraph.withColorAndAntialiasing(Color.red));
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

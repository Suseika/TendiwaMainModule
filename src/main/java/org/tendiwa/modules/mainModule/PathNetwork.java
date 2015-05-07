package org.tendiwa.modules.mainModule;

import org.jgrapht.graph.UnmodifiableUndirectedGraph;
import org.tendiwa.core.meta.Cell;
import org.tendiwa.drawing.extensions.TimeProfiler;
import org.tendiwa.geometry.*;
import org.tendiwa.geometry.extensions.CachedCellSet;
import org.tendiwa.geometry.extensions.ChebyshovDistanceBuffer;
import org.tendiwa.geometry.extensions.intershapeNetwork.IntershapeNetwork;
import org.tendiwa.pathfinding.astar.AStar;
import org.tendiwa.pathfinding.astar.MovementCost;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

final class PathNetwork {
	private final CoastlineGeometryConfig config;
	private final Rectangle worldSize;
	private final CellSet water;
	private final Collection<FiniteCellSet> shapeExitsSets = new LinkedList<>();
	private final List<List<Cell>> paths;

	PathNetwork(
		CoastlineGeometryConfig config,
		Rectangle worldSize,
		CellSet citiesCells,
		CellSet water
	) {
		this.config = config;
		this.worldSize = worldSize;
		this.water = water;
		CachedCellSet spaceBetweenCities = spaceBetweenCities(citiesCells);
		UnmodifiableUndirectedGraph<FiniteCellSet, CellSegment> network = IntershapeNetwork
			.withShapeExits(shapeExitsSets)
			.withWalkableCells(spaceBetweenCities);

		MovementCost cost = (
			from,
			to
		) -> (spaceBetweenCities.contains(to) ? 1 : 100000000) * from.diagonalComponent(to);
		paths = network.edgeSet()
			.stream()
			.map(segment -> pathFromStartToEnd(segment, cost))
			.collect(Collectors.toList());
//		pathsBetweenCities = new ArrayList<>(1);
		TimeProfiler.profiler.saveTime("Paths between cities");
//		canvas.draw(cellsCloseToCoast, DrawingCellSet.withColor(Color.PINK));
//		chart.saveTime("Final drawing");
//		chart.draw();
	}

	List<List<Cell>> paths() {
		return paths;
	}

	private CachedCellSet spaceBetweenCities(CellSet citiesCells) {
		CachedCellSet cellsCloseToCoast = cellsCloseToCoast();
		CellSet shapeExitsCombined = shapeExitsSets
			.stream()
			.map(a -> (CellSet) a)
			.reduce(CellSet.empty(), CellSet::or);
		return new CachedCellSet(
			(x, y) ->
				config.worldSize.contains(x, y)
					&& (!water.contains(x, y)
					&& !citiesCells.contains(x, y)
					&& !cellsCloseToCoast.contains(x, y) || shapeExitsCombined.contains(x, y)),
			config.worldSize
		);
	}

	private CachedCellSet cellsCloseToCoast() {
		return new CachedCellSet(
			new ChebyshovDistanceBuffer(
				config.minDistanceFromCoastToCityBorder,
				(x, y) -> worldSize.contains(x, y) && water.contains(x, y)
			),
			worldSize
		);
	}

	private List<Cell> pathFromStartToEnd(CellSegment segment, MovementCost cost) {
		return new AStar(cost).path(segment.start(), segment.end());
	}
}

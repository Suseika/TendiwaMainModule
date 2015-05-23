package org.tendiwa.modules.mainModule;

import org.tendiwa.core.meta.Cell;
import org.tendiwa.geometry.*;
import org.tendiwa.geometry.extensions.CachedCellSet;
import org.tendiwa.geometry.extensions.ChebyshovDistanceBufferBorder;
import org.tendiwa.geometry.graphs2d.Graph2D;
import org.tendiwa.geometry.smartMesh.MeshedNetwork;
import org.tendiwa.geometry.smartMesh.MeshedNetworkBuilder;
import org.tendiwa.pathfinding.dijkstra.PathTable;
import org.tendiwa.settlements.cityBounds.CityBounds;
import org.tendiwa.settlements.utils.RectangleWithNeighbors;
import org.tendiwa.settlements.utils.RectangularBuildingLots;
import org.tendiwa.settlements.utils.streetsDetector.DetectedStreets;

import java.util.Set;
import java.util.stream.Stream;

final class CityAroundCell {
	private final CoastlineGeometryConfig config;
	private final Cell citySeed;
	private final Rectangle worldSize;

	MeshedNetwork network;
	Set<RectangleWithNeighbors> buildingPlaces;
	Stream<Chain2D> streets;
	FiniteCellSet exits;

	CityAroundCell(
		Cell citySeed,
		CoastlineGeometryConfig config,
		ProgressDrawing progress,
		Rectangle worldSize,
		CellSet water
	) {
		this.config = config;
		this.citySeed = citySeed;
		this.worldSize = worldSize;
		BoundedCellSet cityShape = new CityShape(
			new NearbyCoast(
				water
			)
		);
		progress.drawCityBackground(citySeed, cityShape);
		Graph2D outerCycle = CityBounds.create(
			cityShape,
			citySeed,
			cityRadiusModified()
		);
		progress.drawCityBounds(outerCycle);
		network = new MeshedNetworkBuilder(outerCycle)
			.withDefaults()
			.withRoadsFromPoint(2)
			.withDeviationAngle(Math.PI / 30)
			.withSecondaryRoadNetworkDeviationAngle(0.1)
			.withRoadSegmentLength(40)
			.withSnapSize(10)
			.withMaxStartPointsPerCycle(2)
			.build();
		exits = exitsSet();
		buildingPlaces = RectangularBuildingLots.placeInside(network);
		progress.drawLots(buildingPlaces);
		streets = DetectedStreets.toChain2DStream(network);
	}

	private final class CityShape extends BoundedCellSetWr {

		public CityShape(BoundedCellSet coast) {
			super(
				new PathTable(
					citySeed,
					worldSize.without(coast),
					cityRadiusModified()
				).computeFull()
			);
		}
	}


	private final class NearbyCoast extends BoundedCellSetWr {

		NearbyCoast(CellSet cells) {
			super(
				new CachedCellSet(
					new ChebyshovDistanceBufferBorder(
						config.minDistanceFromCoastToCityBorder,
						cells
					),
					cityBoundRectangle()
				)
			);
		}
	}

	private Rectangle cityBoundRectangle() {
		return citySeed.centerRectangle(cityRadiusModified() * 2 + 1, cityRadiusModified() * 2 + 1)
			.intersection(worldSize)
			.get();
	}

	private int cityRadiusModified() {
		return config.maxCityRadius + citySeed.x() % 30 - 15;
	}

	private FiniteCellSet exitsSet() {
		throw new UnsupportedOperationException();
	}

}

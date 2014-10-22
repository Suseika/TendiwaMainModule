package org.tendiwa.modules.mainModule;

import com.google.common.collect.ImmutableList;
import org.jgrapht.UndirectedGraph;
import org.tendiwa.core.World;
import org.tendiwa.geometry.Point2D;
import org.tendiwa.geometry.Segment2D;
import org.tendiwa.settlements.buildings.*;
import org.tendiwa.settlements.streets.LotStreetAssigner;
import org.tendiwa.settlements.streets.Namer;
import org.tendiwa.settlements.utils.RoadRejector;
import org.tendiwa.settlements.utils.StreetsDetector;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

class CoastlineCity {
	private World world;
	private Consumer<Segment2D> drawRoad;
	private CoastlineCityGeometry city;

	public CoastlineCity(World world, Consumer<Segment2D> drawRoad, CoastlineCityGeometry city) {
		this.world = world;
		this.drawRoad = drawRoad;
		this.city = city;
	}

	public void invoke() {
		UndirectedGraph<Point2D, Segment2D> actualRoadGraph = RoadRejector.rejectPartOfNetworksBorders(
			city.roadsPlanarGraphModel.getFullRoadGraph(),
			city.roadsPlanarGraphModel,
			0.5,
			new Random(123445634)
		);
		Set<ImmutableList<Point2D>> streets = StreetsDetector.detectStreets(actualRoadGraph);
		LotsTouchingStreets b2s = new LotsTouchingStreets(streets, 3.3);
		Namer<List<Point2D>> streetNamer = (street) -> "Улица Говна";
		FairLotFacadeAndStreetAssigner assigner = FairLotFacadeAndStreetAssigner.create(b2s);
		LotFacadeAssigner facadeAssigner = assigner;
		LotStreetAssigner streetAssigner = assigner;
		UrbanPlanner urbanPlanner = new UrbanPlanner(world.getDefaultPlane(), 3.3, new Random(565656565));
		urbanPlanner.addAvailableArchitecture(
			new House(),
			new ArchitecturePolicyBuilder().withMinInstancesNoGreaterThan(1).withMaxInstances(5).build()
		);
		urbanPlanner.addAvailableArchitecture(
			new DummyArchitecture(),
			new ArchitecturePolicyBuilder().withMinInstancesNoGreaterThan(1).withMaxInstances(7).build()
		);
		City.builder()
			.addLots(city.buildingPlaces)
			.placeBuildings(urbanPlanner);
		actualRoadGraph.edgeSet().forEach(drawRoad);
		city.roadsPlanarGraphModel.getNetworks().stream()
			.flatMap(cell -> cell.network().edgeSet().stream())
			.forEach(drawRoad);
	}
}

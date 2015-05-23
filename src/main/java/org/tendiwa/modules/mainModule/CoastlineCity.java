package org.tendiwa.modules.mainModule;

import com.google.common.collect.ImmutableSet;
import org.tendiwa.collections.Collectors;
import org.tendiwa.core.Location;
import org.tendiwa.core.World;
import org.tendiwa.geometry.BasicCellSegment;
import org.tendiwa.geometry.Chain2D;
import org.tendiwa.geometry.Point2D;
import org.tendiwa.geometry.Segment2D;
import org.tendiwa.geometry.graphs2d.Graph2D;
import org.tendiwa.geometry.smartMesh.MeshedNetwork;
import org.tendiwa.modules.mainModule.ontology.Ground;
import org.tendiwa.settlements.buildings.*;
import org.tendiwa.settlements.streets.LotStreetAssigner;
import org.tendiwa.settlements.streets.Namer;
import org.tendiwa.settlements.utils.NetworkGraphWithHolesInHull;
import org.tendiwa.settlements.utils.streetsDetector.DetectedStreets;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;


class CoastlineCity {
	public static final double STREETS_WIDTH = 3.3;
	private World world;
	private final Random random;
	private Consumer<Segment2D> drawRoad;
	private CityAroundCell city;

	public CoastlineCity(
		World world,
		Location location,
		CityAroundCell city,
		Random random
	) {
		this.world = world;
		this.random = random;
		this.drawRoad = roadDrawingAlgorithm(world, location);
		this.city = city;
	}

	private Consumer<Segment2D> roadDrawingAlgorithm(World world, Location location) {
		return segment ->
			new BasicCellSegment(segment)
				.asList()
				.stream()
				.filter(c -> world.asRectangle().contains(c))
				.forEach(c -> location.place(Ground.piece, c));
	}

	void placeContents() {
		Graph2D actualRoadGraph = rejectExtraRoads(city.network);
		Set<Chain2D> streets = detectStreets(actualRoadGraph);
		UrbanPlanner urbanPlanner = createUrbanPlanner(streets);
		City.builder()
			.addLots(city.buildingPlaces)
			.placeBuildings(urbanPlanner);
		actualRoadGraph.edgeSet().forEach(drawRoad);
		drawRoads();
	}

	private ImmutableSet<Chain2D> detectStreets(Graph2D actualRoadGraph) {
		return DetectedStreets
			.toChain2DStream(actualRoadGraph)
			.collect(Collectors.toImmutableSet());
	}

	private Graph2D rejectExtraRoads(MeshedNetwork mesh) {
		return new NetworkGraphWithHolesInHull(
			mesh,
			0.5,
			random
		);
	}

	private UrbanPlanner createUrbanPlanner(Set<Chain2D> streets) {
		PolylineProximity b2s = new PolylineProximity(streets, city.buildingPlaces, STREETS_WIDTH);
		Namer<List<Point2D>> streetNamer = (street) -> "Улица Говна";
		FairLotFacadeAndStreetAssigner assigner = FairLotFacadeAndStreetAssigner.create(b2s);
		LotFacadeAssigner facadeAssigner = assigner;
		LotStreetAssigner streetAssigner = assigner;
		UrbanPlanner urbanPlanner = new UrbanPlanner(
			world.getDefaultPlane(),
			STREETS_WIDTH,
			facadeAssigner,
			random
		);
		urbanPlanner.addAvailableArchitecture(
			new House(),
			new ArchitecturePolicyBuilder().withMinInstancesNoGreaterThan(100).build()
		);
		urbanPlanner.addAvailableArchitecture(
			new DummyArchitecture(),
			new ArchitecturePolicyBuilder().build()
		);
		return urbanPlanner;
	}

	private void drawRoads() {
		city.network.edgeSet().forEach(drawRoad);
	}
}

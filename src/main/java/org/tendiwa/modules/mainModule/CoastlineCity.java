package org.tendiwa.modules.mainModule;

import com.google.common.collect.ImmutableSet;
import org.jgrapht.UndirectedGraph;
import org.tendiwa.collections.Collectors;
import org.tendiwa.core.Location;
import org.tendiwa.core.World;
import org.tendiwa.geometry.*;
import org.tendiwa.geometry.smartMesh.SmartMesh2D;
import org.tendiwa.settlements.buildings.*;
import org.tendiwa.settlements.streets.LotStreetAssigner;
import org.tendiwa.settlements.streets.Namer;
import org.tendiwa.settlements.utils.RoadRejector;
import org.tendiwa.settlements.utils.streetsDetector.DetectedStreets;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;

import static org.tendiwa.groovy.Registry.floorTypes;

class CoastlineCity {
	public static final double STREETS_WIDTH = 3.3;
	private World world;
	private final Random random;
	private Consumer<Segment2D> drawRoad;
	private CoastlineCityGeometry city;

	public CoastlineCity(
		World world,
		Location location,
		CoastlineCityGeometry city,
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
				.forEach(c -> location.place(floorTypes.get("ground"), c));
	}

	void placeContents() {
		UndirectedGraph<Point2D, Segment2D> actualRoadGraph = rejectExtraRoads(city.mesh);
		Set<Chain2D> streets = detectStreets(actualRoadGraph);
		UrbanPlanner urbanPlanner = createUrbanPlanner(streets);
		City.builder()
			.addLots(city.buildingPlaces)
			.placeBuildings(urbanPlanner);
		actualRoadGraph.edgeSet().forEach(drawRoad);
		drawRoads();
	}

	private ImmutableSet<Chain2D> detectStreets(UndirectedGraph<Point2D, Segment2D> actualRoadGraph) {
		return DetectedStreets
			.toChain2DStream(actualRoadGraph)
			.collect(Collectors.toImmutableSet());
	}

	private UndirectedGraph<Point2D, Segment2D> rejectExtraRoads(SmartMesh2D mesh) {
		return RoadRejector.rejectPartOfNetworksBorders(
			mesh.graph(),
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
		city.mesh.networks().stream()
			.flatMap(cell -> cell.network().edgeSet().stream())
			.forEach(drawRoad);
	}
}

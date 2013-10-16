package tendiwa.modules;

import tendiwa.core.*;
import tendiwa.core.Directions;
import tendiwa.core.EnhancedRectangle;
import tendiwa.core.PathSegment;
import tendiwa.core.RectangleSystem;
import tendiwa.locationFeatures.FeatureForest;
import tendiwa.resources.FloorTypes;
import tendiwa.resources.ObjectTypes;

import java.util.Set;

import static tendiwa.core.DSL.*;

public class TestLocationDrawer implements LocationDrawer {
@Override
public boolean meetsRequirements(Set<LocationFeature> features) {
	return features.stream().anyMatch(f -> f instanceof FeatureForest);
}

@Override
public void draw(Location location, tendiwa.core.LocationPlace place) {
	int width = place.width;
	int height = place.height;
	location.square(0, 0, width, height, FloorTypes.grass, true);
	location.square(1, 1, width - 2, height - 2, ObjectTypes.wall_grey_stone, false);
	location.line(1, 1, width - 3, height - 3, ObjectTypes.wall_grey_stone);
	location.line(width - 3, 1, 1, height - 3, ObjectTypes.wall_grey_stone);
	RectangleSystem rs = builder(8)
		.place(rectangle(10, 20), atPoint(26, 22))
		.place(rectangle(6, 7), near(LAST_RECTANGLE).fromSide(S).inMiddle())
		.place(recursivelySplitRec(20, 20).minWidth(2).borderWidth(1), unitedWith(LAST_RECTANGLE).fromSide(W).align(N))
		.done();
	for (EnhancedRectangle r : rs) {
		location.fillRectangle(r, ObjectTypes.wall_grey_stone);
	}
	EnhancedRectangle rectangle = new EnhancedRectangle(20, 20, 20, 30);
	location
		.diffusionBuilder()
		.setDepth(4)
		.setRectangle(rectangle)
		.setFrom(ObjectTypes.wall_grey_stone)
		.setTo(FloorTypes.water)
		.addFromDirection(Directions.NE)
		.addFromDirection(Directions.S)
		.build();
	// Diffusion with oceans
	int diffusionRadius = 8;
	for (CardinalDirection dir : CardinalDirection.values()) {
		for (EnhancedRectangle neighbor : rs.getNeighborsFromSide(place, dir)) {
			place.getCommonSidePiece(neighbor).createRectangle(diffusionRadius);
		}
	}
	for (LocationNeighborship neighborship : place.getNeighborships()) {
		EnhancedRectangle neighbor = neighborship.getPlace();
		CardinalDirection side = neighborship.getSide();
		location.diffusionBuilder()
			.setDepth(diffusionRadius)
			.addFromDirection(neighborship.getSide())
			.setFrom(FloorTypes.water)
			.setTo(FloorTypes.grass)
			.build();
	}
}

@Override
public boolean canHandlePaths(Set<PathSegment> paths) {
	return true;
}
}

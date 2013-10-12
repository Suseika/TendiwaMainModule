package tendiwa.modules;

import tendiwa.core.*;
import tendiwa.geometry.LocationPlace;
import tendiwa.geometry.PathSegment;
import tendiwa.locationFeatures.FeatureOcean;

import java.util.Set;

public class Ocean implements LocationDrawer {
@Override
public boolean meetsRequirements(Set<LocationFeature> features) {
	return features.stream().anyMatch((f) -> f instanceof FeatureOcean);
}

@Override
public void draw(Location location, LocationPlace place) {
	int water = StaticData.getFloorType("water").getId();
	int width = place.getRectangle().width;
	int height = place.getRectangle().height;
	location.square(0, 0, width, height, TerrainBasics.ELEMENT_OBJECT, water);
}

@Override
public boolean canHandlePaths(Set<PathSegment> paths) {
	return paths.isEmpty();
}
}

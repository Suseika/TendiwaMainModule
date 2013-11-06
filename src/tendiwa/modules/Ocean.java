package tendiwa.modules;

import tendiwa.core.*;
import tendiwa.core.LocationPlace;
import tendiwa.core.PathSegment;
import tendiwa.locationFeatures.FeatureOcean;
import tendiwa.resources.TerrainTypes;

import java.util.Set;

public class Ocean implements LocationDrawer {
@Override
public boolean meetsRequirements(Set<LocationFeature> features) {
//	return features.stream().anyMatch((f) -> f instanceof FeatureOcean);
	for(LocationFeature feature : features) {
		if (feature instanceof FeatureOcean) {
			return true;
		}
	}
	return false;
}

@Override
public void draw(Location location, LocationPlace place) {
	int width = place.width;
	int height = place.height;
	location.square(0, 0, width, height, TerrainTypes.water, true);
}

@Override
public boolean canHandlePaths(Set<PathSegment> paths) {
	return paths.isEmpty();
}
}

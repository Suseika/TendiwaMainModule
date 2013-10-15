package tendiwa.modules;

import tendiwa.core.Location;
import tendiwa.core.LocationDrawer;
import tendiwa.core.LocationFeature;
import tendiwa.geometry.LocationPlace;
import tendiwa.geometry.PathSegment;
import tendiwa.locationFeatures.Road;
import tendiwa.locationFeatures.VolcanicTerrain;
import tendiwa.resources.ObjectTypes;

import java.util.Set;

public class Forest implements LocationDrawer {

@Override
public void draw(Location location, LocationPlace place) {
	int width = place.getRectangle().width;
	int height = place.getRectangle().height;
	location.line(1, 1, width - 3, height - 3, ObjectTypes.wall_grey_stone);
	location.line(width - 3, 1, 1, height - 3, ObjectTypes.wall_grey_stone);
}

@Override
public boolean meetsRequirements(Set<LocationFeature> features) {
	return features.stream().allMatch(
		f -> !(f instanceof VolcanicTerrain)
	);
}

@Override
public boolean canHandlePaths(Set<PathSegment> paths) {
	return paths.size() <= 2
		&& paths.stream().allMatch(ps -> ps.getType() == Road.class);
}

}

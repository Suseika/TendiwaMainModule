package tendiwa.modules;

import tendiwa.core.*;
import tendiwa.geometry.PathSegment;
import tendiwa.locationFeatures.Road;
import tendiwa.locationFeatures.VolcanicTerrain;

import java.util.Set;

public class Forest implements LocationDrawer {

@Override
public void draw(Location location, LocationPlace place) {
	int greyWall = StaticData.getObjectType("grey_wall").getId();
//	location.line(1, 1, width - 3, height - 3, ELEMENT_OBJECT, greyWall);
//	location.line(width - 3, 1, 1, height - 3, ELEMENT_OBJECT, greyWall);

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

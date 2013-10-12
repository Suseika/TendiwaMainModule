package tendiwa.modules;

import tendiwa.core.*;
import tendiwa.geometry.LocationPlace;
import tendiwa.geometry.PathSegment;

import java.util.Set;

public class TestLocationDrawer implements LocationDrawer {
@Override
public boolean meetsRequirements(Set<LocationFeature> features) {
	return true;
}

@Override
public void draw(Location location, LocationPlace place) {
	System.out.println(place.getRectangle());
	int grey_wall = StaticData.getObjectType("wall_grey_stone").getId();
	int grass = StaticData.getFloorType("grass").getId();
	int width = place.getRectangle().width;
	int height = place.getRectangle().height;
	location.square(0, 0, width, height, TerrainBasics.ELEMENT_FLOOR, grass, true);
	location.square(1, 1, width - 2, height - 2, TerrainBasics.ELEMENT_OBJECT, grey_wall, false);
}

@Override
public boolean canHandlePaths(Set<PathSegment> paths) {
	return true;
}
}

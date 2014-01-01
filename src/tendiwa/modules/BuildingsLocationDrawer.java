package tendiwa.modules;

import org.tendiwa.entities.FloorTypes;
import org.tendiwa.entities.WallTypes;
import tendiwa.core.*;

import java.util.Set;

public class BuildingsLocationDrawer implements LocationDrawer {
@Override
public boolean meetsRequirements(Set<LocationFeature> features) {
	return true;
}

@Override
public void draw(Location location, LocationPlace place) {
	location.square(0, 0, location.getWidth(), location.getHeight(), FloorTypes.GROUND, true);
	EnhancedRectangle rectangle = new EnhancedRectangle(2, 3, 10, 11);
	location.square(rectangle, WallTypes.GREY_STONE, false);
	location.square(rectangle, FloorTypes.STONE, true);
	location.place(WallTypes.VOID, rectangle.getPointOnSide(CardinalDirection.S, 2));

}

@Override
public boolean canHandlePaths(Set<PathSegment> paths) {
	return true;
}
}

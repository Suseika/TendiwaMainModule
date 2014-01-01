package tendiwa.modules;

import org.tendiwa.entities.FloorTypes;
import org.tendiwa.entities.ObjectTypes;
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
	EnhancedPoint pointOnSide = rectangle.getPointOnSide(CardinalDirection.S, 2);
	location.place(WallTypes.VOID, pointOnSide);
	location.place(
		ObjectTypes.LADDER_UP,
		rectangle
			.getPointOnSide(CardinalDirection.S, 3)
			.moveToSide(CardinalDirection.N)
	);
	location.changePlane(1);
	EnhancedRectangle smallerRectangle = rectangle.shrink(1);
	location.square(smallerRectangle, WallTypes.GREY_STONE, false);
	location.square(smallerRectangle, FloorTypes.STONE, true);

}

@Override
public boolean canHandlePaths(Set<PathSegment> paths) {
	return true;
}
}

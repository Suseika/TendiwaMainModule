package tendiwa.modules;

import tendiwa.core.Location;
import tendiwa.core.LocationDrawer;
import tendiwa.core.LocationFeature;
import tendiwa.drawing.TestCanvas;
import tendiwa.geometry.*;
import tendiwa.resources.FloorTypes;
import tendiwa.resources.ObjectTypes;

import java.util.Set;

import static tendiwa.geometry.DSL.*;

public class TestLocationDrawer implements LocationDrawer {
@Override
public boolean meetsRequirements(Set<LocationFeature> features) {
	return true;
}

@Override
public void draw(Location location, LocationPlace place) {
	int width = place.getRectangle().width;
	int height = place.getRectangle().height;
	location.square(0, 0, width, height, FloorTypes.grass, true);
	location.square(1, 1, width - 2, height - 2, ObjectTypes.wall_grey_stone, false);
	location.line(1, 1, width - 3, height - 3, ObjectTypes.wall_grey_stone);
	location.line(width - 3, 1, 1, height - 3, ObjectTypes.wall_grey_stone);
	RectangleSystem rs = builder(8)
		.place(rectangle(10, 20), atPoint(40, 22))
		.place(rectangle(6, 7), near(LAST_RECTANGLE).fromSide(S).inMiddle())
		.place(recursivelySplitRec(20, 50).minWidth(5).borderWidth(2), unitedWith(LAST_RECTANGLE).fromSide(W).align(N))
		.done();
	for (EnhancedRectangle r : rs) {
		location.fillRectangle(r, ObjectTypes.wall_grey_stone);
	}
	EnhancedRectangle rectangle = new EnhancedRectangle(70, 50, 20, 30);
	location
		.diffusionBuilder()
		.setDepth(4)
		.setRectangle(rectangle)
		.setFrom(ObjectTypes.wall_grey_stone)
		.setTo(FloorTypes.water)
		.addFromDirection(Directions.NE)
		.addFromDirection(Directions.S)
		.build();
}

@Override
public boolean canHandlePaths(Set<PathSegment> paths) {
	return true;
}
}

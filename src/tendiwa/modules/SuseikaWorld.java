package tendiwa.modules;

import tendiwa.core.WorldDrawer;

import static tendiwa.geometry.DSL.*;

import tendiwa.geometry.WorldRectangleBuilder;
import tendiwa.locationFeatures.Forest;
public class SuseikaWorld implements WorldDrawer {


@Override
public void draw(WorldRectangleBuilder builder, int width, int height) {
	builder
		.place(rectangle(width / 2, height), atPoint(0, 0))
		.place(rectangle(width - width / 2, height), near(LAST_RECTANGLE).fromSide(E).align(N))
		.setLocationFeatures(0, Forest.class);

}

}

package tendiwa.modules;

import tendiwa.core.WorldDrawer;
import tendiwa.geometry.EnhancedRectangle;
import tendiwa.geometry.WorldRectangleBuilder;
import tendiwa.locationFeatures.FeatureOcean;
import tendiwa.locationFeatures.Forest;

import static tendiwa.geometry.DSL.*;

public class SuseikaWorld implements WorldDrawer {

@Override
public void draw(WorldRectangleBuilder builder, int width, int height) {
	EnhancedRectangle worldRectangle = new EnhancedRectangle(0, 0, width, height);
	builder
		.place(recursivelySplitRec(width, height).minWidth(120).borderWidth(0), atPoint(0,0))
		.findAllRectangles((rec, rs, rsb) -> rec.touchesFromInside(worldRectangle))
		.setLocationFeatures(FOUND_RECTANGLES, new FeatureOcean())
		.findAllRectangles((rec, rs, rsb) -> !rec.touchesFromInside(worldRectangle))
		.setLocationFeatures(FOUND_RECTANGLES, new Forest());
}

}

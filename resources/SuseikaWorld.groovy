import tendiwa.core.*
import tendiwa.locationFeatures.FeatureForest
import tendiwa.locationFeatures.FeatureOcean

import static tendiwa.core.DSL.*

public class SuseikaWorld implements WorldDrawer {

    @Override
    public void drawWorld(WorldRectangleBuilder b, int width, int height) {
        final EnhancedRectangle worldRectangle = new EnhancedRectangle(0, 0, width, height);
        b
                .place(recursivelySplitRec(width, height).minWidth(400).borderWidth(0), atPoint(0, 0))
                .findAllRectangles(new FindCriteria() {
            @Override
            public boolean check(EnhancedRectangle rectangle, RectangleSystem rs, RectangleSystemBuilder builder) {
                return rectangle.touchesFromInside(worldRectangle);
            }
        })
                .setLocationFeatures(FOUND_RECTANGLES, new FeatureOcean())
                .findAllRectangles(new FindCriteria() {
            @Override
            public boolean check(EnhancedRectangle rectangle, RectangleSystem rs, RectangleSystemBuilder builder) {
                return !rectangle.touchesFromInside(worldRectangle);
            }
        })
                .setLocationFeatures(FOUND_RECTANGLES, new FeatureForest());
    }

}

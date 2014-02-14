import org.tendiwa.core.EnhancedRectangle
import org.tendiwa.core.FindCriteria
import org.tendiwa.core.RectangleSystem
import org.tendiwa.core.RectangleSystemBuilder
import org.tendiwa.core.WorldDrawer
import org.tendiwa.core.WorldRectangleBuilder
import tendiwa.core.*
import org.tendiwa.locationFeatures.FeatureForest
import org.tendiwa.locationFeatures.FeatureOcean

import static org.tendiwa.core.DSL.*

public class SuseikaWorld implements WorldDrawer {

    @Override
    public void drawWorld(WorldRectangleBuilder b, int width, int height) {
        final EnhancedRectangle worldRectangle = new EnhancedRectangle(0, 0, width, height);
        b
                .place(recursivelySplitRec(width, height).minWidth(80).borderWidth(0), atPoint(0, 0))
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

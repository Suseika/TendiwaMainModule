import org.tendiwa.core.Directions
import org.tendiwa.core.EnhancedRectangle
import org.tendiwa.core.Location
import org.tendiwa.core.LocationDrawer
import org.tendiwa.core.LocationFeature
import org.tendiwa.core.LocationPlace
import org.tendiwa.core.PathSegment
import org.tendiwa.core.RectangleSystem
import tendiwa.core.*
import org.tendiwa.locationFeatures.FeatureForest

import static org.tendiwa.groovy.DSL.*
import static org.tendiwa.core.DSL.*

public class TestLocationDrawer implements LocationDrawer {
    @Override
    public boolean meetsRequirements(Set<LocationFeature> features) {
        for (LocationFeature feature : features) {
            if (feature instanceof FeatureForest) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(Location location, LocationPlace place) {
        int width = place.width;
        int height = place.height;
        location.square(0, 0, width, height, floorTypes.grass, true);
//	location.square(0, 0, width, height, WallTypes.wall_grey_stone, false);
        location.square(40, 15, 15, 15, floorTypes.stone, true);
        HelperCoastline.INSTANCE.draw(location, place);
//	location.square(1, 1, width - 2, height - 2, ObjectTypes.wall_grey_stone, false);
//	location.line(1, 1, width - 3, height - 3, ObjectTypes.wall_grey_stone);
//	location.line(width - 3, 1, 1, height - 3, ObjectTypes.wall_grey_stone);
        RectangleSystem rs = builder(8)
                .place(rectangle(10, 20), atPoint(26, 22))
                .place(rectangle(6, 7), near(LAST_RECTANGLE).fromSide(S).inMiddle())
                .place(recursivelySplitRec(20, 20).minWidth(2).borderWidth(1), unitedWith(LAST_RECTANGLE).fromSide(W).align(N))
                .done();
        for (EnhancedRectangle r : rs) {
            location.fillRectangle(r, wallTypes.grey_stone_wall);
        }
        EnhancedRectangle rectangle = new EnhancedRectangle(20, 20, 20, 30);
        location
                .transitionBuilder()
                .setDepth(8)
                .setRectangle(rectangle)
                .setFrom(itemTypes.short_bow)
                .addFromDirection(Directions.NE)
                .addFromDirection(Directions.S)
                .build();
    }

    @Override
    public boolean canHandlePaths(Set<PathSegment> paths) {
        return true;
    }
}

import org.tendiwa.core.CardinalDirection
import org.tendiwa.core.EnhancedPoint
import org.tendiwa.core.EnhancedRectangle
import org.tendiwa.core.Location
import org.tendiwa.core.LocationDrawer
import org.tendiwa.core.LocationFeature
import org.tendiwa.core.LocationPlace
import org.tendiwa.core.PathSegment
import tendiwa.core.*

import static org.tendiwa.groovy.DSL.*

public class BuildingsLocationDrawer implements LocationDrawer {
    @Override
    public boolean meetsRequirements(Set<LocationFeature> features) {
        return true;
    }

    @Override
    public void draw(Location location, LocationPlace place) {
        location.square(0, 0, location.getWidth(), location.getHeight(), floorTypes.ground, true);
        EnhancedRectangle rectangle = new EnhancedRectangle(2, 3, 10, 11);
        location.squareOfThin(rectangle, borderObjectTypes.wall_grey_stone);
        location.square(rectangle, floorTypes.stone, true);
        EnhancedPoint pointOnSide = rectangle.getPointOnSide(CardinalDirection.S, 2);
        def ladderCell = rectangle
                .getPointOnSide(CardinalDirection.S, 3)
                .moveToSide(CardinalDirection.N, 2)
        location.place(objectTypes.ladder, ladderCell);
        location.changePlane(1);
        location.square(0, 0, location.getWidth(), location.getHeight(), floorTypes.ground, true);
        EnhancedRectangle smallerRectangle = rectangle.shrink(1);
        location.square(rectangle, floorTypes.grass, true);
        location.square(smallerRectangle, wallTypes.wall_grey_stone, false);
        location.square(smallerRectangle, floorTypes.stone, true);
        location.place(objectTypes.ladder, ladderCell);

    }

    @Override
    public boolean canHandlePaths(Set<PathSegment> paths) {
        return true;
    }
}

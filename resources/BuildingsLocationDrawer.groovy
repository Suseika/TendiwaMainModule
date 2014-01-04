import tendiwa.core.*;
import static org.tendiwa.groovy.DSL.*;

import java.util.Set;

public class BuildingsLocationDrawer implements LocationDrawer {
    @Override
    public boolean meetsRequirements(Set<LocationFeature> features) {
        return true;
    }

    @Override
    public void draw(Location location, LocationPlace place) {
        location.square(0, 0, location.getWidth(), location.getHeight(), floorTypes.ground, true);
        EnhancedRectangle rectangle = new EnhancedRectangle(2, 3, 10, 11);
        location.square(rectangle, wallTypes.greyStone, false);
        location.square(rectangle, floorTypes.stone, true);
        EnhancedPoint pointOnSide = rectangle.getPointOnSide(CardinalDirection.S, 2);
        location.place(wallTypes.void, pointOnSide);
        location.place(
                objectTypes.ladder,
                rectangle
                        .getPointOnSide(CardinalDirection.S, 3)
                        .moveToSide(CardinalDirection.N)
        );
        location.changePlane(1);
        EnhancedRectangle smallerRectangle = rectangle.shrink(1);
        location.square(smallerRectangle, wallTypes.greyStone, false);
        location.square(smallerRectangle, floorTypes.stone, true);

    }

    @Override
    public boolean canHandlePaths(Set<PathSegment> paths) {
        return true;
    }
}

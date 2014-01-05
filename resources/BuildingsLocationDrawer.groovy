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
        location.square(rectangle, wallTypes.wall_grey_stone, false);
        location.square(rectangle, floorTypes.stone, true);
        EnhancedPoint pointOnSide = rectangle.getPointOnSide(CardinalDirection.S, 2);
        location.place(wallTypes.void, pointOnSide);
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

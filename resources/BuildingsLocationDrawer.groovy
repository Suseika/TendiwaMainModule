import org.tendiwa.core.*
import org.tendiwa.geometry.EnhancedRectangle

import static org.tendiwa.groovy.DSL.*

public class BuildingsLocationDrawer implements LocationDrawer {
    private EnhancedRectangle rectangle

    @Override
    public boolean meetsRequirements(Set<LocationFeature> features) {
        return true;
    }

    @Override
    public void draw(Location location, LocationPlace place) {
        location.square(0, 0, location.getWidth(), location.getHeight(), floorTypes.ground, true);
        rectangle = new EnhancedRectangle(4, 7, 13, 8)
        location.squareOfThin(rectangle, borderObjectTypes.wall_grey_stone);
        location.lineOfThin(rectangle.getSideAsSidePiece(Directions.S), borderObjectTypes.void);
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

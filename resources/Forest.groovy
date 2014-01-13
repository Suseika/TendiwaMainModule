import org.tendiwa.core.Location
import org.tendiwa.core.LocationDrawer
import org.tendiwa.core.LocationFeature
import org.tendiwa.core.LocationPlace
import org.tendiwa.core.PathSegment
import tendiwa.core.*

import static org.tendiwa.groovy.DSL.getWallTypes;

public class Forest implements LocationDrawer {

    @Override
    public void draw(Location location, LocationPlace place) {
        int width = place.width;
        int height = place.height;
        location.line(1, 1, width - 3, height - 3, wallTypes.greyStone);
        location.line(width - 3, 1, 1, height - 3, wallTypes.greyStone);
    }

    @Override
    public boolean meetsRequirements(Set<LocationFeature> features) {
        return false;
    }

    @Override
    public boolean canHandlePaths(Set<PathSegment> paths) {
        return paths.size() <= 2;
//		&& paths.stream().allMatch(ps -> ps.getType() == Road.class);
    }

}

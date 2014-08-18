package org.tendiwa.modules.mainModule

import org.tendiwa.core.Location
import org.tendiwa.core.LocationDrawer
import org.tendiwa.core.LocationFeature
import org.tendiwa.core.LocationPlace
import org.tendiwa.core.PathSegment
import org.tendiwa.groovy.DSL
import org.tendiwa.locationFeatures.FeatureOcean

import static org.tendiwa.groovy.DSL.getFloorTypes;

public class Ocean implements LocationDrawer {
    @Override
    public boolean meetsRequirements(Set<LocationFeature> features) {
//	return features.stream().anyMatch((f) -> f instanceof FeatureOcean);
        for (LocationFeature feature : features) {
            if (feature instanceof FeatureOcean) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(Location location, LocationPlace place) {
        int width = place.width;
        int height = place.height;
        location.square(0, 0, width, height, DSL.floorTypes.water, true);
    }

    @Override
    public boolean canHandlePaths(Set<PathSegment> paths) {
        return paths.isEmpty();
    }
}

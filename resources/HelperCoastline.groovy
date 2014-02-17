import org.tendiwa.geometry.EnhancedRectangle
import org.tendiwa.core.Location
import org.tendiwa.core.LocationFeature
import org.tendiwa.core.LocationHelper
import org.tendiwa.core.LocationNeighborship
import org.tendiwa.core.LocationPlace
import tendiwa.core.*
import org.tendiwa.locationFeatures.FeatureOcean

import static org.tendiwa.groovy.DSL.getFloorTypes;

public enum HelperCoastline implements LocationHelper {
    INSTANCE;

    @Override
    public void draw(Location location, LocationPlace place) {
        location.square(5, 5, 5, 5, floorTypes.water);
        // Transition to oceans
        int diffusionRadius = 10;
        for (LocationNeighborship neighborship : place.getNeighborships()) {
            boolean foundOcean = false;
            for (LocationFeature feature : neighborship.getPlace().getFeatures()) {
                if (feature instanceof FeatureOcean) {
                    foundOcean = true;
                    break;
                }
            }
            if (!foundOcean) {
                continue;
            }
            if (neighborship.getLength() <= diffusionRadius * 2) {
                continue;
            }
            EnhancedRectangle borderRectangle = place
                    .getRectangleInFrontOfNeighbor(neighborship, diffusionRadius);
            location.transitionBuilder()
                    .setRectangle(borderRectangle)
                    .setDepth(diffusionRadius)
                    .addFromDirection(neighborship.getSide())
                    .setFrom(floorTypes.water)
                    .build();

        }
    }
}

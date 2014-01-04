
import tendiwa.core.HorizontalPlane;
import tendiwa.core.Settlement;
import tendiwa.core.terrain.settlements.BuildingPlace;
import static org.tendiwa.groovy.DSL.*;

public class Village extends Settlement {
    public Village(HorizontalPlane plane, int x, int y, int width, int height) {
        super(plane, x, y, width, height);
//		fillWithCells(TerrainTypes.grass);
        createRandomRoadSystem();
        roadSystem.createRoad(width / 2, 0, width / 2, height - 1);
        roadSystem.drawRoads(floorTypes.ground);
        quarterSystem.build(roadSystem.getReferencePoints());
        for (BuildingPlace place : quarterSystem.buildingPlaces) {
//			placeBuilding(place, Inn.class);
        }
    }
}

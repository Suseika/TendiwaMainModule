package org.tendiwa.modules.mainModule

import org.tendiwa.core.HorizontalPlane;
import org.tendiwa.core.settlements.Settlement;
import org.tendiwa.core.settlements.BuildingPlace
import org.tendiwa.groovy.DSL;

import static org.tendiwa.groovy.DSL.*;

public class Village extends Settlement {
    public Village(HorizontalPlane plane, int x, int y, int width, int height) {
        super(plane, x, y, width, height);
//		fillWithCells(TerrainTypes.grass);
        createRandomRoadSystem();
        roadSystem.createRoad(width / 2, 0, width / 2, height - 1);
        roadSystem.drawRoads(DSL.floorTypes.ground);
        quarterSystem.build(roadSystem.getReferencePoints());
        for (BuildingPlace place : quarterSystem.buildingPlaces) {
//			placeBuilding(place, Inn.class);
        }
    }
}

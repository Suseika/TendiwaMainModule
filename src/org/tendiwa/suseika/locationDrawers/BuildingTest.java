package org.tendiwa.suseika.locationDrawers;

import tendiwa.core.*;
import tendiwa.core.meta.Coordinate;
import tendiwa.core.terrain.settlements.BuildingPlace;
import tendiwa.resources.FloorTypes;
import tendiwa.resources.ObjectTypes;

public class BuildingTest extends Settlement {
public BuildingTest(HorizontalPlane plane, int x, int y, int width, int height) {
	super(plane, x, y, width, height);

	int buildingSizeX = 18;
	int buildingSizeY = 19;

	fillWithCells(FloorTypes.grass, ObjectType.VOID);
	// -2 and +5 are because of road.width
	Coordinate nw = new Coordinate((width - buildingSizeX) / 2 - 2, (height - buildingSizeY) / 2 - 2);
	Coordinate ne = new Coordinate(nw.x + buildingSizeX + 5, nw.y);
	Coordinate se = new Coordinate(nw.x + buildingSizeX + 5, nw.y + buildingSizeY + 5);
	Coordinate sw = new Coordinate(nw.x, nw.y + buildingSizeY + 5);
	roadSystem.createRoad(nw.x, nw.y, ne.x, ne.y);
	roadSystem.createRoad(ne.x, ne.y, se.x, se.y);
	roadSystem.createRoad(se.x, se.y, sw.x, sw.y);
	roadSystem.createRoad(sw.x, sw.y, nw.x, nw.y);
	roadSystem.drawRoads(FloorTypes.ground);
	quarterSystem.build(roadSystem.getReferencePoints());
	for (BuildingPlace place : quarterSystem.buildingPlaces) {
		if (place.contains(nw.x + buildingSizeX / 2, nw.y + buildingSizeY / 2)) {
//			placeBuilding(place, House.class, Directions.getRandomCardinal());
			break;
		}
	}
}
}

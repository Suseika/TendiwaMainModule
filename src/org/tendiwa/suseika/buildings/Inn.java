package org.tendiwa.suseika.buildings;

import tendiwa.core.*;
import tendiwa.core.terrain.settlements.BuildingPlace;
import tendiwa.resources.FloorTypes;
import tendiwa.resources.ObjectTypes;

public class Inn extends Building {
public static final long serialVersionUID = 11672547L;

public Inn(BuildingPlace bp, tendiwa.core.CardinalDirection side) {
	super(bp, side);
}

public void draw() {
	int wallGreyStone = StaticData.getObjectType("wall_gray_stone").getId();

	tendiwa.core.RectangleSystem crs = new tendiwa.core.RectangleSystem(1);
		/* BASIS */
	// Lobby
	tendiwa.core.Orientation dir;
	tendiwa.core.CardinalDirection side = tendiwa.core.Directions.S;
	int lobbyWidth = 5;
	if (side == tendiwa.core.Directions.N || side == tendiwa.core.Directions.S) {
		dir = tendiwa.core.Orientation.HORIZONTAL;
	} else {
		dir = tendiwa.core.Orientation.VERTICAL;
	}
	tendiwa.core.EnhancedRectangle rightRooms = crs.addRectangle(new tendiwa.core.EnhancedRectangle(x, y, width, height));
	// Separate middle rectangle (lobby) and left rectangle, get left rooms
	tendiwa.core.EnhancedRectangle leftRooms = crs.cutRectangleFromSide(rightRooms, side.clockwiseQuarter(), ((((dir.isHorizontal()) ? width
			: height) - lobbyWidth) / 2 - 1));
	// Separate middle rectangle (lobby) and right rectangle, get lobby
	tendiwa.core.EnhancedRectangle lobby = crs.cutRectangleFromSide(rightRooms, side.clockwiseQuarter(), lobbyWidth);

	// Separate rectangle above lobby
	tendiwa.core.EnhancedRectangle aboveLobby = crs.cutRectangleFromSide(lobby, side.opposite(), 4);
	// Left hall
	tendiwa.core.EnhancedRectangle leftHall = crs.cutRectangleFromSide(leftRooms, side.counterClockwiseQuarter(), 2);
	// Right hall
	tendiwa.core.EnhancedRectangle rightHall = crs.cutRectangleFromSide(rightRooms, side.clockwiseQuarter(), 2);
	// 1 - left rooms, 4 - left hall, 3 - above middle, 2 - middle, 5 -
	// right hall, 0 - right rooms
	// crs.cutRectangleFromSide(rightRoomsId, side, 1);
	// int firstSideRoom = 6;
	// Place left rooms and link them with left hall
	while (dir.isHorizontal() && leftRooms.height > 5 || dir.isVertical() && leftRooms.width > 5) {
		crs.cutRectangleFromSide(leftRooms, side, 4);
	}
	// Place right rooms and link them with right hall
	while (dir.isHorizontal() && rightRooms.height > 5 || dir.isVertical() && rightRooms.width > 5) {
		crs.cutRectangleFromSide(rightRooms, side, 4);
	}
	// Link last room

	terrainModifier = settlement.getTerrainModifier(crs);
	lobby.stretch(side, -1);
	buildBasis(FloorTypes.stone, ObjectTypes.wall_grey_stone);

	placeFrontDoor(lobby, side);

		/* CONTENTS */
	// Rectangle lobbyRec = terrainModifier.content.get(lobbyId);
	// for (int i=firstSideRoom, size = crs.rectangles.size();i<size;i++) {
	// ArrayList<Coordinate> cells =
	// getCellsNearWalls(crs.rectangles.get(i));
	// }
}

public boolean fitsToPlace(BuildingPlace place) {
	return (place.width > 23 || place.height > 23);
}
}

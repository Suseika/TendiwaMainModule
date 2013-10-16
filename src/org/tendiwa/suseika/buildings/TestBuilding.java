package org.tendiwa.suseika.buildings;

import tendiwa.core.Building;
import tendiwa.core.StaticData;
import tendiwa.core.terrain.settlements.BuildingPlace;
import tendiwa.core.CardinalDirection;
import tendiwa.resources.FloorTypes;
import tendiwa.resources.ObjectTypes;

public class TestBuilding extends Building {
	protected TestBuilding(BuildingPlace bp, CardinalDirection side) {
		super(bp, side);
	}
	public static final long serialVersionUID = 346347;
	public void draw() {
		int wallGreyStone = StaticData.getObjectType("wall_gray_stone").getId();
		
		getTerrainModifier(4);
		buildBasis(FloorTypes.stone, ObjectTypes.wall_grey_stone);
		for (CardinalDirection side : doorSides) {
			placeFrontDoor(side);
		}
	}
	@Override
	public boolean fitsToPlace(BuildingPlace place) {
		// TODO Auto-generated method stub
		return true;
	}
}

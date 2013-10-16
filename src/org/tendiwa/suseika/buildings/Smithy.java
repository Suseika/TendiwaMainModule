package org.tendiwa.suseika.buildings;

import tendiwa.core.Building;
import tendiwa.core.terrain.settlements.BuildingPlace;
import tendiwa.core.CardinalDirection;
import tendiwa.core.EnhancedRectangle;
import tendiwa.core.RectangleSystem;
import tendiwa.resources.FloorTypes;
import tendiwa.resources.ObjectTypes;

public class Smithy extends Building {

protected Smithy(BuildingPlace bp, CardinalDirection side) {
	super(bp, side);
}

public void draw() {
	RectangleSystem crs = new RectangleSystem(1);
	CardinalDirection side = CardinalDirection.S;
	EnhancedRectangle initialRec = crs.addRectangle(new EnhancedRectangle(x, y, width, height));
	EnhancedRectangle r1 = crs.cutRectangleFromSide(initialRec, side, 5);
	EnhancedRectangle r2 = crs.cutRectangleFromSide(r1, side.clockwiseQuarter(), 5);
	crs.excludeRectangle(r2);
	terrainModifier = settlement.getTerrainModifier(crs);
	buildBasis(FloorTypes.stone, ObjectTypes.wall_grey_stone);
}

@Override
public boolean fitsToPlace(BuildingPlace place) {
	// TODO Auto-generated method stub
	return true;
}
}

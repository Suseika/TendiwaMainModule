package org.tendiwa.modules.mainModule;

import org.tendiwa.core.CardinalDirection;
import org.tendiwa.core.Location;
import org.tendiwa.geometry.*;
import org.tendiwa.groovy.Registry;
import org.tendiwa.settlements.buildings.Architecture;
import org.tendiwa.settlements.buildings.BuildingFeatures;
import org.tendiwa.settlements.buildings.BuildingTag;

public class House implements Architecture {

	@Override
	public void draw(BuildingFeatures features, CardinalDirection front, Location location) {
		Rectangle bounds = location.getRelativeBounds();
		Rectangle buildingRec = bounds.shrink(3);
		Cell doorCell = buildingRec.getSideAsSidePiece(front).getCellInside(front.left(), 3);
		Cell pathStart = doorCell.moveToSide(front);
		Cell pathEnd = pathStart.moveToSide(front, 3);
		FiniteCellSet path = FiniteCellSet.of(CellSegment.vector(pathStart, pathEnd));

		location.square(buildingRec, Registry.wallTypes.get("grey_stone_wall"), false);
		location.place(Registry.wallTypes.get("void"), doorCell);
		location.fillRectangle(buildingRec, Registry.floorTypes.get("stone"));
		location.drawCellSet(path, Registry.floorTypes.get("ground"));

		features.setLocalizationId("suseika_housey");
		features.addRoom(buildingRec.shrink(1));
	}

	@Override
	public boolean fits(Rectangle rectangle) {
		return rectangle.width >= 9 && rectangle.height >= 9;
	}

	@Override
	public Rectangle typicalBuildingPlace() {
		return new Rectangle(0, 0, 17, 13);
	}

	@Override
	public BuildingTag[] tags() {
		return new BuildingTag[]{BuildingTags.RESIDENT};
	}
}

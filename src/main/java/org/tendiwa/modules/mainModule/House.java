package org.tendiwa.modules.mainModule;

import org.tendiwa.core.CardinalDirection;
import org.tendiwa.core.Location;
import org.tendiwa.core.meta.Cell;
import org.tendiwa.geometry.BasicCellSegment;
import org.tendiwa.geometry.FiniteCellSet;
import org.tendiwa.geometry.Rectangle;
import org.tendiwa.modules.mainModule.ontology.GreyStoneWall;
import org.tendiwa.modules.mainModule.ontology.Ground;
import org.tendiwa.modules.mainModule.ontology.StoneFloor;
import org.tendiwa.settlements.buildings.Architecture;
import org.tendiwa.settlements.buildings.BuildingFeatures;
import org.tendiwa.settlements.buildings.BuildingTag;
import org.tendiwa.settlements.utils.RectangleWithNeighbors;

import static org.tendiwa.geometry.GeometryPrimitives.rectangle;

public class House implements Architecture {

	@Override
	public void draw(BuildingFeatures features, CardinalDirection front, Location location) {
		Rectangle bounds = location.getRelativeBounds();
		Rectangle buildingRec = bounds.shrink(3);
		Cell doorCell = buildingRec.side(front).getCell(front.left(), 3);
		Cell pathStart = doorCell.moveToSide(front);
		Cell pathEnd = pathStart.moveToSide(front, 3);
		FiniteCellSet path = FiniteCellSet.of(BasicCellSegment.cells(pathStart, pathEnd));

		location.square(buildingRec, GreyStoneWall.piece, false);
		location.removeWall(doorCell);
		location.fillRectangle(buildingRec, StoneFloor.piece);
		location.drawCellSet(path, Ground.piece);

		features.setLocalizationId("suseika_housey");
		features.addRoom(buildingRec.shrink(1));
	}

	@Override
	public boolean fits(RectangleWithNeighbors place) {
		return place.mainRectangle().width() >= 9 && place.mainRectangle().height() >= 9;
	}

	@Override
	public Rectangle typicalBuildingPlace() {
		return rectangle(0, 0, 17, 13);
	}

	@Override
	public BuildingTag[] tags() {
		return new BuildingTag[]{BuildingTags.RESIDENT};
	}
}

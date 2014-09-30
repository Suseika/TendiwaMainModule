package org.tendiwa.modules.mainModule;

import org.tendiwa.core.CardinalDirection;
import org.tendiwa.core.Location;
import org.tendiwa.geometry.Rectangle;
import org.tendiwa.groovy.Registry;
import org.tendiwa.settlements.RectangleWithNeighbors;
import org.tendiwa.settlements.buildings.Architecture;
import org.tendiwa.settlements.buildings.BuildingFeatures;
import org.tendiwa.settlements.buildings.BuildingTag;

public class DummyArchitecture implements Architecture {
	@Override
	public void draw(BuildingFeatures features, CardinalDirection front, Location location) {
		location.fillRectangle(location.getRelativeBounds().shrink(1), Registry.wallTypes.get("grey_stone_wall"));
	}

	@Override
	public boolean fits(RectangleWithNeighbors lot) {
		return lot.rectangle.width >= 3 && lot.rectangle.height >= 3;
	}

	@Override
	public Rectangle typicalBuildingPlace() {
		return new Rectangle(0, 0, 10, 10);
	}

	@Override
	public BuildingTag[] tags() {
		return new BuildingTag[0];
	}
}

package org.tendiwa.modules.mainModule;

import org.tendiwa.core.CardinalDirection;
import org.tendiwa.core.Location;
import org.tendiwa.geometry.Rectangle;
import org.tendiwa.groovy.Registry;
import org.tendiwa.settlements.buildings.Architecture;
import org.tendiwa.settlements.buildings.BuildingFeatures;
import org.tendiwa.settlements.buildings.BuildingTag;
import org.tendiwa.settlements.utils.RectangleWithNeighbors;

import static org.tendiwa.geometry.GeometryPrimitives.rectangle;

public final class DummyArchitecture implements Architecture {
	@Override
	public void draw(BuildingFeatures features, CardinalDirection front, Location location) {
		location.fillRectangle(location.getRelativeBounds().shrink(1), Registry.wallTypes.get("wall_grey_stone"));
	}

	@Override
	public boolean fits(RectangleWithNeighbors lot) {
		return lot.mainRectangle().width() >= 3 && lot.mainRectangle().height() >= 3;
	}

	@Override
	public Rectangle typicalBuildingPlace() {
		return rectangle(0, 0, 10, 10);
	}

	@Override
	public BuildingTag[] tags() {
		return new BuildingTag[0];
	}
}

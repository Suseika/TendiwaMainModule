package org.tendiwa.modules.mainModule;

import org.tendiwa.drawing.extensions.PlaceableToColorMap;
import org.tendiwa.groovy.Registry;

import java.awt.Color;

public final class MainPlaceableToColor extends PlaceableToColorMap {
	public MainPlaceableToColor() {
		setColor(Registry.floorTypes.get("grass"), Color.green);
		setColor(Registry.floorTypes.get("water"), Color.blue);
		setColor(Registry.floorTypes.get("ground"), Color.orange);
		setColor(Registry.floorTypes.get("stone"), Color.lightGray);
		setColor(Registry.wallTypes.get("wall_grey_stone"), Color.darkGray);
	}
}

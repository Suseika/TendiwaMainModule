package org.tendiwa.modules.mainModule;

import org.tendiwa.drawing.extensions.PlaceableToColorMap;
import org.tendiwa.modules.mainModule.ontology.*;
import org.tendiwa.modules.mainModule.ontology.Water;

import java.awt.Color;

public final class MainPlaceableToColor extends PlaceableToColorMap {
	public MainPlaceableToColor() {
		setColor(Grass.piece, Color.green);
		setColor(Water.piece, Color.blue);
		setColor(Ground.piece, Color.orange);
		setColor(StoneFloor.piece, Color.lightGray);
		setColor(GreyStoneWall.piece, Color.darkGray);
	}
}

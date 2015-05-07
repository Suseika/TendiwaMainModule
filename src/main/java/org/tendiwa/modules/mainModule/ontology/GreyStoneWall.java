package org.tendiwa.modules.mainModule.ontology;

import org.tendiwa.core.ObjectType;
import org.tendiwa.core.WallType;

public enum GreyStoneWall implements WallType {
	piece;

	@Override
	public ObjectType getType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isUsable() {
		return false;
	}

	@Override
	public String getResourceName() {
		return "grey_stone_wall";
	}
}

package org.tendiwa.modules.mainModule.ontology;

import org.tendiwa.core.FloorType;

public enum Grass implements FloorType {
	piece;
	@Override
	public boolean isLiquid() {
		return false;
	}

	@Override
	public String getResourceName() {
		return "grass";
	}
}

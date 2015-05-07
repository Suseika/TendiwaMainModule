package org.tendiwa.modules.mainModule.ontology;

import org.tendiwa.core.FloorType;

public enum Water implements FloorType {
	piece;

	@Override
	public boolean isLiquid() {
		return true;
	}

	@Override
	public String getResourceName() {
		return "water";
	}
}

package org.tendiwa.modules.mainModule;

import org.tendiwa.settlements.buildings.BuildingTag;

public enum BuildingTags implements BuildingTag {
	MUNDANE,
	RESIDENT,
	LUXURY,
	SHOP,
	MAGIC;


	@Override
	public String getLocalizationId() {
		return toString().toLowerCase();
	}
}

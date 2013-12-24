package org.tendiwa.items;

import org.tendiwa.entities.Materials;
import tendiwa.core.AmmunitionType;
import tendiwa.core.Material;
import tendiwa.core.Shootable;

public class WoodenArrow implements Shootable {
@Override
public Material getMaterial() {
	return Materials.wood;
}

@Override
public String getResourceName() {
	return "wooden_arrow";
}

@Override
public double getWeight() {
	return 1;
}

@Override
public double getVolume() {
	return 1;
}

@Override
public boolean isStackable() {
	return true;
}

@Override
public AmmunitionType getAmmunitionType() {
	return AmmunitionTypes.arrow;
}

@Override
public String getLocalizationId() {
	return getResourceName();
}
}

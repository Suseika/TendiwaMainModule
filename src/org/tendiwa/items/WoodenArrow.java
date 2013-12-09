package org.tendiwa.items;

import org.tendiwa.entities.Materials;
import tendiwa.core.Material;
import tendiwa.core.StackableItemType;

public class WoodenArrow extends StackableItemType {
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
}

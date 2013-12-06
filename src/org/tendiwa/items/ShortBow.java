package org.tendiwa.items;

import org.tendiwa.entities.Materials;
import tendiwa.core.*;

public class ShortBow extends UniqueItemType implements Wieldable {
public ShortBow() {
}

@Override
public Material getMaterial() {
	return Materials.wood;
}

@Override
public String getResourceName() {
	return "short_bow";
}


@Override
public double getWeight() {
	return 20;
}

@Override
public double getVolume() {
	return 200;
}

@Override
public Handedness getHandedness() {
	return Handedness.TWO_HANDS;
}
}
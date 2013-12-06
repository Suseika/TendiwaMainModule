package org.tendiwa.items;

import com.google.common.collect.ImmutableSet;
import org.tendiwa.entities.Materials;
import tendiwa.core.ApparelSlot;
import tendiwa.core.Material;
import tendiwa.core.UniqueItemType;
import tendiwa.core.Wearable;

import java.util.Collection;

public class IronHelm extends UniqueItemType implements Wearable {
ImmutableSet<ApparelSlot> slots = ImmutableSet.of(ApparelSlot.HEADGEAR);

public IronHelm() {
}

@Override
public Material getMaterial() {
	return Materials.iron;
}

@Override
public String getResourceName() {
	return "iron_helm";
}

@Override
public double getWeight() {
	return 100;
}

@Override
public double getVolume() {
	return 100;
}

@Override
public Collection<ApparelSlot> getSlots() {
	return slots;
}
}

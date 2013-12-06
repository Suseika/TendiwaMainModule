package org.tendiwa.items;

import com.google.common.collect.ImmutableSet;
import org.tendiwa.entities.Materials;
import tendiwa.core.ApparelSlot;
import tendiwa.core.Material;
import tendiwa.core.UniqueItemType;
import tendiwa.core.Wearable;

import java.util.Collection;

public class IronArmor extends UniqueItemType implements Wearable {
ImmutableSet<ApparelSlot> of = ImmutableSet.of(ApparelSlot.OUTER_BODYWEAR);

@Override
public Material getMaterial() {
	return Materials.iron;
}

@Override
public String getResourceName() {
	return "iron_armor";
}

@Override
public double getWeight() {
	return 300;
}

@Override
public double getVolume() {
	return 300;
}

@Override
public Collection<ApparelSlot> getSlots() {
	return of;
}
}

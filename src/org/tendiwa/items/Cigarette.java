package org.tendiwa.items;

import org.tendiwa.entities.Materials;
import tendiwa.core.*;
import tendiwa.core.Character;

public class Cigarette implements Consumable {
@Override
public boolean canConsume(tendiwa.core.Character consumer) {
	return false;
}

@Override
public void onConsume(Character consumer) {

}

@Override
public Material getMaterial() {
	return Materials.paper;
}

@Override
public double getWeight() {
	return 0.01;
}

@Override
public double getVolume() {
	return 0.01;
}

@Override
public boolean isStackable() {
	return true;
}

@Override
public String getLocalizationId() {
	return "cigarette";
}

@Override
public String getResourceName() {
	return "cigarette";
}
}

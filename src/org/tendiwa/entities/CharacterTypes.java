package org.tendiwa.entities;

import com.google.common.collect.ImmutableSet;
import tendiwa.core.CharacterAbility;
import tendiwa.core.CharacterAspect;
import tendiwa.core.CharacterType;

import java.util.Collection;

public class CharacterTypes {
public static final CharacterType human = new CharacterType("human", 70, 170, CharacterAspect.HUMANOID) {
	@Override
	public Collection<CharacterAbility> getAvailableActions() {
		return ImmutableSet.<CharacterAbility>of(CharacterAbilities.JUMP, CharacterAbilities.SHOUT);
	}

	@Override
	public int getMaxHp() {
		return 77;
	}
};
public static final CharacterType bear = new CharacterType("bear", 70, 170, CharacterAspect.ANIMAL) {
	@Override
	public Collection<CharacterAbility> getAvailableActions() {
		return ImmutableSet.of();
	}

	@Override
	public int getMaxHp() {
		return 121;
	}
};

}

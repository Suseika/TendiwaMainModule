package org.tendiwa.entities;

import com.google.common.collect.ImmutableSet;
import tendiwa.core.CharacterAction;
import tendiwa.core.CharacterAspect;
import tendiwa.core.CharacterType;

import java.util.Collection;

public class Characters {
public static final CharacterType human = new CharacterType("human", 70, 170, CharacterAspect.HUMANOID) {
	@Override
	public Collection<CharacterAction> getAvailableActions() {
		return ImmutableSet.of(CharacterActions.jump);
	}
};

}

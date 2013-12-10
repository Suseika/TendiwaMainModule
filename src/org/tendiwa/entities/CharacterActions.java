package org.tendiwa.entities;

import org.tendiwa.events.EventSound;
import tendiwa.core.*;
import tendiwa.core.Character;

public class CharacterActions {
public static CharacterAction jump = new ActionToCell() {
	@Override
	public void act(Character actor, int x, int y) {
		actor.move(x, y, MovingStyle.LEAP);
	}

	@Override
	public String getResourceName() {
		return "jump";
	}
};
public static CharacterAction shout = new ActionWithoutTarget() {
	@Override
	public void act(Character actor) {
		actor.getTimeStream().makeSound(actor.getX(), actor.getY(), Sounds.shout);
		Tendiwa.getClientEventManager().event(new EventSound(Sounds.shout, actor.getX(), actor.getY()));
	}

	@Override
	public String getResourceName() {
		return null;
	}
};
}

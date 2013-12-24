package org.tendiwa.entities;

import org.tendiwa.events.EventSound;
import tendiwa.core.*;
import tendiwa.core.Character;

public enum CharacterAbilities implements CharacterAbility {
	JUMP {
		@Override
		public ActionToCell getAction() {
			return new ActionToCell() {
				@Override
				public void act(Character actor, int x, int y) {
					actor.move(x, y, MovingStyle.LEAP);
				}
			};
		}

		@Override
		public String getResourceName() {
			return "jump";
		}
	},
	SHOUT {
		@Override
		public ActionWithoutTarget getAction() {
			return new ActionWithoutTarget() {
				@Override
				public void act(Character actor) {
					actor.getTimeStream().makeSound(actor.getX(), actor.getY(), Sounds.shout, actor);
				}
			};
		}

		@Override
		public String getResourceName() {
			return "shout";
		}
	}
}

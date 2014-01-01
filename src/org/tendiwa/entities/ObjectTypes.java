package org.tendiwa.entities;

import com.google.common.collect.ImmutableSet;
import tendiwa.core.*;
import tendiwa.core.Character;

import java.util.Collection;

public enum ObjectTypes implements ObjectType, Usable {
	LADDER_UP {
		private Collection<? extends ActionTargetType> actions = ImmutableSet.of(
			new ActionWithoutTarget() {
				@Override
				public void act(Character actor) {
					actor.getTimeStream().makeSound(actor.getX(), actor.getY(), Sounds.shout, actor);
				}
			},
			new ActionToCell() {
				@Override
				public void act(Character actor, int x, int y) {
					synchronized (Character.renderLockObject) {
						EntityPlacer.place(actor.getPlane(), ItemsTypes.ironArmor, x, y);
					}
					Tendiwa.waitForAnimationToStartAndComplete();
				}
			}
		);

		@Override
		public Passability getPassability() {
			return Passability.FREE;
		}

		@Override
		public ObjectClass getObjectClass() {
			return ObjectClass.DEFAULT;
		}

		@Override
		public String getResourceName() {
			return "ladder";
		}

		@Override
		public Collection<? extends ActionTargetType> getActions() {
			return actions;
		}
	}

}

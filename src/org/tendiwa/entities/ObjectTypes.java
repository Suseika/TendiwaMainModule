package org.tendiwa.entities;

import com.google.common.collect.ImmutableSet;
import org.tendiwa.events.EventItemAppear;
import org.tendiwa.events.EventProjectileFly;
import tendiwa.core.*;
import tendiwa.core.Character;

import java.util.Collection;

public enum ObjectTypes implements ObjectType, Usable {
	LADDER_UP {
		private Collection<? extends CharacterAbility> actions = ImmutableSet.of(
			new CharacterAbility<ActionWithoutTarget>() {

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
					return "objects.actions.ladder.sound";
				}
			},
			new CharacterAbility<ActionToCell>() {

				@Override
				public ActionToCell getAction() {
					return new ActionToCell() {
						@Override
						public void act(Character actor, int x, int y) {
							System.out.println("пизда");
							UniqueItem uniqueItem = new UniqueItem(ItemsTypes.ironHelm);
							synchronized (Character.renderLockObject) {
								actor.getPlane().addItem(uniqueItem, x, y);
								Tendiwa.getClientEventManager().event(new EventItemAppear(uniqueItem, x, y));
							}
							Tendiwa.waitForAnimationToStartAndComplete();
						}
					};
				}

				@Override
				public String getResourceName() {
					return "objects.actions.ladder.item";
				}
			}
		);

		@Override
		public Passability getPassability() {
			return Passability.FREE;
		}

		@Override
		public String getResourceName() {
			return "ladder";
		}

		@Override
		public Collection<? extends CharacterAbility> getUsages() {
			return actions;
		}
	}

}

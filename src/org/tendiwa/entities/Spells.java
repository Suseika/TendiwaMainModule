package org.tendiwa.entities;

import org.tendiwa.events.EventExplosion;
import org.tendiwa.events.EventProjectileFly;
import tendiwa.core.Character;
import tendiwa.core.*;

public enum Spells implements Spell {
	BLINK() {
		@Override
		public SpellToCell getAction() {
			return new SpellToCell() {
				@Override
				public void effect(Character caster, int x, int y) {
					caster.move(x, y, MovingStyle.BLINK);
				}
			};
		}

		@Override
		public int getManaPointsRequired() {
			return 2;
		}

		@Override
		public String getResourceName() {
			return "blink";
		}

	},
	FIREBALL() {
		@Override
		public SpellTargetType getAction() {
			return new SpellToCell() {
				@Override
				public void effect(Character caster, int x, int y) {
					synchronized (Character.renderLockObject) {
						Tendiwa.getClientEventManager().event(new EventProjectileFly(
							new SpellProjectile(getResourceName()),
							caster.getX(),
							caster.getY(),
							x,
							y,
							EventProjectileFly.FlightStyle.PROPELLED
						));
					}
					Tendiwa.waitForAnimationToStartAndComplete();
					synchronized (Character.renderLockObject) {
						Tendiwa.getClientEventManager().event(new EventExplosion(x, y));
					}
				}
			};
		}

		@Override
		public int getManaPointsRequired() {
			return 5;
		}

		@Override
		public String getResourceName() {
			return "fireball";
		}

	};
}

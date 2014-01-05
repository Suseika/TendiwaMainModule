import org.tendiwa.events.EventExplosion
import org.tendiwa.events.EventItemAppear
import org.tendiwa.events.EventProjectileFly
import tendiwa.core.*

import static org.tendiwa.groovy.DSL.*

newSoundType {
    name "shout"
    bass 30
    mid 90
    treble 40
}
newCharacterAbility {
    name "shout"
    action([act: { Character actor ->
        actor.getTimeStream().makeSound(actor.getX(), actor.getY(), soundTypes.shout, actor)
    }] as ActionWithoutTarget)
}
newCharacterAbility {
    name "objects.actions.ladder.sound";
    action([act: { Character actor ->
        actor.getTimeStream().makeSound(actor.getX(), actor.getY(), soundTypes.shout, actor);
    }] as ActionWithoutTarget)
}
newCharacterAbility {
    name "objects.actions.ladder.item";
    action([act: { Character actor, int x, int y ->
        UniqueItem uniqueItem = new UniqueItem(itemTypes.iron_helm);
        synchronized (Character.renderLockObject) {
            actor.getPlane().addItem(uniqueItem, x, y);
            Tendiwa.getClientEventManager().event(new EventItemAppear(uniqueItem, x, y));
        }
        Tendiwa.waitForAnimationToStartAndComplete();
    }] as ActionToCell)
}
newCharacterAbility {
    name "objects.actions.ladder.go_up"
    action ([act: { Character actor ->
        actor.moveByPlane(1);
    }] as ActionWithoutTarget)
}
newCharacterAbility {
    name "objects.actions.ladder.go_down"
    action ([act: { Character actor ->
        actor.moveByPlane(-1);
    }] as ActionWithoutTarget)
}
newCharacterAbility {
    name "jump"
    action([act: { Character actor, int x, int y ->
        actor.move(x, y, MovingStyle.LEAP);
    }] as ActionToCell)
}
newCharacterType {
    weight 100
    height 170
    name "human"
    aspects(CharacterAspect.HUMANOID, CharacterAspect.ROBOT)
    maxHp 200
    actions(characterAbilities.shout, characterAbilities.jump)
}
newCharacterType {
    weight 201
    height 202
    name "bear"
    aspects(CharacterAspect.ANIMAL)
    maxHp 300
}
newSpell {
    name "blink"
    mana 2
    action([act: { Character caster, int x, int y ->
        caster.move(x, y, MovingStyle.BLINK);
    }] as ActionToCell)
}
newSpell {
    name "fireball"
    mana 5
    action([act: { Character caster, int x, int y ->
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
    }] as ActionToCell)
}
newObjectType {
    name "ladder"
    passability Passability.NO
    action characterAbilities["objects.actions.ladder.sound"]
    action characterAbilities["objects.actions.ladder.item"]
    action characterAbilities["objects.actions.ladder.go_up"]
    action characterAbilities["objects.actions.ladder.go_down"]
}
newFloorType {
    name "water"
    liquid true
}
newFloorType {
    name "grass"
    liquid false
}
newFloorType {
    name "stone"
    liquid false
}
newFloorType {
    name "dry_grass"
    liquid false
}
newFloorType {
    name "ground"
    liquid false
}
newFloorType {
    name "snow"
    liquid false
}
newFloorType {
    name "wood"
    liquid false
}
newFloorType {
    name "swamp"
    liquid false
}
newFloorType {
    name "emptiness"
    liquid false
}
newWallType {
    name "wall_grey_stone"
}
newWallType {
    name "wooden_wall"
}
newWallType {
    name "red_stone_wall"
}
newWallType {
    name "lattice"
}
newWallType {
    name "cave"
}
newMaterial {
    name "iron"
    durability 100
    density 7800
}
newMaterial {
    name "steel"
    durability 100
    density 7800
}
newMaterial {
    name "wood"
    durability 100
    density 520
}
newMaterial {
    name "flesh"
    durability 100
    density 976
}
newMaterial {
    name "leather"
    durability 100
    density 760
}
newMaterial {
    name "paper"
    durability 1
    density 1
}
newAmmunitionType {
    name "arrow"
}
newItemType {
    name "short_bow"
    material materials.wood
    weight 20
    volume 200
    isStackable false
    handedness Handedness.TWO_HANDS
    ammunitionUsed ammunitionTypes.arrow
}
newItemType {
    name "iron_armor"
    material materials.iron
    weight 300
    volume 300
    isStackable false
    slots ApparelSlot.OUTER_BODYWEAR
}
newItemType {
    name "iron_helm"
    material materials.iron
    weight 100
    volume 100
    isStackable false
    slots ApparelSlot.HEADGEAR
}
newItemType {
    name "wooden_arrow"
    material materials.wood
    weight 1
    volume 1
    isStackable true
    ammunitionType ammunitionTypes.arrow
}
newItemType {
    name "cigarette"
    material materials.paper
    weight 0.01
    volume 0.01
    isStackable true
}

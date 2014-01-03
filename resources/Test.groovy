import tendiwa.core.CharacterAspect
import tendiwa.core.MovingStyle


def actions = [
        jump: { actor, x, y ->
            actor.move(x, y, MovingStyle.LEAP)
        },
        shout: { actor ->
            actor.getTimeStream().makeSound(sounds.shout)
        }
]
def characters = [
        human: [
                name: "human",
                weight: 70,
                height: 170,
                aspects: [CharacterAspect.HUMANOID],
                maxHp: 77,
                actions: [
                        actions.jump,
                        actions.shout
                ]

        ]
]

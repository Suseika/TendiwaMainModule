import tendiwa.core.CharacterAspect
import static org.tendiwa.groovy.DSL.*;

characterType {
    weight 100
    height 170
    name "penisman"
    aspects (CharacterAspect.HUMANOID, CharacterAspect.ROBOT)
}
characterType {
    weight 201
    height 202
    name "bear"
    aspects (CharacterAspect.ANIMAL)
}
human = characters.penisman
println human.weight
println human.height
println human.name
println human.aspects

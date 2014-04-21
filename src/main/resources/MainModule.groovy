import com.google.inject.Inject
import org.tendiwa.core.*;
import org.tendiwa.core.WorldProvidingModule;
import org.tendiwa.core.factories.CharacterFactory
import org.tendiwa.core.factories.NpcFactory
import org.tendiwa.core.factories.WorldFactory
import org.tendiwa.core.meta.Condition
import org.tendiwa.core.player.SinglePlayerMode
import org.tendiwa.drawing.*

import static org.tendiwa.groovy.DSL.*

public class MainModule extends Module implements WorldProvidingModule {

    private final CharacterFactory characterFactory
    private final WorldFactory worldFactory
    private final SinglePlayerMode singlePlayer
    private final NpcFactory npcFactory

    @Inject
    public MainModule(
            CharacterFactory characterFactory,
            NpcFactory npcFactory,
            WorldFactory worldFactory,
            SinglePlayerMode singlePlayer
    ) {
        this.npcFactory = npcFactory
        this.singlePlayer = singlePlayer
        this.worldFactory = worldFactory
        this.characterFactory = characterFactory

//	ResourcesRegistry.registerDrawer(new TestLocationDrawer());
//	ResourcesRegistry.registerDrawer(new Forest());

        ResourcesRegistry.registerDrawer(new Ocean());
        ResourcesRegistry.registerDrawer(new BuildingsLocationDrawer());
    }

    public static void main(String[] args) {
        Tendiwa.loadModules();
        Module mainModule = Tendiwa.getMainModule();
        TestCanvas canvas = canvas(2);
        Tendiwa.createWorld(mainModule);
//        canvas.draw(.getWorld());

    }

    @Override
    public World createWorld() {
        World world = worldFactory.create(new SuseikaWorld(), 400, 300);
        Character playerCharacter = characterFactory.create(159, 111, characters.human, "Suseika");
        world.getDefaultPlane().addCharacter(playerCharacter);
        singlePlayer.setPlayerCharacter(playerCharacter, world)
        def bear1 = npcFactory.create(159, 117, characters.bear, "mishka")
        def bear2 = npcFactory.create(158, 114, characters.bear, "mishka")
        world.getDefaultPlane().addCharacter(bear1)
        world.getDefaultPlane().addCharacter(bear2)
//        playerCharacter.getItem(itemTypes.short_bow);
//        playerCharacter.getItem(itemTypes.short_bow);
//        playerCharacter.getItem(itemTypes.short_bow);
//        playerCharacter.getItem(itemTypes.short_bow);
//        playerCharacter.getItem(itemTypes.short_bow);
//        playerCharacter.getItem(itemTypes.short_bow);

        playerCharacter.getItem(itemTypes.short_bow);
        playerCharacter.getItem(itemTypes.iron_armor);
        playerCharacter.getItem(itemTypes.wooden_arrow, 10);
        playerCharacter.getItem(itemTypes.iron_helm);
        playerCharacter.learnSpell(spells.fireball);
        playerCharacter.learnSpell(spells.blink);
        playerCharacter.wield(playerCharacter.getInventory().getItem(new Condition<Item>() {
            @Override
            public boolean check(Item item) {
                return item.getType() == itemTypes.short_bow;
            }
        }));
        return world;
    }
}

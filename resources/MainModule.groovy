import com.google.inject.Inject
import org.tendiwa.core.*
import org.tendiwa.core.factories.CharacterFactory
import org.tendiwa.core.factories.WorldFactory
import org.tendiwa.core.meta.Condition
import org.tendiwa.core.player.SinglePlayerMode
import org.tendiwa.drawing.*
import org.tendiwa.modules.DrawingWorld

import java.awt.*

import static org.tendiwa.core.DSL.canvas
import static org.tendiwa.groovy.DSL.*

public class MainModule extends Module implements WorldProvidingModule {

    private final CharacterFactory characterFactory
    private final WorldFactory worldFactory
    private final SinglePlayerMode singlePlayer

    @Inject
    public MainModule(
            CharacterFactory characterFactory,
            WorldFactory worldFactory,
            SinglePlayerMode singlePlayer
    ) {
        this.singlePlayer = singlePlayer
        this.worldFactory = worldFactory
        this.characterFactory = characterFactory
        DefaultDrawingAlgorithms.register(EnhancedRectangle.class, DrawingRectangle.withColorLoop(Color.GRAY, Color.BLACK, Color.BLUE));
        DefaultDrawingAlgorithms.register(RectangleSystem.class, DrawingRectangleSystem
                .withColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW));
        DefaultDrawingAlgorithms.register(
                RectangleSidePiece.class,
                DrawingRectangleSidePiece.withColor(Color.MAGENTA));
        DefaultDrawingAlgorithms.register(Segment.class, DrawingSegment.withColor(Color.BLUE));
        DefaultDrawingAlgorithms.register(Chunk.class, DrawingTerrain.defaultAlgorithm());
        DefaultDrawingAlgorithms.register(World.class, DrawingWorld.level(0));

//	ResourcesRegistry.registerDrawer(new TestLocationDrawer());
//	ResourcesRegistry.registerDrawer(new Forest());

        ResourcesRegistry.registerDrawer(new BuildingsLocationDrawer());
        ResourcesRegistry.registerDrawer(new Ocean());
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
        Character playerCharacter = characterFactory.create(17, 11, characters.human, "Suseika");
        world.getDefaultPlane().addCharacter(playerCharacter);
        singlePlayer.setPlayerCharacter(playerCharacter, world)

//        world.createCharacter(125, 131, characters.bear, "mishka");
//        world.createCharacter(125, 132, characters.bear, "mishka");
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

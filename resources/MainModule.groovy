import org.tendiwa.core.*
import org.tendiwa.core.meta.Condition
import org.tendiwa.drawing.*
import org.tendiwa.modules.DrawingWorld

import java.awt.*

import static org.tendiwa.core.DSL.canvas
import static org.tendiwa.groovy.DSL.*

public class MainModule extends Module implements WorldProvider {

    public MainModule() {
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
        Tendiwa.initWithDummyClient();
        Tendiwa.loadModules();
        Module mainModule = Tendiwa.getMainModule();
        TestCanvas canvas = canvas(2);
        Tendiwa.createWorld(mainModule);
        canvas.draw(Tendiwa.getWorld());

    }

    @Override
    public World createWorld() {
        World world = World.create(new SuseikaWorld(), 400, 300);
        Character playerCharacter = world.createPlayerCharacter(16, 14, characters.human, "Suseika");
        world.setPlayerCharacter(playerCharacter);

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

import org.tendiwa.core.Chunk
import org.tendiwa.core.EnhancedRectangle
import org.tendiwa.core.Item
import org.tendiwa.core.Module
import org.tendiwa.core.RectangleSidePiece
import org.tendiwa.core.RectangleSystem
import org.tendiwa.core.ResourcesRegistry
import org.tendiwa.core.Segment
import org.tendiwa.core.Tendiwa
import org.tendiwa.core.World
import org.tendiwa.core.WorldProvider
import org.tendiwa.drawing.DefaultDrawingAlgorithms
import org.tendiwa.drawing.DrawingRectangle
import org.tendiwa.drawing.DrawingRectangleSidePiece
import org.tendiwa.drawing.DrawingRectangleSystem
import org.tendiwa.drawing.DrawingSegment
import org.tendiwa.drawing.DrawingTerrain
import tendiwa.core.*;
import org.tendiwa.core.Character
import org.tendiwa.core.meta.Condition;
import tendiwa.drawing.*
import org.tendiwa.modules.DrawingWorld;

import java.awt.*;
import static org.tendiwa.groovy.DSL.*;

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
        MainModule mainModule = new MainModule();
//	TestCanvas canvas = canvas(2);
        Tendiwa.createWorld(mainModule);
//	canvas.draw(Tendiwa.getWorld());

    }

    @Override
    public World createWorld() {
        World world = World.create(new SuseikaWorld(), 400, 300);
        Character playerCharacter = world.createPlayerCharacter(386, 17, characters.human, "Suseika");
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

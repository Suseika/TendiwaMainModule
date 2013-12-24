package tendiwa.modules;

import org.tendiwa.entities.CharacterTypes;
import org.tendiwa.entities.ItemsTypes;
import org.tendiwa.entities.Spells;
import tendiwa.core.Character;
import tendiwa.core.*;
import tendiwa.core.meta.Condition;
import tendiwa.drawing.*;

import java.awt.*;

import static tendiwa.core.DSL.canvas;

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
	DefaultDrawingAlgorithms.register(World.class, DrawingWorld.defaultAlgorithm());

	ResourcesRegistry.registerDrawer(new TestLocationDrawer());
//	ResourcesRegistry.registerDrawer(new Forest());
	ResourcesRegistry.registerDrawer(new Ocean());
}

public static void main(String[] args) {
	MainModule mainModule = new MainModule();
	TestCanvas canvas = canvas();
	canvas.draw(mainModule.createWorld());
}

@Override
public World createWorld() {
	World world = World.create(new SuseikaWorld(), 400, 300);
	Character playerCharacter = world.createPlayerCharacter(120, 130, CharacterTypes.human, "Suseika");
	world.setPlayerCharacter(playerCharacter);
	world.createCharacter(125, 131, CharacterTypes.bear, "mishka");
	world.createCharacter(125, 132, CharacterTypes.bear, "mishka");
	playerCharacter.getItem(ItemsTypes.shortBow);
	playerCharacter.getItem(ItemsTypes.shortBow);
	playerCharacter.getItem(ItemsTypes.shortBow);
	playerCharacter.getItem(ItemsTypes.shortBow);
	playerCharacter.getItem(ItemsTypes.shortBow);
	playerCharacter.getItem(ItemsTypes.shortBow);
	playerCharacter.getItem(ItemsTypes.shortBow);
	playerCharacter.getItem(ItemsTypes.ironArmor);
	playerCharacter.getItem(ItemsTypes.woodenArrow, 94);
	playerCharacter.getItem(ItemsTypes.ironHelm);
	playerCharacter.learnSpell(Spells.FIREBALL);
	playerCharacter.learnSpell(Spells.BLINK);
	playerCharacter.wield(playerCharacter.getInventory().getItem(new Condition<Item>() {
		@Override
		public boolean check(Item item) {
			return item.getType() == ItemsTypes.shortBow;
		}
	}));
	return world;
}
}

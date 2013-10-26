package tendiwa.modules;

import tendiwa.core.Module;
import tendiwa.core.ResourcesRegistry;
import tendiwa.core.TerrainBasics;
import tendiwa.core.World;
import tendiwa.drawing.*;
import tendiwa.core.EnhancedRectangle;
import tendiwa.core.RectangleSidePiece;
import tendiwa.core.RectangleSystem;
import tendiwa.core.Segment;

import java.awt.*;

import static tendiwa.core.DSL.*;
import static tendiwa.core.World.create;

public class MainModule extends Module {

public MainModule() {
	DefaultDrawingAlgorithms.register(EnhancedRectangle.class, DrawingRectangle.withColorLoop(Color.GRAY, Color.BLACK, Color.BLUE));
	DefaultDrawingAlgorithms.register(RectangleSystem.class, DrawingRectangleSystem
		.withColors(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW));
	DefaultDrawingAlgorithms.register(
		RectangleSidePiece.class,
		DrawingRectangleSidePiece.withColor(Color.MAGENTA));
	DefaultDrawingAlgorithms.register(Segment.class, DrawingSegment.withColor(Color.BLUE));
	DefaultDrawingAlgorithms.register(TerrainBasics.class, DrawingTerrain.defaultAlgorithm());
	DefaultDrawingAlgorithms.register(World.class, DrawingWorld.defaultAlgorithm());

	ResourcesRegistry.registerDrawer(new TestLocationDrawer());
	ResourcesRegistry.registerDrawer(new Forest());
	ResourcesRegistry.registerDrawer(new Ocean());
}

public static void main(String[] args) {
	new MainModule();
}
public World createWorld() {
	return World.create(new SuseikaWorld(), 800, 600);
}

}

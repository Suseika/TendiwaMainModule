package tendiwa.modules;

import tendiwa.core.FloorType;
import tendiwa.core.HorizontalPlane;
import tendiwa.core.WallType;
import tendiwa.core.World;
import tendiwa.drawing.DrawingAlgorithm;

import java.awt.*;

public class DrawingWorld {

public static DrawingAlgorithm<World> defaultAlgorithm() {
	return new DrawingAlgorithm<World>() {
		@Override
		public void draw(World world) {
			HorizontalPlane defaultPlane = world.getDefaultPlane();
			int width = world.getWidth();
			int height = world.getHeight();
			if (width > canvas.width || height > canvas.height) {
				throw new RuntimeException("Size of world (" + width + "x" + height + ") is greater than size of canvas (" + width + "x" + height + ")");
			}
			drawRectangle(new Rectangle(0, 0, width, height), Color.BLACK);
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					FloorType floorType = FloorType.getById(defaultPlane.getFloor(x, y));
					WallType wallType = WallType.getById(defaultPlane.getWall(x, y));
					if (defaultPlane.getCharacter(x, y) != null) {
						drawPoint(x, y, Color.YELLOW);
					} else if (defaultPlane.hasAnyItems(x, y)) {
						System.out.println("Penis");
						drawPoint(x, y, Color.ORANGE);
					} else if (wallType == WallType.NO_WALL) {
						// Draw floor
						if (floorType.isLiquid()) {
							drawPoint(x, y, new Color(50, 50, 180));
						} else {
							drawPoint(x, y, Color.GREEN);
						}
					} else {
						// Draw wall
						drawPoint(x, y, Color.GRAY);
					}
				}
			}
		}
	};
}
}

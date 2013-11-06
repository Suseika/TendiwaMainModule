package tendiwa.modules;

import tendiwa.core.TerrainType;
import tendiwa.core.HorizontalPlane;
import tendiwa.core.World;
import tendiwa.drawing.DrawingAlgorithm;
import tendiwa.resources.TerrainTypes;

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
					TerrainType terrainType = TerrainType.getById(defaultPlane.getTerrainElement(x, y));
					TerrainType.TerrainClass terrainClass = terrainType.getTerrainClass();
					if (terrainClass == TerrainType.TerrainClass.WALL) {
						drawPoint(x, y, Color.GRAY);
					} else if (terrainClass == TerrainType.TerrainClass.FLOOR) {
						if (terrainType == TerrainTypes.water) {
							drawPoint(x, y, new Color(50, 50, 180));
						} else {
							drawPoint(x, y, Color.GREEN);
						}
					} else if (defaultPlane.getCharacter(x, y) != null) {
						drawPoint(x, y, Color.YELLOW);
					}
				}
			}
		}
	};
}
}

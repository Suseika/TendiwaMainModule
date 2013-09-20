package tests.painting;

import java.awt.Rectangle;

import tendiwa.core.HorizontalPlane;
import tendiwa.drawing.TestCanvas;
import tendiwa.drawing.TestCanvasBuilder;
import tendiwa.geometry.RandomRectangleSystem;
import tendiwa.geometry.RectangleSystem;
import tendiwa.locationtypes.Forest;

public class WorldDrawTest {
	public static void main(String[] args) {
		TestCanvas canvas = new TestCanvasBuilder().build();
		HorizontalPlane plane = new HorizontalPlane();
		RectangleSystem rs = new RandomRectangleSystem(0, 0, 600, 800, 30, 0);
		for (Rectangle r : rs) {
			plane.generateLocation(r.x, r.y, r.width, r.height, Forest.class);
		}
		canvas.draw(plane);
	}
}
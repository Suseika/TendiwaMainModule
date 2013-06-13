package tests.painting;

import java.awt.Rectangle;

import javax.swing.SwingUtilities;

import org.junit.Test;

import painting.TestCanvas;
import tendiwa.core.HorizontalPlane;
import tendiwa.core.ModuleLoader;
import tendiwa.core.meta.Chance;
import tendiwa.locationtypes.BuildingTest;
import tendiwa.locationtypes.Forest;
import tendiwa.recsys.RandomRectangleSystem;
import tendiwa.recsys.RectangleSystem;

public class WorldDrawTest {

	public static void main(String[] args) {
		ModuleLoader.loadModules();
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WorldDrawTest().testDraw();
            }
        });
	}
	@Test
	public void testDraw() {
		HorizontalPlane plane = new HorizontalPlane();
		RectangleSystem rs = new RandomRectangleSystem(0, 0, 400, 400, 40, 0);
		for (Rectangle r : rs) {
			plane.generateLocation(r.x, r.y, r.width, r.height, Forest.class);
		}
		new TestCanvas().draw(plane);
	}
}
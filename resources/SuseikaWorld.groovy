import org.tendiwa.core.*
import org.tendiwa.locationFeatures.FeatureForest
import org.tendiwa.locationFeatures.FeatureOcean
import org.tendiwa.geometry.Rectangle
import org.tendiwa.geometry.RectangleSystem
import org.tendiwa.geometry.RectangleSystemBuilder

import static org.tendiwa.geometry.DSL.*

public class SuseikaWorld implements WorldDrawer {

	@Override
	public void drawWorld(WorldRectangleBuilder builder, int width, int height) {
		final Rectangle worldRectangle = new Rectangle(0, 0, width, height);
		builder
			.place(recursivelySplitRec(width, height).minWidth(80).borderWidth(0), atPoint(0, 0))
			.findAllRectangles(
			{ Rectangle rectangle, RectangleSystem rs, RectangleSystemBuilder b ->
				return rectangle.touchesFromInside(worldRectangle);
			} as FindCriteria)
			.setLocationFeatures(FOUND_RECTANGLES, new FeatureOcean())
			.findAllRectangles(
			{ Rectangle rectangle, RectangleSystem rs, RectangleSystemBuilder b ->
				return !rectangle.touchesFromInside(worldRectangle);
			} as FindCriteria)
			.setLocationFeatures(FOUND_RECTANGLES, new FeatureForest());
	}

}

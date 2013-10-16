package tendiwa.modules;

import tendiwa.drawing.DrawingRectangleSystem;
import tendiwa.drawing.TestCanvas;

import java.awt.*;

import static tendiwa.core.DSL.*;
import static tendiwa.core.Directions.E;
import static tendiwa.core.Directions.N;

public class DidYouEatPatternsDemo extends tendiwa.core.RectangleBuilderTemplate {
public static void main(String[] args) {
	tendiwa.core.RectangleBuilderTemplate template = new DidYouEatPatternsDemo();
	TestCanvas canvas = canvas();
	tendiwa.core.RectangleSystem phrases = builder(0).
		place(
			builder(0)
				.place(template, atPoint(5, 8))
				.place(template.rotate(HALF_CIRCLE), awayFrom(LAST_BOUNDING_REC).fromSide(S).margin(20).align(W))
				.rememberBoundingRec()
				.place(template.rotate(CLOCKWISE), awayFrom(LAST_BOUNDING_REC).fromSide(W).margin(7).align(N))
				.place(template.rotate(COUNTER_CLOCKWISE), awayFrom(REMEMBERED_BOUNDING_REC).fromSide(E).margin(7).align(N))
				.done()
//					.repeat(3)
//					.placingNextAt(near(LAST_BOUNDING_REC).fromSide(E).align(N))
//					.repeat(3)
//					.placingNextAt(near(LAST_BOUNDING_REC).fromSide(S).align(W))
			, atPoint(5, 12)).done();

	canvas.draw(phrases, DrawingRectangleSystem.graphAndRectangles(
		Color.RED,
		Color.BLACK,
		Color.DARK_GRAY,
		Color.GRAY,
		Color.LIGHT_GRAY)
	);
}

@Override
public tendiwa.core.RectangleSystem build() {
	int letterIntervalLength = 2;
	int wordIntervalLength = 8;
	tendiwa.core.Placement letterInterval = awayFrom(LAST_BOUNDING_REC).fromSide(E).margin(letterIntervalLength).align(N);
	tendiwa.core.Placement wordInterval = awayFrom(LAST_BOUNDING_REC).fromSide(E).margin(wordIntervalLength).align(N);

	return builder(0)
		.place(tendiwa.core.CyrillicTemplates.YE, somewhere())
		.place(tendiwa.core.CyrillicTemplates.EL, letterInterval)
		.place(tendiwa.core.CyrillicTemplates.EL, wordInterval)
		.place(tendiwa.core.CyrillicTemplates.I, letterInterval)
		.place(tendiwa.core.CyrillicTemplates.TE, wordInterval)
		.place(tendiwa.core.CyrillicTemplates.YERY, letterInterval)
		.place(tendiwa.core.CyrillicTemplates.U, wordInterval)
		.place(tendiwa.core.CyrillicTemplates.ZE, letterInterval)
		.place(tendiwa.core.CyrillicTemplates.O, letterInterval)
		.place(tendiwa.core.CyrillicTemplates.ER, letterInterval)
		.place(tendiwa.core.CyrillicTemplates.YERY, letterInterval)
		.done();
}
}

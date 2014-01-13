package org.tendiwa.modules;

import org.tendiwa.core.CyrillicTemplates;
import org.tendiwa.core.Placement;
import org.tendiwa.core.RectangleBuilderTemplate;
import org.tendiwa.core.RectangleSystem;
import org.tendiwa.drawing.DrawingRectangleSystem;
import org.tendiwa.drawing.TestCanvas;

import java.awt.*;

import static org.tendiwa.core.Directions.E;
import static org.tendiwa.core.Directions.N;
import static org.tendiwa.core.DSL.*;

public class DidYouEatPatternsDemo extends RectangleBuilderTemplate {
public static void main(String[] args) {
	RectangleBuilderTemplate template = new DidYouEatPatternsDemo();
	TestCanvas canvas = canvas();
	RectangleSystem phrases = builder(0).
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
public RectangleSystem build() {
	int letterIntervalLength = 2;
	int wordIntervalLength = 8;
	Placement letterInterval = awayFrom(LAST_BOUNDING_REC).fromSide(E).margin(letterIntervalLength).align(N);
	Placement wordInterval = awayFrom(LAST_BOUNDING_REC).fromSide(E).margin(wordIntervalLength).align(N);

	return builder(0)
		.place(CyrillicTemplates.YE, somewhere())
		.place(CyrillicTemplates.EL, letterInterval)
		.place(CyrillicTemplates.EL, wordInterval)
		.place(CyrillicTemplates.I, letterInterval)
		.place(CyrillicTemplates.TE, wordInterval)
		.place(CyrillicTemplates.YERY, letterInterval)
		.place(CyrillicTemplates.U, wordInterval)
		.place(CyrillicTemplates.ZE, letterInterval)
		.place(CyrillicTemplates.O, letterInterval)
		.place(CyrillicTemplates.ER, letterInterval)
		.place(CyrillicTemplates.YERY, letterInterval)
		.done();
}
}

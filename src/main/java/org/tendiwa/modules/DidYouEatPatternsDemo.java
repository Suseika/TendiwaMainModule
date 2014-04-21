package org.tendiwa.modules;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.jukito.JukitoRunner;
import org.jukito.UseModules;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.tendiwa.geometry.extensions.CyrillicTemplates;
import org.tendiwa.geometry.Placement;
import org.tendiwa.geometry.RectangleBuilderTemplate;
import org.tendiwa.geometry.RectangleSystem;
import org.tendiwa.drawing.DrawingModule;
import org.tendiwa.drawing.DrawingRectangleSystem;
import org.tendiwa.drawing.TestCanvas;

import java.awt.*;

import static org.tendiwa.geometry.DSL.*;
import static org.tendiwa.core.Directions.E;
import static org.tendiwa.core.Directions.N;

@RunWith(JukitoRunner.class)
@UseModules(DrawingModule.class)
public class DidYouEatPatternsDemo extends RectangleBuilderTemplate {
@Inject
@Named("default")
TestCanvas canvas;

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

@Test
public void draw() throws InterruptedException {
	RectangleBuilderTemplate template = new DidYouEatPatternsDemo();
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
	Thread.sleep(2000);

}
}

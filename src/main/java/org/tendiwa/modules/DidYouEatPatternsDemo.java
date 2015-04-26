package org.tendiwa.modules;

import org.junit.Test;
import org.tendiwa.demos.Demos;
import org.tendiwa.drawing.TestCanvas;
import org.tendiwa.geometry.Placement;
import org.tendiwa.geometry.RecTree;
import org.tendiwa.geometry.RecTreeBuilder;
import org.tendiwa.geometry.extensions.CyrillicTemplates;

import java.awt.Color;

import static org.tendiwa.core.Directions.E;
import static org.tendiwa.core.Directions.N;
import static org.tendiwa.geometry.DSL.*;
import static org.tendiwa.geometry.GeometryPrimitives.rectangle;
import static org.tendiwa.geometry.RectanglePointer.named;
import static org.tendiwa.geometry.RectanglePointer.previous;

public final class DidYouEatPatternsDemo implements Runnable {
	public static void main(String[] args) {
		Demos.run(DidYouEatPatternsDemo.class);
	}

	@Override
	public void run() {
		TestCanvas canvas = new TestCanvas(1, rectangle(800, 600));
		int letterIntervalLength = 2;
		int wordIntervalLength = 8;
		Placement letterInterval = awayFrom(previous).fromSide(E).margin(letterIntervalLength).align(N);
		Placement wordInterval = awayFrom(previous).fromSide(E).margin(wordIntervalLength).align(N);
		RecTree template =
			new RecTreeBuilder()
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
		RecTree phrases = new RecTreeBuilder()
			.place(
				new RecTreeBuilder()
					.place(template, atPoint(5, 8))
					.place(template.rotate(HALF_CIRCLE), awayFrom(previous).fromSide(S).margin(20).align(W))
					.call("second")
					.place(template.rotate(CLOCKWISE), awayFrom(previous).fromSide(W).margin(7).align(N))
					.place(template.rotate(COUNTER_CLOCKWISE), awayFrom(named("second")).fromSide(E).margin(7).align(N))
					.done()
//					.repeat(3)
//					.placingNextAt(near(LAST_BOUNDING_REC).fromSide(E).align(N))
//					.repeat(3)
//					.placingNextAt(near(LAST_BOUNDING_REC).fromSide(S).align(W))
				, atPoint(5, 12)).done();
	}
}

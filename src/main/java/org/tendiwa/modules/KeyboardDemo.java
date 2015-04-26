package org.tendiwa.modules;

import org.tendiwa.geometry.RecTree;
import org.tendiwa.geometry.RecTreeBuilder;
import org.tendiwa.geometry.RecTree_Wr;
import org.tendiwa.geometry.Rectangle;

import static org.tendiwa.geometry.DSL.*;
import static org.tendiwa.geometry.GeometryPrimitives.rectangle;
import static org.tendiwa.geometry.RectanglePointer.*;

public final class KeyboardDemo {
	public void draw() {
		RecTree keyboard = new Keyboard();
		RecTree keyboards = new RecTreeBuilder()
			.place(keyboard, atPoint(5, 5))
			.place(keyboard.rotate(CLOCKWISE), awayFrom(previous).fromSide(S).margin(10).align(E))
			.place(keyboard.rotate(HALF_CIRCLE), awayFrom(previous).fromSide(S).margin(2).inMiddle())
			.done();
	}

	private static final class Keyboard extends RecTree_Wr {

		private final static int buttonWidth = 5;
		private final static int upperButtonHeight = 3;
		private final static Rectangle button = rectangle(buttonWidth, buttonWidth);
		private final static Rectangle topRowButton = rectangle(buttonWidth, upperButtonHeight);

		public Keyboard() {
			super(
				new RecTreeBuilder()
					// Esc
					.place(topRowButton, somewhere())
						// Functional row
					.place(
						topRowButton
							.repeat(4)
							.placingNextAt(near(previous).fromSide(E).inMiddle())
							.repeat(3)
							.placingNextAt(awayFrom(previous).fromSide(E).margin(5).inMiddle()),
						awayFrom(previous).fromSide(E).margin(3).inMiddle()
					)
						// Shit key
					.place(rectangle(10, 1), near(previous).fromSide(E).align(N))
						// ~ 1-0 - =
					.place(
						button
							.repeat(13)
							.placingNextAt(near(previous).fromSide(E).inMiddle()),
						awayFrom(first).fromSide(S).margin(2).align(W)
					)
					.call("remember")
						// Backspace
					.place(rectangle(buttonWidth * 2 + 1, buttonWidth), near(previous).fromSide(E).inMiddle())
						// Tab
					.place(rectangle(7, buttonWidth), near(named("remember")).fromSide(S).align(W))
					.call("shit")
						// qwertyuiop[]
					.place(
						rectangle(buttonWidth, buttonWidth)
							.repeat(12)
							.placingNextAt(near(previous).fromSide(E).inMiddle()),
						near(previous).fromSide(E).inMiddle()
					)
						// | \
					.place(rectangle(9, buttonWidth), near(previous).fromSide(E).inMiddle())
						// Caps Lock
					.place(rectangle(8, buttonWidth), near(named("shit")).fromSide(S).align(W))
					.call("grok")
						// asdfghjkl;'
					.place(
						rectangle(buttonWidth, buttonWidth)
							.repeat(11)
							.placingNextAt(near(previous).fromSide(E).inMiddle()),
						near(previous).fromSide(E).inMiddle()
					)
						// Enter
					.place(rectangle(14, buttonWidth), near(previous).fromSide(E).inMiddle())
						// Left shift
					.place(rectangle(12, buttonWidth), near(named("grok")).fromSide(S).align(W))
					.call("ping")
						// zxcvbnm,./
					.place(
						button.repeat(10).placingNextAt(near(previous).fromSide(E).inMiddle()),
						near(previous).fromSide(E).inMiddle()
					)
						// Right shift
					.place(rectangle(16, buttonWidth), near(previous).fromSide(E).inMiddle())
						// Left Ctrl, left win, left alt
					.place(
						rectangle(buttonWidth + 1, buttonWidth)
							.repeat(3)
							.placingNextAt(near(previous).fromSide(E).inMiddle()),
						near(named("ping")).fromSide(S).align(W)
					)
						// Space
					.place(rectangle(buttonWidth * 8, buttonWidth), near(previous).fromSide(E).inMiddle())
						// Right alt, win, menu, ctrl
					.place(
						rectangle(buttonWidth + 1, buttonWidth)
							.repeat(4)
							.placingNextAt(near(previous).fromSide(E).inMiddle()),
						near(previous).fromSide(E).inMiddle())
					.done()
			);
		}
	}
}

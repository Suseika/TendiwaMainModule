package org.tendiwa.modules.mainModule;

import org.tendiwa.drawing.Canvas;
import org.tendiwa.drawing.TestCanvas;

import javax.inject.Inject;
import javax.inject.Provider;

final class CoastlineCanvasProvider implements Provider<Canvas> {
	private final CoastlineGeometryConfig config;

	@Inject
	CoastlineCanvasProvider(
		CoastlineGeometryConfig config
	) {
		this.config = config;
	}
	@Override
	public Canvas get() {
		return new TestCanvas(1, config.worldSize.width, config.worldSize.height);
	}
}

package org.tendiwa.modules.mainModule;

import org.tendiwa.drawing.DrawableInto;
import org.tendiwa.drawing.TestCanvas;

import javax.inject.Inject;
import javax.inject.Provider;

final class CoastlineCanvasProvider implements Provider<DrawableInto> {
	private final CoastlineGeometryConfig config;

	@Inject
	CoastlineCanvasProvider(
		CoastlineGeometryConfig config
	) {
		this.config = config;
	}
	@Override
	public DrawableInto get() {
		return new TestCanvas(1, config.worldSize.width, config.worldSize.height);
	}
}

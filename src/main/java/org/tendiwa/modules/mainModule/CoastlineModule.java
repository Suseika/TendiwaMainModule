package org.tendiwa.modules.mainModule;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.tendiwa.drawing.Canvas;

final class CoastlineModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(ProgressDrawing.class);
		bind(CoastlineGeometryConfig.class);
		bind(Canvas.class)
			.toProvider(CoastlineCanvasProvider.class)
			.in(Scopes.SINGLETON);
	}
}

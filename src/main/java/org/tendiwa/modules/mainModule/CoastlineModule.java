package org.tendiwa.modules.mainModule;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.tendiwa.drawing.DrawableInto;

final class CoastlineModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(ProgressDrawing.class);
		bind(CoastlineGeometryConfig.class);
		bind(DrawableInto.class)
			.toProvider(CoastlineCanvasProvider.class)
			.in(Scopes.SINGLETON);
	}
}

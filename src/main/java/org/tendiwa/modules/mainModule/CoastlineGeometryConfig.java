package org.tendiwa.modules.mainModule;

import org.tendiwa.core.worlds.Genesis;
import org.tendiwa.core.worlds.GenesisConfig;
import org.tendiwa.geometry.Rectangle;

import static org.tendiwa.geometry.GeometryPrimitives.rectangle;

final class CoastlineGeometryConfig implements GenesisConfig {
	int maxCityRadius = 80;
	int minDistanceFromCoastToCityBorder = 3;
	int minDistanceBetweenCityCenters = maxCityRadius * 3;
	int minDistanceFromCoastToCityCenter = 20;
	Rectangle worldSize = rectangle(1200, 1200);

	@Override
	public Class<? extends Genesis> genesisType() {
		return CoastlineGeometry.class;
	}
}

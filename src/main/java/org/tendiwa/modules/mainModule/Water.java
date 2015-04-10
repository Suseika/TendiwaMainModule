package org.tendiwa.modules.mainModule;

import org.tendiwa.geometry.CellSet;
import org.tendiwa.noise.Noise;
import org.tendiwa.noise.SimpleNoiseSource;

final class Water implements CellSet {

	private final SimpleNoiseSource noise = noise();

	@Override
	public boolean contains(int x, int y) {
		return noise.noise(x, y) <= 110;
	}

	private SimpleNoiseSource noise() {
		return (x, y) -> Noise.noise(
			((double) x + 62900) / 100,
			((double) y + 1501200) / 100,
			7
		);
	}
}

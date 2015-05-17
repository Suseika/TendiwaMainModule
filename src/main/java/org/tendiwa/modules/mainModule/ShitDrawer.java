package org.tendiwa.modules.mainModule;

import org.tendiwa.core.Location;
import org.tendiwa.core.World;
import org.tendiwa.geometry.Rectangle;
import org.tendiwa.modules.mainModule.ontology.Water;
import org.tendiwa.settlements.utils.RectangleWithNeighbors;

import java.util.Optional;

class ShitDrawer {
	private final World world;
	private final Location location;
	private final CityAroundCell city;

	public ShitDrawer(World world, Location location, CityAroundCell city) {
		this.world = world;
		this.location = location;
		this.city = city;
	}

	public void invoke() {
		for (RectangleWithNeighbors buildingPlace : city.buildingPlaces) {
			location.fillRectangle(
				buildingPlace.mainRectangle().intersection(world.asRectangle()).get(),
				Water.piece
			);
			for (Rectangle neighbor : buildingPlace.neighbors()) {
				Optional<Rectangle> intersection = neighbor.intersection(world.asRectangle());
				if (intersection.isPresent()) {
					location.fillRectangle(
						intersection.get(),
						Water.piece
					);
				}
			}
		}
	}
}

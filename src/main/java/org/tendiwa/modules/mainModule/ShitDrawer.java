package org.tendiwa.modules.mainModule;

import org.tendiwa.core.Location;
import org.tendiwa.core.World;
import org.tendiwa.geometry.Rectangle;
import org.tendiwa.groovy.Registry;
import org.tendiwa.settlements.utils.BasicRectangleWithNeighbors;

import java.util.Optional;

class ShitDrawer {
	private final World world;
	private final Location location;
	private final CoastlineCityGeometry city;

	public ShitDrawer(World world, Location location, CoastlineCityGeometry city) {
		this.world = world;
		this.location = location;
		this.city = city;
	}

	public void invoke() {
		for (BasicRectangleWithNeighbors buildingPlace : city.buildingPlaces) {
			location.fillRectangle(
				buildingPlace.rectangle.intersectionWith(world.asRectangle()).get(),
				Registry.floorTypes.get("water")
			);
			for (Rectangle neighbor : buildingPlace.neighbors) {
				Optional<Rectangle> intersection = neighbor.intersectionWith(world.asRectangle());
				if (intersection.isPresent()) {
					location.fillRectangle(
						intersection.get(),
						Registry.floorTypes.get("water")
					);
				}
			}
		}
	}
}

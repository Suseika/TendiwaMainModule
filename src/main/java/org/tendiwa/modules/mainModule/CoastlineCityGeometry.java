package org.tendiwa.modules.mainModule;

import com.google.common.collect.ImmutableList;
import org.tendiwa.geometry.Point2D;
import org.tendiwa.settlements.utils.RectangleWithNeighbors;
import org.tendiwa.settlements.networks.RoadsPlanarGraphModel;

import java.util.Set;

class CoastlineCityGeometry {
	RoadsPlanarGraphModel roadsPlanarGraphModel;
	Set<RectangleWithNeighbors> buildingPlaces;
	Set<ImmutableList<Point2D>> streets;
}

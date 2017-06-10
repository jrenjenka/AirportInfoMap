package airportInfoMap;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PImage;

public class RouteMarker extends SimpleLinesMarker {
	
	public RouteMarker(ShapeFeature route) {
		super(((ShapeFeature)route).getLocations(), route.getProperties());
	}
	
	

}

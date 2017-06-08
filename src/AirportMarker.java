

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PGraphics;
import processing.core.PImage;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 * 
 * @author Liubov Sereda
 * Date June 08, 2017
 * 
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	PImage icon;
	
	public AirportMarker(Feature city, PImage icon) {
		super(((PointFeature)city).getLocation(), city.getProperties());
		this.icon = icon;
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.pushStyle();
		pg.image(icon, x, y);
		//pg.fill(255, 255, 255);
		//pg.ellipse(x, y, 5, 5);
		
		
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		 // show rectangle with title
		
		// show routes
		
		
	}
	
}

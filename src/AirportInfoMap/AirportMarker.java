package airportInfoMap;


import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PConstants;
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
	
	int width;
	int height;

	public AirportMarker(Feature city, PImage icon) {
		super(((PointFeature)city).getLocation(), city.getProperties());
		this.icon = icon;
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.pushStyle();
		pg.imageMode(PConstants.CENTER);
		pg.image(icon, x, y);
		pg.popStyle();
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		 // show rectangle with title
		String title = getName() + ", " + getCode() + "\n" + getCity() + ", " + getCountry();
		
		width = (int) pg.textWidth(title) + 10;
		height = 40;
		
		pg.pushStyle();
		pg.stroke(110);
		pg.fill(255,255,255);
		pg.rect(x - width/2, y - height - 10, width, height, 5);
		
		pg.textAlign(PConstants.CENTER, PConstants.CENTER);
		pg.textSize(10);
		pg.fill(0);
		pg.text(title, x, y - height/2 - 10);
		
		pg.popStyle();
		// show routes
		
		
	}
	
	private String getName() {
		return getProperty("name").toString().replaceAll("^\"|\"$", "");
	}
	
	private String getCode() {
		return getProperty("code").toString().replaceAll("^\"|\"$", "");
	}
	
	private String getCountry() {
		return getProperty("country").toString().replaceAll("^\"|\"$", "");
	}
	
	private String getCity() {
		return getProperty("city").toString().replaceAll("^\"|\"$", "");
	}
 }

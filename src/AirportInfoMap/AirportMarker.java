package airportInfoMap;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
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
		pg.fill(250,250,250);
		pg.rect(x - width/2, y - height - 10, width, height, 5);
		
		pg.textAlign(PConstants.CENTER, PConstants.CENTER);
		pg.textSize(10);
		pg.fill(0);
		pg.text(title, x, y - height/2 - 10);
		
		pg.popStyle();		
		
	}
	
	public String getName() {
		return getProperty("name").toString().replaceAll("^\"|\"$", "");
	}
	
	public String getCode() {
		return getProperty("code").toString().replaceAll("^\"|\"$", "");
	}
	
	public String getCountry() {
		return getProperty("country").toString().replaceAll("^\"|\"$", "");
	}
	
	public String getCity() {
		return getProperty("city").toString().replaceAll("^\"|\"$", "");
	}
 }

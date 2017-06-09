package airportInfoMap;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PImage;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 * @author Liubov Sereda
 * Date June 08, 2017
 * 
 */
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
	private String cityFile = "city-data.json";
	PGraphics pg;
	RectButton airportsButton;
	RectButton routesButton;
	
	public void setup() {
		// setting up PAppler
		size(900, 700, OPENGL);
		
		
		// setting up map and default events
		map = new UnfoldingMap(this, 0, 0, 900, 900);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
		
		List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
		
		// load image for the marker
		// Icon credit: made by http://www.freepik.com loaded from http://www.flaticon.com
		PImage icon = loadImage("icon.png");
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature, icon);
			String airportCode = m.getProperty("code").toString();
			String city = m.getProperty("city").toString();
			
			// https://stackoverflow.com/a/2608682
			city = city.replaceAll("^\"|\"$", "");
			
			for (Feature c : cities) {
				String name = c.getProperty("name").toString();
				if(city.equals(name)) {
					airportList.add(m);
					
					// put airport in hashmap with OpenFlights unique id for key
					airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
				}
			}
			
			/*
			if(!airportCode.equals("\"\"")) 
			{
				m.setRadius(5);
				airportList.add(m);
				
				// https://stackoverflow.com/a/2608682
				airportCode = airportCode.replaceAll("^\"|\"$", "");
				
			
				// put airport in hashmap with OpenFlights unique id for key
				airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
			}
			*/
			
		
		}
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
		
			//System.out.println(sl.getProperties());
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			//routeList.add(sl);
		}
		
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		//map.addMarkers(routeList);
		
		map.addMarkers(airportList);
		
		// Setup buttons
		setupButtons();
		
	}
	
	public void draw() {

		background(0);
		map.draw();
		airportsButton.display();
		routesButton.display();
		
	}
	
	@Override
	public void mouseMoved() {
		
	}
	
	@Override
	public void mouseClicked() {
		if (mouseX > 10 && mouseX < 110 && mouseY > 10 && mouseY < 30) {
			if (airportsButton.getButtonPressed()) { 
				airportsButton.setButtonPressed(false);
			} else {
			airportsButton.setButtonPressed(true);
			}
		}
		
		if (mouseX > 120 && mouseX < 220 && mouseY > 10 && mouseY < 30) {
			if (routesButton.getButtonPressed()) {
				routesButton.setButtonPressed(false);
			} else {
			routesButton.setButtonPressed(true);
			}
		}
	}
	
	// helper method for buttons setup
	private void setupButtons() {
		int hcolor = color(134, 187, 247);
		int bcolor = color(71, 145, 229);
		
		airportsButton = new RectButton(this, 400, 40);
		airportsButton.setCoordinates(10, 10);
		airportsButton.setSize(100, 30, 7);
		airportsButton.setBaseColor(bcolor);
		airportsButton.setHighlightColor(hcolor);
		airportsButton.setLabel("Show all airports", 10, 70);
			
		routesButton = new RectButton(this, 400, 40);
		routesButton.setCoordinates(120, 10);
		routesButton.setSize(100, 30, 7);
		routesButton.setBaseColor(bcolor);
		routesButton.setHighlightColor(hcolor);
		routesButton.setLabel("Show all routes", 10, 70);
		
	}

}

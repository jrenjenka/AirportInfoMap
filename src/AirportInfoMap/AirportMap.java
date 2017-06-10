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
	
	private List<Marker> airportMarkers;
	private Marker lastSelected;
	PGraphics pg;
	
	List<Marker> routeMarkers;
	
	private String cityFile = "city-data.json";
	List<Feature> cities;
	
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
		airportMarkers = new ArrayList<Marker>();
		HashMap<Integer, Location> airports = new HashMap<Integer, Location>();
		
		// load image for the marker
		// Icon credit: made by http://www.freepik.com loaded from http://www.flaticon.com
		PImage icon = loadImage("icon.png");
		
		cities = GeoJSONReader.loadData(this, cityFile);
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature, icon);
		
			String name = m.getProperty("name").toString().replaceAll("^\"|\"$", "");
			
			// add marker to list of markers
			if (!name.equals("All Airports")) {
				// show marker only if it is nearby big city
				checkCities(m);
				airportMarkers.add(m);
			}
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());

		}
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeMarkers = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			
			SimpleLinesMarker rt = new SimpleLinesMarker(((ShapeFeature)route).getLocations(), route.getProperties());
			
			rt.setHidden(true);
			//System.out.println(rt.getProperties());
			//System.out.println(rt.getLocations());
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
			routeMarkers.add(rt);
		}
		
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		//map.addMarkers(routeList);
		
		map.addMarkers(airportMarkers);
		map.addMarkers(routeMarkers);
		
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
		
		// highlight button if over
		if (mouseX > 10 && mouseX < 110 && mouseY > 10 && mouseY < 30) {
			airportsButton.setOverButton(true);
		} else {
			airportsButton.setOverButton(false);
		}
		
		if (mouseX > 120 && mouseX < 220 && mouseY > 10 && mouseY < 30) {
			routesButton.setOverButton(true);
		} else {
			routesButton.setOverButton(false);
		}
	}
	
	@Override
	public void mouseClicked() {
		
		// highlight buttons if pressed
		if (mouseX > 10 && mouseX < 110 && mouseY > 10 && mouseY < 30) {
			
			if (airportsButton.getButtonPressed()) { 
				hideAllAirports();
				airportsButton.setButtonPressed(false);
			} else {
				showAllAirports();
				airportsButton.setButtonPressed(true);
			}
		}
		
		if (mouseX > 120 && mouseX < 220 && mouseY > 10 && mouseY < 30) {
			if (routesButton.getButtonPressed()) {
				hideAllRoutes();
				routesButton.setButtonPressed(false);
			} else {
				showAllRoutes();
				routesButton.setButtonPressed(true);
			}
		}
		
		if (lastSelected != null) {
			lastSelected.setSelected(false);
			lastSelected = null;
		} else {
			selectMarkerIfClicked();
		}
	}
	
	// helper method for selecting marker if mouse is over it
		public void selectMarkerIfClicked() {
			for (Marker m : airportMarkers) {
				if (m.isInside(map, mouseX, mouseY) && !m.isHidden()) {
					lastSelected = m;
					lastSelected.setSelected(true);
					System.out.println("Selected");
					System.out.println(lastSelected.getProperty("name"));
					System.out.println(lastSelected.isSelected());
					return;
				}
			}
		}
	
	// helper method for buttons setup
	private void setupButtons() {
		int bcolor = color(71, 145, 229);
		int scolor = color(134, 187, 247);
		int hcolor = color(111, 153, 167);
		
		airportsButton = new RectButton(this, 400, 40);
		airportsButton.setCoordinates(10, 10);
		airportsButton.setSize(100, 30, 7);
		airportsButton.setBaseColor(bcolor);
		airportsButton.setSelectedColor(scolor);
		airportsButton.setHighlightColor(hcolor);
		airportsButton.setLabel("Show all airports", 10, 70);
			
		routesButton = new RectButton(this, 400, 40);
		routesButton.setCoordinates(120, 10);
		routesButton.setSize(100, 30, 7);
		routesButton.setBaseColor(bcolor);
		routesButton.setSelectedColor(scolor);
		routesButton.setHighlightColor(hcolor);
		routesButton.setLabel("Show all routes", 10, 70);
		
	}
	
	// helper method for showing all airports if buttons are pressed
	private void showAllAirports() {
		for (Marker m : airportMarkers) {
			if (m.isHidden()) {
				m.setHidden(false);
			}
		}
	}
	
	// helper method for hiding all airports except airports for big cities
	private void hideAllAirports() {
		for (Marker m : airportMarkers) {
			checkCities(m);
		}
	}
	
	// helper method for showing all routes if the button is clicked
	private void showAllRoutes() {
		for (Marker m : routeMarkers) {
			m.setHidden(false);
		}
	}
	
	// helper method for hiding all routes if the button is clicked
	private void hideAllRoutes() {
		for (Marker m : routeMarkers) {
			m.setHidden(true);
		}
	}
	
	// helper method for checking if airport is nearby big city
	private void checkCities(Marker m) {
		m.setHidden(true);
		
		String city = m.getProperty("city").toString();
		
		// https://stackoverflow.com/a/2608682
		city = city.replaceAll("^\"|\"$", "");
		for (Feature c : cities) {
			String name = c.getProperty("name").toString();
			
			if(city.equals(name)) { m.setHidden(false);	}
		} 
		
	}

}

package airportInfoMap;

/** An class that implements button on the map
 * Credit for solution https://forum.processing.org/two/discussion/10272/using-creategraphics-in-eclipse
 * Button templates http://processingjs.org/learning/topic/buttons/
 * Date June 08, 2017
 * 
 */

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;


public class RectButton {
	 PApplet parent;
	 PGraphics pg;
	 
	 // Button properties
	 private int xbase, ybase;
	 private int width, height, radius;
	 private int basecolor, highlightcolor;
	 private int currentcolor;
	 
	 // Label properties
	 private String label;
	 private int fontSize;
	 private int fontColor;
	 
	 // Button behaviour
	 private boolean pressed = false; 
	
	 // constructor
	 public RectButton(PApplet p, int _w, int _h)
	    {
	        parent = p;
	        pg = parent.createGraphics(_w, _h, PConstants.P3D);
	    }
	 
	 // setters 
	 public void setCoordinates(int x, int y) {
		 xbase = x;
	     ybase = y;
	 }
	 
	 public void setSize(int w, int h, int r) {
		 width = w;
	     height = h;
	     radius = r;
	 }
	 
	 public void setBaseColor(int bcolor) {
		 basecolor = bcolor;
		 currentcolor = basecolor;
	 }
	 
	 public void setHighlightColor(int hcolor) {
		 highlightcolor = hcolor;
	 }
	 
	 public void setLabel(String t, int ts, int tc) {
		 this.label = t;
	     this.fontSize = ts;
	     this.fontColor = tc;
	 }
	 
	 // Setter and getter for button interactivity
	 public void setButtonPressed(boolean state) {
		 pressed = state;
	 }
	 
	 public boolean getButtonPressed() {
		 return pressed;
	 }
	 
	 // draw button
	 public void display()
	    {
	        pg.beginDraw();
	     
	        // draw button
	        pg.fill(currentcolor);
	        pg.noStroke();
	        pg.rect(xbase, ybase, width, height, radius);
	        
	        // add label
	        pg.textAlign(PConstants.CENTER, PConstants.CENTER);
			pg.textSize(fontSize);
			pg.fill(fontColor);
			pg.text(label, xbase+width/2, ybase+height/2);
	        
	        pg.endDraw();
	        parent.image(pg, 0, 0); 
	      
	        pressedButton();
	    }
	 
	 // interactive button behaviour
	 public void pressedButton() {
		 if (pressed) { 
			 pg.stroke(highlightcolor);
			 currentcolor = basecolor;
		} else { 
			pg.noStroke(); 
			currentcolor = highlightcolor;
		}
	 }
}

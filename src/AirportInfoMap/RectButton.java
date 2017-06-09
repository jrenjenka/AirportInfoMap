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
	 int xbase, ybase;
	 int width, height, radius;
	 int basecolor;
	 int currentcolor, highlightcolor;
	 int r, g, b;
	 
	 // Label properties
	 String label;
	 int fontSize;
	 int fontColor;
	 
	 
	 public RectButton(PApplet p, int _w, int _h)
	    {
	        parent = p;
	        pg = parent.createGraphics(_w, _h, PConstants.P3D);
	    }
	 
	 public void setCoordinates(int x, int y) {
		 this.xbase = x;
	     this.ybase = y;
	 }
	 
	 public void setSize(int w, int h, int r) {
		 this.width = w;
	     this.height = h;
	     this.radius = r;
	 }
	 
	 public void setBaseColor(int r, int g, int b) {
	        //this.basecolor = pg.color(r, g, b);
		 //this.r = r;
		 //this.g = g;
		 //this.b = b;
	 }
	 
	 public void setLabel(String t, int ts, int tc) {
		 this.label = t;
	     this.fontSize = ts;
	     this.fontColor = tc;
	 }
	 
	 public void display()
	    {
	        pg.beginDraw();
	        pg.fill(r, g, b);
	        pg.rect(xbase, ybase, width, height, radius);
	        pg.endDraw();
	        parent.image(pg, 0, 0); 
	    }
}

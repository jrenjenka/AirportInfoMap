package AirportInfoMap;

// Credit for solution https://forum.processing.org/two/discussion/10272/using-creategraphics-in-eclipse

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PGraphics;

public class RectButton {
	 PApplet parent;
	 PGraphics pg;
	 
	 public RectButton(PApplet p, int _w, int _h)
	    {
	        parent = p;
	        pg = parent.createGraphics(_w, _h, PConstants.P3D);
	    }
	 
	 public void display()
	    {
	        pg.beginDraw();
	        pg.fill(0);
	        pg.rect(50, 50, 100, 100);
	        pg.endDraw();
	        parent.image(pg, 0, 0); 
	    }
}

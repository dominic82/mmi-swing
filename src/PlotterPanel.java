/*
 * Coderahmen fuer die Mensch-Maschine-Interaktion-Uebungen
 * TU Dortmund - Lehrstuhl Informatik VII
 * 
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JPanel;

public class PlotterPanel extends JPanel implements MouseMotionListener {
	private static final long serialVersionUID = 1L;
	
	private Point.Double origin = new Point.Double(0,0);
	private Point.Double extent = new Point.Double(10,10);
	private Function functionF;
	private Function functionG;
	private boolean showFxGraph = true, showGxGraph = true, showGrid = true;
	private Point startDrag = null;
	private Point.Double dragStartOrigin = new Point.Double();

	PlotterPanel() {
		super();
		setBackground(Color.black);
		functionF = new Function("");
		functionG = new Function("");
		this.addMouseMotionListener(this);
	}
	
	/**
	 * Setzt den String fuer den Funktionsausdruck F neu.
	 * Wirft eine IllegalArgumentException, wenn der Ausdruck nicht verarbeitet werden kann.
	 * Nach erfolgreicher Aktualisierung wird ein Neuzeichnen des Panels veranlasst.
	 * @param function ein gueltiger Funktionsausdruck
	 */
	public void setFunctionF(String function) throws IllegalArgumentException {
		functionF.setFunction(function);	
		repaint();
	}
	
	/**
	 * Setzt den String fuer den Funktionsausdruck G neu.
	 * Wirft eine IllegalArgumentException, wenn der Ausdruck nicht verarbeitet werden kann.
	 * Nach erfolgreicher Aktualisierung wird ein Neuzeichnen des Panels veranlasst.
	 * @param function ein gueltiger Funktionsausdruck
	 */
	public void setFunctionG(String function) throws IllegalArgumentException {
		functionG.setFunction(function);
		repaint();
	}
	
	public void setShowFxGraph(boolean showFxGraph) {
		this.showFxGraph = showFxGraph;
		repaint();
	}

	public void setShowGxGraph(boolean showGxGraph) {
		this.showGxGraph = showGxGraph;
		repaint();
	}

	public void setShowGrid(boolean showGrid) {
		this.showGrid = showGrid;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	   
	    drawAxis(g);
	    if (showFxGraph) {
		    g.setColor(Color.RED);
			drawGraph(g, functionF);
	    }
	    if (showGxGraph) {
			g.setColor(Color.BLUE);
			drawGraph(g, functionG);
	    }
	}
	
	private void drawAxis(Graphics g) {
		if (showGrid) {
			g.setColor(Color.DARK_GRAY);
			for (int i=-999; i < 1000; i++) {
				int xPos = mapX(i);
				if (xPos >= 0 && xPos < this.getWidth()) {

					// *******************
					// Aufgabe 1.3.c
					// Gitternetz senkrechte Linien
					g.drawLine(xPos, mapY(-999), xPos, mapY(999));
					// *******************

					g.drawString(String.valueOf(i), xPos+1, mapY(0)-3);
				}
				int yPos = mapY(i);
				if (yPos >= 0 && yPos < this.getHeight()) {
					
					// *******************
					// Aufgabe 1.3.c
					// Gitternetz senkrechte Linien
					g.drawLine(mapX(-999), yPos, mapX(999), yPos);
					// *******************
					
					g.drawString(String.valueOf(i), mapX(0)+1, yPos-3);
				}
			}
		}

		g.setColor(Color.YELLOW);

		// *******************
		// Aufgabe 1.3.c
		// Gitternetz Achsen
		g.drawLine(mapX(-999), mapY(0), mapX(999), mapY(0)); // X-Achse
		g.drawLine(mapX(0), mapY(-999), mapX(0), mapY(999)); // Y-Achse
		// *******************
	}
	
	private void drawGraph(Graphics g, Function f) {
		double abscissa, ordinate, lastOrdinate = 0;
		
		for (int x=0; x <= this.getWidth(); x++) {
			abscissa = mapInvX(x);
			ordinate = f.valueAt(abscissa);
			if (x != 0) {

				// *******************
				// Aufgabe 1.3.c
				// Funktionsgraph zeichnen
				g.drawLine(x - 1, mapY(lastOrdinate), x, mapY(ordinate));
				// *******************
			}
			lastOrdinate = ordinate;
		}		
	}

	private int mapX(double x) {
		return (int)((((x-origin.x))/extent.x+0.5)*this.getWidth());
	}
	
	private int mapY(double y) {
		return (int)((((-y+origin.y))/extent.y+0.5)*this.getHeight());
	}
	
	private double mapInvX(int x) {
		return ((double)x/this.getWidth()-0.5)*extent.x + origin.x;
	}
	
	//private double mapInvY(int y) {
	//	return -((double)y/this.getHeight()-0.5)*extent.y + origin.y;
	//}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (startDrag == null) {
			startDrag = e.getPoint();
			dragStartOrigin.setLocation(origin);
		}
		Point.Double offset = new Point.Double((double)(startDrag.x - e.getPoint().x)/this.getWidth()*extent.x, -(double)(startDrag.y - e.getPoint().y)/this.getHeight()*extent.y);
		origin.setLocation(dragStartOrigin.x + offset.x, dragStartOrigin.y + offset.y);
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		startDrag = null;
	}
}


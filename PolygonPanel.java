import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

public class PolygonPanel extends JPanel implements MouseListener {
	private static final long serialVersionUID = 1L;
	/**
	 * Ein vorgegebenes Polygon als Punkt-Array.
	 * Der Anzeigebereich geht von -1 bis +1 in beide Richtungen.
	 */
	private final Point.Double[] polygon = {
			new Point.Double(-0.5, 0.0),
			new Point.Double(-0.8, 0.7),
			new Point.Double(-0.3, 0.8),
			new Point.Double( 0.4, 0.0),
			new Point.Double( 0.8,-0.1),
			new Point.Double( 0.8,-0.6),
			new Point.Double( 0.0, 0.0),
			new Point.Double( 0.2,-0.6)
	};
	
	/**
	 * Die angeklickten Punkte auf dem Panel in Polygonkoordinaten.
	 */
	private Point.Double start = null, end = null;
	/**
	 * Eine Liste mit den Parametern der Schnittpunkte.
	 * Die Parameterwerte t beziehen sich auf die Gleichung:
	 * x = (end-start)*t + start 
	 * Zusaetzlich befinden sich in diesem Vector die Parameterwerte
	 * 0.0 und 1.0 fuer den Start- und den Endpunkt.
	 */
	private Vector<Double> intersections = null;
	
	public PolygonPanel() {
		this.setBackground(Color.WHITE);
		this.setPreferredSize(new Dimension(600,600));
		this.addMouseListener(this);
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		// Polygon zeichnen
		Polygon poly = new Polygon();
		for (int i=0; i < polygon.length; i++) {
			poly.addPoint(mapX(polygon[i].x), mapY(polygon[i].y));
		}
		graphics.drawPolygon(poly);
		
		
		graphics.setColor(Color.RED);
		if (start != null) {
			// Zeichne den Startpunkt, wenn dieser gesetzt ist
			graphics.fillOval(mapX(start.x)-5, mapY(start.y)-5, 11, 11);
			graphics.setColor(Color.BLUE);
			if (end != null) {
				// Zeichne den Endpunkt, wenn dieser bereits gesetzt wurde
				graphics.fillOval(mapX(end.x)-5, mapY(end.y)-5, 11, 11);
				
				// Zeichne die unterbrochene Verbindungslinie
				if (intersections != null) {
					// Beginnt die Verbindung im Inneren?
					boolean in = isInsidePolygon(start);
					double length = 0.0;
					graphics.setColor(Color.BLACK);
					for (int i=1; i < intersections.size(); i++) {
						double last = intersections.elementAt(i-1);
						double current = intersections.elementAt(i);
						
						// Zeichne die Verbindungslinie nur, wenn innerhalb des Polygons
						if (in) {
							double x1 = start.x + (end.x-start.x)*last;
							double y1 = start.y + (end.y-start.y)*last;
							double x2 = start.x + (end.x-start.x)*current;
							double y2 = start.y + (end.y-start.y)*current;
							graphics.drawLine(mapX(x1), mapY(y1), mapX(x2),	mapY(y2));
							
							// Summiere die zurueckgelegte Strecke im Polygon auf
							length += Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
						}
						in = !in;
					}
					// Ergebnis ausgeben
					graphics.drawString("Laenge der Strecke im Polygon: "+length, 10, getHeight()-10);
				}
			} else {
				graphics.setColor(Color.BLACK);
				graphics.drawString("Bitte waehlen Sie einen weiteren Punkt auf dem Panel mit der Maus aus", 10, getHeight()-10);
			}
		} else {
			graphics.setColor(Color.BLACK);
			graphics.drawString("Bitte waehlen Sie zwei Punkte auf dem Panel mit der Maus aus", 10, getHeight()-10);
		}
	}
	
	/**
	 * Berechnet fuer das vorgegebene Polygon und die selektierten Punkte die
	 * Menge der Schnittpunkte. Fuer jeden Schnittpunkt wird sein Wert als
	 * Parameterwert der ausgewaehlten Strecke im Vector intersections abgelegt.
	 * Zusaetzlich enthaelt dieser die Werte 0.0 und 1.0.
	 */
	private void calculate() {
		intersections = new Vector<Double>();
		// Start- und Endpunkt einfuegen
		intersections.add(0.0);
		intersections.add(1.0);
		for (int i=1; i <= polygon.length; i++) {
			// Schneide die Verbindungslinie mit allen Polygonkanten
			Point2D.Double st = intersectLineLine(polygon[i-1], polygon[i%polygon.length], start, end);
			if (0 <= st.x && st.x <= 1 && 0 <= st.y && st.y <= 1)
				intersections.add(st.y);
		}
		// Sortiere die Parameterwerte fuer eine einfache Darstellung der
		// unterbrochenen Verbindungslinie
		Collections.sort(intersections);
	}
	
	/**
	 * Berechnet den Schnitt zwischen den beiden gegebenen Strecken.
	 * @param line1 eine Strecke
	 * @param line2 eine Strecke
	 * @return den Parameterwert s des Schnittpunktes, bezogen auf
	 *  die Geradengleichung der zweiten Geraden:
	 *  x = (b2-a2) * s + a2
	 */
	private Point2D.Double intersectLineLine(Point2D.Double a1, Point2D.Double b1, Point2D.Double a2, Point2D.Double b2) {
		// Gleichung der 2. Strecke als Gerade:
		// x = (b2-a2) * s + a2 (0 <= s <= 1)
		// Gleichung der 1. Strecke als Gerade:
		// x = (b1-a1) * t + a1 (0 <= t <= 1)
		// 
		double s = 0;
		double t = 0;

		//
		// Berechnen Sie an dieser Stelle fuer die Aufgabe 4.4 a) die Parameter s und t, entsprechend der Aufgabenstellung.
		//
		// ********************
		// Aufgabe 4.4.a
		double a1x = a1.getX(); double a1y = a1.getY();
		double a2x = a2.getX(); double a2y = a2.getY();
		double b1x = b1.getX(); double b1y = a1.getY();
		double b2x = b2.getX(); double b2y = a2.getY();

		// lineare Gleichungssysteme aufgelöst nach s und t
		try {
			s = (((a1x-a2x)/(b1x-a1x))-((a1y-a2y)/(b1y-a1y)))
				/
				(((b2x-a2x)/(b1x-a1x))-((b2y-a2y)/(b1y-a1y)));
		}
		catch (ArithmeticException e) { s = Double.NaN; }
		try {
			t = (((a1x-a2x)/(b2x-a2x))-((a1y-a2y)/(b2y-a2y)))
				/
				(((b1y-a1y)/(b2y-a2y))-((b1x-a1x)/(b2x-a2x)));
		}
		catch (ArithmeticException e) { t = Double.NaN; }
		
		// Berechnung hat ein Ergebnis geliefert
		if(s != Double.NaN && t != Double.NaN) {
			return new Point2D.Double(t, s);
		}

		// Sonderfallbehandlung
		// keine Loesung
		if(s == Double.NaN && t == Double.NaN) {
			// wenn's denn unendlich sein soll ...
			return new Point2D.Double(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		}

		// hier angekommen hat s oder t einen Wert;
		// Hilfsfunktionen sind direkt im Anschluss
		// an diese Funktion definiert
		if(s != Double.NaN) {
			Point2D.Double p = pointOnSegment(a2, b2, s);
			if(closeEnough(p, a1)) { t = 0.0; }
			else {
				if(closeEnough(p, b1)) { t = 1.0; }
				else { t = Double.POSITIVE_INFINITY; }
			}
		}
		else { // t != Double.NaN
			Point2D.Double p = pointOnSegment(a1, b1, t);
			if(closeEnough(p, a2)) { s = 0.0; }
			else {
				if(closeEnough(p, b2)) { s = 1.0; }
				else { s = Double.POSITIVE_INFINITY; }
			}			
		}
		return new Point2D.Double(t, s);
	}

	
	// rechnet a + (b - a)s aus, wobei a, b Vektoren sind
	// und 0 <= s <= 1
	private Point2D.Double pointOnSegment(Point2D.Double a,
										  Point2D.Double b,
										  Double s) {
		Double x, y;
		x = a.getX() + (b.getX() - a.getX()) * s;
		y = a.getY() + (b.getY() - a.getY()) * s;
		return new Point2D.Double(x, y);
	}
	
	
	// testet ob 2 Punkte so nah beieinander liegen, dass
	// sie bzgl. Rundungsfehlern eigentlich identisch sind
	private Boolean closeEnough(Point2D.Double p1,
								Point2D.Double p2) {
		Double fuzziness = 0.0000001;
		Double dx = p2.getX() - p1.getX();
		Double dy = p2.getY() - p1.getY();
		return(Math.sqrt(dx*dx + dy*dy) < fuzziness);
	}
	
	
	// ********************
	// Ende Aufgabe 4.4.a
	// ********************

	
	/**
	 * Berechnet fuer einen gegebenen Punkt, ob dieser innerhalb oder ausserhalb des Polygons liegt.
	 * @param p ein Punkt
	 * @return true, wenn der Punkt innerhalb des gegebenen Polygons liegt
	 */
	private boolean isInsidePolygon(Point2D.Double p) {
		//
		// Berechnen Sie an dieser Stelle fuer die Aufgabe 4.4 b) entsprechend der Aufgabenstellung,
		// ob der gegebene Punkt sich innerhalb des Polygons befindet.
		//
		// ********************
		// Aufgabe 4.4.b
		// lege einen Strahl von p zu einem Punkt weit
		// jenseits des Polygons, zaehle wie oft dieser
		// Strahl die Kanten des Polygons schneidet
		// (bequemerweise mit der in Aufgabe 4.4.a
		// implementierten Funktion und Strahl parallel
		// der x-Achse)
		// ungerade Anzahl der Schnitte: Punkt liegt im
		// Polygon, sonst ausserhalb
		int countIntersections = 0;
		final Double largeValue = Double.MAX_VALUE / 4.0;
		Point2D.Double coefficients;
		Point2D.Double rayEndpoint = new Point2D.Double(largeValue, p.getY());
		for(int i = 0; i < polygon.length - 1; i++) {
			coefficients = intersectLineLine(polygon[i],
											 polygon[i+1],
											 p,
											 rayEndpoint);
			if(coefficients.getX() != Double.POSITIVE_INFINITY) {
				countIntersections++;
			}
		}
		coefficients = intersectLineLine(polygon[polygon.length-1],
										  polygon[0],
										  p,
										  rayEndpoint);
		if(coefficients.getX() != Double.POSITIVE_INFINITY) {
			countIntersections++;
		}

		// countIntersections is gerade wenn
		// countIntersections mod 2 == 1
		return(countIntersections % 2 == 1);
	}
	// ********************
	// Ende Aufgabe 4.4.b
	// ********************

	
	
	/**
	 * Berechnet die Determinante der Matrix:
	 * | x1 x2 |
	 * | y1 y2 |
	 * @param x1
	 * @param x2
	 * @param y1
	 * @param y2
	 * @return die Determinante
	 */
	private double determinante (double x1, double x2, double y1, double y2) {
		return x1*y2 - x2*y1;
	}
	
	/**
	 * Rechnet eine x-Koordinaten im Polygonkoordinaten in Panel-Koordinaten um.
	 * @param x
	 * @return
	 */
	private int mapX(double x) {
		return (int)((x+1.0)/2.0*this.getWidth());
	}
	
	/**
	 * Rechnet eine y-Koordinaten im Polygonkoordinaten in Panel-Koordinaten um.
	 * @param y
	 * @return
	 */
	private int mapY(double y) {
		return (int)((-y+1.0)/2.0*this.getHeight());
	}
	
	/**
	 * Rechnet eine x-Koordinaten in Panel-Koordinaten in Polygonkoordinaten um.
	 * @param x
	 * @return
	 */
	private double mapInverseX(int x) {
		return (double)x/this.getWidth()*2.0-1.0;
	}
	
	/**
	 * Rechnet eine y-Koordinaten in Panel-Koordinaten in Polygonkoordinaten um.
	 * @param y
	 * @return
	 */
	private double mapInverseY(int y) {
		return -((double)y/this.getHeight()*2.0-1.0);
	}
	
	/**
	 * Rechnet eine eine Panel-Koordinate in Polygonkoordinaten um.
	 * @param p
	 * @return
	 */
	private Point.Double mapInverse(Point p) {
		return new Point.Double(mapInverseX(p.x),mapInverseY(p.y));
	}

	/**
	 * Reagiert auf Mausklicks auf dem Panel
	 */
	public void mouseClicked(MouseEvent event) {
		if (end != null || start == null) {
			// neue Position gewaehlt oder allererster Klick.
			end = null;
			start = mapInverse(event.getPoint());
		} else {
			// zweite Position gewaehlt
			end = mapInverse(event.getPoint());
			calculate();
		}
		this.repaint();
	}

	public void mouseEntered(MouseEvent event) {}
	public void mouseExited(MouseEvent event) {}
	public void mousePressed(MouseEvent event) {}
	public void mouseReleased(MouseEvent event) {}
}

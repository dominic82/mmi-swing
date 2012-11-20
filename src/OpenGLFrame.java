/*
 * Coderahmen fuer die Mensch-Maschine-Interaktion-Uebungen
 * TU Dortmund - Lehrstuhl Informatik VII
 * 
 */
import java.awt.Point;
import java.awt.event.*;

import javax.swing.*;
import javax.media.opengl.*; 
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

public class OpenGLFrame extends JFrame implements GLEventListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	
	private GLJPanel panel; // Bei Darstellungsproblemen: GLJPanel durch GLCanvas ersetzen
	private double xRotation, yRotation;
	private double oldX, oldY;
	private Point dragStart;
	
	
	public OpenGLFrame () {
		super("OpenGL");
		GLCapabilities capabilities = new GLCapabilities();
		capabilities.setHardwareAccelerated(true);
		panel = new GLJPanel(capabilities); // Bei Darstellungsproblemen: GLJPanel durch GLCanvas ersetzen
		panel.addGLEventListener(this);
		this.add(panel);
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		panel.addMouseMotionListener(this);
	}
	
	public static void main(String[] args) {
		new OpenGLFrame();
	}
	
	public void display(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		GLU glu = new GLU();
		GLUT glut = new GLUT();
		
		// Setup Projection-Matrix
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(60, ((double)this.getWidth())/this.getHeight(), 0.001, 1000);		

		// Setup Modelview-Matrix
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glTranslated(0,0,-10);
		gl.glRotated(xRotation, 0, -1, 0);
		gl.glRotated(yRotation, -1, 0, 0);
		

		// Clear screen and render objects
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glColor3d(0.0, 0.5, 0.5);
		glut.glutSolidTeapot(1.0);


		// Bearbeiten Sie an dieser Stelle den Code zur Loesung der Aufgabe 5.4 a) und c)
		// ********************
		// Aufgabe 5.4.a
		gl.glColor3f(1.0f, 0.0f, 0.0f); // rot
		gl.glTranslated(3.0, 0.0, 0.0); // passt in etwa zur Vorlage
		glut.glutSolidIcosahedron();
		
	}

	public void displayChanged(GLAutoDrawable drawable, boolean arg1, boolean arg2) { }

	public void init(GLAutoDrawable drawable) {
		
        // Bearbeiten Sie diese Methode zur Loesung der Aufgabe 5.4 b)
        
        GL gl = drawable.getGL();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glShadeModel(GL.GL_FLAT);
        gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE);
        
		float position[] = {-4,10,-2,1};
		float ambient[] = {0,0,0,1};
		float diffuse[] = {1,1,1,1};
		float specular[] = {0.3f,0.3f,0.3f,1};
		float emission[] = {0,0,0,1};
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, position, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambient, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuse, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular, 0);
		
		gl.glEnable(GL.GL_LIGHT0);
		gl.glEnable(GL.GL_LIGHTING);
		
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, specular, 0);
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_EMISSION, emission, 0);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL gl = drawable.getGL();
		gl.glViewport(x, y, width, height);
	}

	public void mouseDragged(MouseEvent event) {
		if (event.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK) {
			if (dragStart == null) {
				dragStart = event.getPoint();
				oldX = xRotation;
				oldY = yRotation;
			} else {
				xRotation = oldX + dragStart.x - event.getPoint().x;
				yRotation = oldY + dragStart.y - event.getPoint().y;
				panel.repaint();
			}
		}
	}

	public void mouseMoved(MouseEvent event) {
		dragStart = null;
	}		
}

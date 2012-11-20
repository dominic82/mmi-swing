/*
 * Coderahmen fuer die Mensch-Maschine-Interaktion-Uebungen
 * TU Dortmund - Lehrstuhl Informatik VII
 * 
 */
import javax.swing.*;
import javax.media.opengl.*; 

public class SimpleOpenGLFrame extends JFrame implements GLEventListener {
	private static final long serialVersionUID = 1L;

	public SimpleOpenGLFrame () {
		super("OpenGL");
		GLCapabilities capabilities = new GLCapabilities();
		capabilities.setHardwareAccelerated(true);
		GLJPanel panel = new GLJPanel(capabilities); // Bei Darstellungsproblemen: GLJPanel durch GLCanvas ersetzen
		panel.addGLEventListener(this);
		this.add(panel);
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new SimpleOpenGLFrame();
	}

	public void display(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		// Bearbeiten Sie an dieser Stelle den Code zur Loesung der Aufgabe 5.3 a) und b)
		// ********************
		// Aufgabe 5.3.a
		final double xFactor = 2.0 / 7.0;
		// 7 adjunkte Rechtecke haben 8
		// senkrechte Kanten, deren Farben
		// gem. Vorlage so definiert werden:
		final float red[] = {0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f};
		final float green[] = {0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f};
		final float blue[] = {0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f};
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glBegin(GL.GL_QUAD_STRIP); {
			for(int i = 0; i < 8; i++) {
				double x = -1.0 + xFactor * (double)i;
				gl.glColor3f(red[i], green[i], blue[i]);
				gl.glVertex2d(x, -1.0);
				gl.glVertex2d(x, 1.0);
			}
		} gl.glEnd();
		// Aufgabe 5.3.b
		gl.glBegin(GL.GL_TRIANGLES); {
			gl.glColor3f(1.0f, 0.0f, 0.0f);
			gl.glVertex3d(-1.0, -1.0, -1.0);
			gl.glVertex3d(-1.0, 1.0, -1.0);
			gl.glVertex3d(1.0, 0.0, 1.0);
		} gl.glEnd();
	}

	public void displayChanged(GLAutoDrawable drawable, boolean arg1, boolean arg2) {

	}

	public void init(GLAutoDrawable drawable) {
		GL gl = drawable.getGL();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);


		// Bearbeiten Sie an dieser Stelle den Code zur Loesung der Aufgabe 5.3 b)

		
	}

	public void reshape(GLAutoDrawable drawable, int arg1, int arg2, int arg3, int arg4) {

	}
}

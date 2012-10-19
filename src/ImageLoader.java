import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;


public class ImageLoader extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JButton loadButton;
	private JPanel controlPanel;

	ImageLoader() {
		super();
		this.setLayout(new BorderLayout());
			
		loadButton = new JButton("Bild auswaehlen...");
		controlPanel = new JPanel();
		controlPanel.add(loadButton);
		this.add(controlPanel, BorderLayout.EAST);
	}
}

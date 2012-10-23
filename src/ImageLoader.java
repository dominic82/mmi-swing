import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.filechooser.*;

import java.io.*;

public class ImageLoader extends JPanel
	implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JButton loadButton;
	private JPanel controlPanel;

	ImageLoader() {
		super();
		this.setLayout(new BorderLayout());
			
		loadButton = new JButton("Bild auswaehlen...");
		loadButton.addActionListener(this);
		controlPanel = new JPanel();
		controlPanel.add(loadButton);
		this.add(controlPanel, BorderLayout.EAST);	
	}

    
    // ********************
    // Uebungsblatt 0
    // laedt eine Datei eines gegebenen Dateinames als Icon
    // ********************
	public void loadImage(String fileName) {
		ImageIcon loadedImage = new ImageIcon(fileName);
		JLabel imageLabel = new JLabel(loadedImage);
		JScrollPane scrollPane = new JScrollPane(imageLabel);
		this.add(scrollPane);
		// vielleicht nicht die korrekte Methode;
		// hier verhindert sie, dass man nochmal auf den Tab
		// klicken muss um das Icon zu sehen
		scrollPane.doLayout();
		this.doLayout();
	}
    // ********************


	@Override
	public void actionPerformed(ActionEvent arg0) {
	    // ********************
	    // Uebungsblatt 0
	    // JFileChooser -> loadImage()
	    // ********************
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new ImageFilter());
		fileChooser.setAcceptAllFileFilterUsed(false);
		int fileChooserReturnValue = fileChooser.showOpenDialog(this);
		if(fileChooserReturnValue == JFileChooser.APPROVE_OPTION) {
			loadImage(fileChooser.getSelectedFile().getAbsolutePath());
		}
		// ********************
	}
}


// ********************
// Uebungsblatt 0
// diese Klasse filtert JPEGs und GIFs aus aufgrund von Dateinamen-Erweiterungen
// ********************
class ImageFilter extends javax.swing.filechooser.FileFilter {

	@Override
	public boolean accept(File file) {

		if(file.isDirectory()) { return true; }

		String fileName = file.getName();
		int lastDot = fileName.lastIndexOf('.');
		if(lastDot < 1 || lastDot > fileName.length() - 4) { // mindestens einbuchstabiger Name und dreibuchstabige Extension
			return false;
		}
		String unifiedExtension = fileName.substring(lastDot + 1).toLowerCase();
		if(unifiedExtension.equals("jpg") ||
				unifiedExtension.equals("jpeg") ||
				unifiedExtension.equals("gif")) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public String getDescription() {
		return "Bildformate GIF und JPEG";
	}
	
}

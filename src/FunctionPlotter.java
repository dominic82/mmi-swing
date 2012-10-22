/*
 * Coderahmen fuer die Mensch-Maschine-Interaktion-Uebungen
 * TU Dortmund - Lehrstuhl Informatik VII
 * 
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class FunctionPlotter extends JPanel implements ActionListener, ItemListener {
	private static final long serialVersionUID = 1L;
	
	private JLabel coords;
	private PlotterPanel plotterPanel;
	private JCheckBox showGrid;
	private JCheckBox showFxGraph;
	private JCheckBox showGxGraph;
	private JComboBox formulaField1;
	private JComboBox formulaField2;
	
	
	FunctionPlotter() {
		super();
		
		JPanel infoPanel = new JPanel();
		coords = new JLabel("hover output panel for position...");
		infoPanel.add(coords);
		infoPanel.setPreferredSize(new Dimension(330, 30));
		infoPanel.setMinimumSize(new Dimension(330, 30));
		
		plotterPanel = new PlotterPanel();
		
		JPanel formulaPanel1 = new JPanel();
		formulaField1 = new JComboBox();
		formulaField1.setEditable(true);

		// *******************
		// Aufgabe 1.3.b
		// Eventhandler registrieren
		formulaField1.addActionListener(this);
		// *******************

		formulaField1.addItem("sin(e^x)");
		formulaField1.setPreferredSize(new Dimension(200,30));
		JLabel fxLabel1 = new JLabel("f(x) = ");
		fxLabel1.setForeground(Color.RED);
		
		formulaPanel1.setLayout(new FlowLayout());
		formulaPanel1.add(fxLabel1);
		formulaPanel1.add(formulaField1);
		
		JPanel formulaPanel2 = new JPanel();
		formulaField2 = new JComboBox();
		formulaField2.setEditable(true);

		// *******************
		// Aufgabe 1.3.b
		// Eventhandler registrieren		
		formulaField2.addActionListener(this);
		// *******************

		formulaField2.addItem("sqrt(x)/x^2");
		formulaField2.setPreferredSize(new Dimension(200,30));
		JLabel fxLabel2 = new JLabel("g(x) = ");
		fxLabel2.setForeground(Color.BLUE);
		
		formulaPanel2.setLayout(new FlowLayout());
		formulaPanel2.add(fxLabel2);
		formulaPanel2.add(formulaField2);
		
		
		showGrid = new JCheckBox("Show Grid");
		showGrid.addItemListener(this);
		showFxGraph = new JCheckBox("Show Graph of f(x)");
		showFxGraph.addItemListener(this);
		showGxGraph = new JCheckBox("Show Graph of g(x)");
		showGxGraph.addItemListener(this);
		
		showGrid.setSelected(true);
		showFxGraph.setSelected(true);
		showGxGraph.setSelected(true);
		
		JPanel checkBoxPanel = new JPanel();
		checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));
		checkBoxPanel.setPreferredSize(new Dimension(200,200));
		checkBoxPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		checkBoxPanel.add(showGrid);
		checkBoxPanel.add(showFxGraph);
		checkBoxPanel.add(showGxGraph);
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
		controlPanel.add(formulaPanel1);
		controlPanel.add(formulaPanel2);
		controlPanel.add(checkBoxPanel);
		controlPanel.add(infoPanel);
		controlPanel.setPreferredSize(new Dimension(350,350));
		
		this.setLayout(new BorderLayout());
		this.add(controlPanel, BorderLayout.EAST);
		this.add(plotterPanel, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().getClass() == JComboBox.class) {
			JComboBox sender = (JComboBox)e.getSource();
			if (sender.getSelectedIndex() == -1) {
				try {
					if (sender == formulaField1) {
						plotterPanel.setFunctionF(sender.getSelectedItem().toString());

						// *******************
						// Aufgabe 1.3.b
						// Eventhandler: Eingabe an Pos. 0 einfuegen
						sender.insertItemAt(sender.getSelectedItem().toString(), 0);
						// *******************

				} else if (sender == formulaField2) {
						plotterPanel.setFunctionG(sender.getSelectedItem().toString());

						// *******************
						// Aufgabe 1.3.b
						// Eventhandler: Eingabe an Pos. 0 einfuegen
						sender.insertItemAt(sender.getSelectedItem().toString(), 0);
						// *******************
					}
				} catch (IllegalArgumentException ex) {
					System.out.println(ex);
				}
			} else {
				if (sender == formulaField1) {
					plotterPanel.setFunctionF(sender.getSelectedItem().toString());
				} else {
					plotterPanel.setFunctionG(sender.getSelectedItem().toString());
				}
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource().getClass() == JCheckBox.class) {
			JCheckBox sender = (JCheckBox)e.getSource();
			if (sender == showGrid) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					plotterPanel.setShowGrid(true);
				else
					plotterPanel.setShowGrid(false);
			} else if (sender == showFxGraph) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					plotterPanel.setShowFxGraph(true);
				else
					plotterPanel.setShowFxGraph(false);
			} else if (sender == showGxGraph) {
				if (e.getStateChange() == ItemEvent.SELECTED)
					plotterPanel.setShowGxGraph(true);
				else
					plotterPanel.setShowGxGraph(false);
			} 
		}
	}
}
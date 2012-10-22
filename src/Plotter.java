
import java.awt.event.*;

import javax.swing.*;

public class Plotter extends JFrame
        implements ActionListener { // eigene Methoden zur Eventbehandlung

    private static final long serialVersionUID = 1L;
    private JMenuBar menuBar;
    private JMenu plotterMenu;
    private JMenuItem plotterMenuInfoItem;
    private JMenuItem plotterMenuExitItem;
    private JTabbedPane tabbedPane;

    public Plotter() {
        super("Plotter");

        setupMenu();
        setupWindow();
    }

    private void setupMenu() {
        menuBar = new JMenuBar();
        plotterMenu = new JMenu("Plotter");
        plotterMenuInfoItem = new JMenuItem("Info");
        plotterMenuInfoItem.addActionListener(this); // this.actionPerformed() aufrufen
        plotterMenu.add(plotterMenuInfoItem);

        plotterMenuExitItem = new JMenuItem("Exit");
        plotterMenuExitItem.addActionListener(this); // hier auch
        plotterMenu.add(plotterMenuExitItem);
        menuBar.add(plotterMenu);
        this.setJMenuBar(menuBar);
    }

    private void setupWindow() {
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        add(tabbedPane);

        setSize(900, 600);
        setLocation(50, 50);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void addTab(String title, JPanel panel) {
        this.tabbedPane.add(title, panel);
    }

    public static void main(String[] args) {
        Plotter plotter = new Plotter();
        plotter.addTab("Image Loader", new ImageLoader());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem) e.getSource();
        String menuItemText = menuItem.getText();

        if (menuItemText.equals("Exit")) {
            this.dispose(); // Fenster schliessen
        }
        if (menuItemText.equals("Info")) {
            // Info-Fenster anzeigen
            JOptionPane.showMessageDialog(this, "Dies ist ein kurzer Info-Text.\nVielen Dank.");
        }
    }
}

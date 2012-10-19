
import java.awt.event.*;
import javax.swing.*;

public class Plotter extends JFrame implements ActionListener {
    
    private static final long serialVersionUID = 1L;
    private JMenuBar menuBar;
    private JMenu plotterMenu;
    private JMenuItem plotterMenuInfoItem;
    private JMenuItem plotterMenuExitItem;
    private JTabbedPane tabbedPane;
    
    public Plotter() {
        super("Plotter");
        //Beenden des Programms
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupMenu();
        setupWindow();
    }
    
    private void setupMenu() {
        menuBar = new JMenuBar();
        plotterMenu = new JMenu("Plotter");
        plotterMenuInfoItem = new JMenuItem("Info");
        plotterMenuInfoItem.addActionListener(this);
        plotterMenu.add(plotterMenuInfoItem);
        
        plotterMenuExitItem = new JMenuItem("Exit");
        //Beenden über Exit Button
        plotterMenuExitItem.addActionListener(this);
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
    
    public void setupInfoDialog() {
        JLabel infoText = new JLabel("Mein Info Text");
        JOptionPane infoDialog = new JOptionPane(infoText);
    }
    
    public void addTab(String title, JPanel panel) {
        this.tabbedPane.add(title, panel);
    }
    
    public void actionPerformed(ActionEvent ae) {
//        this.dispose();
    }
    
    public static void main(String[] args) {
        Plotter plotter = new Plotter();
        plotter.addTab("Image Loader", new ImageLoader());
    }
}

package interfaz_grafica;
import java.util.*;
import javax.swing.*;

public class Interfaz_grafica extends JFrame{
	private JPanel panel;
	private DefaultListModel<String> listModel;
	private JList<String>songList;
	private List<JTextField>listInputs;
	private List<JTextField>listLabels;
	private JComboBox<String> searchAtributes;
	private JTextArea songDeatil;
	public Interfaz_grafica() {
		placeComponents();
	}
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Interfaz_grafica().setVisible(true);
            }
        });
    }
	private void placeComponents() {
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}
}

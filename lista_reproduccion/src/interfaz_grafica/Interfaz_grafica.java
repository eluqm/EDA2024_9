package interfaz_grafica;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;

public class Interfaz_grafica extends JFrame{
	private JPanel panel;
	private DefaultListModel<String> listModel;
	private JList<String>songList;
	private List<JTextField>listInputs=new ArrayList<>();
	private List<JLabel>listLabels=new ArrayList<>();
	private String[]strLabels= {"Título","Artista"};
	private JComboBox<String> searchAtributes;
	private JTextArea songDeatil;
	private int x=800,y=600, sizeAtr=strLabels.length,sizeMuestra=2;
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
		setSize(x,y);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		panel=new JPanel();
		panel.setLayout(null);
		setLocationRelativeTo(null);
		add(panel);
		int a=20,b=30,c=30,d=30,in=20;
		for(int i=0;i<strLabels.length;i++) {
			String str=strLabels[i];
			JLabel label=new JLabel(str);
			panel.add(label);
			JTextField jtext=new JTextField();
			panel.add(jtext);
			if(i<sizeMuestra) {
				label.setBounds(in,in+b*i,in*4,in);
				jtext.setBounds(in*5,in+b*i,in*5,24);
			}
			listLabels.add(label);
			listInputs.add(jtext);
		}
		listModel = new DefaultListModel<>();
        songList = new JList<>(listModel);
        JScrollPane tabla=new JScrollPane(songList);
        tabla.setBounds(in,in*12,in*36,in*12);
        panel.add(tabla);
        
        JButton agregarCancion=new JButton("Agregar Canción");
        JButton buscarCancion=new JButton("Buscar Canción");
        JButton eliminarCancion=new JButton("Eliminar Canción");
        agregarCancion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregarCancion();
			}
        });
        buscarCancion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCancion();
			}
        });
        eliminarCancion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarCancion();
			}
        });
        panel.add(eliminarCancion);
        panel.add(buscarCancion);
        panel.add(agregarCancion);
        agregarCancion.setBounds(in*5,in*25,in*7,24);
        buscarCancion.setBounds(in*15,in*25,in*7,24);
        eliminarCancion.setBounds(in*25,in*25,in*7,24);
	}
	private void agregarCancion() {
		
	}
    private void buscarCancion() {
		
	}
    private void eliminarCancion() {
	}
}

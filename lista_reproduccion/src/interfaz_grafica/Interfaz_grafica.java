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
	private JTextArea songDetail;
	private int x=800,y=600, sizeAtr=strLabels.length,sizeMuestra=2;
	private JButton buscarCancion;
    private JButton eliminarCancion;
    private JButton agregarCancion;
    private JTextField busquedaText;
    private JTextField tituloText;
    private JTextField artistaText;
    private JLabel busqueda_;
    private JLabel titulo_;
    private JLabel artista_;
    private JLabel detalles_;
    private JLabel buscar_;
    private JLabel agregar_;
    private JButton agregar;
    private JButton buscar;
	public Interfaz_grafica() {
		placeComponents();
		mostrar("ninguno");
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
		getContentPane().add(panel);
		int a=20,b=30,c=30,d=30,in=20;
		listModel = new DefaultListModel<>();
        songList = new JList<>(listModel);
        JScrollPane tabla=new JScrollPane(songList);
        tabla.setBounds(in,in*14,in*36,in*12);
        panel.add(tabla);
        
        agregarCancion=new JButton("Agregar Canción");
        buscarCancion=new JButton("Buscar Canción");
        eliminarCancion=new JButton("Eliminar Canción");
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
        agregarCancion.setBounds(20,246,in*7,24);
        buscarCancion.setBounds(170,246,in*7,24);
        eliminarCancion.setBounds(320,246,in*7,24);
        
        agregar_=new JLabel("Agregar canción:");
        panel.add(agregar_);
        agregar_.setBounds(156,44,in*5,24);
        
        buscar_=new JLabel("Buscar canción:");
        panel.add(buscar_);
        buscar_.setBounds(156,44,in*5,24);
        
        busqueda_=new JLabel("Buscar por:");
        panel.add(busqueda_);
        busqueda_.setBounds(20,102,in*5,24);
        
        titulo_=new JLabel("Título:");
        panel.add(titulo_);
        titulo_.setBounds(49,102,in*5,24);
        
        artista_=new JLabel("Artista:");
        panel.add(artista_);
        artista_.setBounds(49,162,in*5,24);
        
        detalles_=new JLabel("Detelles de la canción:");
        panel.add(detalles_);
        detalles_.setBounds(541,10,123,24);
        
        busquedaText=new JTextField();
        panel.add(busquedaText);
        busquedaText.setBounds(99,163,in*5,24);
        
        tituloText=new JTextField("");
        panel.add(tituloText);
        tituloText.setBounds(99,103,in*5,24);
        
        artistaText=new JTextField();
        panel.add(artistaText);
        artistaText.setBounds(99,163,in*5,24);
        
        songDetail=new JTextArea();
        panel.add(songDetail);
        songDetail.setBounds(483,44,227,226);
        
        searchAtributes = new JComboBox<>(new String[] {"Título", "Artista", "Género"});
        panel.add(searchAtributes);
        searchAtributes.setBounds(99,102,in*5,24);
        
        agregar=new JButton("Agregar");
        panel.add(agregar);
        agregar.setBounds(219,130,in*7,24);
        agregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				agregar();
			}
        });
        
        buscar=new JButton("Buscar");
        panel.add(buscar);
        buscar.setBounds(219,130,in*7,24);
        buscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
        });
	}
	private void agregarCancion() {
		mostrar("agregar");
	}
    private void buscarCancion() {
		mostrar("buscar");
	}
    private void eliminarCancion() {
    	eliminar();
	}
    private void agregar() {
    	
    }
    private void buscar() {
    	
    }
    private void eliminar() {
    	
    }
    private void mostrar(String s) {
    	boolean a=true,b=true;
    	if(s.equals("agregar")) {
    		a=true;
    		b=false;
    	}else if(s.equals("buscar")) {
    		a=false;
    		b=true;
    	}else if(s.equals("ninguno")) {
    		a=false;
    		b=false;
    	}
    	titulo_.setVisible(a);
		artista_.setVisible(a);
		tituloText.setVisible(a);
		artistaText.setVisible(a);
		agregar_.setVisible(a);
		agregar.setVisible(a);
		buscar.setVisible(b);
		busquedaText.setVisible(b);
		buscar_.setVisible(b);
		searchAtributes.setVisible(b);
		busqueda_.setVisible(b);
    }
}

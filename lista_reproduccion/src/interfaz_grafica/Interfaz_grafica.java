package interfaz_grafica;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvMalformedLineException;

public class Interfaz_grafica extends JFrame{
	private JPanel panel;
	private Image image;
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
    private JLabel mensaje_;
    ListaReproduccion lista_reproduccion;
    private boolean isDeleting = false;
	public Interfaz_grafica() {
		placeComponents();
		aplicarEstilos();
		mostrar("ninguno");
		lista_reproduccion=new ListaReproduccion();
		cargarCanciones("C:\\Users\\Usuario\\Downloads\\archive\\spotify_data.csv");
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
		
		image = new ImageIcon(getClass().getResource("/images/music.jpg")).getImage();
		panel=new JPanel(){
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    g.drawImage(image, 10, 10, this); 
                }
            }
        };
		panel.setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().add(panel);
		int a=20,b=30,c=30,d=30,in=20;
		listModel = new DefaultListModel<>();
        songList = new JList<>(listModel);
        songList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !isDeleting) {
                String selectedTitle = songList.getSelectedValue();
                if (selectedTitle != null) {
                    mostrarDetallesCancion();
                }
            }
            mensaje_.setText("");
        });
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
        mensaje_=new JLabel("");
        panel.add(mensaje_);
        mensaje_.setBounds(254,162,in*6,24);
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
    	mensaje_.setText("agregando canción...");
    	String titulo = tituloText.getText();
        String artista = artistaText.getText();
        Cancion cancion = new Cancion(titulo, artista);
        lista_reproduccion.agregarCancion(cancion);
        listModel.addElement(cancion.getTitulo());
    	mensaje_.setText("canción agregada");
    }
    private void buscar() {
    	mensaje_.setText("buscando canciones...");
    	String atributo = (String) searchAtributes.getSelectedItem();
        String terminoBusqueda = busquedaText.getText().toLowerCase();
        DefaultListModel<String> filteredModel = new DefaultListModel<>();
        for (int i = 0; i < listModel.size(); i++) {
            String titulo = listModel.getElementAt(i);
            Cancion cancion = lista_reproduccion.buscarCancion(titulo);
            if (cancion != null) {
                if (atributo.equals("Título") && cancion.getTitulo().toLowerCase().contains(terminoBusqueda)) {
                    filteredModel.addElement(cancion.getTitulo());
                } else if (atributo.equals("Artista") && cancion.getArtista().toLowerCase().contains(terminoBusqueda)) {
                    filteredModel.addElement(cancion.getTitulo());
                } else if (atributo.equals("Género") && cancion.getGenre().toLowerCase().contains(terminoBusqueda)) {
                    filteredModel.addElement(cancion.getTitulo());
                }
            }
        }
        songList.setModel(filteredModel);
    	mensaje_.setText("busqueda finalizada");
    }
    private void eliminar() {
    	mensaje_.setText("eliminando canción...");
    	isDeleting = true;
    	String titulo = songList.getSelectedValue();
        lista_reproduccion.eliminarCancion(titulo);
        listModel.removeElement(titulo);
        isDeleting = false;
    	mensaje_.setText("canción eliminada");
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
		mensaje_.setText("");
    }
    private void cargarCanciones(String rutaArchivo) {
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(rutaArchivo))
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(',')
                        .withIgnoreQuotations(false)
                        .build())
                .build()) {
            int lineaActual = -1;
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                lineaActual++;
                if (lineaActual == 100000) {
                    break;
                }
                try {
                    if (nextLine.length < 20) {
                        continue;
                    }
                    String artista = nextLine[1];
                    String titulo = nextLine[2];
                    String trackId = nextLine[3];
                    int popularidad = Integer.parseInt(nextLine[4]);
                    int ano = Integer.parseInt(nextLine[5]);
                    String genero = nextLine[6];
                    double danceability = Double.parseDouble(nextLine[7]);
                    double energy = Double.parseDouble(nextLine[8]);
                    int key = Integer.parseInt(nextLine[9]);
                    double loudness = Double.parseDouble(nextLine[10]);
                    int mode = Integer.parseInt(nextLine[11]);
                    double speechiness = Double.parseDouble(nextLine[12]);
                    double acousticness = Double.parseDouble(nextLine[13]);
                    double instrumentalness = Double.parseDouble(nextLine[14]);
                    double liveness = Double.parseDouble(nextLine[15]);
                    double valence = Double.parseDouble(nextLine[16]);
                    double tempo = Double.parseDouble(nextLine[17]);
                    int durationMs = Integer.parseInt(nextLine[18]);
                    int timeSignature = Integer.parseInt(nextLine[19]);
                    Cancion cancion = new Cancion(titulo, artista, trackId, popularidad, ano, genero, danceability, energy, key, loudness, mode, speechiness, acousticness, instrumentalness, liveness, valence, tempo, durationMs, timeSignature);
                    lista_reproduccion.agregarCancion(cancion);
                    listModel.addElement(cancion.getTitulo());
//                    System.out.println(cancion.getTitulo());
                } catch (IllegalArgumentException e) {
                    continue;
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
    private void aplicarEstilos() {
        UIManager.put("Label.font", new Font("Verdana", Font.BOLD, 14));
        UIManager.put("Button.font", new Font("Verdana", Font.PLAIN, 14));
        UIManager.put("TextField.font", new Font("Verdana", Font.PLAIN, 14));
        UIManager.put("ComboBox.font", new Font("Verdana", Font.PLAIN, 14));
        UIManager.put("TextArea.font", new Font("Verdana", Font.PLAIN, 14));

        panel.setBackground(new Color(50, 50, 50));
        Color labelColor = new Color(215, 115, 0); 
        for (JLabel label : listLabels) {
            label.setForeground(labelColor);
        }
        busqueda_.setForeground(labelColor);
        titulo_.setForeground(labelColor);
        artista_.setForeground(labelColor);
        detalles_.setForeground(labelColor);
        buscar_.setForeground(labelColor);
        agregar_.setForeground(labelColor);
        mensaje_.setForeground(labelColor);
        Color textFieldBgColor = new Color(240, 248, 255); 
        Color textFieldFgColor = new Color(15, 00, 00); 
        busquedaText.setBackground(textFieldBgColor);
        tituloText.setBackground(textFieldBgColor);
        artistaText.setBackground(textFieldBgColor);
        songDetail.setBackground(textFieldBgColor);
        songList.setBackground(textFieldBgColor);

        busquedaText.setForeground(textFieldFgColor);
        tituloText.setForeground(textFieldFgColor);
        artistaText.setForeground(textFieldFgColor);
        songDetail.setForeground(textFieldFgColor);
        songList.setForeground(textFieldFgColor);
        Color buttonBgColor = new Color(222, 043, 141); 
        Color buttonFgColor = Color.WHITE;
        agregarCancion.setBackground(buttonBgColor);
        buscarCancion.setBackground(buttonBgColor);
        eliminarCancion.setBackground(buttonBgColor);
        agregar.setBackground(buttonBgColor);
        buscar.setBackground(buttonBgColor);

        agregarCancion.setForeground(buttonFgColor);
        buscarCancion.setForeground(buttonFgColor);
        eliminarCancion.setForeground(buttonFgColor);
        agregar.setForeground(buttonFgColor);
        buscar.setForeground(buttonFgColor);
    }
    private void mostrarDetallesCancion() {
    	String titulo = songList.getSelectedValue();
        Cancion cancion = lista_reproduccion.buscarCancion(titulo);
        if (cancion != null) {
            songDetail.setText(("Título: " + cancion.getTitulo() + "\n" +
                                    "Artista: " + cancion.getArtista() + "\n" +
                                    "Track ID: " + cancion.getTrackId() + "\n" +
                                    "Popularidad: " + cancion.getPopularity() + "\n" +
                                    "Año: " + cancion.getYear() + "\n" +
                                    "Género: " + cancion.getGenre() + "\n" +
                                    "Danceabilidad: " + cancion.getDanceability() + "\n" +
                                    "Energía: " + cancion.getEnergy() + "\n" +
                                    "Clave: " + cancion.getKey() + "\n" +
                                    "Sonoridad: " + cancion.getLoudness() + "\n" +
                                    "Modo: " + cancion.getMode() + "\n" +
                                    "Habladuría: " + cancion.getSpeechiness() + "\n" +
                                    "Acousticness: " + cancion.getAcousticness() + "\n" +
                                    "Instrumentalness: " + cancion.getInstrumentalness() + "\n" +
                                    "Vivacidad: " + cancion.getLiveness() + "\n" +
                                    "Valencia: " + cancion.getValence() + "\n" +
                                    "Tempo: " + cancion.getTempo() + "\n" +
                                    "Duración (ms): " + cancion.getDurationMs() + "\n" +
                                    "Compás: " + cancion.getTimeSignature()));
        } else {
            songDetail.setText("");
        }
    }
}

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

public class Interfaz extends JFrame {
    // paneles contenedores
    JPanel panelDatos;
    JPanel panelTabla;
    JPanel panelBusqueda;
    JPanel panelOpciones;
    JPanel panelDetallesBusqueda;
    JPanel panelListas;
    JPanel panelFiltro;
    JPanel panelCambiarPosicion;
    JPanel panelReproduccion;

    // datos
    JTextField titulo, artista, idCancion, genero, speechiness, tempo, id;
    PlaceholderTextField popularidad, anio, danceability, energy, key, loudness, acousticness;
    PlaceholderTextField instrumentalness, liveness, valence, mode, timeSignature, durationMs;

    JTable tablaCanciones;
    private JTextField buscar;
    private JTextField cambiarDe;
    private JTextField cambiarA;

    ListaEnlazada listaCanciones;
    private int idgenerado;

    public Interfaz() {
        listaCanciones = new ListaEnlazada();
        tablaCanciones = new JTable(
                new DefaultTableModel(new Object[] { "ID", "Nombre", "Artista", "Año", "Duración", "Popularidad" }, 0));
        posicionar();

        new CargarDatosWorker("data/spotify_data.csv", listaCanciones, tablaCanciones).execute();

        tablaCanciones.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = tablaCanciones.getSelectedRow();
            if (selectedRow >= 0) {
                Cancion cancionSeleccionada = listaCanciones.get(selectedRow);
                titulo.setText(String.valueOf(cancionSeleccionada.getTrack_name()));
                artista.setText(String.valueOf(cancionSeleccionada.getArtist_name()));
                idCancion.setText(String.valueOf(cancionSeleccionada.getTrack_id()));
                popularidad.setText(String.valueOf(cancionSeleccionada.getPopularity()));
                anio.setText(String.valueOf(cancionSeleccionada.getYear()));
                genero.setText(String.valueOf(cancionSeleccionada.getGenre()));
                danceability.setText(String.valueOf(cancionSeleccionada.getDanceability()));
                energy.setText(String.valueOf(cancionSeleccionada.getEnergy()));
                key.setText(String.valueOf(cancionSeleccionada.getKey()));
                loudness.setText(String.valueOf(cancionSeleccionada.getLoudness()));
                speechiness.setText(String.valueOf(cancionSeleccionada.getSpeechiness()));
                acousticness.setText(String.valueOf(cancionSeleccionada.getAcousticness()));
                instrumentalness.setText(String.valueOf(cancionSeleccionada.getInstrumentalness()));
                liveness.setText(String.valueOf(cancionSeleccionada.getLiveness()));
                valence.setText(String.valueOf(cancionSeleccionada.getValence()));
                tempo.setText(String.valueOf(cancionSeleccionada.getTempo()));
                durationMs.setText(String.valueOf(cancionSeleccionada.getDuration_ms()));
                mode.setText(String.valueOf(cancionSeleccionada.getMode()));
                timeSignature.setText(String.valueOf(cancionSeleccionada.getTime_signature()));
                id.setText(String.valueOf(cancionSeleccionada.getId()));
            }
        });
        this.id.setEditable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interfaz().setVisible(true);
        });
    }

    public void posicionar() {
        setTitle("Gestor de canciones");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializando componentes de la tabla canciones
        String[] columnaNombres = { "ID", "Nombre", "Artista", "Año", "Duración", "Popularidad" };
        DefaultTableModel modeloTabla = new DefaultTableModel(columnaNombres, 0);
        tablaCanciones = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaCanciones);

        // panel de datos
        // label de panel datos
        JLabel tituloLabel = new JLabel("Titulo: ");
        tituloLabel.setBounds(9, 5, 33, 13);
        JLabel artistaLabel = new JLabel("Artista(s): ");
        artistaLabel.setBounds(9, 20, 51, 13);
        JLabel idCancionLabel = new JLabel("ID de la cancion: ");
        idCancionLabel.setBounds(9, 35, 80, 13);
        JLabel popularidadLabel = new JLabel("Popularidad: ");
        popularidadLabel.setBounds(9, 50, 61, 13);
        JLabel anioLabel = new JLabel("Año: ");
        anioLabel.setBounds(9, 65, 25, 13);
        JLabel generoLabel = new JLabel("Genero: ");
        generoLabel.setBounds(9, 80, 40, 13);
        JLabel danceabilityLabel = new JLabel("Bailabilidad: ");
        danceabilityLabel.setBounds(9, 95, 58, 13);
        JLabel energyLabel = new JLabel("Energia: ");
        energyLabel.setBounds(9, 110, 42, 13);
        JLabel keyLabel = new JLabel("Clave: ");
        keyLabel.setBounds(9, 125, 32, 13);
        JLabel loudnessLabel = new JLabel("Volumen: ");
        loudnessLabel.setBounds(192, 5, 47, 13);
        JLabel speechinessLabel = new JLabel("Speechiness: ");
        speechinessLabel.setBounds(192, 23, 64, 13);
        JLabel acousticnessLabel = new JLabel("Acustico: ");
        acousticnessLabel.setBounds(194, 46, 45, 13);
        JLabel instrumentalnessLabel = new JLabel("Instrumentalidad: ");
        instrumentalnessLabel.setBounds(194, 69, 84, 13);
        JLabel livenessLabel = new JLabel("Liveness: ");
        livenessLabel.setBounds(193, 92, 46, 13);
        JLabel valenceLabel = new JLabel("Valencia: ");
        valenceLabel.setBounds(193, 115, 46, 13);
        JLabel tempoLabel = new JLabel("Tempo: ");
        tempoLabel.setBounds(193, 138, 39, 13);
        JLabel modeLabel = new JLabel("Mode: ");
        modeLabel.setBounds(9, 140, 32, 13);
        JLabel timeSignatureLabel = new JLabel("Compas: ");
        timeSignatureLabel.setBounds(9, 155, 44, 13);
        JLabel durationMsLabel = new JLabel("Duracion: ");
        durationMsLabel.setBounds(193, 157, 47, 13);

        titulo = new JTextField();
        titulo.setBounds(90, 2, 62, 19);
        artista = new JTextField();
        artista.setBounds(90, 20, 50, 19);
        idCancion = new JTextField();
        idCancion.setBounds(90, 35, 50, 19);

        // JTextField popularidad = new JTextField();
        popularidad = new PlaceholderTextField("Ingrese valores de 0 a 100");
        popularidad.setBounds(90, 50, 62, 19);
        ((AbstractDocument) popularidad.getDocument()).setDocumentFilter(new LimitadorDatos(0, 100));

        // JTextField anio = new JTextField();
        anio = new PlaceholderTextField("Ingrese un año entre 1860 y 2024");
        anio.setBounds(90, 65, 64, 19);
        ((AbstractDocument) anio.getDocument()).setDocumentFilter(new LimitadorDatos(1860, 2024));

        genero = new JTextField();
        genero.setBounds(90, 80, 53, 19);

        // JTextField danceability = new JTextField();
        danceability = new PlaceholderTextField("0 a 1.0");
        danceability.setBounds(90, 95, 62, 19);
        ((AbstractDocument) danceability.getDocument()).setDocumentFilter(new LimitadorDatos(0.0, 1.0, 4, true));

        // JTextField energy = new JTextField();
        energy = new PlaceholderTextField("0 a 1.0");
        energy.setBounds(90, 110, 62, 19);
        ((AbstractDocument) energy.getDocument()).setDocumentFilter(new LimitadorDatos(0.0, 1.0, 4, true));

        // JTextField key = new JTextField();
        key = new PlaceholderTextField("0 a 11");
        key.setBounds(90, 125, 62, 19);
        ((AbstractDocument) key.getDocument()).setDocumentFilter(new LimitadorDatos(0, 11));

        // JTextField loudness = new JTextField();
        loudness = new PlaceholderTextField("dbs -60 a 0");
        loudness.setBounds(274, 2, 62, 19);
        ((AbstractDocument) loudness.getDocument()).setDocumentFilter(new LimitadorDatos(-60.0, 0.0, 6, true));

        speechiness = new JTextField();
        speechiness.setBounds(274, 20, 39, 19);

        // JTextField acousticness = new JTextField();
        acousticness = new PlaceholderTextField("0 a 1.0");
        acousticness.setBounds(274, 43, 62, 19);
        ((AbstractDocument) acousticness.getDocument()).setDocumentFilter(new LimitadorDatos(0.0, 1.0, 4, true));

        // JTextField instrumentalness = new JTextField();
        instrumentalness = new PlaceholderTextField("0 a 1.0");
        instrumentalness.setBounds(274, 66, 62, 19);
        ((AbstractDocument) instrumentalness.getDocument()).setDocumentFilter(new LimitadorDatos(0.0, 1.0, 4, true));

        // JTextField liveness = new JTextField();
        liveness = new PlaceholderTextField("0 a 1.0");
        liveness.setBounds(274, 89, 62, 19);
        ((AbstractDocument) liveness.getDocument()).setDocumentFilter(new LimitadorDatos(0.0, 1.0, 4, true));

        // JTextField valence = new JTextField();
        valence = new PlaceholderTextField("0 a 1.0");
        valence.setBounds(274, 112, 62, 19);
        ((AbstractDocument) valence.getDocument()).setDocumentFilter(new LimitadorDatos(0.0, 1.0, 4, true));

        tempo = new JTextField();
        tempo.setBounds(274, 135, 32, 19);

        durationMs = new JTextField();
        durationMs.setBounds(274, 164, 39, 19);

        // JTextField mode = new JTextField();
        mode = new PlaceholderTextField("0 a 1");
        mode.setBounds(90, 140, 62, 19);
        ((AbstractDocument) mode.getDocument()).setDocumentFilter(new LimitadorDatos(0, 1));

        // JTextField timeSignature = new JTextField();
        timeSignature = new PlaceholderTextField("0 a 7");
        timeSignature.setBounds(90, 155, 62, 19);
        ((AbstractDocument) timeSignature.getDocument()).setDocumentFilter(new LimitadorDatos(0, 7));

        panelDatos.setLayout(null);

        panelDatos.add(modeLabel);
        panelDatos.add(energy);
        panelDatos.add(durationMsLabel);
        panelDatos.add(tempoLabel);
        panelDatos.add(valenceLabel);
        panelDatos.add(livenessLabel);
        panelDatos.add(instrumentalnessLabel);
        panelDatos.add(acousticnessLabel);
        panelDatos.add(speechinessLabel);
        panelDatos.add(loudnessLabel);
        panelDatos.add(keyLabel);
        panelDatos.add(energyLabel);
        panelDatos.add(danceabilityLabel);
        panelDatos.add(generoLabel);
        panelDatos.add(anioLabel);
        panelDatos.add(popularidadLabel);
        panelDatos.add(idCancionLabel);
        panelDatos.add(artistaLabel);
        panelDatos.add(tituloLabel);
        panelDatos.add(timeSignature);
        panelDatos.add(durationMs);
        panelDatos.add(tempo);
        panelDatos.add(valence);
        panelDatos.add(liveness);
        panelDatos.add(instrumentalness);
        panelDatos.add(acousticness);
        panelDatos.add(speechiness);
        panelDatos.add(mode);
        panelDatos.add(loudness);
        panelDatos.add(timeSignatureLabel);
        panelDatos.add(danceability);
        panelDatos.add(genero);
        panelDatos.add(anio);
        panelDatos.add(popularidad);
        panelDatos.add(idCancion);
        panelDatos.add(artista);
        panelDatos.add(titulo);
        panelDatos.add(key);

        // configuracion del panel principal
        panel.setLayout(null);
        panel.add(panelBusqueda);
        panel.add(panelDatos);
        panel.add(panelDetallesBusqueda);
        panel.add(panelListas);
        panel.add(panelOpciones);
        panel.add(panelTabla);
        panel.add(panelFiltro);

        panelCambiarPosicion.setBounds(435, 141, 341, 93);
        panel.add(panelCambiarPosicion);
        panelCambiarPosicion.setLayout(null);

        // cambiar posicion de cancion
        JLabel labelCambiarPosicion = new JLabel("Cambiar posicion: ");
        JLabel labelCambiarDe = new JLabel("De: ");
        JLabel labelCambiarA = new JLabel("A: ");
        cambiarA = new JTextField();
        cambiarDe = new JTextField();
        //
        JButton botonCambiar = new JButton("Cambiar");
        botonCambiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarPosicion();
            }
        });
        labelCambiarPosicion.setBounds(10, 10, 101, 13);
        labelCambiarDe.setBounds(10, 36, 45, 13);
        labelCambiarA.setBounds(165, 36, 23, 13);
        cambiarA.setBounds(198, 33, 96, 19);
        cambiarDe.setBounds(37, 33, 96, 19);
        botonCambiar.setBounds(103, 62, 85, 21);
        panelCambiarPosicion.add(labelCambiarPosicion);
        panelCambiarPosicion.add(cambiarDe);
        panelCambiarPosicion.add(labelCambiarA);
        panelCambiarPosicion.add(labelCambiarDe);
        panelCambiarPosicion.add(cambiarA);
        panelCambiarPosicion.add(botonCambiar);

        panelReproduccion.setBounds(23, 510, 433, 43);
        panel.add(panelReproduccion);
        panelReproduccion.setLayout(null);

        // guardar lista - reproduccion aleatoria
        JButton botonGuardarLista = new JButton("Guardar lista");
        botonGuardarLista.setBounds(253, 10, 91, 21);
        botonGuardarLista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarLista();
            }
        });
        panelReproduccion.add(botonGuardarLista);

        // reproduccion aleatoria
        JButton botonReproduccionAleatoria = new JButton("Reproduccion aleatoria");
        botonReproduccionAleatoria.setBounds(20, 10, 193, 21);
        botonReproduccionAleatoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproduccionAleatoria();
            }
        });
        panelReproduccion.add(botonReproduccionAleatoria);
        panelBusqueda.setBounds(23, 200, 395, 34);
        panelBusqueda.setLayout(null);

        buscar = new JTextField();
        buscar.setBounds(10, 10, 191, 19);
        panelBusqueda.add(buscar);
        buscar.setColumns(10);

        JComboBox<String> buscarPor = new JComboBox<>();
        buscarPor.setBounds(211, 9, 82, 21);
        buscarPor.addItem("Titulo");
        buscarPor.addItem("Artista");
        panelBusqueda.add(buscarPor);

        // buscar cancion por
        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.setBounds(303, 9, 85, 21);
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarCancion();
            }
        });
        panelBusqueda.add(botonBuscar);
        panelDatos.setBounds(23, 10, 362, 180);
        panelDetallesBusqueda.setBounds(590, 244, 186, 256);
        JLabel labelResultadoBusueda = new JLabel("Datos de la cancion buscada: ");
        panelDetallesBusqueda.add(labelResultadoBusueda);
        panelListas.setBounds(498, 510, 278, 43);
        panelListas.setLayout(null);

        JComboBox<String> listasGuardadas = new JComboBox<>();
        listasGuardadas.setBounds(61, 10, 113, 22);

        panelListas.add(listasGuardadas);

        // mostrar lista guardada
        JButton botonListaGuardada = new JButton("Ir");
        botonListaGuardada.setBounds(184, 11, 85, 21);
        botonListaGuardada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                irAListaGuardada();
            }
        });
        panelListas.add(botonListaGuardada);

        JLabel labelIrLista = new JLabel("Ir a lista:");
        labelIrLista.setBounds(10, 15, 45, 13);
        panelListas.add(labelIrLista);
        panelOpciones.setBounds(395, 10, 102, 124);
        panelOpciones.setLayout(null);

        // añadir cancion
        JButton botonAñadir = new JButton("Añadir");
        botonAñadir.setBounds(10, 10, 84, 21);
        botonAñadir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                añadirCancion();
            }
        });
        panelOpciones.add(botonAñadir);

        // eliminar cancion seleccionada
        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.setBounds(10, 41, 85, 21);
        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarCancion();
            }
        });
        panelOpciones.add(botonEliminar);

        // editar cancion seleccionada
        JButton botonEditar = new JButton("Editar");
        botonEditar.setBounds(9, 72, 85, 21);
        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarCancion();
            }
        });
        panelOpciones.add(botonEditar);

        // limpiar datos
        JButton botonLimpiarDatos = new JButton("Limpiar");
        botonLimpiarDatos.setBounds(9, 103, 85, 21);
        botonLimpiarDatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarDatos();
            }
        });

        panelOpciones.add(botonLimpiarDatos);
        panelTabla.setBounds(21, 244, 559, 256);
        panelTabla.setLayout(new BorderLayout());
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        panelFiltro.setBounds(507, 10, 269, 124);
        panelFiltro.setLayout(null);

        JComboBox<String> ordenarPor = new JComboBox<>();
        ordenarPor.setBounds(10, 10, 92, 21);
        ordenarPor.addItem("Popularidad");
        ordenarPor.addItem("Año");
        ordenarPor.addItem("Duracion");
        panelFiltro.add(ordenarPor);

        JComboBox<Integer> añoEspecifico = new JComboBox<>();
        añoEspecifico.setBounds(130, 10, 105, 21);
        for (int i = 2000; i < 2024; i++) {
            añoEspecifico.addItem(i);
        }
        panelFiltro.add(añoEspecifico);

        // filtrar canciones por
        JButton botonFiltrar = new JButton("Filtrar");
        botonFiltrar.setBounds(78, 82, 85, 21);
        botonFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtrarCanciones();
            }
        });
        panelFiltro.add(botonFiltrar);

        ButtonGroup ascendenteDescendente = new ButtonGroup();
        JRadioButton ascendente = new JRadioButton("Ascendente");
        ascendente.setBounds(21, 55, 103, 21);
        panelFiltro.add(ascendente);

        JRadioButton descendente = new JRadioButton("Descendente");
        descendente.setBounds(142, 55, 103, 21);
        panelFiltro.add(descendente);
        ascendenteDescendente.add(ascendente);
        ascendenteDescendente.add(descendente);
        getContentPane().add(panel);
    }

    private void agregarCampos(JPanel panel) {
        titulo = new JTextField();
        artista = new JTextField();
        idCancion = new JTextField();

        popularidad = new PlaceholderTextField("0-100");
        ((AbstractDocument) popularidad.getDocument()).setDocumentFilter(new LimitadorDatos(0, 100));
        anio = new PlaceholderTextField("Introduzca un año entre 1860-2024");
        ((AbstractDocument) anio.getDocument()).setDocumentFilter(new LimitadorDatos(0, 2024));
        genero = new JTextField();
        danceability = new PlaceholderTextField("0-1.0");
        ((AbstractDocument) danceability.getDocument()).setDocumentFilter(new LimitadorDatos(0.0, 1.0, 4, true));
        energy = new PlaceholderTextField("0-1.0");
        ((AbstractDocument) energy.getDocument()).setDocumentFilter(new LimitadorDatos(0.0, 1.0, 4, true));
        key = new PlaceholderTextField("0-11");
        ((AbstractDocument) key.getDocument()).setDocumentFilter(new LimitadorDatos(0, 11));
        loudness = new PlaceholderTextField("dbs -60 a 0");
        ((AbstractDocument) loudness.getDocument()).setDocumentFilter(new LimitadorDatos(-60.0, 0.0, 6, true));
        speechiness = new JTextField();
        acousticness = new PlaceholderTextField("0-1.0");
        ((AbstractDocument) acousticness.getDocument()).setDocumentFilter(new LimitadorDatos(0.0, 1.0, 4, true));
        instrumentalness = new PlaceholderTextField("0-1.0");
        ((AbstractDocument) instrumentalness.getDocument()).setDocumentFilter(new LimitadorDatos(0.0, 1.0, 4, true));
        liveness = new PlaceholderTextField("0-1.0");
        ((AbstractDocument) liveness.getDocument()).setDocumentFilter(new LimitadorDatos(0.0, 1.0, 4, true));
        valence = new PlaceholderTextField("0-1.0");
        ((AbstractDocument) valence.getDocument()).setDocumentFilter(new LimitadorDatos(0.0, 1.0, 4, true));
        tempo = new JTextField();
        durationMs = new PlaceholderTextField("Duracion en milisegundos");
        ((AbstractDocument) valence.getDocument()).setDocumentFilter(new LimitadorDatos(0, 10000));
        mode = new PlaceholderTextField("0-1");
        ((AbstractDocument) mode.getDocument()).setDocumentFilter(new LimitadorDatos(0, 1));
        timeSignature = new PlaceholderTextField("0-7");
        ((AbstractDocument) timeSignature.getDocument()).setDocumentFilter(new LimitadorDatos(0, 7));
        id = new JTextField();
        id.setText(String.valueOf(generarId()));

        // Añadir componentes al panel de datos
        panelDatos.add(new JLabel("Titulo: "));
        panelDatos.add(titulo);
        panelDatos.add(new JLabel("Artista(s): "));
        panelDatos.add(artista);
        panelDatos.add(new JLabel("ID de la cancion: "));
        panelDatos.add(idCancion);
        panelDatos.add(new JLabel("Popularidad: "));
        panelDatos.add(popularidad);
        panelDatos.add(new JLabel("Año: "));
        panelDatos.add(anio);
        panelDatos.add(new JLabel("Genero: "));
        panelDatos.add(genero);
        panelDatos.add(new JLabel("Bailabilidad: "));
        panelDatos.add(danceability);
        panelDatos.add(new JLabel("Energia: "));
        panelDatos.add(energy);
        panelDatos.add(new JLabel("Clave: "));
        panelDatos.add(key);
        panelDatos.add(new JLabel("Volumen: "));
        panelDatos.add(loudness);
        panelDatos.add(new JLabel("Speechiness: "));
        panelDatos.add(speechiness);
        panelDatos.add(new JLabel("Acustico: "));
        panelDatos.add(acousticness);
        panelDatos.add(new JLabel("Instrumentalidad: "));
        panelDatos.add(instrumentalness);
        panelDatos.add(new JLabel("Liveness: "));
        panelDatos.add(liveness);
        panelDatos.add(new JLabel("Valencia: "));
        panelDatos.add(valence);
        panelDatos.add(new JLabel("Tempo: "));
        panelDatos.add(tempo);
        panelDatos.add(new JLabel("Mode: "));
        panelDatos.add(mode);
        panelDatos.add(new JLabel("Compas: "));
        panelDatos.add(timeSignature);
        panelDatos.add(new JLabel("Duracion: "));
        panelDatos.add(durationMs);
        panelDatos.add(new JLabel("ID: "));
        panelDatos.add(id);
    }

    private void añadirCancion() {
        String tituloCancion = titulo.getText().trim();
        if (tituloCancion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título de la canción es obligatorio.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String artistaCancion = artista.getText().trim().isEmpty() ? "" : artista.getText().trim();
        String idCancionTexto = idCancion.getText().trim().isEmpty() ? "" : idCancion.getText().trim();
        int popularidadCancion = popularidad.getText().trim().isEmpty() ? 0
                : Integer.parseInt(popularidad.getText().trim());
        int anioCancion = anio.getText().trim().isEmpty() ? 0 : Integer.parseInt(anio.getText().trim());
        String generoCancion = genero.getText().trim().isEmpty() ? "" : genero.getText().trim();
        double danceabilityCancion = danceability.getText().trim().isEmpty() ? 0.0
                : Double.parseDouble(danceability.getText().trim());
        double energyCancion = energy.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(energy.getText().trim());
        int keyCancion = key.getText().trim().isEmpty() ? 0 : Integer.parseInt(key.getText().trim());
        double loudnessCancion = loudness.getText().trim().isEmpty() ? 0.0
                : Double.parseDouble(loudness.getText().trim());
        double speechinessCancion = speechiness.getText().trim().isEmpty() ? 0.0
                : Double.parseDouble(speechiness.getText().trim());
        double acousticnessCancion = acousticness.getText().trim().isEmpty() ? 0.0
                : Double.parseDouble(acousticness.getText().trim());
        double instrumentalnessCancion = instrumentalness.getText().trim().isEmpty() ? 0.0
                : Double.parseDouble(instrumentalness.getText().trim());
        double livenessCancion = liveness.getText().trim().isEmpty() ? 0.0
                : Double.parseDouble(liveness.getText().trim());
        double valenceCancion = valence.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(valence.getText().trim());
        double tempoCancion = tempo.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(tempo.getText().trim());
        int durationMsCancion = durationMs.getText().trim().isEmpty() ? 0
                : Integer.parseInt(durationMs.getText().trim());
        int modeCancion = mode.getText().trim().isEmpty() ? 0 : Integer.parseInt(mode.getText().trim());
        int timeSignatureCancion = timeSignature.getText().trim().isEmpty() ? 0
                : Integer.parseInt(timeSignature.getText().trim());

        Cancion nuevaCancion = new Cancion(artistaCancion,
                tituloCancion, idCancionTexto, popularidadCancion, anioCancion,
                generoCancion, danceabilityCancion, energyCancion, keyCancion, loudnessCancion, modeCancion,
                speechinessCancion, acousticnessCancion, instrumentalnessCancion, livenessCancion,
                valenceCancion, tempoCancion, durationMsCancion, timeSignatureCancion, id);

        listaCanciones.agregarCancion(nuevaCancion);

        DefaultTableModel model = (DefaultTableModel) tablaCanciones.getModel();
        model.addRow(new Object[] {
                idCancionTexto,
                tituloCancion,
                artistaCancion,
                anioCancion,
                durationMsCancion,
                popularidadCancion
        });

        limpiarDatos();
        this.id++;
    }

    private void eliminarCancion() {
        int selectedRow = tablaCanciones.getSelectedRow();
        if (selectedRow >= 0) {
            Cancion cancionAeliminar = listaCanciones.get(selectedRow);
            listaCanciones.eliminarCancion(cancionAeliminar);

            DefaultTableModel model = (DefaultTableModel) tablaCanciones.getModel();
            model.removeRow(selectedRow);

            limpiarDatos();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una canción para eliminar.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarCancion() {
        int selectedRow = tablaCanciones.getSelectedRow();
        if (selectedRow >= 0) {
            String tituloCancion = titulo.getText().trim();
            if (tituloCancion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El título de la canción es obligatorio.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cancion cancion = listaCanciones.get(selectedRow);

            cancion.setTrack_name(tituloCancion);
            cancion.setArtist_name(artista.getText().trim().isEmpty() ? "" : artista.getText().trim());
            cancion.setTrack_id(idCancion.getText().trim().isEmpty() ? "" : idCancion.getText().trim());
            cancion.setPopularity(
                    popularidad.getText().trim().isEmpty() ? 0 : Integer.parseInt(popularidad.getText().trim()));
            cancion.setYear(anio.getText().trim().isEmpty() ? 0 : Integer.parseInt(anio.getText().trim()));
            cancion.setGenre(genero.getText().trim().isEmpty() ? "" : genero.getText().trim());
            cancion.setDanceability(
                    danceability.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(danceability.getText().trim()));
            cancion.setEnergy(energy.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(energy.getText().trim()));
            cancion.setKey(key.getText().trim().isEmpty() ? 0 : Integer.parseInt(key.getText().trim()));
            cancion.setLoudness(
                    loudness.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(loudness.getText().trim()));
            cancion.setSpeechiness(
                    speechiness.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(speechiness.getText().trim()));
            cancion.setAcousticness(
                    acousticness.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(acousticness.getText().trim()));
            cancion.setInstrumentalness(instrumentalness.getText().trim().isEmpty() ? 0.0
                    : Double.parseDouble(instrumentalness.getText().trim()));
            cancion.setLiveness(
                    liveness.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(liveness.getText().trim()));
            cancion.setValence(valence.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(valence.getText().trim()));
            cancion.setTempo(tempo.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(tempo.getText().trim()));
            cancion.setDuration_ms(
                    durationMs.getText().trim().isEmpty() ? 0 : Integer.parseInt(durationMs.getText().trim()));
            cancion.setMode(mode.getText().trim().isEmpty() ? 0 : Integer.parseInt(mode.getText().trim()));
            cancion.setTime_signature(
                    timeSignature.getText().trim().isEmpty() ? 0 : Integer.parseInt(timeSignature.getText().trim()));

            DefaultTableModel model = (DefaultTableModel) tablaCanciones.getModel();
            model.setValueAt(cancion.getTrack_id(), selectedRow, 0);
            model.setValueAt(cancion.getTrack_name(), selectedRow, 1);
            model.setValueAt(cancion.getArtist_name(), selectedRow, 2);
            model.setValueAt(cancion.getYear(), selectedRow, 3);
            model.setValueAt(cancion.getDuration_ms(), selectedRow, 4);
            model.setValueAt(cancion.getPopularity(), selectedRow, 5);

            limpiarDatos();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una canción para editar.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarDatos() {
        titulo.setText("");
        artista.setText("");
        idCancion.setText("");
        popularidad.setText("");
        anio.setText("");
        genero.setText("");
        danceability.setText("");
        energy.setText("");
        key.setText("");
        loudness.setText("");
        speechiness.setText("");
        acousticness.setText("");
        instrumentalness.setText("");
        liveness.setText("");
        valence.setText("");
        tempo.setText("");
        durationMs.setText("");
        mode.setText("");
        timeSignature.setText("");
    }

    private void buscarCancion() {

    }

    private void filtrarCanciones() {

    }

    private void cambiarPosicion() {

    }

    private void guardarLista() {

    }

    private void reproduccionAleatoria() {

    }

    private void irAListaGuardada() {

    }

    public void aplicarEstilos() {

    }
}
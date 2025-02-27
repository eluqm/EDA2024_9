package com.canciones;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Comparator;

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
    PlaceholderTextField instrumentalness, liveness, valence, mode, timeSignature, durationMs, limiteReproduccion;

    // comboBox filtrar
    JComboBox<String> ordenarPor;
    JComboBox<Integer> añoEspecifico;
    JRadioButton ascendente;
    JRadioButton descendente;

    JTable tablaCanciones;
    JTable tablaResultadosBusqueda = new JTable(
            new DefaultTableModel(new Object[] { "ID", "Nombre", "Artista", "Año", "Duración", "Popularidad" }, 0));

    private JTextField buscar;
    private JTextField cambiarDe;
    private JTextField cambiarA;

    ListaEnlazada listaCanciones;
    ListaEnlazada listaAleatoria;
    private HashMap<String, ListaEnlazada> listasGuardadas = new HashMap<>();
    JComboBox<String> listasGuardadasComboBox;

    public Interfaz() {
        listaCanciones = new ListaEnlazada();
        tablaCanciones = new JTable(
                new DefaultTableModel(new Object[] { "ID", "Nombre", "Artista", "Año", "Duración", "Popularidad" }, 0));
        posicionar();
        listaAleatoria = new ListaEnlazada();
        new CargarDatos("data/spotify_data.csv", listaCanciones, tablaCanciones).execute();

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
        actualizarListasGuardadasComboBox();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interfaz().setVisible(true);
        });
    }

    public void posicionar() {
        setTitle("Gestor de canciones");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        // panel datos
        panelDatos = new JPanel();
        panelDatos.setLayout(new GridLayout(10, 4));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de la cancion"));
        agregarCampos(panelDatos);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(panelDatos, c);

        // panel opciones
        panelOpciones = new JPanel();
        panelOpciones.setLayout(new GridLayout(4, 1));
        // Botón añadir cancion
        JButton botonAñadir = new JButton("Añadir");
        botonAñadir.addActionListener(e -> añadirCancion());
        panelOpciones.add(botonAñadir);
        // eliminar cancion seleccionada
        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.addActionListener(e -> eliminarCancion());
        panelOpciones.add(botonEliminar);
        // editar cancion seleccionada
        JButton botonEditar = new JButton("Editar");
        botonEditar.addActionListener(e -> editarCancion());
        panelOpciones.add(botonEditar);
        // limpiar datos
        JButton botonLimpiarDatos = new JButton("Limpiar");
        // botonLimpiarDatos.setBounds(9, 103, 85, 21);
        botonLimpiarDatos.addActionListener(e -> limpiarDatos());
        panelOpciones.add(botonLimpiarDatos);
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 1;
        add(panelOpciones, c);

        // panel filtro
        panelFiltro = new JPanel();
        panelFiltro.setLayout(new GridLayout(3, 2));
        panelFiltro.setBorder(BorderFactory.createTitledBorder("Filtrar"));
        // opciones de filtrado
        ordenarPor = new JComboBox<>();
        ordenarPor.addItem("Popularidad");
        ordenarPor.addItem("Año");
        ordenarPor.addItem("Duracion");
        panelFiltro.add(ordenarPor);
        // año especifico
        añoEspecifico = new JComboBox<>();
        añoEspecifico.addItem(null);
        for (int i = 2000; i < 2024; i++) {
            añoEspecifico.addItem(i);
        }
        panelFiltro.add(añoEspecifico);
        // boton filtrar canciones por
        JButton botonFiltrar = new JButton("Filtrar");
        botonFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtrarCanciones();
            }
        });
        // radiobutton ascendente/descendente
        ButtonGroup ascendenteDescendente = new ButtonGroup();
        ascendente = new JRadioButton("Ascendente");
        panelFiltro.add(ascendente);
        descendente = new JRadioButton("Descendente");
        panelFiltro.add(descendente);
        ascendenteDescendente.add(ascendente);
        ascendenteDescendente.add(descendente);
        panelFiltro.add(botonFiltrar);
        c.gridx = 4;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 0.0;
        add(panelFiltro, c);

        // panel cambiar posicion
        panelCambiarPosicion = new JPanel();
        panelCambiarPosicion.setLayout(new GridLayout(1, 3));
        panelCambiarPosicion.setBorder(BorderFactory.createTitledBorder("Cambiar Posición"));
        JLabel labelCambiarDe = new JLabel("De: ");
        JLabel labelCambiarA = new JLabel("A: ");
        cambiarA = new JTextField();
        cambiarDe = new JTextField();
        // boton cambiar posicion
        JButton botonCambiar = new JButton("Cambiar");
        botonCambiar.addActionListener(e -> cambiarPosicion());
        panelCambiarPosicion.add(labelCambiarDe);
        panelCambiarPosicion.add(cambiarDe);
        panelCambiarPosicion.add(labelCambiarA);
        panelCambiarPosicion.add(cambiarA);
        panelCambiarPosicion.add(botonCambiar);
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 2;
        add(panelCambiarPosicion, c);

        // panel busqueda
        panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new GridLayout(1, 3));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Búsqueda"));
        buscar = new JTextField();
        panelBusqueda.add(buscar);
        buscar.setColumns(10);
        // opciones para buscar por
        JComboBox<String> buscarPor = new JComboBox<>(new String[] { "Título", "Artista" });
        panelBusqueda.add(buscarPor);
        // boton buscar cancion por
        JButton botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(e -> buscarCancion(buscar.getText(), buscarPor.getSelectedItem().toString()));
        panelBusqueda.add(botonBuscar);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        add(panelBusqueda, c);

        // panel tabla canciones
        panelTabla = new JPanel(new BorderLayout());
        // Inicializando componentes de la tabla canciones
        panelTabla.add(new JScrollPane(tablaCanciones), BorderLayout.CENTER);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 4;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(panelTabla, c);

        // panel detalles busqueda
        panelDetallesBusqueda = new JPanel();
        panelDetallesBusqueda.setLayout(new GridLayout(1, 2));
        panelDetallesBusqueda.setBorder(BorderFactory.createTitledBorder("Detalles de Búsqueda"));
        tablaResultadosBusqueda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPaneResultados = new JScrollPane(tablaResultadosBusqueda);
        scrollPaneResultados.setPreferredSize(new Dimension(600, 200));
        panelDetallesBusqueda.add(scrollPaneResultados, BorderLayout.CENTER);
        c.gridx = 4;
        c.gridy = 2;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        add(panelDetallesBusqueda, c);

        // panel reproduccion aleatoria
        panelReproduccion = new JPanel();
        panelReproduccion.setLayout(new GridLayout(1, 3));
        limiteReproduccion = new PlaceholderTextField("¿De cuántas canciones será su lista aleatoria?");
        panelReproduccion.add(limiteReproduccion);
        panelReproduccion.setBorder(BorderFactory.createTitledBorder("Reproducción Aleatoria"));
        JButton botonReproduccionAleatoria = new JButton("Reproduccion aleatoria");
        botonReproduccionAleatoria.addActionListener(e -> reproduccionAleatoria());
        panelReproduccion.add(botonReproduccionAleatoria);
        // guardar lista - reproduccion aleatoria
        JButton botonGuardarLista = new JButton("Guardar lista");
        botonGuardarLista.addActionListener(e -> guardarLista());
        panelReproduccion.add(botonGuardarLista);
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 4;
        c.weightx = 0.0;
        c.weighty = 0.0;
        add(panelReproduccion, c);

        // panel listas guardadas
        panelListas = new JPanel();
        panelListas.setLayout(new GridLayout(1, 3));
        panelListas.setBorder(BorderFactory.createTitledBorder("Listas Guardadas"));
        listasGuardadasComboBox = new JComboBox<>();
        panelListas.add(listasGuardadasComboBox);
        // mostrar lista guardada
        JButton botonListaGuardada = new JButton("Ir");
        botonListaGuardada.addActionListener(e -> irAListaGuardada());
        JLabel labelIrLista = new JLabel("Ir a lista:");
        panelListas.add(labelIrLista);
        panelListas.add(botonListaGuardada);
        c.gridx = 4;
        c.gridy = 4;
        c.gridwidth = 3;
        add(panelListas, c);
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

    private int obtenermaxId() {
        DefaultTableModel model = (DefaultTableModel) tablaCanciones.getModel();
        int rowCount = model.getRowCount();
        int maxId = 0;
        for (int i = 0; i < rowCount; i++) {
            int currentId = (int) model.getValueAt(i, 0);
            if (currentId > maxId) {
                maxId = currentId;
            }
        }
        System.out.println("id: " + maxId);
        return maxId + 1;
    }

    public int generarId() {
        return obtenermaxId();
    }

    private void añadirCancion() {
        if (titulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título de la canción es obligatorio.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int popularidadS, año, keyS, durationS, timeSignatureS, modeS;
        double BailabilidadS, energyS, loudnessS, speechinessS, acousticnessS, instrumentalnessS, livenessS, valenceS,
                tempoS;
        try {
            popularidadS = popularidad.getRealText().isEmpty() ? 0 : Integer.parseInt(popularidad.getRealText());
            año = anio.getRealText().isEmpty() ? 0 : Integer.parseInt(anio.getRealText());
            keyS = key.getRealText().isEmpty() ? 0 : Integer.parseInt(key.getRealText());
            durationS = durationMs.getText().isEmpty() ? 0 : Integer.parseInt(durationMs.getText());
            timeSignatureS = timeSignature.getRealText().isEmpty() ? 0 : Integer.parseInt(timeSignature.getRealText());
            modeS = mode.getRealText().isEmpty() ? 0 : Integer.parseInt(mode.getRealText());

            BailabilidadS = danceability.getRealText().isEmpty() ? 0.0 : Double.parseDouble(danceability.getRealText());
            energyS = energy.getRealText().isEmpty() ? 0.0 : Double.parseDouble(energy.getRealText());
            loudnessS = loudness.getRealText().isEmpty() ? 0.0 : Double.parseDouble(loudness.getRealText());
            speechinessS = speechiness.getText().isEmpty() ? 0.0 : Double.parseDouble(speechiness.getText());
            acousticnessS = acousticness.getRealText().isEmpty() ? 0.0 : Double.parseDouble(acousticness.getRealText());
            instrumentalnessS = instrumentalness.getRealText().isEmpty() ? 0.0
                    : Double.parseDouble(instrumentalness.getRealText());
            livenessS = liveness.getRealText().isEmpty() ? 0.0 : Double.parseDouble(liveness.getRealText());
            valenceS = valence.getRealText().isEmpty() ? 0.0 : Double.parseDouble(valence.getRealText());
            tempoS = tempo.getText().isEmpty() ? 0.0 : Double.parseDouble(tempo.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor ingrese valores válidos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Cancion nuevaCancion = new Cancion(artista.getText(),
                titulo.getText(),
                idCancion.getText(),
                popularidadS,
                año,
                genero.getText(),
                BailabilidadS,
                energyS,
                keyS,
                loudnessS, modeS,
                speechinessS,
                acousticnessS,
                instrumentalnessS,
                livenessS,
                valenceS,
                tempoS,
                durationS,
                timeSignatureS, Integer.parseInt(id.getText()));

        listaCanciones.agregarCancion(nuevaCancion);
        DefaultTableModel model = (DefaultTableModel) tablaCanciones.getModel();
        model.addRow(new Object[] {
                nuevaCancion.getId(),
                nuevaCancion.getTrack_name(),
                nuevaCancion.getArtist_name(),
                nuevaCancion.getYear(),
                nuevaCancion.getDuration_ms(),
                nuevaCancion.getPopularity()
        });
        limpiarDatos();
        id.setText(String.valueOf(generarId()));
    }

    private void eliminarCancion() {
        int[] selectedRows = tablaCanciones.getSelectedRows();

        if (selectedRows.length > 0) {
            DefaultTableModel model = (DefaultTableModel) tablaCanciones.getModel();

            for (int i = selectedRows.length - 1; i >= 0; i--) {
                int selectedRow = selectedRows[i];
                Cancion cancionAeliminar = listaCanciones.get(selectedRow);
                listaCanciones.eliminarCancion(cancionAeliminar);

                model.removeRow(selectedRow);
            }

            limpiarDatos();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una o más canciones para eliminar.", "Error",
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
            cancion.setPopularity(popularidad.getRealText().trim().isEmpty() ? 0
                    : Integer.parseInt(popularidad.getRealText().trim()));
            cancion.setYear(anio.getRealText().trim().isEmpty() ? 0 : Integer.parseInt(anio.getRealText().trim()));
            cancion.setGenre(genero.getText().trim().isEmpty() ? "" : genero.getText().trim());
            cancion.setDanceability(danceability.getRealText().trim().isEmpty() ? 0.0
                    : Double.parseDouble(danceability.getRealText().trim()));
            cancion.setEnergy(
                    energy.getRealText().trim().isEmpty() ? 0.0 : Double.parseDouble(energy.getRealText().trim()));
            cancion.setKey(key.getRealText().trim().isEmpty() ? 0 : Integer.parseInt(key.getRealText().trim()));
            cancion.setLoudness(
                    loudness.getRealText().trim().isEmpty() ? 0.0 : Double.parseDouble(loudness.getRealText().trim()));
            cancion.setSpeechiness(
                    speechiness.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(speechiness.getText().trim()));
            cancion.setAcousticness(acousticness.getRealText().trim().isEmpty() ? 0.0
                    : Double.parseDouble(acousticness.getRealText().trim()));
            cancion.setInstrumentalness(instrumentalness.getRealText().trim().isEmpty() ? 0.0
                    : Double.parseDouble(instrumentalness.getRealText().trim()));
            cancion.setLiveness(
                    liveness.getRealText().trim().isEmpty() ? 0.0 : Double.parseDouble(liveness.getRealText().trim()));
            cancion.setValence(
                    valence.getRealText().trim().isEmpty() ? 0.0 : Double.parseDouble(valence.getRealText().trim()));
            cancion.setTempo(tempo.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(tempo.getText().trim()));
            cancion.setDuration_ms(
                    durationMs.getRealText().trim().isEmpty() ? 0 : Integer.parseInt(durationMs.getRealText().trim()));
            cancion.setMode(mode.getRealText().trim().isEmpty() ? 0 : Integer.parseInt(mode.getRealText().trim()));
            cancion.setTime_signature(timeSignature.getRealText().trim().isEmpty() ? 0
                    : Integer.parseInt(timeSignature.getRealText().trim()));
            cancion.setId(cancion.getId());
            DefaultTableModel model = (DefaultTableModel) tablaCanciones.getModel();
            model.setValueAt(cancion.getId(), selectedRow, 0);
            model.setValueAt(cancion.getTrack_name(), selectedRow, 1);
            model.setValueAt(cancion.getArtist_name(), selectedRow, 2);
            model.setValueAt(cancion.getYear(), selectedRow, 3);
            model.setValueAt(cancion.getDuration_ms(), selectedRow, 4);
            model.setValueAt(cancion.getPopularity(), selectedRow, 5);
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
        id.setText(String.valueOf(generarId()));
    }

    private void buscarCancion(String busqueda, String criterio) {
        DefaultTableModel model = (DefaultTableModel) tablaResultadosBusqueda.getModel();
        model.setRowCount(0);
        if (listaCanciones == null) {
            JOptionPane.showMessageDialog(this, "La lista de canciones no está inicializada.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Cancion cancion : listaCanciones) {
            boolean coincide = false;

            switch (criterio) {
                case "Título":
                    coincide = cancion.getTrack_name().toLowerCase().contains(busqueda.toLowerCase());
                    break;
                case "Artista":
                    coincide = cancion.getArtist_name().toLowerCase().contains(busqueda.toLowerCase());
                    break;
            }

            if (coincide) {
                model.addRow(new Object[] {
                        cancion.getId(),
                        cancion.getTrack_name(),
                        cancion.getArtist_name(),
                        cancion.getYear(),
                        cancion.getDuration_ms(),
                        cancion.getPopularity()
                });
            }
        }

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No se encontraron canciones con el criterio especificado.",
                    "Resultado de búsqueda",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void filtrarCanciones() {
        String criterioOrdenacion = (String) ordenarPor.getSelectedItem();
        Integer añoSeleccionado = (Integer) añoEspecifico.getSelectedItem();
        boolean esAscendente = ascendente.isSelected();

        ListaEnlazada cancionesFiltradas = filtrarPorAño(listaCanciones, añoSeleccionado);

        // Ordena las canciones filtradas
        cancionesFiltradas = ordenarCanciones(cancionesFiltradas, criterioOrdenacion, esAscendente);

        actualizarTablaResultados(cancionesFiltradas);
    }

    private ListaEnlazada filtrarPorAño(ListaEnlazada todasLasCanciones, Integer añoSeleccionado) {
        ListaEnlazada cancionesFiltradas = new ListaEnlazada();
        for (Cancion cancion : todasLasCanciones) {
            if (añoSeleccionado == null || cancion.getYear() == añoSeleccionado) {
                cancionesFiltradas.agregarCancion(cancion);
            }
        }
        return cancionesFiltradas;
    }

    private ListaEnlazada ordenarCanciones(ListaEnlazada lista, String criterio, boolean ascendente) {
        Comparator<Cancion> comparator = null;
        switch (criterio) {
            case "Popularidad":
                comparator = Comparator.comparingInt(Cancion::getPopularity);
                break;
            case "Año":
                comparator = Comparator.comparingInt(Cancion::getYear);
                break;
            case "Duracion":
                comparator = Comparator.comparingInt(Cancion::getDuration_ms);
                break;
        }

        if (comparator != null) {
            if (!ascendente) {
                comparator = comparator.reversed();
            }
            lista = ordenarListaEnlazada(lista, comparator);
        }

        return lista;
    }

    private ListaEnlazada ordenarListaEnlazada(ListaEnlazada lista, Comparator<Cancion> comparator) {
        // Convertir a ArrayList para ordenar
        ArrayList<Cancion> arrayList = new ArrayList<>();
        for (Cancion cancion : lista) {
            arrayList.add(cancion);
        }
        arrayList.sort(comparator);

        // Volver a convertir a ListaEnlazada
        ListaEnlazada listaOrdenada = new ListaEnlazada();
        for (Cancion cancion : arrayList) {
            listaOrdenada.agregarCancion(cancion);
        }
        return listaOrdenada;
    }

    private void actualizarTablaResultados(ListaEnlazada cancionesFiltradas) {
        DefaultTableModel model = (DefaultTableModel) tablaResultadosBusqueda.getModel();
        model.setRowCount(0);

        for (Cancion cancion : cancionesFiltradas) {
            model.addRow(new Object[] {
                    cancion.getId(),
                    cancion.getTrack_name(),
                    cancion.getArtist_name(),
                    cancion.getYear(),
                    cancion.getDuration_ms(),
                    cancion.getPopularity()
            });
        }

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No se encontraron canciones con los filtros especificados.",
                    "Resultado de filtrado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cambiarPosicion() {
        try {
            int idDe = Integer.parseInt(cambiarDe.getText().trim());
            int idA = Integer.parseInt(cambiarA.getText().trim());

            // Verificar si ambos IDs son iguales
            if (idDe == idA) {
                JOptionPane.showMessageDialog(this, "Los IDs no pueden ser iguales.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Encontrar las posiciones en la lista
            int posicionDe = -1;
            int posicionA = -1;

            for (int i = 0; i < listaCanciones.size(); i++) {
                if (listaCanciones.get(i).getId() == idDe) {
                    posicionDe = i;
                }
                if (listaCanciones.get(i).getId() == idA) {
                    posicionA = i;
                }
                if (posicionDe != -1 && posicionA != -1) {
                    break;
                }
            }

            // Verificar si ambos IDs existen
            if (posicionDe == -1 || posicionA == -1) {
                JOptionPane.showMessageDialog(this, "Uno o ambos ID(s) no existen.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Intercambiar las posiciones en la lista
            Cancion temp = listaCanciones.get(posicionDe);
            listaCanciones.set(posicionDe, listaCanciones.get(posicionA));
            listaCanciones.set(posicionA, temp);

            // Actualizar la tabla
            DefaultTableModel model = (DefaultTableModel) tablaCanciones.getModel();
            Object[] rowDe = new Object[model.getColumnCount()];
            Object[] rowA = new Object[model.getColumnCount()];

            for (int i = 0; i < model.getColumnCount(); i++) {
                rowDe[i] = model.getValueAt(posicionDe, i);
                rowA[i] = model.getValueAt(posicionA, i);
            }

            for (int i = 0; i < model.getColumnCount(); i++) {
                model.setValueAt(rowDe[i], posicionA, i);
                model.setValueAt(rowA[i], posicionDe, i);
            }

            JOptionPane.showMessageDialog(this, "Posiciones intercambiadas con éxito.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores de ID válidos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarLista() {
        String nombreLista = JOptionPane.showInputDialog(this, "Ingrese un nombre para la lista guardada:");

        if (nombreLista == null || nombreLista.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        listasGuardadas.put(nombreLista, new ListaEnlazada(listaAleatoria)); // Guardar una copia de la lista actual
        listasGuardadasComboBox.addItem(nombreLista); // Actualizar JComboBox
        JOptionPane.showMessageDialog(this, "Lista guardada exitosamente.");
    }

    private void reproduccionAleatoria() {
        try {
            int limite = Integer.parseInt(limiteReproduccion.getText().trim());

            if (limite <= 0 || limite > listaCanciones.size()) {
                JOptionPane.showMessageDialog(this,
                        "El límite debe ser un número válido entre 1 y " + listaCanciones.size() + ".", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            ArrayList<Cancion> canciones = new ArrayList<>();
            for (Cancion cancion : listaCanciones) {
                canciones.add(cancion);
            }

            // Barajar las canciones
            java.util.Collections.shuffle(canciones);

            // Tomar las primeras 'limite' canciones
            canciones = new ArrayList<>(canciones.subList(0, limite));

            listaAleatoria.clear(); // Limpiar la lista aleatoria anterior
            for (Cancion cancion : canciones) {
                listaAleatoria.agregarCancion(cancion);
            }

            DefaultTableModel model = (DefaultTableModel) tablaResultadosBusqueda.getModel();
            model.setRowCount(0); // Limpiar tabla

            for (Cancion cancion : canciones) {
                model.addRow(new Object[] { cancion.getId(), cancion.getTrack_name(), cancion.getArtist_name(),
                        cancion.getYear(), cancion.getDuration_ms(), cancion.getPopularity() });
            }

            JOptionPane.showMessageDialog(this, "Lista de reproducción aleatoria generada.");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void irAListaGuardada() {
        String listaSeleccionada = (String) listasGuardadasComboBox.getSelectedItem();

        if (listaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una lista guardada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ListaEnlazada listaGuardada = listasGuardadas.get(listaSeleccionada);
        if (listaGuardada == null) {
            JOptionPane.showMessageDialog(this, "La lista seleccionada no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tablaResultadosBusqueda.getModel();
        model.setRowCount(0); // Limpiar tabla

        for (Cancion cancion : listaGuardada) {
            model.addRow(new Object[] { cancion.getId(), cancion.getTrack_name(), cancion.getArtist_name(),
                    cancion.getYear(), cancion.getDuration_ms(), cancion.getPopularity() });
        }

        JOptionPane.showMessageDialog(this, "Lista cargada exitosamente.");

    }

    private void actualizarListasGuardadasComboBox() {
        listasGuardadasComboBox.removeAllItems();
        for (String lista : listasGuardadas.keySet()) {
            listasGuardadasComboBox.addItem(lista);
        }
    }
}
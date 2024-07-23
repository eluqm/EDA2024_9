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
        JComboBox<String> ordenarPor = new JComboBox<>();
        ordenarPor.addItem("Popularidad");
        ordenarPor.addItem("Año");
        ordenarPor.addItem("Duracion");
        panelFiltro.add(ordenarPor);
        // año especifico
        JComboBox<Integer> añoEspecifico = new JComboBox<>();
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
        JRadioButton ascendente = new JRadioButton("Ascendente");
        panelFiltro.add(ascendente);
        JRadioButton descendente = new JRadioButton("Descendente");
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
        panelCambiarPosicion.add(cambiarDe);
        panelCambiarPosicion.add(labelCambiarA);
        panelCambiarPosicion.add(labelCambiarDe);
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
        botonBuscar.addActionListener(e -> buscarCancion(getName()));
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
        c.gridx = 4;
        c.gridy = 2;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        add(panelDetallesBusqueda, c);

        // panel reproduccion aleatoria
        panelReproduccion = new JPanel();
        panelReproduccion.setLayout(new GridLayout(1, 2));
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
        c.gridwidth = 3;
        c.weightx = 0.0;
        c.weighty = 0.0;
        add(panelReproduccion, c);

        // panel listas guardadas
        panelListas = new JPanel();
        panelListas.setLayout(new GridLayout(1, 3));
        panelListas.setBorder(BorderFactory.createTitledBorder("Listas Guardadas"));
        JComboBox<String> listasGuardadas = new JComboBox<>();
        panelListas.add(listasGuardadas);
        // mostrar lista guardada
        JButton botonListaGuardada = new JButton("Ir");
        botonListaGuardada.addActionListener(e -> irAListaGuardada());
        JLabel labelIrLista = new JLabel("Ir a lista:");
        panelListas.add(labelIrLista);
        panelListas.add(botonListaGuardada);
        c.gridx = 3;
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
        generarId();
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
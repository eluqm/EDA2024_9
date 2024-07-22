import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

public class Interfaz extends JFrame{
    JPanel panel = new JPanel();

    // paneles contenedores
    JPanel panelDatos = new JPanel();
    JPanel panelTabla = new JPanel();
    JPanel panelBusqueda = new JPanel();
    JPanel panelOpciones = new JPanel();
    JPanel panelDetallesBusqueda = new JPanel();
    JPanel panelListas = new JPanel();
    JPanel panelFiltro = new JPanel();
    JPanel panelCambiarPosicion = new JPanel();
    JPanel panelReproduccion = new JPanel();

    // datos
    JTextField titulo;
    JTextField artista;
    JTextField idCancion;
    PlaceholderTextField popularidad;
    PlaceholderTextField anio;
    JTextField genero;
    PlaceholderTextField danceability;
    PlaceholderTextField energy;
    PlaceholderTextField key;
    PlaceholderTextField loudness;
    JTextField speechiness;
    PlaceholderTextField acousticness;
    PlaceholderTextField instrumentalness;
    PlaceholderTextField liveness;
    PlaceholderTextField valence;
    JTextField tempo;
    JTextField durationMs;
    PlaceholderTextField mode;
    PlaceholderTextField timeSignature;

    JTable tablaCanciones;
    private JTextField buscar;
    private JTextField cambiarDe;
    private JTextField cambiarA;

    ListaEnlazada listaCanciones;
    private int id;

    public Interfaz() {
        listaCanciones = new ListaEnlazada();
        posicionar();
        aplicarEstilos();
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
            }
        });
        this.id=listaCanciones.size();
    }
    
}
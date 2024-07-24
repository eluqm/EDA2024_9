package com.canciones;

import javax.swing.SwingWorker;
import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.List;
public class CargarDatos extends SwingWorker<Void, Object[]>{
    private String archivo = "";
    private ListaEnlazada<Cancion> listaCanciones = new ListaEnlazada<>();
    private JTable tablaCanciones = new JTable();
    private DefaultTableModel modeloTabla = new DefaultTableModel();
    private int idgenerado;
    
    public CargarDatos(String archivo, ListaEnlazada<Cancion> listaCanciones, JTable tablaCanciones) {
        this.archivo = archivo;
        this.listaCanciones = listaCanciones;
        this.tablaCanciones = tablaCanciones;
        this.idgenerado=1;
        this.modeloTabla = (DefaultTableModel) tablaCanciones.getModel();
    }
    @Override
    protected Void doInBackground() throws Exception {
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(archivo))
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(',')
                        .withIgnoreQuotations(true)
                        .build())
                .build()) {
            int lineaActual = 0;
            String[] nextLine;
            
            while ((nextLine = reader.readNext()) != null) {
                lineaActual++;
                if (lineaActual == 1000000) {
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
                    int id = generarId();
                    Cancion cancion = new Cancion(artista, titulo, trackId, popularidad, ano, genero, danceability,
                            energy, key, loudness, mode, speechiness, acousticness, instrumentalness, liveness, valence,
                            tempo, durationMs, timeSignature, id);
                    listaCanciones.insertar(cancion);

                    Object[] rowData = {
                            cancion.getId(),
                            cancion.getTrack_name(),
                            cancion.getArtist_name(),
                            cancion.getYear(),
                            cancion.getDuration_ms(),
                            cancion.getPopularity()
                    };
                    publish(rowData);
                } catch (IllegalArgumentException e) {
                    continue;
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return null;
    }
    public int generarId(){
        return idgenerado++;
    }
    @Override
    protected void process(List<Object[]> chunks) {
        for (Object[] rowData : chunks) {
            modeloTabla.addRow(rowData);
        }
    }
}
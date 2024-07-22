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
    private final String archivo;
    private final ListaEnlazada listaCanciones;
    private final JTable tablaCanciones;
    private final DefaultTableModel modeloTabla;
    private int idgenerado;
    
    public CargarDatosWorker(String archivo, ListaEnlazada listaCanciones, JTable tablaCanciones) {
        this.archivo = archivo;
        this.listaCanciones = listaCanciones;
        this.tablaCanciones = tablaCanciones;
        this.idgenerado=1;
        this.modeloTabla = (DefaultTableModel) tablaCanciones.getModel();
    }
    
}
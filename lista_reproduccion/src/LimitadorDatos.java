import javax.swing.text.*;
public class LimitadorDatos {
    private int minInt;
    private int maxInt;
    private double minDouble;
    private double maxDouble;
    private int limite;
    private boolean isDouble;
    private boolean limitOnly;
    private boolean negativo;
    
    public LimitadorDatos(int min, int max) {
        this.minInt = min;
        this.maxInt = max;
        this.isDouble = false;
        this.limitOnly = false;
        this.negativo = false;
    }

    public LimitadorDatos(double min, double max, int limite, boolean isDouble) {
        this.minDouble = min;
        this.maxDouble = max;
        this.limite = limite;
        this.isDouble = isDouble;
        this.limitOnly = false;
        this.negativo = min < 0;
    }

    public LimitadorDatos(double minDouble, double maxDouble, boolean isDouble, boolean negativo) {
        this.minDouble = minDouble;
        this.maxDouble = maxDouble;
        this.isDouble = isDouble;
        this.negativo = negativo;
    }

    public LimitadorDatos(int limite, boolean limitOnly) {
        this.limite = limite;
        this.limitOnly = limitOnly;
        this.isDouble = false;
    }
}
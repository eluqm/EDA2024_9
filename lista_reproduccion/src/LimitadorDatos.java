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
    private boolean isValid(String text) {
        if (limitOnly) {
            return text.length() <= limite;
        }
        try {
            if (isDouble) {
                double value = Double.parseDouble(text);
                if (value < minDouble || value > maxDouble) {
                    return false;
                }
                if (negativo && text.contains("-")) {
                    return value >= minDouble;
                }
                return text.length() <= limite;
            } else {
                int value = Integer.parseInt(text);
                return value >= minInt && value <= maxInt;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.insert(offset, string);
        if (isValid(sb.toString())) {
            super.insertString(fb, offset, string, attr);
        }
    }
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        StringBuilder sb = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
        sb.replace(offset, offset + length, text);
        if (isValid(sb.toString())) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}
package com.canciones;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PlaceholderTextField extends JTextField {
    private String placeholder;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
        setText(placeholder);
        setForeground(Color.GRAY);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().equals(placeholder)) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setText(placeholder);
                    setForeground(Color.GRAY);
                }
            }
        });
    }
    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        if (!hasFocus() && getText().isEmpty()) {
            setText(placeholder);
            setForeground(Color.GRAY);
        }
    }
    public boolean isPlaceholder(){
        return getText().equals(placeholder);
    }
    public String getRealText() {
        return isPlaceholder() ? "" : getText();
    }
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty() && !(javax.swing.FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)) {
            int h = getHeight();
            ((java.awt.Graphics2D) g).setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, 
                    java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
            java.awt.Insets ins = getInsets();
            java.awt.FontMetrics fm = g.getFontMetrics();
            int c0 = getBackground().getRGB();
            int c1 = getForeground().getRGB();
            int m = 0xfefefefe;
            int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);
            g.setColor(new java.awt.Color(c2, true));
            g.drawString(placeholder, ins.left, h / 2 + fm.getAscent() / 2 - 2);
        }
    }
}
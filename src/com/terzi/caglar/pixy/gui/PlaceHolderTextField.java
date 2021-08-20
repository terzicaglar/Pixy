package com.terzi.caglar.pixy.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.Document;

/*
    Code taken from https://stackoverflow.com/questions/16213836/java-swing-jtextfield-set-placeholder
 */

@SuppressWarnings("serial")
public class PlaceHolderTextField extends JTextField {

    private String placeholder;

    public PlaceHolderTextField() {
    }

    public PlaceHolderTextField(
            final Document pDoc,
            final String pText,
            final int pColumns)
    {
        super(pDoc, pText, pColumns);
    }

    public PlaceHolderTextField(final int pColumns) {
        super(pColumns);
    }

    public PlaceHolderTextField(final String pText) {
        super(pText);
    }

    public PlaceHolderTextField(final String pText, final int pColumns) {
        super(pText, pColumns);
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        if (placeholder == null || placeholder.length() == 0 || getText().length() > 0) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getDisabledTextColor());
        g.drawString(placeholder, getInsets().left, pG.getFontMetrics()
                .getMaxAscent() + getInsets().top);
    }

    public void setPlaceholder(final String s) {
        placeholder = s;
    }

}
/*
 * Created on 31.07.2007
 */
package pro.java.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

/**
 * @Info
 * @WhatToDo
 * - Im Javadoc den Hinweis auf die Performance-Änderungen von der
 * Orginalklasse DefaultTableCellRenderer implementieren.
 * - Hinweis in der Methode getTableCellRenderer() auf die Änderungsmöglichkeiten.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 31.07.2007
 * @version 1.0
 */
public class PRoTableRenderer extends JLabel implements TableCellRenderer {

    private static final long serialVersionUID = 162684155802379430L;

    private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

    private static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

    private static Border getNoFocusBorder() {

        if (System.getSecurityManager() != null) {

            return SAFE_NO_FOCUS_BORDER;
        }

        return noFocusBorder;
    }
    private boolean bSelectRow = Boolean.FALSE;

    private int selectRow = 1;

    private Color backgroundNormal = null;
    private Color backgroundSelected = null;

    // We need a place to store the color the JLabel should be returned 
    // to after its foreground and background colors have been set 
    // to the selection background color. 
    // These ivars will be made protected when their names are finalized. 
    private Color unselectedForeground;
    private Color unselectedBackground;

    public PRoTableRenderer() {

        super();

        super.setHorizontalAlignment(SwingConstants.CENTER);
        super.setOpaque(Boolean.TRUE);
        super.setBorder(getNoFocusBorder());
    }

    private void checkColumn(JTable table, int col, Object value) {

        super.setFont(table.getFont().deriveFont(Font.PLAIN));
        this.setForeground(Color.BLACK);

        if (col == 0 || col == 5 || col == 7) {

            final int element = Integer.parseInt(String.valueOf(value));

            Color c = null;
            if (col == 0) { // Points.

                if (element >= 0) {
                    c = Color.GREEN.darker();
                }
                if (element < 0) {
                    c = Color.RED;
                }

                super.setFont(table.getFont().deriveFont(Font.BOLD));
            }

            if (col == 5) { // Wrong.

                if (element > 0) {
                    c = Color.RED;
                }
                if (element <= 0) {
                    c = Color.GREEN.darker();
                }
            }

            if (col == 7) { // Malus.

                if (element > 0) {
                    c = Color.RED;
                }
                if (element <= 0) {
                    c = Color.GREEN.darker();
                }
            }

            this.setForeground(c);
        }
    }

    private void checkFocus(
            JTable table,
            boolean isSelected, boolean hasFocus,
            int row, int column
    ) {
        if (hasFocus) {

            Border border = null;
            if (isSelected) {
                border = UIManager.getBorder(
                        "Table.focusSelectedCellHighlightBorder"
                );
            }
            if (border == null) {

                border = UIManager.getBorder("Table.focusCellHighlightBorder");
            }
            super.setBorder(border);

            if (!isSelected && table.isCellEditable(row, column)) {

                Color color = UIManager.getColor("Table.focusCellForeground");
                if (color != null) {
                    super.setForeground(color);
                }

                color = UIManager.getColor("Table.focusCellBackground");
                if (color != null) {
                    super.setBackground(color);
                }
            }
        } else {

            super.setBorder(getNoFocusBorder());
        }
    }

    private void checkRow(int row) {

        if (bSelectRow) {

            if (row == selectRow) {

                this.setBackground(backgroundSelected);
            }

            if (row > selectRow) {

                this.setBackground(backgroundNormal);
            }
        }
    }

    private void checkSelected(JTable table, boolean isSelected) {

        if (isSelected) {

            super.setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
        }

        if (!isSelected) {

            super.setForeground((unselectedForeground != null)
                    ? unselectedForeground : table.getForeground()
            );
            super.setBackground((unselectedBackground != null)
                    ? unselectedBackground : table.getBackground()
            );
        }
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void firePropertyChange(
            String propertyName,
            boolean oldValue, boolean newValue
    ) {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    protected void firePropertyChange(
            String propertyName,
            Object oldValue, Object newValue
    ) {
        // Strings get interned...
        if (propertyName == "text") {

            super.firePropertyChange(propertyName, oldValue, newValue);
        }
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(
     * 	javax.swing.JTable, java.lang.Object, boolean, boolean, int, int
     * )
     */
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column
    ) {
        final int col = table.convertColumnIndexToModel(column);
        this.checkColumn(table, col, value);
        this.checkRow(row);
        this.checkSelected(table, isSelected);

        /*		if (col == 0) {

         super.setFont(table.getFont().deriveFont(Font.BOLD));
         }
		
         if (col != 0) {

         super.setFont(table.getFont().deriveFont(Font.PLAIN));
         }*/
        this.checkFocus(table, isSelected, hasFocus, row, column);
        this.setValue(value);

        return this;
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     *
     * @since 1.5
     */
    public void invalidate() {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    public boolean isOpaque() {

        final Color back = getBackground();

        Component table = getParent();
        if (table != null) {
            table = table.getParent();
        }

        // p should now be the JTable. 
        final boolean colorMatch
                = (back != null)
                && (table != null)
                && back.equals(table.getBackground())
                && table.isOpaque();

        return !colorMatch && super.isOpaque();
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     *
     * @since 1.5
     */
    public void repaint() {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void repaint(long tm, int x, int y, int width, int height) {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void repaint(Rectangle r) {
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void revalidate() {
    }

    /**
     * Overrides <code>JComponent.setBackground</code> to assign the
     * unselected-background color to the specified color.
     *
     * @param c set the background color to this value
     */
    public void setBackground(Color c) {

        super.setBackground(c);
        unselectedBackground = c;
    }

    public void setBackgroundNormal(Color bg) {

        this.backgroundNormal = bg;
    }

    public void setBackgroundSelected(Color bg) {

        this.backgroundSelected = bg;
    }

    /**
     * Overrides <code>JComponent.setForeground</code> to assign the
     * unselected-foreground color to the specified color.
     *
     * @param c set the foreground color to this value
     */
    public void setForeground(Color c) {

        super.setForeground(c);
        unselectedForeground = c;
    }

    public void setSelectedRow(boolean b) {

        bSelectRow = b;

        this.selectRow = 0;
    }

    public void setSelectedRow(boolean b, int selectRow) {

        bSelectRow = b;

        this.selectRow = selectRow;
    }

    /**
     * Sets the <code>String</code> object for the cell being rendered to
     * <code>value</code>.
     *
     * @param value the string value for this cell; if value is
     * <code>null</code> it sets the text value to an empty string
     * @see JLabel#setText
     *
     */
    protected void setValue(Object value) {

        super.setText((value == null) ? "" : value.toString());
    }

    /**
     * Notification from the <code>UIManager</code> that the look and feel [L&F]
     * has changed. Replaces the current UI object with the latest version from
     * the <code>UIManager</code>.
     *
     * @see JComponent#updateUI
     */
    public void updateUI() {

        super.updateUI();

        this.setForeground(null);
        this.setBackground(null);
    }

    /**
     * Overridden for performance reasons. See the
     * <a href="#override">Implementation Note</a>
     * for more information.
     */
    public void validate() {
    }
}

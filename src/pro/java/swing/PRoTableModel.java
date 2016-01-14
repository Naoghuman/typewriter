/*
 * Created on 23.06.2007
 */
package pro.java.swing;

import javax.swing.table.AbstractTableModel;

import pro.java.util.PRoArrayList;

/**
 * Die Klasse <code>PRoTableModel</code> stellt ein einfaches
 * <code>Tabellenmodel</code> dar. Über den Konstruktor werden die
 * <code>Spaltenbezeichnungen</code> und die Daten der einzelnen
 * <code>Zeilen</code> übergeben.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 23.06.2007
 * @version 1.0
 */
public class PRoTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 341360233663363875L;

    private final boolean DEBUG = Boolean.FALSE;

    private PRoArrayList<String> columns = null;
    public PRoArrayList<PRoArrayList<Object>> data = null;

    public PRoTableModel(
            final PRoArrayList<String> columns,
            final PRoArrayList<PRoArrayList<Object>> data
    ) {
        super();

        this.columns = columns;
        this.data = data;
    }

    @Override
    public Class<?> getColumnClass(final int c) {

        return this.getValueAt(0, c).getClass();
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return columns.getSize();
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(final int col) {
        return columns.get(col);
    }

    public final PRoArrayList<PRoArrayList<Object>> getData() {

        return this.data;
    }

    /* (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return data.getSize();
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
    public Object getValueAt(final int row, final int col) {

        return data.get(row).get(col);
    }

    /**
     * Don't need to implement this method unless your table's editable.
     */
    @Override
    public boolean isCellEditable(final int row, final int col) {

        // Note that the data/cell address is constant,
        // no matter where the cell appears onscreen.
        if (col < 2) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    private void printDebugData() {

        final int numRows = this.getRowCount();
        final int numCols = this.getColumnCount();

        for (int i = 0; i < numRows; i++) {

            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {

                System.out.print("  " + this.getValueAt(i, j).toString());
            }

            System.out.println();
        }

        System.out.println("--------------------------");
    }

    /**
     * Don't need to implement this method unless your table's data can change.
     */
    @Override
    public void setValueAt(final Object value, final int row, final int col) {

        if (DEBUG) {

            System.out.println(
                    "Setting value at " + row + "," + col + " to " + value
                    + " (an instance of " + value.getClass() + ")"
            );
        }

        final PRoArrayList<Object> v = new PRoArrayList<Object>();
        v.add(value);
        data.add(row, v);
        super.fireTableCellUpdated(row, col);

        if (DEBUG) {

            System.out.println("New value of data:");
            this.printDebugData();
        }
    }
}

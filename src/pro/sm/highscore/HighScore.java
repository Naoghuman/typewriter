/*
 * Created on 29.07.2007
 */
package pro.sm.highscore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import pro.java.swing.PRoTableModel;
import pro.java.swing.PRoTableRenderer;
import pro.java.swing.TableSorter;
import pro.java.util.PRoArrayList;
import pro.sm.SM;
import pro.sm.resource.ReaderSM;
import pro.sm.resource.WriterSM;
import pro.sm.util.Rainbow;

/**
 * Die Klasse <code>HighScore</code> generiert die Tabelle mit den aktuellen
 * Highscore-Einträgen und zeigt diese in einem <code>JOptionPane</code>.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 29.07.2007
 * @version 1.0
 */
public class HighScore implements IEntry {

    private static final Color BACKGROUND = new Color(250, 250, 250);

    private static final PRoArrayList<String> COLUMNS = new PRoArrayList<String>();
    private static final PRoArrayList<String> TOOLTIPS = new PRoArrayList<String>();

    private boolean bSelectRow = Boolean.FALSE;
    private int index = 0;

    private JTable jTable = null;

    private Entry entry = null;
    private PRoTableRenderer tableRenderer = null;
    private ReaderSM reader = null;

    public HighScore() {
        this(null);
    }

    public HighScore(final Entry entry) {

        super();

        this.entry = entry;

        this.init();
    }

    private final PRoArrayList<Entry> createEntrys(
            final PRoArrayList<String> highscore,
            final Entry entry
    ) {
        final PRoArrayList<Entry> entrys = new PRoArrayList<Entry>();
        final int size = highscore.getSize();

        Entry e = null;
        String[] split = null;
        for (int i = 0; i < size; i++) {

            split = highscore.get(i).split(";");

            e = new Entry();
            e.createEntry(split);
            entrys.add(e);
        }

        if (entry != null) {
            entrys.add(entry);
        }

        return entrys;
    }

    private boolean isClearTable(final int answer) {

        return answer == 0;
    }

    private final DefaultTableColumnModel getDefaultTableColumnModel() {

        final DefaultTableColumnModel dtcm = new DefaultTableColumnModel();
        final int[] sizeColumn = new int[]{36, 36, 106, 58, 42, 32, 43, 28};
        final int size = sizeColumn.length;

        TableColumn col = null;
        for (int i = 0; i < size; i++) {

            col = new TableColumn(i, sizeColumn[i]);
            col.setHeaderValue(COLUMNS.get(i));
            dtcm.addColumn(col);
        }

        return dtcm;
    }

    private final JTableHeader getTableHeader(
            final JTable jt, final PRoArrayList<String> tooltips
    ) {
        final TableColumnModel tcModel = jt.getColumnModel();
        final JTableHeader jth = new JTableHeader(tcModel) {

            private static final long serialVersionUID
                    = -9178355294814684470L;

            @Override
            public String getToolTipText(final MouseEvent e) {

                final Point p = e.getPoint();
                final int index = tcModel.getColumnIndexAtX(p.x);
                final int realIndex = tcModel.getColumn(
                        index
                ).getModelIndex();

                return tooltips.get(realIndex);
            }

            @Override
            public void setBackground(Color bg) {

                super.setBackground(Color.LIGHT_GRAY);
            }
        };

        return jth;
    }

    private final void init() {

        reader = new ReaderSM();

        if (entry != null) {
            bSelectRow = Boolean.TRUE;
        }

        this.initColumns();
        this.initToolTips();
        this.initTable();
    }

    private final void initColumns() {

        COLUMNS.add("Punkte");
        COLUMNS.add("An/Min");
        COLUMNS.add("Name");
        COLUMNS.add("Datum");
        COLUMNS.add("Zeichen");
        COLUMNS.add("Fehler");
        COLUMNS.add("Prozent");
        COLUMNS.add("Malus");
    }

    private final PRoArrayList<PRoArrayList<Object>> initData() {

        final PRoArrayList<String> highscore = reader.readTXT("highscore.txt");
        final PRoArrayList<Entry> entrys = this.createEntrys(highscore, entry);
        final int size = entrys.getSize();

        Collections.sort(entrys);

        if (entry != null) {

            for (int i = 0; i < size; i++) {
                if (entrys.get(i).toString().equals(entry.toString())) {

                    index = i;
                    break;
                }
            }

            WriterSM.write(entrys);
        }

        final PRoArrayList<PRoArrayList<Object>> data
                = new PRoArrayList<PRoArrayList<Object>>();

        Entry e = null;
        PRoArrayList<Object> o = null;
        for (int i = 0; i < size; i++) {

            e = entrys.get(i);
            o = e.getFormat(e);
            data.add(o);
        }

        return data;
    }

    private final void initTable() {

        final PRoArrayList<PRoArrayList<Object>> data = this.initData();
        final TableSorter sorter = new TableSorter(
                new PRoTableModel(COLUMNS, data)
        );

        jTable = new JTable(sorter, this.getDefaultTableColumnModel());
        jTable.setBackground(BACKGROUND);

        tableRenderer = new PRoTableRenderer();
        tableRenderer.setBackgroundNormal(BACKGROUND);
        tableRenderer.setSelectedRow(bSelectRow, index);

        jTable.setDefaultRenderer(Object.class, tableRenderer);
        jTable.setEnabled(Boolean.FALSE);
        jTable.setTableHeader(this.getTableHeader(jTable, TOOLTIPS));

        sorter.setTableHeader(jTable.getTableHeader());
    }

    private final void initToolTips() {

        TOOLTIPS.add("Anzahl der Punkte für die �bung.");
        TOOLTIPS.add("Anschläge pro Minute.");
        TOOLTIPS.add("Name.");
        TOOLTIPS.add("Tag der Übung.");
        TOOLTIPS.add("Anzahl der getippten Buchstaben.");
        TOOLTIPS.add("Anzahl der Fehler während der Übung.");
        TOOLTIPS.add("Prozentuale Auswertung (Zeichen/Fehler).");
        TOOLTIPS.add("Abzug Punkte für die Fehler.");
    }

    public final void showTable() {

        // Aktuelle Farbe als Selektionsfarbe.
        tableRenderer.setBackgroundSelected(
                Rainbow.getRainbow(SM.getHue(), 0.30f)
        );

        final JScrollPane jsp = new JScrollPane(
                jTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        jsp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jsp.setPreferredSize(new Dimension(550, 200));

        final Object[] options = {"Löschen", "OK"};
        final int answer = JOptionPane.showOptionDialog(
                null, jsp, "Highscore",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options, options[1]
        );

        if (this.isClearTable(answer)) {
            this.clearTable();
        }
    }

    private void clearTable() {

        /*
         * Vergleich
         * - Bester Eintrag der aktuellen Tabelle ermitteln.
         * - Eintrag von pro_highscore.txt.
         * - Vergleich beider Einträge,
         * - Der Bessere wird in die neue Highscore-Tabelle geschrieben.
         */
        entry = null;
        bSelectRow = Boolean.FALSE;

        final PRoArrayList<String> highscore = reader.readTXT(
                "pro_highscore.txt"
        );
        final PRoArrayList<Entry> entrys = this.createEntrys(highscore, entry);
        WriterSM.write(entrys);

        // Geänderte Einträge werden gezeigt.
        new HighScore().showTable();
    }
}

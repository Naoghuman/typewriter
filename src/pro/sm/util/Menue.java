/*
 * Created on 27.07.2007
 */
package pro.sm.util;

import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import pro.sm.SM;

/**
 * In der Klasse <code>Menue</code> wird das <code>Menu</code> des Programm
 * initialisiert.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 27.07.2007
 * @version 1.0
 */
public class Menue extends JMenuBar {

    private static final long serialVersionUID = -7059628179851493871L;

    private ActionListener ac = null;

    private SM sm = null;

    public Menue(SM sm) {

        super();

        this.sm = sm;

        this.init();
    }

    private final JMenuItem getMenuItem(
            final String text, final char memnomic, final String tooltip
    ) {
        final JMenuItem jmi = new JMenuItem(text, memnomic);
        jmi.addActionListener(ac);
        jmi.setToolTipText(tooltip);

        return jmi;
    }

    private final void init() {

        ac = sm.getActionListener();

        super.add(Box.createHorizontalStrut(5));

        this.initProgramm();
        this.initHighscore();
        this.initHelp();
    }

    private final void initHelp() {

        final JMenu jm = new JMenu("Hilfe");
        jm.setMnemonic('i');

        jm.add(this.getMenuItem(
                "Hilfe", 'H',
                "Zeigt die 'Hilfe' des Programms."
        ));
        jm.add(this.getMenuItem(
                "About", 'A', "Zeigt 'Informationen' zum Programm und Autor."
        ));
        jm.add(this.getMenuItem(
                "License", 'L', "Zeigt die 'License'."
        ));

        super.add(jm);
    }

    private final void initHighscore() {

        final JMenu jm = new JMenu("Highscore");
        jm.setMnemonic('H');

        jm.add(this.getMenuItem(
                "Highscore", 'i',
                "Zeigt die 'Highscore-Tabelle' des Programms."
        ));

        super.add(jm);
    }

    private final void initProgramm() {

        final JMenu jm = new JMenu("Programm");
        jm.setMnemonic('P');

        jm.add(this.getMenuItem("Start", 'S', "Startet die 'Übung'."));
        jm.add(this.getMenuItem("Stop", 't', "Stoppt die 'Übung'."));
        jm.addSeparator();
        jm.add(this.getMenuItem("Beenden", 'B', "Beendet das Programm."));

        super.add(jm);
    }
}

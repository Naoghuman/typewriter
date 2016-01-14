package pro.sm;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import pro.java.awt.PRoBorderLayout;
import pro.java.util.PRoVersionChecker;

/**
 * Mit der Klasse <code>StartSM</code> wird das Programm
 * <code>PRo's Schreibmaschinen: Schnell schreiben</code> gestartet.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 02.08.2007
 * @version 1.0
 */
public class StartSM {

    private final static void createAndShowGUI() {

        PRoVersionChecker.checkVersion(
                "1.5.0", "You need Java version {0} or greater."
        );

        final JFrame frame = new JFrame("PRo's Schreibmaschine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new PRoBorderLayout());
        frame.setResizable(Boolean.FALSE);

        frame.getContentPane().add(new SM(frame), PRoBorderLayout.CENTER);

        final int height = 350;
        final int width = 600;
        frame.setSize(width, height);

        final Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(
                (d.width >> 1) - (width >> 1),
                (d.height >> 1) - (height >> 1)
        );

        frame.requestFocusInWindow();
        frame.setVisible(Boolean.TRUE);
    }

    public static void main(String[] args) {

        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                createAndShowGUI();
            }
        });
    }
}

/*
 * Created on 25.07.2007
 */
package pro.sm.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import pro.sm.exercise.Exercise;
import pro.sm.help.About;
import pro.sm.help.Help;
import pro.sm.help.License;
import pro.sm.highscore.HighScore;

/**
 * In der Klasse <code>Listener</code> werden die Ereignisse (Action-,
 * KeyEvents) des Programms abgefangen.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 25.07.2007
 * @version 1.0
 */
public class Listener extends KeyAdapter implements ActionListener {

    private static final int[] KEYS = new int[]{
        KeyEvent.VK_BACK_SPACE, KeyEvent.VK_CONTROL,
        KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE,
        KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_F5
    };

    private Exercise exercise = null;

    public Listener(final Exercise exercise) {

        this.exercise = exercise;
    }

    public final void actionPerformed(final ActionEvent e) {

        String ac = e.getActionCommand();
        if (ac.equals("About")) {
            this.showAbout();
            return;
        }
        if (ac.equals("Beenden")) {
            this.exitProgramm();
            return;
        }
        if (ac.equals("Highscore")) {
            this.showHighscore();
            return;
        }
        if (ac.equals("Hilfe")) {
            this.showHelp();
            return;
        }
        if (ac.equals("License")) {
            this.showLicense();
            return;
        }
        if (ac.equals("Start")) {
            this.startExercise();
            return;
        }
        if (ac.equals("Stop")) {
            this.stopExercise();
        }
    }

    private final void checkConfigurationEvents(final int keyCode) {

        if (keyCode == KeyEvent.VK_ESCAPE) {
            this.exitProgramm();
        }
        if (keyCode == KeyEvent.VK_CONTROL
                || keyCode == KeyEvent.VK_ENTER
                || keyCode == KeyEvent.VK_F1) {
            this.startExercise();
        }
        if (keyCode == KeyEvent.VK_BACK_SPACE
                || keyCode == KeyEvent.VK_F2) {
            this.stopExercise();
        }

        if (keyCode == KeyEvent.VK_F5) {

            this.showHighscore();
        }
    }

    private final void checkKeyEvents(final int keyCode, final char keyChar) {

        if (!exercise.getVeloCity().isNull()) {

            if (keyChar != 'ö' && keyChar != 'Ö') {

                exercise.keyPressed((char) keyCode);
                return;
            }

            if (keyChar == 'ö' || keyChar == 'Ö') {
                exercise.keyPressed('Ö');
            }
        }
    }

    private final void exitProgramm() {

        this.stopExercise();

        System.exit(0);
    }

    public final void keyPressed(final KeyEvent e) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                final int keyCode = e.getKeyCode();
                for (int kc : KEYS) {

                    if (kc == keyCode) {

                        Listener.this.checkConfigurationEvents(keyCode);
                        return;
                    }
                }

                final char keyChar = e.getKeyChar();
                Listener.this.checkKeyEvents(keyCode, keyChar);
            }
        });
    }

    private final void showAbout() {
        About.showAbout();
    }

    private final void showHelp() {
        Help.showHelp();
    }

    private final void showHighscore() {

        final HighScore hs = new HighScore();
        hs.showTable();
    }

    private final void showLicense() {
        License.showLicense();
    }

    private final void startExercise() {
        exercise.startExercise();
    }

    private final void stopExercise() {

        exercise.stopExercise();
        exercise.reset();
    }
}

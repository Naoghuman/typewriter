/*
 * Created on 25.07.2007
 */
package pro.sm.exercise;

import javax.swing.SwingUtilities;

import pro.java.util.PRoINull;

/**
 * Über die Klasse <code>VeloCity</code> wird die Geschwindigkeit der Anzeige
 * der Buchstaben-Reihenfolge geändert.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 25.07.2007
 * @version 1.0
 */
public class VeloCity extends Thread implements PRoINull {

    /**
     * Liefert eine Reference auf die Klasse <code>NoVeloCity</code>.
     *
     * @return NoVeloCity.
     */
    public static final VeloCity newNull() {

        return new NoVeloCity();
    }

    private boolean isAlive = Boolean.TRUE;

    private int velocity = 50;

    private Letters letters = null;

    /**
     * Standard-Konstruktor der Klasse <code>VeloCity</code>.
     */
    protected VeloCity() {
    }

    /**
     * Konstruktor der Klasse <code>VeloCity</code>.
     *
     * @param exercise Reference auf die Klasse <code>Exercise</code>.
     */
    public VeloCity(final Exercise exercise) {

        letters = new Letters(exercise);

        this.start();
    }

    /**
     * Die Geschwindigkeit, mit der neue Buchstaben der Buchstabenreihe
     * hinzugefügt wird.
     *
     * @return int.
     */
    public int getVelocity() {
        return this.velocity;
    }

    /**
     * Liefert einen boolschen Wert, ob eine Schreib-Übung aktiv ist.
     *
     * @return boolean.
     */
    public final synchronized boolean isItAlive() {
        return isAlive;
    }

    /*
     * (non-Javadoc)
     * @see pro.java.util.PRoINull#isNull()
     */
    public boolean isNull() {
        return Boolean.FALSE;
    }

    /**
     * Fügt einen neuen Buchstaben der Buchstabenreihe hinzu. Die Pause zwischen
     * dem Hinzufügen der neuen Buchstaben entspricht der Geschwindigkeit
     * (Anschläge pro Minute).
     */
    public final void run() {

        while (this.isItAlive()) {

            SwingUtilities.invokeLater(letters);
            try {

                Thread.sleep(60000 / velocity);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * Die Geschwindigkeit der Buchstaben-Anzeige verändert sich um den Betrag
     * <code>value</code>.<p>
     *
     * Minimum ist 50 Anschläge pro Minute.<br>
     * Maximum ist 300 Anschläge pro Minute.
     *
     * @param value
     */
    public void setVeloCity(final int value) {

        velocity = value;

        if (velocity < 50) {
            velocity = 50;
        }
        if (velocity > 300) {
            velocity = 300;
        }
    }

    /**
     * Stoppt die Schreib-�bung.
     */
    public synchronized void stopExercise() {

        isAlive = Boolean.FALSE;
    }
}

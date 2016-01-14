/*
 * Created on 25.07.2007
 */
package pro.sm.exercise;

import java.awt.Toolkit;

import pro.sm.SM;
import pro.sm.highscore.Data;
import pro.sm.highscore.Entry;
import pro.sm.highscore.ITotal;
import pro.sm.highscore.Input;

/**
 * Die Klasse <code>Exercise</code> administiert die Datenverwaltung einer
 * Schreib-Übung.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 25.07.2007
 * @version 1.0
 */
public class Exercise implements IExercise, ITotal {

    private static final char FIRST_CHAR = '»';
    private static final int MAX_LETTERS = 23;

    private static final StringBuffer LETTERS = new StringBuffer();

    private SM sm = null;
    ;
	
	private VeloCity veloCity = null;

    /**
     * Konstruktor der Klasse <code>Exercise</code>.
     *
     * @param sm Reference auf die Klasse <code>SM</code>.
     */
    public Exercise(SM sm) {

        this.sm = sm;

        this.init();
    }

    /**
     * F�gt einen Buchstaben der Buchstabenreihe hinzu.
     *
     * @param c der neue Buchstabe.
     */
    public final void add(final char c) {

        LETTERS.append(c);

		// Zuerst wird die Geschwindigkeit kontrolliert, da nach Beenden der
        // Übung die Geschwindigkeit automatisch auf 50an/min zurückgesetzt
        // wird.
        this.checkVeloCity();
        this.checkRange();

        this.update(LETTER, LETTERS.toString());
    }

    private final void checkRange() {

        if (LETTERS.length() >= MAX_LETTERS) {

            this.stopExercise();
            LETTERS.deleteCharAt(LETTERS.length() - 1);

            final Input input = new Input(sm);
            input.showPRoDialog();
        }
    }

    private final void checkVeloCity() {

        this.setTotal(this.getTotal() + 1);

        // Alle 2 Buchstaben wird die Geschwindigkeit erh�ht.
        if (this.getTotal() % 2 == 0) {

            final VeloCity vc = this.getVeloCity();
            vc.setVeloCity(vc.getVelocity() + 1);

            sm.getEntry().getVeloCity().set(vc.getVelocity());
            this.update(VELOCITY, sm.getEntry().toString(VELOCITY));

            sm.setColorRainbow(vc.getVelocity());
        }
    }

    /* (non-Javadoc)
     * @see pro.sm.highscore.ITotal#getTotal()
     */
    public int getTotal() {
        return sm.getTotal();
    }

    /**
     * Liefert eine Reference auf die Klasse <code>VeloCity</code> oder
     * <code>null</code>.
     *
     * @return VeloCity.
     */
    public final VeloCity getVeloCity() {

        return (veloCity != null ? veloCity : VeloCity.newNull());
    }

    private final void init() {

        LETTERS.append(FIRST_CHAR);
        this.update(LETTER, LETTERS.toString());
    }

    /**
     * Wertet den getippten Buchstaben aus.
     *
     * @param keyChar der getippte Buchstabe.
     */
    public final void keyPressed(final char keyChar) {

        final int vc = this.getVeloCity().getVelocity();
        final Entry e = sm.getEntry();
        final Data<Integer> points = e.getPoints();
        if (LETTERS.length() >= 2 && keyChar == LETTERS.charAt(1)) {

            // Richtig.
            points.set(points.get() + ((vc - 40) >> 3));

            final Data<Integer> tipped = e.getTipped();
            tipped.set(tipped.get() + 1);

            this.remove();
            this.update(TIPPED);
        } else {

            // Falsch.
            Toolkit.getDefaultToolkit().beep();

            final int decal = (((vc - 40) >> 3) << 1);
            points.set(points.get() - decal);

            final Data<Integer> malus = e.getMalus();
            malus.set(malus.get() + decal);

            final Data<Integer> wrong = e.getWrong();
            wrong.set(wrong.get() + 1);

            this.update(MALUS);
            this.update(WRONG);
        }

        this.update(PERCENT);
        this.update(POINTS);
    }

    /**
     * Entfernt den ersten Buchstaben nach dem Zeichen '>>'.
     */
    public final void remove() {

        if (LETTERS.length() >= 2) {

            LETTERS.deleteCharAt(1);
            this.update(LETTER, LETTERS.toString());
        }
    }

    /**
     * S�mtliche Werte werden zur�ckgesetzt f�r eine neue �bung.
     */
    public final void reset() {

        LETTERS.delete(0, LETTERS.length());
        LETTERS.append(FIRST_CHAR);

        sm.getEntry().reset();

        this.update(LETTER, LETTERS.toString());
        this.update(MALUS);
        this.update(PERCENT);
        this.update(POINTS);
        this.update(TIPPED);

        final VeloCity vc = this.getVeloCity();
        vc.setVeloCity(50);
        this.update(VELOCITY, sm.getEntry().toString(VELOCITY));
        sm.setColorRainbow(50);

        this.update(WRONG);
    }

    /* (non-Javadoc)
     * @see pro.sm.highscore.ITotal#setTotal(int)
     */
    public void setTotal(int total) {
        sm.setTotal(total);
    }

    /**
     * Startet die �bung. L�uft schon eine �bung, so passiert nichts.
     */
    public final void startExercise() {

        if (this.getVeloCity().isNull()) {

            this.reset();
            veloCity = new VeloCity(this);
        }
    }

    /**
     * Stoppt die �bung.
     */
    public final void stopExercise() {

        final VeloCity vc = this.getVeloCity();
        vc.stopExercise();

        if (!vc.isNull()) {
            veloCity = null;
        }
    }

    /* (non-Javadoc)
     * @see pro.sm.IUpdate#update(byte)
     */
    public final void update(final byte b) {
        sm.update(b);
    }

    /* (non-Javadoc)
     * @see pro.sm.exercise.IExercise#update(byte, java.lang.String)
     */
    public final void update(final byte b, final String text) {

        sm.update(b, text);
    }
}

/*
 * Created on 25.07.2007
 */
package pro.sm.exercise;

import java.util.Collections;
import java.util.Random;

import pro.java.util.PRoArrayList;

/**
 * Die Klasse <code>Letters</code> initialisiert die zu tippende
 * Buchstabenreihe.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 25.07.2007
 * @version 1.0
 */
public class Letters implements Runnable {

    private static final char[] ABC = new char[]{
        'A', 'B', 'C', 'D', 'E',
        'F', 'G', 'H', 'J', 'K',
        'L', 'M', 'N', 'O', 'P',
        'Q', 'R', 'S', 'T', 'U',
        'V', 'W', 'X', 'Y', 'Z',};
    private static final char[] EXTRA = new char[]{
        'Ö', ',', '.', ' ', ' ', ' '
    };

    private static final PRoArrayList<Short> RANDOM = new PRoArrayList<Short>(4096);

    private short index = 0;

    private char[] letters = null;

    private Exercise exercise = null;

    /**
     * Konstruktor der Klasse <code>Letters</code>.
     *
     * @param exercise Reference auf die Klasse <code>Exercise</code>.
     */
    public Letters(Exercise exercise) {

        this.exercise = exercise;

        this.init();
    }

    private final void init() {

        final int sizeAbc = ABC.length;
        final int sizeExtra = EXTRA.length;
        final int sizeLetters = sizeAbc + sizeExtra;

        letters = new char[sizeLetters];
        System.arraycopy(ABC, 0, letters, 0, sizeAbc);
        System.arraycopy(EXTRA, 0, letters, sizeAbc, sizeExtra);

        final Random RA = new Random();
        for (short i = 0; i < 4096; i++) {

            RANDOM.add((short) RA.nextInt(sizeLetters));
        }

        Collections.shuffle(RANDOM);
    }

    private final short random() {

        if (++index >= 4096) {

            index = 0;
            Collections.shuffle(RANDOM);
        }

        return RANDOM.get(index);
    }

    /**
     * Überträgt einen zufälligen Buchstaben.
     */
    public final void run() {
        exercise.add(letters[this.random()]);
    }
}

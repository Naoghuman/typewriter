/*
 * Created on 29.07.2007
 */
package pro.sm.highscore;

/**
 * Das Interface <code>IEntry</code> enthält die Konstanten für den Zugriff auf
 * die verschiedenen <code>Highscore-Parameter</code>.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 29.07.2007
 * @version 1.0
 */
public interface IEntry {

    public static final byte LETTER = -1;

    public static final byte MALUS = 0;

    public static final byte PERCENT = 1;

    public static final byte POINTS = 2;

    public static final byte TIPPED = 3;

    public static final byte VELOCITY = 4;

    public static final byte WRONG = 5;
}

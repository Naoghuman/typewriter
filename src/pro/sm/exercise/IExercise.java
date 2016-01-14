/*
 * Created on 29.07.2007
 */
package pro.sm.exercise;

import pro.sm.highscore.IEntry;

/**
 * Das Interface <code>IExercise</code> enthält die beiden
 * <code>Update-Methoden</code> für das Aktualisieren der Anzeige der
 * Buchstaben-Reihenfolge.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 29.07.2007
 * @version 1.0
 */
public interface IExercise extends IEntry {

    /**
     * Aktualisiert die Anzeige des Parameters <code>b</code>.
     *
     * @param b Parameter f�r die Aktualisierung.
     */
    public abstract void update(final byte b);

    /**
     * Aktualisiert die Anzeige des Parameters <code>b</code> mit dem
     * übergebenen Text.
     *
     * @param b Parameter f�r die Aktualisierung.
     * @param text der neue Text.
     */
    public abstract void update(final byte b, final String text);
}

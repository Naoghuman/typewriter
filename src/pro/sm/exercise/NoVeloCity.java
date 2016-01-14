/*
 * Created on 28.07.2007
 */
package pro.sm.exercise;

/**
 * Die Klasse <code>NoVeloCity</code> ein <code>null-Object</code> dar.<p>
 *
 * Sollten Defaultwerte implementiert werden, so sind die entsprechenden
 * Methoden der <code>Parent-Class</code> zu überschreiben.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 28.07.2007
 * @version 1.0
 */
public class NoVeloCity extends VeloCity {

    private static final int MIN_VELOCITY = 50;

    /**
     * Standard-Konstruktor der Klasse <code>NoVeloCity</code>.
     */
    public NoVeloCity() {
    }

    /*
     * (non-Javadoc)
     * @see pro.sm.VeloCity#getVelocity()
     */
    public final int getVelocity() {
        return MIN_VELOCITY;
    }

    /* (non-Javadoc)
     * @see pro.sm.VeloCity#isNull()
     */
    @Override
    public final boolean isNull() {
        return Boolean.TRUE;
    }

    /* (non-Javadoc)
     * @see pro.sm.VeloCity#setVeloCity(int)
     */
    @Override
    public final void setVeloCity(final int value) {
    }

    /* (non-Javadoc)
     * @see pro.sm.VeloCity#stopExercise()
     */
    @Override
    public final synchronized void stopExercise() {
    }
}

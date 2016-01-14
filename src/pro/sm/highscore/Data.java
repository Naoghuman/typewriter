/*
 * Created on 02.08.2007
 */
package pro.sm.highscore;

import java.text.DecimalFormat;

/**
 * Die Klasse <code>Data</code> stellt ein komplexes <code>Element</code> eines
 * Highscore-Eintrags dar.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 02.08.2007
 * @version 1.0
 */
public class Data<E> implements Comparable<Object> {

    private int iGreater = 0;
    private int iSmaller = 0;

    private DecimalFormat df = null;
    private E data = null;

    /**
     * Konstruktor der Klasse <code>Data</code>.
     *
     * @param df DecimalFormat für die Anzeige des Wertes.
     * @param smaller Rückgabewert, wenn der Wert dieser Klasse kleiner als der
     * Wert eines anderen Vergleich-Objekt ist.
     * @param greater Rückgabewert, wenn der Wert dieser Klasse größer als der
     * Wert eines anderen Vergleichs-Objekt ist.
     */
    public Data(DecimalFormat df, int smaller, int greater) {

        this.df = df;
        this.iSmaller = smaller;
        this.iGreater = greater;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public int compareTo(Object o) {

        if (data instanceof Integer) {

            Integer i = (Integer) this.get();
            int compareTo = i.compareTo(((Data<Integer>) o).get());

            if (compareTo < 0) {
                return iSmaller;
            }
            if (compareTo > 0) {
                return iGreater;
            }
        }

        if (data instanceof Double) {

            Double d = (Double) this.get();
            double compareTo = d.compareTo(((Data<Double>) o).get());

            if (compareTo < 0.0d) {
                return iSmaller;
            }
            if (compareTo > 0.0d) {
                return iGreater;
            }
        }

        return 0;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (data instanceof String) {

            return ((String) data).equals(String.valueOf(obj));
        }

        return data == obj;
    }

    /**
     * Liefert einen formatieren String des Wertes dieser Klasse.
     *
     * @return formatierter String.
     */
    protected String format() {
        return df.format(data);
    }

    /**
     * Liefert den Wert dieser Klasse.
     *
     * @return Wert dieser Klasse.
     */
    public final E get() {
        return data;
    }

    /**
     * Setzt den Wert dieser Klasse.
     *
     * @param data der neue Wert.
     */
    public final void set(final E data) {
        this.data = data;
    }
}

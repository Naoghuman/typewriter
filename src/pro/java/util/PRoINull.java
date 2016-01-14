/*
 * Created on 28.07.2007
 */
package pro.java.util;

/**
 * Das Interface <code>PRoINull</code> wird von Klassen und deren Subklassen
 * implementiert, die auf <code>null</code> geprüft werden. Je nach
 * Anforderungen überschreibt die <code>Subklasse</code> die angewandten
 * Methoden von <code>Klasse</code> in der <code>AnwenderKlasse</code> mit
 * Defaultwerte oder nicht:
 * <p>
 *
 * class <b>AnwenderKlasse</b> {<br>
 * &nbsp;&nbsp;&nbsp;public Klasse getKlasse() {
 * <p>
 *
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return (klasse == null ? Klasse.newNull()
 * : klasse);<br>
 * &nbsp;&nbsp;&nbsp;}<br>
 * }
 * <p>
 *
 * class <b>Klasse implements PRoINull</b> {<br>
 * &nbsp;&nbsp;&nbsp;public static final Klasse newNull() {
 * <p>
 *
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return new NullKlasse();<br>
 * &nbsp;&nbsp;&nbsp;}
 * <p>
 *
 * &nbsp;&nbsp;&nbsp;public boolean isNull() {
 * <p>
 *
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return Boolean.FALSE;<br>
 * &nbsp;&nbsp;&nbsp;}<br>
 * }
 * <p>
 *
 * class <b>NullKlasse extends Klasse</b> {<br>
 * &nbsp;&nbsp;&nbsp;public boolean isNull() {
 * <p>
 *
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return Boolean.TRUE;<br>
 * &nbsp;&nbsp;&nbsp;}<br>
 * }
 * <p>
 *
 * @author PRo (Peter Rogge) | Copyright (c) 28.07.2007
 * @version 1.0
 */
public interface PRoINull {

    /**
     * Liefert ein <code>Boolean</code>, ob die Klasse ein
     * <code>Null-Object</code> darstellt.
     *
     * @return <code>true</code>, wenn die Klasse ein <code>null-Object</code>
     * darstellt, anderfalls <code>false</code>.
     */
    public abstract boolean isNull();
}

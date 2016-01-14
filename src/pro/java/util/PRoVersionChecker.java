package pro.java.util;

import java.text.MessageFormat;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

/*
 * Orginalklasse von Cay S. Horstmann (http://horstmann.com) (Violett)
 * Von mit entsprechend modifiziert.
 */
/**
 * Die Klasse <code>PRoVersionChecker</code> wird direkt am Anfang der
 * <code>StartKlasse</code> des jeweiligen Programms aufgerufen, um sicher zu
 * stellen, das die installierte <code>JDK</code> des Clients die
 * Mindestanforderungen des Programms erfüllt.
 *
 * @author PRo (Peter Rogge) | TraumAG | Copyright (c) 30.12.2006
 * @version 1.1
 * @change 28.04.2007
 */
public final class PRoVersionChecker extends Object {

    /**
     * Checks if the current VM has at least the given version, and exits the
     * program with an error dialog if not.
     *
     * @param desiredVersion the desired version
     */
    public final static void checkVersion(
            final String desiredVersion, final String errorVersion
    ) {
        final String actualVersion = System.getProperty("java.version");

        boolean versionOk = Boolean.FALSE;
        try {

            versionOk = versionCompare(actualVersion, desiredVersion) >= 0;
        } catch (final NumberFormatException exception) {

            versionOk = Boolean.FALSE;
        }

        if (!versionOk) {

            final MessageFormat formatter = new MessageFormat(errorVersion);
            final String message = formatter.format(new Object[]{
                desiredVersion
            });

            JOptionPane.showMessageDialog(null, message);
            System.exit(1);
        }
    }

    private final static int versionCompare(final String v1, final String v2) {

        final StringTokenizer t1 = new StringTokenizer(v1, "._");
        final StringTokenizer t2 = new StringTokenizer(v2, "._");

        while (t1.hasMoreTokens()) {

            if (!t2.hasMoreTokens()) {
                return 1;
            }

            final int n1 = Integer.parseInt(t1.nextToken());
            final int n2 = Integer.parseInt(t2.nextToken());
            final int d = n1 - n2;
            if (d != 0) {
                return d;
            }
        }

        return t2.hasMoreTokens() ? -1 : 0;
    }

    /**
     * Private Konstruktor, da die Funktionalit�t der Klasse �ber die
     * <code>statische</code> Methode <code>checkVersion(String, String)</code>
     * erzielt wird.
     */
    private PRoVersionChecker() {
        super();
    }
}

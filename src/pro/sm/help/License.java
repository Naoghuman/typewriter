/*
 * Created on 28.07.2007
 */
package pro.sm.help;

import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import pro.sm.resource.ReaderSM;

/**
 * Die Klasse <code>License</code> öffnet ein <code>JOptionPane</code> mit der
 * License.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 28.07.2007
 * @version 1.0
 */
public class License {

    private static final String PATH_LICENSE = "pro/sm/resource/license.html";

    /**
     * Zeigt ein <code>JOptionPane</code> mit den Informationen
     * <code>license.html</code>.
     */
    public static final void showLicense() {

        final JScrollPane jsp = new JScrollPane(
                ReaderSM.readHTML(PATH_LICENSE),
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        jsp.setPreferredSize(new Dimension(500, 240));

        Object[] option = new Object[]{"OK"};
        JOptionPane.showOptionDialog(
                null, jsp, "License",
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.PLAIN_MESSAGE,
                null, option, option[0]
        );
    }
}

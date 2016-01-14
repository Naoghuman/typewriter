/*
 * Created on 28.07.2007
 */
package pro.sm.help;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import pro.sm.resource.ReaderSM;

/**
 * Die Klasse <code>About</code> öffnet ein <code>JOptionPane</code> mit
 * Informationen über das Programm.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 28.07.2007
 * @version 1.0
 */
public class About {

    private static final String PATH_ABOUT = "pro/sm/resource/about.html";

    /**
     * Zeigt ein <code>JOptionPane</code> mit den Informationen
     * <code>about.html</code>.
     */
    public static final void showAbout() {

        final JScrollPane jsp = new JScrollPane(
                ReaderSM.readHTML(PATH_ABOUT),
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        JOptionPane.showMessageDialog(
                null, jsp, "About", JOptionPane.INFORMATION_MESSAGE
        );
    }
}

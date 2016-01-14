/*
 * Created on 28.07.2007
 */
package pro.sm.help;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import pro.sm.resource.ReaderSM;

/**
 * Die Klasse <code>Help</code> öffnet ein <code>JOptionPane</code> mit der
 * Hilfe des Programms.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 28.07.2007
 * @version 1.0
 */
public class Help {

    private static final String PATH_HELP = "pro/sm/resource/help.html";

    /**
     * Zeigt ein <code>JOptionPane</code> mit den Informationen
     * <code>help.html</code>.
     */
    public static final void showHelp() {

        final JScrollPane jsp = new JScrollPane(
                ReaderSM.readHTML(PATH_HELP),
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        JOptionPane.showMessageDialog(
                null, jsp, "Hilfe", JOptionPane.INFORMATION_MESSAGE
        );
    }
}

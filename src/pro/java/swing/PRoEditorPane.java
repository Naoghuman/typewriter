package pro.java.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JEditorPane;

/*
 * Created on 30.07.2007
 */
/**
 * Einfache <code>Util-Klasse</code>, um ein <code>JEditorPane</code> mit
 * <code>Text-Antialiasing</code> zu erhalten.
 *
 * TODO - Die Funktionalitäten von JEditorPane übernehmen. - Abspecken.
 *
 * @author PRo (Peter Rogge) | Copyright (c) 30.07.2007
 * @version 1.0
 */
public class PRoEditorPane extends JEditorPane {

    private static final long serialVersionUID = -4607286805931680175L;

    public PRoEditorPane() {

        super();
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {

        final Graphics2D g2D = (Graphics2D) g.create();
        g2D.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );

        super.paint(g2D);
    }
}

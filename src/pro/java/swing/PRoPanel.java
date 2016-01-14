/*
 * Created on 08.01.2007
 */
package pro.java.swing;

import java.awt.LayoutManager;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPanelUI;

import pro.java.awt.PRoBorderLayout;

/**
 * <code>PRoPanel</code> is a generic lightweight container.<p>
 *
 * To inform the original functions see the <code>JavaDoc</code> from
 * <code>javax.swing.JPanel</code>.<p>
 *
 * <b>Benötigte Klassen:</b><br>
 * - <code>pro.java.awt.PRoBorderLayout</code><br>
 *
 * @author PRo (Peter Rogge) | TraumAG | Copyright (c) 08.01.2007
 * @beaninfo description: A generic lightweight container.
 * @version 1.0
 * @change 20.02.2007
 */
public class PRoPanel extends JComponent {

    private static final long serialVersionUID = 8610787188097260215L;

    /**
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "PRoPanelUI";

    /**
     * Creates a new <code>PRoPanel</code> with a double buffer and a
     * <code>pro.java.awt.PRoBorderLayout</code>.
     */
    public PRoPanel() {
        this(Boolean.TRUE);
    }

    /**
     * Creates a new <code>PRoPanel</code> with
     * <code>pro.java.awt.PRoBorderLayout</code> and the specified buffering
     * strategy. If <code>isDoubleBuffered</code> is true, the
     * <code>PRoPanel</code> will use a double buffer.
     *
     * @param isDoubleBuffered a boolean, true for double-buffering, which uses
     * additional memory space to achieve fast, flicker-free updates.
     */
    public PRoPanel(final boolean isDoubleBuffered) {

        this(new PRoBorderLayout(), isDoubleBuffered);
    }

    /**
     * Create a new buffered <code>PRoPanel</code> with the specified layout
     * manager.
     *
     * @param layout the LayoutManager to use.
     */
    public PRoPanel(final LayoutManager layout) {

        this(layout, Boolean.TRUE);
    }

    /**
     * Creates a new <code>PRoPanel</code> with the specified layout manager and
     * buffering strategy.
     *
     * @param layout the LayoutManager to use.
     * @param isDoubleBuffered a boolean, <code>true</code> for
     * double-buffering, which uses additional memory space to achieve fast,
     * flicker-free updates.
     */
    public PRoPanel(
            final LayoutManager layout, final boolean isDoubleBuffered
    ) {
        super.setLayout(layout);
        super.setDoubleBuffered(isDoubleBuffered);
        super.setOpaque(Boolean.TRUE);

        this.updateUI();
    }

    /**
     * Returns the look and feel (L&F) object that renders this component.
     *
     * @return the PanelUI object that renders this component.
     * @since 1.4
     */
    public final PRoPanelUI getUI() {
        return (PRoPanelUI) ui;
    }

    /**
     * Returns a string that specifies the name of the L&F class that renders
     * this component.
     *
     * @return "PanelUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     * @beaninfo expert: true. description: A string that specifies the name of
     * the L&F class.
     */
    @Override
    public final String getUIClassID() {
        return uiClassID;
    }

    /**
     * Returns a string representation of this <code>PRoPanel</code>. This
     * method is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not be
     * <code>null</code>.
     *
     * @return a string representation of this JPanel.
     */
    @Override
    protected final String paramString() {
        return super.paramString();
    }

    /**
     * Sets the look and feel (L&F) object that renders this component.
     *
     * @param ui the PanelUI L&F object
     * @see UIDefaults#getUI
     * @since 1.4
     * @beaninfo bound: true. hidden: true. attribute: visualUpdate true.
     * description: The UI object that implements the Component's LookAndFeel.
     */
    public final void setUI(final PRoPanelUI ui) {
        super.setUI(ui);
    }

    /**
     * Resets the UI property with a value from the current look and feel.
     *
     * @see JComponent#updateUI
     */
    @Override
    public final void updateUI() {
        this.setUI(new PRoPanelUI());
    }
}

/**
 * Grundlegende <code>PRoPanel</code> Implementation.
 *
 * @author PRo (Peter Rogge) | TraumAG | Copyright (c) 08.01.2007
 * @version 1.0
 */
final class PRoPanelUI extends ComponentUI {

    // Shared UI object
    private static BasicPanelUI panelUI;

    public static ComponentUI createUI(final JComponent c) {

        if (panelUI == null) {
            panelUI = new BasicPanelUI();
        }

        return panelUI;
    }

    protected void installDefaults(final PRoPanel pp) {

        LookAndFeel.installColorsAndFont(
                pp, "Panel.background",
                "Panel.foreground", "Panel.font"
        );
        LookAndFeel.installBorder(pp, "Panel.border");
        LookAndFeel.installProperty(pp, "opaque", Boolean.TRUE);
    }

    @Override
    public final void installUI(final JComponent c) {

        final PRoPanel pp = (PRoPanel) c;
        super.installUI(pp);
        this.installDefaults(pp);
    }

    protected void uninstallDefaults(final PRoPanel pp) {

        LookAndFeel.uninstallBorder(pp);
    }

    @Override
    public final void uninstallUI(final JComponent c) {

        final PRoPanel pp = (PRoPanel) c;
        this.uninstallDefaults(pp);
        super.uninstallUI(c);
    }
}

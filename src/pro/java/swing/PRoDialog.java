package pro.java.swing;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JRootPane;
import javax.swing.RootPaneContainer;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import pro.java.awt.PRoBorderLayout;

/**
 * Mit <code>PRoDialog</code> können Sie Ihr eigenes Dialogfenter generieren.<p>
 *
 * Als <code>Defaultlayout</code> ist ein <code>PRoBorderLayout</code> mit 10px
 * Abstand zwischen den Komponenten und einen 10px großen leeren Rahmen um alle
 * Komponenten implementiert.<p>
 *
 * Über die fünf <code>initDirection-Methoden</code> (Norden, Süden, Osten,
 * Westen und Zentrum) können Komponenten den jeweiligen Bereichen hinzugefügt
 * werden. Die Methoden initCenter() und initSouth() sind abstract, d.h. sie
 * müssen implementiert werden, während die restlichen initDirection-Methoden
 * optional überlagert werden können. Natürlich können Sie auch die Methode
 * <code>init()</code> einfach überlagern, damit wären die
 * <code>Defaulteinstellungen</code> der fünf
 * <code>initDirection-Methoden</code> in Ihren Händen.<p>
 *
 * Über die beiden Methoden <code>closePRoDialog()</code> und
 * <code>showPRoDialog()</code> können Sie sich den PRoDialog anzeigen oder
 * entfernen lassen.<p>
 *
 * <b>Hinweis:</b><br>
 * Sollte der leere Rahmen um alle Komponenten oder der Zwischenabstand
 * verändert werden, so ist über die Methode
 * <code>setLayout(LayoutManager)</code> ein neues Layout mit den Maßen zu
 * implementieren.<p>
 *
 * <b>Benötigte Klassen:</b><br>
 * - <code>pro.java.awt.PRoBorderLayout</code><br>
 * - <code>pro.java.swing.PRoPanel</code><p>
 *
 * @author PRo (Peter Rogge) | TraumAG | Copyright (c) 01.11.2006
 * @version 2.0
 * @change 21.02.2007
 * @change 16.05.2007
 */
public abstract class PRoDialog extends Dialog implements WindowConstants {

    private static final class SharedOwnerFrame extends Frame implements
            WindowListener {

        private static final long serialVersionUID = 6636430558426450766L;

        public SharedOwnerFrame() {
            super();
        }

        @Override
        public final void addNotify() {

            super.addNotify();
            this.installListeners();
        }

        @Override
        public final void dispose() {

            try {

                super.getToolkit().getSystemEventQueue();
                super.dispose();
            } catch (final Exception e) {
            }
        }

        /**
         * Install window listeners on owned windows to watch for displayability
         * changes
         */
        private final void installListeners() {

            final Window[] windows = super.getOwnedWindows();
            for (final Window window : windows) {

                if (window != null) {

                    window.removeWindowListener(this);
                    window.addWindowListener(this);
                }
            }
        }

        // This frame can never be shown.
        @Deprecated
        public final void show() {
        }

        public final void windowActivated(final WindowEvent e) {
        }

        /**
         * Watches for displayability changes and disposes shared instance if
         * there are no displayable children left.
         */
        public final void windowClosed(final WindowEvent e) {

            synchronized (this.getTreeLock()) {

                final Window[] windows = this.getOwnedWindows();
                for (final Window window : windows) {
                    if (window != null) {

                        if (window.isDisplayable()) {
                            return;
                        }
                        window.removeWindowListener(this);
                    }
                }

                this.dispose();
            }
        }

        public final void windowClosing(final WindowEvent e) {
        }

        public final void windowDeactivated(final WindowEvent e) {
        }

        public final void windowDeiconified(final WindowEvent e) {
        }

        public final void windowIconified(final WindowEvent e) {
        }

        public final void windowOpened(final WindowEvent e) {
        }
    }

    private static final long serialVersionUID = -7933326619171213559L;

    protected static final Color BACKGROUND = new Color(240, 240, 240);

    private static final Hashtable<StringBuffer, Object> TABLE
            = new Hashtable<StringBuffer, Object>();

    // Don't use String, as it's not guaranteed to be unique in a Hashtable.
    private static final StringBuffer defaultLookAndFeelDecoratedKey
            = new StringBuffer("PRoDialog.defaultLookAndFeelDecorated");

    private static final StringBuffer sharedOwnerFrameKey = new StringBuffer(
            "PRoDialog.sharedOwnerFrame"
    );

    /**
     * Returns a toolkit-private, shared, invisible Frame to be the owner for
     * <code>PRoDialog</code> created with null owners.
     */
    public static Frame getSharedOwnerFrame() {

        Frame sof = (Frame) TABLE.get(sharedOwnerFrameKey);
        if (sof == null) {

            sof = new SharedOwnerFrame();
            TABLE.put(sharedOwnerFrameKey, sof);
        }

        return sof;
    }

    /**
     * Returns true if newly created <code>PRoDialog</code>s should have their
     * Window decorations provided by the current look and feel. This is only a
     * hint, as certain look and feels may not support this feature.
     *
     * @return true if look and feel should provide Window decorations.
     * @since 1.4
     */
    public static boolean isDefaultLookAndFeelDecorated() {

        Boolean defaultLookAndFeelDecorated
                = (Boolean) TABLE.get(defaultLookAndFeelDecoratedKey);
        if (defaultLookAndFeelDecorated == null) {

            defaultLookAndFeelDecorated = Boolean.FALSE;
        }

        return defaultLookAndFeelDecorated.booleanValue();
    }

    /**
     * Provides a hint as to whether or not newly created
     * <code>PRoDialog</code>s should have their Window decorations (such as
     * borders, widgets to close the window, title...) provided by the current
     * look and feel. If <code>defaultLookAndFeelDecorated</code> is true, the
     * current <code>LookAndFeel</code> supports providing window decorations,
     * and the current window manager supports undecorated windows, then newly
     * created <code>PRoDialog</code>s will have their Window decorations
     * provided by the current <code>LookAndFeel</code>. Otherwise, newly
     * created <code>JDialog</code>s will have their Window decorations provided
     * by the current window manager.
     * <p>
     * You can get the same effect on a single PRoDialog by doing the following:
     * <pre>
     *    PRoDialog dialog = new PRoDialog();
     *    dialog.setUndecorated(true);
     *    dialog.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
     * </pre>
     *
     * @param defaultLookAndFeelDecorated A hint as to whether or not current
     * look and feel should provide window decorations
     * @see javax.swing.LookAndFeel#getSupportsWindowDecorations
     * @since 1.4
     */
    public static void setDefaultLookAndFeelDecorated(
            final boolean defaultLookAndFeelDecorated
    ) {
        TABLE.put(
                defaultLookAndFeelDecoratedKey,
                defaultLookAndFeelDecorated
        );
    }

    private boolean init = Boolean.FALSE;
    private boolean rootPaneCheckingEnabled = Boolean.FALSE;

    private int defaultCloseOperation = EXIT_ON_CLOSE;

    private JRootPane rootPane = null;

    private PRoPanel ppPRoDialog = null;

    /**
     * Creates a non-modal dialog without a title and without a specified
     * <code>Frame</code> owner. A shared, hidden frame will be set as the owner
     * of the dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     * true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public PRoDialog() throws HeadlessException {

        this((Frame) null, Boolean.FALSE);
    }

    /**
     * Creates a non-modal dialog without a title with the specified
     * <code>Dialog</code> as its owner.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param owner the non-null <code>Dialog</code> from which the dialog is
     * displayed
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     * true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public PRoDialog(final Dialog owner) throws HeadlessException {

        this(owner, null, Boolean.FALSE);
    }

    /**
     * Creates a modal or non-modal dialog without a title and with the
     * specified owner dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param owner the non-null <code>Dialog</code> from which the dialog is
     * displayed
     * @param modal true for a modal dialog, false for one that allows other
     * windows to be active at the same time
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     * true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public PRoDialog(final Dialog owner, final boolean modal)
            throws HeadlessException {

        this(owner, null, modal);
    }

    /**
     * Creates a non-modal dialog with the specified title and with the
     * specified owner dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param owner the non-null <code>Dialog</code> from which the dialog is
     * displayed
     * @param title the <code>String</code> to display in the dialog's title bar
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     * true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public PRoDialog(final Dialog owner, final String title)
            throws HeadlessException {

        this(owner, title, Boolean.FALSE);
    }

    /**
     * Creates a modal or non-modal dialog with the specified title and the
     * specified owner frame.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param owner the non-null <code>Dialog</code> from which the dialog is
     * displayed
     * @param title the <code>String</code> to display in the dialog's title bar
     * @param modal true for a modal dialog, false for one that allows other
     * windows to be active at the same time
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     * true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public PRoDialog(final Dialog owner, final String title, final boolean modal)
            throws HeadlessException {

        super(owner, title, modal);

        this.initPRoDialog();
    }

    /**
     * Creates a non-modal dialog without a title with the specified
     * <code>Frame</code> as its owner. If <code>owner</code> is
     * <code>null</code>, a shared, hidden frame will be set as the owner of the
     * dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param owner the <code>Frame</code> from which the dialog is displayed
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     * true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public PRoDialog(final Frame owner) throws HeadlessException {

        this(owner, Boolean.FALSE);
    }

    /**
     * Creates a modal or non-modal dialog without a title and with the
     * specified owner <code>Frame</code>. If <code>owner</code> is
     * <code>null</code>, a shared, hidden frame will be set as the owner of the
     * dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param owner the <code>Frame</code> from which the dialog is displayed
     * @param modal true for a modal dialog, false for one that allows others
     * windows to be active at the same time
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     * true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public PRoDialog(final Frame owner, final boolean modal)
            throws HeadlessException {

        this(owner, null, modal);
    }

    /**
     * Creates a non-modal dialog with the specified title and with the
     * specified owner frame. If <code>owner</code> is <code>null</code>, a
     * shared, hidden frame will be set as the owner of the dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param owner the <code>Frame</code> from which the dialog is displayed
     * @param title the <code>String</code> to display in the dialog's title bar
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     * true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public PRoDialog(final Frame owner, final String title)
            throws HeadlessException {

        this(owner, title, Boolean.FALSE);
    }

    /**
     * Creates a modal or non-modal dialog with the specified title and the
     * specified owner <code>Frame</code>. If <code>owner</code> is
     * <code>null</code>, a shared, hidden frame will be set as the owner of
     * this dialog. All constructors defer to this one.
     * <p>
     * NOTE: Any popup components (<code>JComboBox</code>,
     * <code>JPopupMenu</code>, <code>JMenuBar</code>) created within a modal
     * dialog will be forced to be lightweight.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @param owner the <code>Frame</code> from which the dialog is displayed
     * @param title the <code>String</code> to display in the dialog's title bar
     * @param modal true for a modal dialog, false for one that allows other
     * windows to be active at the same time
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     * true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public PRoDialog(final Frame owner, final String title, final boolean modal)
            throws HeadlessException {

        super(
                (owner == null ? PRoDialog.getSharedOwnerFrame() : owner),
                title, modal
        );

        if (owner == null) {

            super.addWindowListener(
                    (WindowListener) PRoDialog.getSharedOwnerFrame()
            );
        }

        this.initPRoDialog();
    }

    /**
     * Adds the specified child <code>Component</code>. This method is
     * overridden to conditionally forwad calls to the <code>contentPane</code>.
     * By default, children are added to the <code>contentPane</code> instead of
     * the frame, refer to {@link javax.swing.RootPaneContainer} for details.
     *
     * @param comp the component to be enhanced
     * @param constraints the constraints to be respected
     * @param index the index
     * @exception IllegalArgumentException if <code>index</code> is invalid
     * @exception IllegalArgumentException if adding the container's parent to
     * itself
     * @exception IllegalArgumentException if adding a window to a container
     * @see #setRootPaneCheckingEnabled
     * @see javax.swing.RootPaneContainer
     */
    @Override
    protected void addImpl(
            final Component comp, final Object constraints, final int index
    ) {
        if (rootPaneCheckingEnabled) {

            this.getContentPane().add(comp, constraints, index);
        } else {

            super.addImpl(comp, constraints, index);
        }
    }

    /**
     * Der <code>PRoDialog</code> wird geschlossen.
     */
    public void closePRoDialog() {

        super.setVisible(Boolean.FALSE);
        super.dispose();
    }

    /**
     * Called by the constructor methods to create the default
     * <code>rootPane</code>.
     */
    private final JRootPane createRootPane() {

        final JRootPane rp = new JRootPane();
		// NOTE: this uses setOpaque vs LookAndFeel.installProperty as there
        // is NO reason for the RootPane not to be opaque. For painting to
        // work the contentPane must be opaque, therefor the RootPane can
        // also be opaque.
        rp.setOpaque(Boolean.TRUE);

        return rp;
    }

    /**
     * Returns the <code>contentPane</code> object for this dialog.
     *
     * @return the <code>contentPane</code> property
     * @see #setContentPane
     * @see RootPaneContainer#getContentPane
     */
    public final Container getContentPane() {

        return rootPane.getContentPane();
    }

    /**
     * Returns the <code>rootPane</code> object for this dialog.
     *
     * @see #setRootPane
     * @see RootPaneContainer#getRootPane
     */
    public final JRootPane getRootPane() {
        return rootPane;
    }

    /**
     * Initialisiert den <code>PRoDialog</code>.
     *
     * Komponenten des <code>PRoDialogs</code> werden den verschiedenen
     * <code>Ausrichtung</code> des Layouts <code>PRoBorderLayout</code>
     * zugeordnet.<p>
     *
     * Die beiden Methoden <code>initCenter()</code> und
     * <code>initSouth()</code> m�ssen implementiert werden, w�hrend die
     * restlichen Ausrichtungen optional sind.<p>
     *
     * <b>Hinweis:</b><br>
     * Wenn �ber die Methode <code>setLayout(LayoutManager)</code> ein anderes
     * Layout implementiert wird, dann ist diese Methode zu �berlagern, damit
     * die neue Komponenten entsprechend angeordnet werden.
     */
    protected void init() {

        this.getContentPane().removeAll();
        ppPRoDialog.removeAll();

        JComponent jc = this.initNorth();
        if (jc != null) {
            ppPRoDialog.add(jc, PRoBorderLayout.NORTH);
        }

        jc = this.initEast();
        if (jc != null) {
            ppPRoDialog.add(jc, PRoBorderLayout.EAST);
        }

        jc = this.initWest();
        if (jc != null) {
            ppPRoDialog.add(jc, PRoBorderLayout.WEST);
        }

        ppPRoDialog.add(this.initCenter(), PRoBorderLayout.CENTER);
        ppPRoDialog.add(this.initSouth(), PRoBorderLayout.SOUTH);

        this.getContentPane().add(ppPRoDialog, PRoBorderLayout.CENTER);

        super.pack();
        super.setLocationRelativeTo(null);
        super.setResizable(Boolean.FALSE);

        init = Boolean.TRUE;
    }

    /**
     * Komponenten werden im <code>Zentrum</code> des <code>PRoDialogs</code>
     * implementiert.<p>
     *
     * Subklassen von <code>PRoBasicDialog</code> m�ssen diese Methode
     * implentieren.
     */
    protected abstract JComponent initCenter();

    /**
     * Optionale Methode um Komponenten im <code>Osten</code> des
     * <code>PRoDialogs</code> zu implementieren.
     */
    protected JComponent initEast() {
        return null;
    }

    /**
     * Optionale Methode um Komponenten im <code>Norden</code> des
     * <code>PRoDialogs</code> zu implementieren.
     */
    protected JComponent initNorth() {
        return null;
    }

    /**
     * Called by the constructors to init the <code>PRoDialog</code> properly.
     */
    private final void initPRoDialog() {

        super.enableEvents(AWTEvent.KEY_EVENT_MASK | AWTEvent.WINDOW_EVENT_MASK);
        super.setLayout(new PRoBorderLayout());
        super.setLocale(JComponent.getDefaultLocale());
        this.setRootPane(this.createRootPane());

        rootPaneCheckingEnabled = Boolean.TRUE;

        if (JDialog.isDefaultLookAndFeelDecorated()) {

            final boolean supportsWindowDecorations = UIManager.getLookAndFeel()
                    .getSupportsWindowDecorations();
            if (supportsWindowDecorations) {

                super.setUndecorated(Boolean.TRUE);
                this.getRootPane().setWindowDecorationStyle(
                        JRootPane.PLAIN_DIALOG
                );
            }
        }

        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        ppPRoDialog = new PRoPanel(
                new PRoBorderLayout(10, 10, 10, 10, 10, 10), Boolean.TRUE
        );

        this.setBackground(BACKGROUND);
        this.setForeground(Color.BLACK);
    }

    /**
     * Komponenten werden im <code>S�den</code> des <code>PRoDialogs</code>
     * implementiert.<p>
     *
     * Subklassen von <code>PRoBasicDialog</code> m�ssen diese Methode
     * implentieren.
     */
    protected abstract JComponent initSouth();

    /**
     * Optionale Methode um Komponenten im <code>Westen</code> des
     * <code>PRoDialogs</code> zu implementieren.
     */
    protected JComponent initWest() {
        return null;
    }

    /**
     * Returns a string representation of this <code>PRoDialog</code>. This
     * method is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not be
     * <code>null</code>.
     *
     * @return a string representation of this <code>PRoDialog</code>.
     */
    @Override
    protected String paramString() {

        String defaultCloseOperationString = "";
        if (defaultCloseOperation == HIDE_ON_CLOSE) {

            defaultCloseOperationString = "HIDE_ON_CLOSE";
        } else if (defaultCloseOperation == DISPOSE_ON_CLOSE) {

            defaultCloseOperationString = "DISPOSE_ON_CLOSE";
        } else if (defaultCloseOperation == DO_NOTHING_ON_CLOSE) {

            defaultCloseOperationString = "DO_NOTHING_ON_CLOSE";
        }

        final String rootPaneString
                = (rootPane != null ? rootPane.toString() : "");
        final String rootPaneCheckingEnabledString = (rootPaneCheckingEnabled ? "true" : "false");

        return super.paramString() + ",defaultCloseOperation="
                + defaultCloseOperationString + ",rootPane="
                + rootPaneString + ",rootPaneCheckingEnabled="
                + rootPaneCheckingEnabledString;
    }

    /**
     * Handles window events depending on the state of the
     * <code>defaultCloseOperation</code> property.
     *
     * @see #setDefaultCloseOperation
     */
    @Override
    protected final void processWindowEvent(final WindowEvent e) {

        super.processWindowEvent(e);

        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            switch (defaultCloseOperation) {
                case EXIT_ON_CLOSE: {

                    super.setVisible(Boolean.FALSE);
                    super.dispose();
                    System.exit(0);
                }
                case HIDE_ON_CLOSE: {

                    super.setVisible(Boolean.FALSE);
                    break;
                }
                case DISPOSE_ON_CLOSE: {

                    super.setVisible(Boolean.FALSE);
                    super.dispose();
                    break;
                }
                case DO_NOTHING_ON_CLOSE:
                default: {
                    break;
                }
            }
        }
    }

    /**
     * Removes the specified component from the container. If <code>comp</code>
     * is not the <code>rootPane</code>, this will forward the call to the
     * <code>contentPane</code>. This will do nothing if <code>comp</code> is
     * not a child of the <code>PRoDialog</code> or <code>contentPane</code>.
     *
     * @param comp the component to be removed
     * @throws NullPointerException if <code>comp</code> is null
     * @see #add
     * @see javax.swing.RootPaneContainer
     */
    @Override
    public final void remove(final Component comp) {

        if (comp == rootPane) {

            super.remove(comp);
        } else {

            this.getContentPane().remove(comp);
        }
    }

    /* (non-Javadoc)
     * @see java.awt.Component#setBackground(java.awt.Color)
     */
    @Override
    public void setBackground(Color c) {

        ppPRoDialog.setBackground(c);
        super.setBackground(c);
    }

    /**
     * Sets the <code>contentPane</code> property. This method is called by the
     * constructor.
     * <p>
     * Swing's painting architecture requires an opaque <code>JComponent</code>
     * in the containment hiearchy. This is typically provided by the content
     * pane. If you replace the content pane it is recommended you replace it
     * with an opaque <code>JComponent</code>.
     *
     * @see JRootPane
     * @param contentPane the <code>contentPane</code> object for this dialog
     * @exception java.awt.IllegalComponentStateException (a runtime exception)
     * if the content pane parameter is <code>null</code>
     * @see #getContentPane
     * @see RootPaneContainer#setContentPane
     * @beaninfo hidden: true description: The client area of the dialog where
     * child components are normally inserted.
     */
    public final void setContentPane(final Container contentPane) {

        rootPane.setContentPane(contentPane);
    }

    /**
     * Sets the operation which will happen by default when the user initiates a
     * "close" on this dialog. The possible choices are:
     * <ul>
     * <li><code>DO_NOTHING_ON_CLOSE</code><br>
     * Do not do anything - require the program to handle the operation in the
     * <code>windowClosing</code> method of a registered
     * <code>WindowListener</code> object.
     * <li><code>HIDE_ON_CLOSE</code><br>
     * Automatically hide the dialog after invoking any registered
     * <code>WindowListener</code> objects
     * <li><code>DISPOSE_ON_CLOSE</code><br>
     * Automatically hide and dispose the dialog after invoking any registered
     * <code>WindowListener</code> objects
     * </ul>
     * <p>
     * The value is set to <code>HIDE_ON_CLOSE</code> by default.
     * <p>
     * <b>Note</b>: When the last displayable window within the Java virtual
     * machine (VM) is disposed of, the VM may terminate. See <a
     * href="../../java/awt/doc-files/AWTThreadIssues.html"> AWT Threading
     * Issues</a> for more information.
     *
     * @see #addWindowListener
     * @see #getDefaultCloseOperation
     * @beaninfo preferred: true description: The dialog's default close
     * operation.
     */
    public final void setDefaultCloseOperation(final int operation) {

        this.defaultCloseOperation = operation;
    }

    /* (non-Javadoc)
     * @see java.awt.Container#setFont(java.awt.Font)
     */
    @Override
    public void setFont(Font f) {

        ppPRoDialog.setFont(f);
        super.setFont(f);
    }

    /* (non-Javadoc)
     * @see java.awt.Component#setForeground(java.awt.Color)
     */
    @Override
    public void setForeground(Color c) {

        ppPRoDialog.setForeground(c);
        super.setForeground(c);
    }

    /**
     * Sets the menubar for this dialog.
     *
     * @param menu the menubar being placed in the dialog
     * @see #getJMenuBar
     * @beaninfo hidden: true description: The menubar for accessing pulldown
     * menus from this dialog.
     */
    protected void setJMenuBar(final JMenuBar menu) {

        rootPane.setJMenuBar(menu);
    }

    /**
     * Sets the <code>LayoutManager</code>. Overridden to conditionally forward
     * the call to the <code>contentPane</code>. Refer to
     * {@link javax.swing.RootPaneContainer} for more information.<p>
     *
     * <b>Wichtig:</b><br>
     * Der neue <code>LayoutManager</code> sollte ein
     * <code>PRoBorderLayout</code> sein, da das Hinzuf�gen der Komponenten in
     * der Methode <code>init()</code> sich auf das Layout
     * <code>PRoBorderLayout</code> bezieht.<br>
     * M�chten Sie ein anderes Layout implementieren, dann ist die Methode
     * <code>init()</code> zu �berlagern.
     *
     * @param manager the <code>LayoutManager</code>
     * @see #setRootPaneCheckingEnabled
     * @see javax.swing.RootPaneContainer
     */
    @Override
    public final void setLayout(final LayoutManager manager) {

        if (init) {

            ppPRoDialog.removeAll();
            ppPRoDialog.setLayout(manager);

            this.getContentPane().removeAll();
            this.init();
        }

        if (!init) {

            if (rootPaneCheckingEnabled) {

                this.getContentPane().setLayout(manager);
            } else {

                super.setLayout(manager);
            }
        }
    }

    /**
     * Sets the <code>rootPane</code> property. This method is called by the
     * constructor.
     *
     * @param root the <code>rootPane</code> object for this dialog
     * @see #getRootPane
     * @beaninfo hidden: true description: the RootPane object for this dialog.
     */
    protected void setRootPane(final JRootPane root) {

        if (rootPane != null) {
            this.remove(rootPane);
        }
        rootPane = root;

        if (rootPane != null) {

            final boolean checkingEnabled = rootPaneCheckingEnabled;
            try {

                rootPaneCheckingEnabled = Boolean.FALSE;
                super.add(rootPane, BorderLayout.CENTER);
            } finally {

                rootPaneCheckingEnabled = checkingEnabled;
            }
        }
    }

    /**
     * Der <code>PRoDialog</code> wird angezeigt.
     */
    public void showPRoDialog() {
        super.setVisible(Boolean.TRUE);
    }

    /**
     * Calls <code>paint(g)</code>. This method was overridden to prevent an
     * unnecessary call to clear the background.
     *
     * @param g the <code>Graphics</code> context in which to paint
     */
    @Override
    public void update(final Graphics g) {
        super.paint(g);
    }
}

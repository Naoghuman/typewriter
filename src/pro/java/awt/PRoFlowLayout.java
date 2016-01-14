/*
 * Created on 09.10.2006
 */
package pro.java.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * A <code>PRoFlowLayout</code> arranges components in a directional flow, much
 * like lines of text in a paragraph. The flow direction is determined by the
 * container's <code>componentOrientation</code> property and may be one of two
 * values:
 * <ul>
 * <li><code>ComponentOrientation.LEFT_TO_RIGHT</code>
 * <li><code>ComponentOrientation.RIGHT_TO_LEFT</code>
 * </ul>
 * <code>PRoFlowLayout</code> are typically used to arrange buttons in a panel.
 * It arranges buttons horizontally until no more buttons fit on the same line.
 * The line alignment is determined by the <code>align</code> property. The
 * possible values are:
 * <ul>
 * <li>{@link #LEFT LEFT}
 * <li>{@link #RIGHT RIGHT}
 * <li>{@link #CENTER CENTER}
 * <li>{@link #LEADING LEADING}
 * <li>{@link #TRAILING TRAILING}
 * </ul>
 * <p>
 * To inform the original functions see the <code>JavaDoc</code> from
 * <code>java.awt.FlowLayout</code>.
 * <p>
 * <b>Changes:</b><br>
 * The default gap (horizontal and vertical) are new now 10px (earlier 5px).
 * Also when you set a gap it wan't be automatical a emty border around all
 * components. When you will set a emty border around all components, use the
 * corresponding constructor therefor. The next change is that I removed all
 * <code>Getter-</code> and <code>Setter-Methods</code>. So if you want to
 * change a value, you must implement the classes new. Internal I convert all
 * <code>primitive Datatyps</code> to reduce the memory demand.
 * <p>
 *
 * @author PRo (Peter Rogge) | Copyright (c) 09.10.2006
 * @version 1.0
 */
public class PRoFlowLayout implements LayoutManager, java.io.Serializable {

    /**
     * the internal serial version which says which version was written<br>
     * - 0 (default) for versions before the Java 2 platform, v1.2<br>
     * - 1 for version >= Java 2 platform v1.2, which includes "newAlign" field
     */
    private static final byte currentSerialVersion = 1;

    /**
     * JDK 1.1 serialVersionUID
     */
    private static final long serialVersionUID = 1394813736813782958L;

    /**
     * This value indicates that each row of components should be
     * left-justified.
     */
    public static final byte LEFT = 0;

    /**
     * This value indicates that each row of components should be centered.
     */
    public static final byte CENTER = 1;

    /**
     * This value indicates that each row of components should be
     * right-justified.
     */
    public static final byte RIGHT = 2;

    /**
     * This value indicates that each row of components should be justified to
     * the leading edge of the container's orientation, for example, to the left
     * in left-to-right orientations.
     *
     * @see java.awt.Component#getComponentOrientation
     * @see java.awt.ComponentOrientation
     * @since 1.2 Package-private pending API change approval
     */
    public static final byte LEADING = 3;

    /**
     * This value indicates that each row of components should be justified to
     * the trailing edge of the container's orientation, for example, to the
     * right in left-to-right orientations.
     *
     * @see java.awt.Component#getComponentOrientation
     * @see java.awt.ComponentOrientation
     * @since 1.2 Package-private pending API change approval
     */
    public static final byte TRAILING = 4;

    /**
     * <code>align</code> is the property that determines how each row
     * distributes empty space. It can be one of the following values:
     * <ul>
     * <code>LEFT</code> <code>RIGHT</code> <code>CENTER</code>
     * <code>LEADING</code> <code>TRAILING</code>
     * </ul>
     */
    private byte align = LEFT; // This is for 1.1 serialization compatibility

    /**
     * <code>newAlign</code> is the property that determines how each row
     * distributes empty space for the Java 2 platform, v1.2 and greater. It can
     * be one of the following three values:
     * <ul>
     * <code>LEFT</code> <code>RIGHT</code> <code>CENTER</code>
     * <code>LEADING</code> <code>TRAILING</code>
     * </ul>
     */
    private byte newAlign = LEFT; // This is the one we actually use

    /**
     * This represent the <code>currentSerialVersion</code> which is bein used.
     * It will be one of two values : <code>0</code> versions before Java 2
     * platform v1.2.. <code>1</code> versions after Java 2 platform v1.2..
     *
     * @serial
     * @since 1.2
     */
    private byte serialVersionOnStream = currentSerialVersion;

    /**
     * This is the left border (in pixels) which specifies the space on the left
     * side form the parent component. This should be a non-negative short.
     */
    private short left = 0;

    /**
     * This is the right border (in pixels) which specifies the space on the
     * right side form the parent component. This should be a non-negative
     * short.
     */
    private short right = 0;

    /**
     * This is the top border (in pixels) which specifies the space on the top
     * side form the parent component. This should be a non-negative short.
     */
    private short top = 0;

    /**
     * This is the bottom border (in pixels) which specifies the space on the
     * bottom side form the parent component. This should be a non-negative
     * short.
     */
    private short bottom = 0;

    /**
     * The flow layout manager allows a seperation of components with gaps. The
     * horizontal gap will specify the space between components.
     */
    private short hgap = 10;

    /**
     * The flow layout manager allows a seperation of components with gaps. The
     * vertical gap will specify the space between rows.
     */
    private short vgap = 10;

    /**
     * Constructs a new <code>PRoFlowLayout</code> with a centered alignment and
     * a default 10-unit horizontal and vertical gap. There is no empty border
     * around all components.
     */
    public PRoFlowLayout() {
        this(CENTER);
    }

    /**
     * Constructs a new <code>PRoFlowLayout</code> with the specified alignment
     * and a default 10-unit horizontal and vertical gap.
     * <p>
     * The value of the alignment argument must be one of
     * <code>PRoFlowLayout.LEFT</code>, <code>PRoFlowLayout.RIGHT</code>,
     * <code>PRoFlowLayout.CENTER</code>, <code>PRoFlowLayout.LEADING</code>, or
     * <code>PRoFlowLayout.TRAILING</code>.
     * <p>
     * There is no empty border around all components.
     *
     * @param align the alignment value.
     */
    public PRoFlowLayout(byte align) {
        this(align, 0, 0, 0, 0);
    }

    /**
     * Creates a new <code>PRoFlowLayout</code> with the indicated alignment and
     * the indicated horizontal and vertical gaps.
     * <p>
     * The value of the alignment argument must be one of
     * <code>PRoFlowLayout.LEFT</code>, <code>PRoFlowLayout.RIGHT</code>,
     * <code>PRoFlowLayout.CENTER</code>, <code>PRoFlowLayout.LEADING</code>, or
     * <code>PRoFlowLayout.TRAILING</code>.
     * <p>
     * There is no empty border around all components.
     *
     * @param align the alignment value.
     * @param hgap the horizontal gap between components.
     * @param vgap the vertical gap between components.
     */
    public PRoFlowLayout(byte align, int hgap, int vgap) {

        this(align, hgap, vgap, 0, 0, 0, 0);
    }

    /**
     * Constructs a new <code>PRoFlowLayout</code> with the specified alignment
     * and a default 10-unit horizontal and vertical gap.
     * <p>
     * The value of the alignment argument must be one of
     * <code>PRoFlowLayout.LEFT</code>, <code>PRoFlowLayout.RIGHT</code>,
     * <code>PRoFlowLayout.CENTER</code>, <code>PRoFlowLayout.LEADING</code>, or
     * <code>PRoFlowLayout.TRAILING</code>.
     * <p>
     * Around all components is a empty border with <code>top</code>,
     * <code>left</code>, <code>bottom</code> and <code>right</code>. None of
     * this border values can be < zero.
     *
     * @param align the alignment value.
     *
     * @param top the empty top border.
     * @param left the empty left border.
     * @param bottom the empty bottom border.
     * @param right the empty right border.
     */
    public PRoFlowLayout(
            byte align,
            final int top, final int left,
            final int bottom, final int right
    ) {
        this(align, 10, 10, top, left, bottom, right);
    }

    /**
     * Creates a new <code>PRoFlowLayout</code> with the indicated alignment and
     * the indicated horizontal and vertical gaps.
     * <p>
     * The value of the alignment argument must be one of
     * <code>PRoFlowLayout.LEFT</code>, <code>PRoFlowLayout.RIGHT</code>,
     * <code>PRoFlowLayout.CENTER</code>, <code>PRoFlowLayout.LEADING</code>, or
     * <code>PRoFlowLayout.TRAILING</code>.
     * <p>
     * Around all components is a empty border with <code>top</code>,
     * <code>left</code>, <code>bottom</code> and <code>right</code>. None of
     * this border values can be < zero.
     *
     * @param align the alignment value.
     * @param hgap the horizontal gap between components.
     * @param vgap the vertical gap between components.
     *
     * @param top the empty top border.
     * @param left the empty left border.
     * @param bottom the empty bottom border.
     * @param right the empty right border.
     *
     * @exception IllegalArgumentException if one of <code>hgap</code>,
     * <code>vgap</code>, <code>top</code>, <code>left</code>,
     * <code>bottom</code> or <code>right</code> < zero.
     */
    public PRoFlowLayout(
            byte align, int hgap, int vgap,
            final int top, final int left,
            final int bottom, final int right
    ) {
        if ((hgap < 0) || (vgap < 0)) {
            throw new IllegalArgumentException("hgap or vgap can't < zero.");
        }

        if ((top < 0) || (left < 0) || (bottom < 0) || (right < 0)) {
            throw new IllegalArgumentException(
                    "No one of top, left, bottom or right can't < zero."
            );
        }

        this.hgap = (short) hgap;
        this.vgap = (short) vgap;

        this.top = (short) top;
        this.left = (short) left;
        this.bottom = (short) bottom;
        this.right = (short) right;

        this.setAlignment(align);
    }

    /**
     * Adds the specified component to the layout. Not used by this class.
     *
     * @param name the name of the component
     * @param comp the component to be added
     */
    public final void addLayoutComponent(final String name, final Component comp) {
    }

    /**
     * Lays out the container. This method lets each <i>visible</i> component
     * take its preferred size by reshaping the components in the target
     * container in order to satisfy the alignment of this
     * <code>FlowLayout</code> object.
     *
     * @param target the specified component being laid out
     * @see Container
     * @see java.awt.Container#doLayout
     */
    public final void layoutContainer(final Container target) {

        synchronized (target.getTreeLock()) {

            final Insets insets = target.getInsets();
            final short maxwidth = (short) (target.getWidth()
                    - (insets.left + insets.right + left + right));
            final short nmembers = (short) target.getComponentCount();
            final boolean ltr = target.getComponentOrientation().isLeftToRight();

            int x = 0, y = insets.top + top;
            int rowh = 0, start = 0;

            for (int i = 0; i < nmembers; i++) {

                final Component m = target.getComponent(i);
                if (m.isVisible()) {
                    final Dimension d = m.getPreferredSize();
                    m.setSize(d.width, d.height);

                    if ((x == 0) || ((x + d.width) <= maxwidth)) {
                        if (x > 0) {
                            x += hgap;
                        }
                        x += d.width;
                        rowh = Math.max(rowh, d.height);
                    } else {
                        this.moveComponents(
                                target,
                                (short) (insets.left + left), (short) y,
                                (short) (maxwidth - x), (short) rowh,
                                (short) start, (short) i,
                                ltr
                        );
                        x = d.width;
                        y += vgap + rowh;
                        rowh = d.height;
                        start = i;
                    }
                }
            }
            this.moveComponents(
                    target,
                    (short) (insets.left + left), (short) y,
                    (short) (maxwidth - x), (short) rowh,
                    (short) start, nmembers,
                    ltr
            );
        }
    }

    /**
     * Returns the minimum dimensions needed to layout the <i>visible</i>
     * components contained in the specified target container.
     *
     * @param target the container that needs to be laid out
     * @return the minimum dimensions to lay out the subcomponents of the
     * specified container
     * @see #preferredLayoutSize
     * @see java.awt.Container
     * @see java.awt.Container#doLayout
     */
    public final Dimension minimumLayoutSize(final Container target) {

        synchronized (target.getTreeLock()) {

            final Dimension dim = new Dimension(0, 0);
            final short nmembers = (short) target.getComponentCount();

            for (short i = 0; i < nmembers; i++) {
                final Component m = target.getComponent(i);
                if (m.isVisible()) {
                    final Dimension d = m.getMinimumSize();
                    dim.height = Math.max(dim.height, d.height);
                    if (i > 0) {
                        dim.width += hgap;
                    }
                    dim.width += d.width;
                }
            }

            final Insets insets = target.getInsets();
            dim.width += insets.left + insets.right + left + right;
            dim.height += insets.top + insets.bottom + top + bottom;

            return dim;
        }
    }

    /**
     * Centers the elements in the specified row, if there is any slack.
     *
     * @param target the component which needs to be moved
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width dimensions
     * @param height the height dimensions
     * @param rowStart the beginning of the row
     * @param rowEnd the the ending of the row
     */
    private final void moveComponents(
            final Container target,
            short x, final short y,
            final short width, final short height,
            final short rowStart, final short rowEnd,
            final boolean ltr
    ) {
        synchronized (target.getTreeLock()) {
            switch (newAlign) {
                case LEFT:
                    x += ltr ? 0 : width;
                    break;
                case CENTER:
                    x += width / 2;
                    break;
                case RIGHT:
                    x += ltr ? width : 0;
                    break;
                case LEADING:
                    break;
                case TRAILING:
                    x += width;
                    break;
            }

            for (short i = rowStart; i < rowEnd; i++) {
                final Component m = target.getComponent(i);
                if (m.isVisible()) {
                    if (ltr) {
                        m.setLocation(x, y + (height - m.getHeight()) / 2);
                    } else {
                        m.setLocation(target.getWidth() - x - m.getWidth(), y
                                + (height - m.getHeight()) / 2);
                    }
                    x += m.getWidth() + hgap;
                }
            }
        }
    }

    /**
     * Returns the preferred dimensions for this layout given the <i>visible</i>
     * components in the specified target container.
     *
     * @param target the container that needs to be laid out
     * @return the preferred dimensions to lay out the subcomponents of the
     * specified container
     * @see Container
     * @see #minimumLayoutSize
     * @see java.awt.Container#getPreferredSize
     */
    public final Dimension preferredLayoutSize(final Container target) {

        synchronized (target.getTreeLock()) {

            final Dimension dim = new Dimension(0, 0);
            final short nmembers = (short) target.getComponentCount();
            boolean firstVisibleComponent = Boolean.TRUE;

            for (short i = 0; i < nmembers; i++) {

                final Component m = target.getComponent(i);
                if (m.isVisible()) {
                    final Dimension d = m.getPreferredSize();
                    dim.height = Math.max(dim.height, d.height);
                    if (firstVisibleComponent) {

                        firstVisibleComponent = Boolean.FALSE;
                    } else {
                        dim.width += hgap;
                    }
                    dim.width += d.width;
                }
            }

            final Insets insets = target.getInsets();
            dim.width += insets.left + insets.right + left + right;
            dim.height += insets.top + insets.bottom + top + bottom;

            return dim;
        }
    }

    /**
     * Reads this object out of a serialization stream, handling objects written
     * by older versions of the class that didn't contain all of the fields we
     * use now..
     */
    private final void readObject(final ObjectInputStream stream) throws IOException,
            ClassNotFoundException {

        stream.defaultReadObject();

        // "newAlign" field wasn't present, so use the old "align" field.
        if (serialVersionOnStream < 1) {
            this.setAlignment(this.align);
        }
        serialVersionOnStream = currentSerialVersion;
    }

    /**
     * Removes the specified component from the layout. Not used by this class.
     *
     * @param comp the component to remove
     * @see java.awt.Container#removeAll
     */
    public final void removeLayoutComponent(final Component comp) {
    }

    /**
     * Sets the alignment for this layout. Possible values are
     * <ul>
     * <li><code>FlowLayout.LEFT</code>
     * <li><code>FlowLayout.RIGHT</code>
     * <li><code>FlowLayout.CENTER</code>
     * <li><code>FlowLayout.LEADING</code>
     * <li><code>FlowLayout.TRAILING</code>
     * </ul>
     *
     * @param align one of the alignment values shown above
     * @see #getAlignment()
     * @since JDK1.1
     */
    public final void setAlignment(final byte align) {

        this.newAlign = align;

		// this.align is used only for serialization compatibility,
        // so set it to a value compatible with the 1.1 version
        // of the class.
        switch (align) {

            case LEADING: {
                this.align = LEFT;
                break;
            }
            case TRAILING: {
                this.align = RIGHT;
                break;
            }
            default: {
                this.align = align;
                break;
            }
        }
    }

    /**
     * Returns a string representation of this <code>FlowLayout</code> object
     * and its values.
     *
     * @return a string representation of this layout
     */
    public final String toString() {

        String str = "";
        switch (align) {
            case LEFT:
                str = ", align=left";
                break;
            case CENTER:
                str = ", align=center";
                break;
            case RIGHT:
                str = ", align=right";
                break;
            case LEADING:
                str = ", align=leading";
                break;
            case TRAILING:
                str = ", align=trailing";
                break;
        }

        return getClass().getName() + "[hgap=" + hgap + ", vgap=" + vgap + str
                + ", left=" + left + ", right=" + right + ", top=" + top
                + ", bottom=" + bottom + "]";
    }
}

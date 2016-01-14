/*
 * Created on 07.10.2006
 */
package pro.java.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;

/**
 * A <code>PRoBorderLayout</code> lays out a container, arranging and resizing
 * its components to fit in five regions: north, south, east, west, and center.
 * Each region may contain no more than one component, and is identified by a
 * corresponding constant:
 * <ul>
 * <li>{@link #NORTH NORTH}
 * <li>{@link #SOUTH SOUTH}
 * <li>{@link #EAST EAST}
 * <li>{@link #WEST WEST}
 * <li>{@link #CENTER CENTER}
 * </ul>
 * To inform the original functions see the <code>JavaDoc</code> from
 * <code>java.awt.BorderLayout</code>.
 * <p>
 * <b>Changes:</b><br>
 * When you will set a emty border around all components, use the corresponding
 * constructor therefor. The next change is that I removed all
 * <code>Getter-</code> and <code>Setter-Methods</code>. So if you want to
 * change a value, you must implement the classes new. Internal I convert all
 * <code>primitive Datatyps</code> to reduce the memory demand.
 * <p>
 *
 * @author PRo (Peter Rogge) | Copyright (c) | 08.10.2006
 * @version 1.0
 */
public final class PRoBorderLayout extends Object implements LayoutManager2,
        java.io.Serializable {

    private static final long serialVersionUID = 1468648589135294126L;

    private static final byte ZERO = 0;

    /**
     * The center layout constraint (middle of container).
     */
    public static final String CENTER = "Center";

    /**
     * The east layout constraint (right side of container).
     */
    public static final String EAST = "East";

    /**
     * The north layout constraint (top of container).
     */
    public static final String NORTH = "North";

    /**
     * The south layout constraint (bottom of container).
     */
    public static final String SOUTH = "South";

    /**
     * The west layout constraint (left side of container).
     */
    public static final String WEST = "West";

    /**
     * Constructs a <code>PRoBorderLayout</code> with the horizontal gaps
     * between components. The horizontal gap is specified by <code>hgap</code>.
     */
    private short hgap = ZERO;

    /**
     * Constructs a <code>PRoBorderLayout</code> with the vertical gaps between
     * components. The vertical gap is specified by <code>vgap</code>.
     */
    private short vgap = ZERO;

    /**
     * Constructs a <code>PRoBorderLayout</code> with a border at the bottom
     * from all components. The bottom border is specified by
     * <code>bottom</code>.
     */
    private short bottom = ZERO;

    /**
     * Constructs a <code>PRoBorderLayout</code> with a border at the left from
     * all components. The left border is specified by <code>left</code>.
     */
    private short left = ZERO;

    /**
     * Constructs a <code>PRoBorderLayout</code> with a border at the right from
     * all components. The right border is specified by <code>right</code>.
     */
    private short right = ZERO;

    /**
     * Constructs a <code>PRoBorderLayout</code> with a border at the top from
     * all components. The top border is specified by <code>top</code>.
     */
    private short top = ZERO;

    /**
     * Constant to specify components location to be the north portion of the
     * <code>PRoBorderLayout</code>.
     *
     * @serial
     * @see #getChild(String, boolean)
     * @see #addLayoutComponent
     * @see #getLayoutAlignmentX
     * @see #getLayoutAlignmentY
     * @see #removeLayoutComponent
     */
    private Component north = null;

    /**
     * Constant to specify components location to be the west portion of the
     * <code>PRoBorderLayout</code>.
     *
     * @serial
     * @see #getChild(String, boolean)
     * @see #addLayoutComponent
     * @see #getLayoutAlignmentX
     * @see #getLayoutAlignmentY
     * @see #removeLayoutComponent
     */
    private Component west = null;

    /**
     * Constant to specify components location to be the east portion of the
     * <code>PRoBorderLayout</code>.
     *
     * @serial
     * @see #getChild(String, boolean)
     * @see #addLayoutComponent
     * @see #getLayoutAlignmentX
     * @see #getLayoutAlignmentY
     * @see #removeLayoutComponent
     */
    private Component east = null;

    /**
     * Constant to specify components location to be the south portion of the
     * <code>PRoBorderLayout</code>.
     *
     * @serial
     * @see #getChild(String, boolean)
     * @see #addLayoutComponent
     * @see #getLayoutAlignmentX
     * @see #getLayoutAlignmentY
     * @see #removeLayoutComponent
     */
    private Component south = null;

    /**
     * Constant to specify components location to be the center portion of the
     * <code>PRoBorderLayout</code>.
     *
     * @serial
     * @see #getChild(String, boolean)
     * @see #addLayoutComponent
     * @see #getLayoutAlignmentX
     * @see #getLayoutAlignmentY
     * @see #removeLayoutComponent
     */
    private Component center = null;

    /**
     * Constructs a new <code>PRoBorderLayout</code> with no gaps between and no
     * border around the components.
     */
    public PRoBorderLayout() {
        this(ZERO, ZERO);
    }

    /**
     * Constructs a new <code>PRoBorderLayout</code> with the specified gaps
     * between and no border around the components.<p>
     * The horizontal gap is specified by <code>hgap</code> and the vertical gap
     * is specified by <code>vgap</code>.
     *
     * @param hgap the horizontal gap.
     * @param vgap the vertical gap.
     */
    public PRoBorderLayout(final int hgap, final int vgap) {

        this(hgap, vgap, ZERO, ZERO, ZERO, ZERO);
    }

    /**
     * Constructs a new <code>PRoBorderLayout</code> with no gaps between and a
     * border around the components.<p>
     * The border around the components is specified by <code>top</code>,
     * <code>left</code>, <code>bottom</code> and <code>right</code>.
     *
     * @param top the top border.
     * @param left the left border.
     * @param bottom the bottom border.
     * @param right the right border.
     */
    public PRoBorderLayout(
            final int top, final int left,
            final int bottom, final int right
    ) {
        this(ZERO, ZERO, top, left, bottom, right);
    }

    /**
     * Constructs a new <code>PRoBorderLayout</code> with the specified gaps
     * between and a border around the components.<p>
     * The horizontal gap is specified by <code>hgap</code> and the vertical gap
     * is specified by <code>vgap</code>.<br>
     * The border around the components is specified by <code>top</code>,
     * <code>left</code>, <code>bottom</code> and <code>right</code>.
     *
     * @param hgap the horizontal gap.
     * @param vgap the vertical gap.
     * @param top the top border.
     * @param left the left border.
     * @param bottom the bottom border.
     * @param right the right border.
     */
    public PRoBorderLayout(
            final int hgap, final int vgap,
            final int top, final int left,
            final int bottom, final int right
    ) {
        super();

        this.hgap = (short) hgap;
        this.vgap = (short) vgap;

        this.top = (short) top;
        this.left = (short) left;
        this.bottom = (short) bottom;
        this.right = (short) right;
    }

    /**
     * Adds the specified component to the layout, using the specified
     * constraint object. For <code>PRoBorderLayout</code>, the constraint must
     * be one of the following constants: NORTH, SOUTH, EAST, WEST or CENTER.<p>
     * Most applications do not call this method directly. This method is called
     * when a component is added to a container using the
     * <code>Container.add</code> method with the same argument types.
     *
     * @param comp the component to be added.
     * @param constraints an object that specifies how and where the component
     * is added to the layout.
     * @see java.awt.Container#add(java.awt.Component, java.lang.Object)
     * @exception IllegalArgumentException if the constraint object is not a
     * string, or if it not one of the five specified constants.
     * @since JDK1.1
     */
    public final void addLayoutComponent(
            final Component comp, final Object constraints
    ) {
        synchronized (comp.getTreeLock()) {
            if ((constraints == null) || (constraints instanceof String)) {

                this.addLayoutComponent((String) constraints, comp);
            } else {
                throw new IllegalArgumentException(
                        "can't add to layout: constraint must be a string (or null)"
                );
            }
        }
    }

    /**
     * @deprecated replaced by
     * <code>addLayoutComponent(Component, Object)</code>.
     */
    @Deprecated
    public final void addLayoutComponent(String name, final Component comp) {

        synchronized (comp.getTreeLock()) {

            /* Special case: treat null the same as "Center". */
            String contraint = name;
            if (contraint == null) {
                contraint = CENTER;
            }

            /*
             * Assign the component to one of the known regions of the layout.
             */
            if (contraint.equals(CENTER)) {
                center = comp;
            } else if (contraint.equals(NORTH)) {
                north = comp;
            } else if (contraint.equals(SOUTH)) {
                south = comp;
            } else if (contraint.equals(EAST)) {
                east = comp;
            } else if (contraint.equals(WEST)) {
                west = comp;
            } else {
                throw new IllegalArgumentException(
                        "cannot add to layout: unknown constraint: " + name
                );
            }
        }
    }

    /**
     * Get the component that corresponds to the given constraint location
     *
     * @param key The desired absolute position, either NORTH, SOUTH, EAST, or
     * WEST.
     * @param ltr Is the component line direction left-to-right?
     */
    private final Component getChild(final String key, final boolean ltr) {

        Component comp = null;

        if (key.equals(NORTH)) {
            comp = north;
        } else if (key.equals(SOUTH)) {
            comp = south;
        } else if (key.equals(WEST)) {
            comp = west;
        } else if (key.equals(EAST)) {
            comp = east;
        } else if (key.equals(CENTER)) {
            comp = center;
        }

        if (comp != null && !comp.isVisible()) {
            comp = null;
        }

        return comp;
    }

    /**
     * Returns the alignment along the x axis. This specifies how the component
     * would like to be aligned relative to other components. The value should
     * be a number between 0 and 1 where 0 represents alignment along the
     * origin, 1 is aligned the furthest away from the origin, 0.5 is centered,
     * etc.
     */
    public final float getLayoutAlignmentX(final Container parent) {

        return Component.CENTER_ALIGNMENT;
    }

    /**
     * Returns the alignment along the y axis. This specifies how the component
     * would like to be aligned relative to other components. The value should
     * be a number between 0 and 1 where 0 represents alignment along the
     * origin, 1 is aligned the furthest away from the origin, 0.5 is centered,
     * etc.
     */
    public final float getLayoutAlignmentY(final Container parent) {

        return Component.CENTER_ALIGNMENT;
    }

    /**
     * Invalidates the layout, indicating that if the layout manager has cached
     * information it should be discarded.
     */
    public final void invalidateLayout(final Container target) {
    }

    /**
     * Lays out the container argument using this border layout.
     * <p>
     * This method actually reshapes the components in the specified container
     * in order to satisfy the constraints of this <code>BorderLayout</code>
     * object. The NORTH and SOUTH components, if any, are placed at the top and
     * bottom of the container, respectively. The WEST and EAST components are
     * then placed on the left and right, respectively. Finally, the CENTER
     * object is placed in any remaining space in the middle.
     * <p>
     * Most applications do not call this method directly. This method is called
     * when a container calls its <code>doLayout</code> method.
     *
     * @param target the container in which to do the layout.
     * @see java.awt.Container
     * @see java.awt.Container#doLayout()
     */
    public final void layoutContainer(final Container target) {

        synchronized (target.getTreeLock()) {

            final Insets insets = target.getInsets();
            short _top = Short.parseShort(String.valueOf(insets.top + top));
            short _bottom = Short.parseShort(String.valueOf(
                    target.getHeight() - insets.bottom - bottom
            ));
            short _left = Short.parseShort(String.valueOf(insets.left + left));
            short _right = Short.parseShort(String.valueOf(
                    target.getWidth() - insets.right - right
            ));

            final boolean ltr = target.getComponentOrientation().isLeftToRight();
            Component c = null;

            if ((c = this.getChild(NORTH, ltr)) != null) {

                c.setSize(_right - _left, c.getHeight());

                final Dimension d = c.getPreferredSize();
                c.setBounds(_left, _top, _right - _left, d.height);
                _top += d.height + vgap;
            }
            if ((c = this.getChild(SOUTH, ltr)) != null) {

                c.setSize(_right - _left, c.getHeight());

                final Dimension d = c.getPreferredSize();
                c.setBounds(_left, _bottom - d.height, _right - _left, d.height);
                _bottom -= d.height + vgap;
            }
            if ((c = this.getChild(EAST, ltr)) != null) {

                c.setSize(c.getWidth(), _bottom - _top);

                final Dimension d = c.getPreferredSize();
                c.setBounds(_right - d.width, _top, d.width, _bottom - _top);
                _right -= d.width + hgap;
            }
            if ((c = this.getChild(WEST, ltr)) != null) {

                c.setSize(c.getWidth(), _bottom - _top);

                final Dimension d = c.getPreferredSize();
                c.setBounds(_left, _top, d.width, _bottom - _top);
                _left += d.width + hgap;
            }
            if ((c = this.getChild(CENTER, ltr)) != null) {

                c.setBounds(_left, _top, _right - _left, _bottom - _top);
            }
        }
    }

    /**
     * Returns the greater of two <code>int</code> values. That is, the result
     * is the argument closer to the value of <code>Integer.MAX_VALUE</code>. If
     * the arguments have the same value, the result is that same value.
     *
     * @param a an argument.
     * @param b another argument.
     * @return the larger of <code>a</code> and <code>b</code>.
     * @see java.lang.Long#MAX_VALUE
     */
    private final int max(final int a, final int b) {
        return (a >= b) ? a : b;
    }

    /**
     * Returns the maximum dimensions for this layout given the components in
     * the specified target container.
     *
     * @param target the component which needs to be laid out
     * @see Container
     * @see #minimumLayoutSize
     * @see #preferredLayoutSize
     */
    public final Dimension maximumLayoutSize(final Container target) {

        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Determines the minimum size of the <code>target</code> container using
     * this layout manager.
     * <p>
     * This method is called when a container calls its
     * <code>getMinimumSize</code> method. Most applications do not call this
     * method directly.
     *
     * @param target the container in which to do the layout.
     * @return the minimum dimensions needed to lay out the subcomponents of the
     * specified container.
     * @see java.awt.Container
     * @see #preferredLayoutSize
     * @see java.awt.Container#getMinimumSize()
     */
    public final Dimension minimumLayoutSize(final Container target) {

        synchronized (target.getTreeLock()) {

            final Dimension dim = new Dimension(0, 0);

            final boolean ltr = target.getComponentOrientation().isLeftToRight();
            Component c = null;

            if ((c = this.getChild(EAST, ltr)) != null) {

                final Dimension d = c.getMinimumSize();
                dim.width += d.width + hgap;
                dim.height = this.max(d.height, dim.height);
            }

            if ((c = this.getChild(WEST, ltr)) != null) {

                final Dimension d = c.getMinimumSize();
                dim.width += d.width + hgap;
                dim.height = this.max(d.height, dim.height);
            }

            if ((c = this.getChild(CENTER, ltr)) != null) {

                final Dimension d = c.getMinimumSize();
                dim.width += d.width;
                dim.height = this.max(d.height, dim.height);
            }

            if ((c = this.getChild(NORTH, ltr)) != null) {

                final Dimension d = c.getMinimumSize();
                dim.width = this.max(d.width, dim.width);
                dim.height += d.height + vgap;
            }

            if ((c = this.getChild(SOUTH, ltr)) != null) {

                final Dimension d = c.getMinimumSize();
                dim.width = this.max(d.width, dim.width);
                dim.height += d.height + vgap;
            }

            final Insets insets = target.getInsets();
            dim.width += left + right + insets.left + insets.right;
            dim.height += top + bottom + insets.top + insets.bottom;

            return dim;
        }
    }

    /**
     * Determines the preferred size of the <code>target</code> container using
     * this layout manager, based on the components in the container.
     * <p>
     * Most applications do not call this method directly. This method is called
     * when a container calls its <code>getPreferredSize</code> method.
     *
     * @param target the container in which to do the layout.
     * @return the preferred dimensions to lay out the subcomponents of the
     * specified container.
     * @see java.awt.Container
     * @see #minimumLayoutSize
     * @see java.awt.Container#getPreferredSize()
     */
    public final Dimension preferredLayoutSize(final Container target) {

        synchronized (target.getTreeLock()) {

            final Dimension dim = new Dimension(0, 0);

            final boolean ltr = target.getComponentOrientation().isLeftToRight();
            Component c = null;

            if ((c = this.getChild(EAST, ltr)) != null) {

                final Dimension d = c.getPreferredSize();
                dim.width += d.width + hgap;
                dim.height = this.max(d.height, dim.height);
            }

            if ((c = this.getChild(WEST, ltr)) != null) {

                final Dimension d = c.getPreferredSize();
                dim.width += d.width + hgap;
                dim.height = this.max(d.height, dim.height);
            }

            if ((c = this.getChild(CENTER, ltr)) != null) {

                final Dimension d = c.getPreferredSize();
                dim.width += d.width;
                dim.height = this.max(d.height, dim.height);
            }

            if ((c = this.getChild(NORTH, ltr)) != null) {

                final Dimension d = c.getPreferredSize();
                dim.width = this.max(d.width, dim.width);
                dim.height += d.height + vgap;
            }

            if ((c = this.getChild(SOUTH, ltr)) != null) {

                final Dimension d = c.getPreferredSize();
                dim.width = this.max(d.width, dim.width);
                dim.height += d.height + vgap;
            }

            final Insets insets = target.getInsets();
            dim.width += left + right + insets.left + insets.right;
            dim.height += top + bottom + insets.top + insets.bottom;

            return dim;
        }
    }

    /**
     * Removes the specified component from this <code>PRoBorderLayout</code>.
     * This method is called when a container calls its <code>remove</code> or
     * <code>removeAll</code> methods. Most applications do not call this method
     * directly.
     *
     * @param comp the component to be removed.
     * @see java.awt.Container#remove(java.awt.Component)
     * @see java.awt.Container#removeAll()
     */
    public final void removeLayoutComponent(final Component comp) {

        synchronized (comp.getTreeLock()) {

            if (comp.equals(CENTER)) {
                center = null;
            } else if (comp.equals(NORTH)) {
                north = null;
            } else if (comp.equals(SOUTH)) {
                south = null;
            } else if (comp.equals(EAST)) {
                east = null;
            } else if (comp.equals(WEST)) {
                west = null;
            }
        }
    }

    /**
     * Returns the string representation of this grid layout's values.
     *
     * @return a string representation of this layout.
     */
    @Override
    public final String toString() {

        return this.getClass().getName() + "[hgap=" + hgap + ", vgap=" + vgap
                + ", left=" + left + ", right=" + right + ", top=" + top
                + ", bottom=" + bottom + "]";
    }
}

package pro.java.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * The <code>PRoGridLayout</code> class is a layout manager that lays out a
 * container's components in a rectangular grid. The container is divided into
 * equal-sized rectangles, and one component is placed in each rectangle.
 * <p>
 * To inform the original functions see the <code>JavaDoc</code> from
 * <code>java.awt.GridLayout</code>.
 * <p>
 * <b>Changes:</b><br>
 * When you will set a emty border around all components, use the corresponding
 * constructor therefor. The next change is that I removed all
 * <code>Getter-</code> and <code>Setter-Methods</code>. So if you want to
 * change a value, you must implement the classes new. Internal I convert all
 * <code>primitive Datatyps</code> to reduce the memory demand.
 * <p>
 * @author PRo (Peter Rogge) | TraumAG | Copyright (c) 13.03.2006
 *
 * @change 08.10.2006
 * @version 1.0
 */
public final class PRoGridLayout implements LayoutManager, java.io.Serializable {

    /**
     * Serialnummer der Klasse <code>pro.frame.memory.util.CridLayoutPRo</code>.
     */
    private static final long serialVersionUID = -3223443564798336753L;

    /**
     * This is the horizontal gap (in pixels) which specifies the space between
     * columns. They can be changed at any time. This should be a non-negative
     * short.
     */
    private short hgap = 0;

    /**
     * This is the vertical gap (in pixels) which specifies the space between
     * rows. They can be changed at any time. This should be a non-negative
     * short.
     */
    private short vgap = 0;

    /**
     * This is the number of rows specified for the grid. The number of rows can
     * be changed at any time. This should be a non negative short, where '0'
     * means 'any number' meaning that the number of Rows in that dimension
     * depends on the other dimension.
     */
    private short rows = 1;

    /**
     * This is the number of columns specified for the grid. The number of
     * columns can be changed at any time. This should be a non negative short,
     * where '0' means 'any number' meaning that the number of Columns in that
     * dimension depends on the other dimension.
     */
    private short cols = 0;

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
     * Creates a grid layout with a default of one column per component, in a
     * single row. There is no empty border around all components.
     */
    public PRoGridLayout() {
        this(1, 0, 0, 0);
    }

    /**
     * Creates a grid layout with the specified number of rows and columns. All
     * components in the layout are given equal size.
     * <p>
     * There is no empty border around all components.
     * <p>
     * One, but not both, of <code>rows</code> and <code>cols</code> can be
     * zero, which means that any number of objects can be placed in a row or in
     * a column.
     *
     * @param rows the rows, with the value zero meaning any number of rows.
     * @param cols the columns, with the value zero meaning any number of
     * columns.
     */
    public PRoGridLayout(final int rows, final int cols) {
        this(rows, cols, 0, 0);
    }

    /**
     * Creates a grid layout with the specified number of rows and columns. All
     * components in the layout are given equal size.
     * <p>
     * In addition, the horizontal and vertical gaps are set to the specified
     * values. Horizontal gaps are placed between each of the columns. Vertical
     * gaps are placed between each of the rows.
     * <p>
     * There is no empty border around all components.
     * <p>
     * One, but not both, of <code>rows</code> and <code>cols</code> can be
     * zero, which means that any number of objects can be placed in a row or in
     * a column.
     * <p>
     * All <code>PRoGridLayout</code> constructors defer to this one.
     *
     * @param rows the rows, with the value zero meaning any number of rows.
     * @param cols the columns, with the value zero meaning any number of
     * columns.
     * @param hgap the horizontal gap.
     * @param vgap the vertical gap.
     * @exception IllegalArgumentException if the value of both
     * <code>rows</code> and <code>cols</code> is set to zero.
     */
    public PRoGridLayout(
            final int rows, final int cols,
            final int hgap, final int vgap
    ) {
        this(rows, cols, hgap, vgap, 0, 0, 0, 0);
    }

    /**
     * Creates a grid layout with the specified number of rows and columns. All
     * components in the layout are given equal size.
     * <p>
     * Around all components is a empty border with <code>top</code>,
     * <code>left</code>, <code>bottom</code> and <code>right</code>. None of
     * this border values can be < zero. <p>
     * One, but not both, of <code>rows</code> and <code>cols</code> can be
     * zero, which means that any number of objects can be placed in a row or in
     * a column.
     *
     * @param rows the rows, with the value zero meaning any number of rows.
     * @param cols the columns, with the value zero meaning any number of
     * columns.
     * @param top the empty top border.
     * @param left the empty left border.
     * @param bottom the empty bottom border.
     * @param right the empty right border.
     */
    public PRoGridLayout(
            final int rows, final int cols,
            final int top, final int left,
            final int bottom, final int right
    ) {
        this(rows, cols, 0, 0, top, left, bottom, right);
    }

    /**
     * Creates a grid layout with the specified number of rows and columns. All
     * components in the layout are given equal size.
     * <p>
     * In addition, the horizontal and vertical gaps are set to the specified
     * values. Horizontal gaps are placed between each of the columns. Vertical
     * gaps are placed between each of the rows.
     * <p>
     * Around all components is a empty border with <code>top</code>,
     * <code>left</code>, <code>bottom</code> and <code>right</code>. None of
     * this border values can be < zero. <p>
     * One, but not both, of <code>rows</code> and <code>cols</code> can be
     * zero, which means that any number of objects can be placed in a row or in
     * a column.
     * <p>
     * All <code>PRoGridLayout</code> constructors defer to this one.
     *
     * @param rows the rows, with the value zero meaning any number of rows.
     * @param cols the columns, with the value zero meaning any number of
     * columns.
     * @param hgap the horizontal gap.
     * @param vgap the vertical gap.
     * @param top the empty top border.
     * @param left the empty left border.
     * @param bottom the empty bottom border.
     * @param right the empty right border.
     * @exception IllegalArgumentException if the value of both
     * <code>rows</code> and <code>cols</code> is set to zero or one of
     * <code>top</code>, <code>left</code>, <code>bottom</code> or
     * <code>right</code> < zero.
     */
    public PRoGridLayout(
            final int rows, final int cols,
            final int hgap, final int vgap,
            final int top, final int left,
            final int bottom, final int right
    ) {
        if ((rows == 0) && (cols == 0)) {
            throw new IllegalArgumentException(
                    "rows and cols can't both be zero."
            );
        }
        if ((top < 0) || (left < 0) || (bottom < 0) || (right < 0)) {
            throw new IllegalArgumentException(
                    "No one of top, left, bottom or right can't < zero."
            );
        }
        this.rows = (short) rows;
        this.cols = (short) cols;

        this.hgap = (short) hgap;
        this.vgap = (short) vgap;

        this.top = (short) top;
        this.left = (short) left;
        this.bottom = (short) bottom;
        this.right = (short) right;
    }

    /**
     * Adds the specified component with the specified name to the layout.
     *
     * @param name the name of the component
     * @param comp the component to be added
     */
    public void addLayoutComponent(String name, Component comp) {
    }

    /**
     * Lays out the specified container using this layout.
     * <p>
     * This method reshapes the components in the specified target container in
     * order to satisfy the constraints of the <code>PRoGridLayout</code>
     * object.
     * <p>
     * The grid layout manager determines the size of individual components by
     * dividing the free space in the container into equal-sized portions
     * according to the number of rows and columns in the layout. The
     * container's free space equals the container's size minus any insets and
     * any specified horizontal or vertical gap. All components in a grid layout
     * are given the same size.
     *
     * @param parent the container in which to do the layout
     * @see java.awt.Container
     * @see java.awt.Container#doLayout
     */
    public final void layoutContainer(final Container parent) {

        synchronized (parent.getTreeLock()) {

            final boolean ltr = parent.getComponentOrientation().isLeftToRight();
            final int ncomponents = parent.getComponentCount();
            final Insets insets = parent.getInsets();

            int nrows = rows;
            int ncols = cols;
            if (ncomponents == 0) {
                return;
            }
            if (nrows > 0) {
                ncols = (ncomponents + nrows - 1) / nrows;
            } else {
                nrows = (ncomponents + ncols - 1) / ncols;
            }

            int w = parent.getWidth() - (insets.left + insets.right + left + right);
            int h = parent.getHeight() - (insets.top + insets.bottom + top + bottom);
            w = (w - (ncols - 1) * hgap) / ncols;
            h = (h - (nrows - 1) * vgap) / nrows;

            if (ltr) {
                for (int c = 0, x = insets.left + left;
                        c < ncols;
                        c++, x += w + hgap) {
                    for (int r = 0, y = insets.top + top;
                            r < nrows;
                            r++, y += h + vgap) {
                        final int i = r * ncols + c;
                        if (i < ncomponents) {
                            parent.getComponent(i).setBounds(x, y, w, h);
                        }
                    }
                }
            } else {
                for (int c = 0, x = parent.getWidth() - insets.right - w - right;
                        c < ncols;
                        c++, x -= w + hgap) {
                    for (int r = 0, y = insets.top + top;
                            r < nrows;
                            r++, y += h + vgap) {
                        final int i = r * ncols + c;
                        if (i < ncomponents) {
                            parent.getComponent(i).setBounds(x, y, w, h);
                        }
                    }
                }
            }
        }
    }

    /**
     * Determines the minimum size of the container argument using this grid
     * layout.
     * <p>
     * The minimum width of a grid layout is the largest minimum width of all of
     * the components in the container times the number of columns, plus the
     * horizontal padding times the number of columns minus one, plus the left
     * and right insets of the target container.
     * <p>
     * The minimum height of a grid layout is the largest minimum height of all
     * of the components in the container times the number of rows, plus the
     * vertical padding times the number of rows minus one, plus the top and
     * bottom insets of the target container.
     *
     * @param parent the container in which to do the layout
     * @return the minimum dimensions needed to lay out the subcomponents of the
     * specified container
     * @see pro.frame.memory.util.PRoGridLayout#preferredLayoutSize
     * @see java.awt.Container#doLayout
     */
    public final Dimension minimumLayoutSize(final Container parent) {

        synchronized (parent.getTreeLock()) {

            final Insets insets = parent.getInsets();
            final int ncomponents = parent.getComponentCount();

            int nrows = rows;
            int ncols = cols;
            if (nrows > 0) {
                ncols = (ncomponents + nrows - 1) / nrows;
            } else {
                nrows = (ncomponents + ncols - 1) / ncols;
            }

            int w = 0;
            int h = 0;
            for (int i = 0; i < ncomponents; i++) {

                final Component comp = parent.getComponent(i);
                final Dimension d = comp.getMinimumSize();
                if (w < d.width) {
                    w = d.width;
                }
                if (h < d.height) {
                    h = d.height;
                }
            }

            return new Dimension(
                    left + right + insets.left + insets.right
                    + ncols * w + (ncols - 1) * hgap,
                    top + bottom + insets.top + insets.bottom
                    + nrows * h + (nrows - 1) * vgap
            );
        }
    }

    /**
     * Determines the preferred size of the container argument using this grid
     * layout.
     * <p>
     * The preferred width of a grid layout is the largest preferred width of
     * all of the components in the container times the number of columns, plus
     * the horizontal padding times the number of columns minus one, plus the
     * left and right insets of the target container.
     * <p>
     * The preferred height of a grid layout is the largest preferred height of
     * all of the components in the container times the number of rows, plus the
     * vertical padding times the number of rows minus one, plus the top and
     * bottom insets of the target container.
     *
     * @param parent the container in which to do the layout
     * @return the preferred dimensions to lay out the subcomponents of the
     * specified container
     * @see pro.frame.memory.util.PRoGridLayout#minimumLayoutSize
     * @see java.awt.Container#getPreferredSize()
     */
    public final Dimension preferredLayoutSize(final Container parent) {

        synchronized (parent.getTreeLock()) {

            final Insets insets = parent.getInsets();
            final int ncomponents = parent.getComponentCount();
            int nrows = rows;
            int ncols = cols;

            if (nrows > 0) {
                ncols = (ncomponents + nrows - 1) / nrows;
            } else {
                nrows = (ncomponents + ncols - 1) / ncols;
            }

            int w = 0;
            int h = 0;
            for (int i = 0; i < ncomponents; i++) {

                final Component comp = parent.getComponent(i);
                final Dimension d = comp.getPreferredSize();
                if (w < d.width) {
                    w = d.width;
                }
                if (h < d.height) {
                    h = d.height;
                }
            }

            return new Dimension(
                    left + right + insets.left + insets.right
                    + ncols * w + (ncols - 1) * hgap,
                    top + bottom + insets.top + insets.bottom
                    + nrows * h + (nrows - 1) * vgap
            );
        }
    }

    /**
     * Removes the specified component from the layout.
     *
     * @param comp the component to be removed
     */
    public void removeLayoutComponent(Component comp) {
    }

    /**
     * Returns the string representation of this grid layout's values.
     *
     * @return a string representation of this grid layout
     */
    public final String toString() {

        return getClass().getName()
                + "[hgap=" + hgap
                + ",vgap=" + vgap
                + ",rows=" + rows
                + ",cols=" + cols
                + ",left=" + left
                + ",right=" + right
                + ",top=" + top
                + ",bottom=" + bottom
                + "]";
    }
}

/*
 * Created on 12.10.2006
 */
package pro.java.util;

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

/**
 * Resizable-array implementation of the <code>List</code> interface. Implements
 * all optional list operations, and permits all elements, including
 * <code>null</code>. In addition to implementing the <code>List</code>
 * interface, this class provides methods to manipulate the size of the array
 * that is used internally to store the list. (This class is roughly equivalent
 * to <code>Vector</code>, except that it is unsynchronized.)
 * <p>
 *
 * To inform the original functions see the <code>JavaDoc</code> from
 * <code>java.util.ArrayList</code>.<p>
 *
 * <b>Changes:</b><br>
 * I removed several functions with a parameter of <code>Collections</code>.
 * Feel free to implement needed functions.<p>
 *
 * @author PRo (Peter Rogge) | TraumAG | Copyright (c) 12.10.2006
 * @version 1.1
 * @change 19.02.2007
 * @change 30.04.2007
 */
public class PRoArrayList<E> extends AbstractList<E> implements List<E>,
        RandomAccess {

    /**
     * The array buffer into which the elements of the ArrayList are stored. The
     * capacity of the ArrayList is the length of this array buffer.
     */
    private E[] elementData = null;

    /**
     * The size of the ArrayList (the number of elements it contains).
     */
    private int size = 0;

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public PRoArrayList() {
        this(10);
    }

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list.
     * @exception IllegalArgumentException if the specified initial capacity is
     * negative.
     */
    @SuppressWarnings("unchecked")
    public PRoArrayList(final int initialCapacity) {

        super();
        if (initialCapacity < 0) {
            throw new IllegalArgumentException(
                    "Illegal Capacity: " + initialCapacity);
        }

        elementData = (E[]) new Object[initialCapacity];
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param o element to be appended to this list.
     * @return <code>true</code> (as per the general contract of
     * Collection.add()).
     */
    @Override
    public final boolean add(final E o) {

        this.ensureCapacity(size + 1);
        elementData[size++] = o;

        return Boolean.TRUE;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted.
     * @param element element to be inserted.
     * @throws IndexOutOfBoundsException if index is out of range
     * <code>(index &lt; 0 || index &gt; size())</code>.
     */
    @Override
    public final void add(final int index, final E element) {

        if (index > size || index < 0) {

            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + size
            );
        }

        this.ensureCapacity(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    /**
     * Removes all of the elements from this list. The list will be empty after
     * this call returns.
     */
    @Override
    public void clear() {

        modCount++;

        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    /**
     * Returns <code>true</code> if this list contains the specified element.
     *
     * @param element which presence in this List is to be tested.
     * @return <code>true</code> if the specified element is present -
     * <code>false</code> otherwise.
     */
    @Override
    public final boolean contains(final Object element) {

        return this.indexOf(element) >= 0;
    }

    /**
     * Increases the capacity of this <code>PRoArrayList</code> instance, if
     * necessary, to ensure that it can hold at least the number of elements
     * specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity.
     */
    @SuppressWarnings("unchecked")
    public final void ensureCapacity(final int minCapacity) {

        modCount++;
        final int oldCapacity = elementData.length;
        if (minCapacity > oldCapacity) {

            final Object[] oldData = elementData;
            int newCapacity = oldCapacity * 3 / 2 + 1;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }

            elementData = (E[]) new Object[newCapacity];
            System.arraycopy(oldData, 0, elementData, 0, size);
        }
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of element to return.
     * @return the element at the specified position in this list.
     * @throws IndexOutOfBoundsException if index is out of range <code>(index
     * 		  &lt; 0 || index &gt;= size())</code>.
     */
    @Override
    public final E get(final int index) {

        this.rangeCheck(index);

        return elementData[index];
    }

    /**
     * Liefert die Kapazität des <code>PRoArrayList</code>. Dies entspricht
     * nicht der Anzahl der enthaltenen Elemente, sondern wieviele Elemente in
     * das <code>PRoArrayList</code> gespeichert werden kann.
     *
     * @return Kapazit�t des <code>PRoArrayList</code>.
     */
    public int getLength() {
        return elementData.length;
    }

    /**
     * Liefert die Anzahl der enthaltenen Elemente im <code>ProArrayList</code>.
     *
     * @return Anzahl der enthaltenen Elemente.
     */
    public int getSize() {
        return size;
    }

    /**
     * Check if the given index is in range. If not, throw an appropriate
     * runtime exception. This method does *not* check if the index is negative:
     * It is always used immediately prior to an array access, which throws an
     * ArrayIndexOutOfBoundsException if index is negative.
     */
    private final void rangeCheck(final int index) {

        if (index >= size) {

            throw new IndexOutOfBoundsException(
                    "Index: " + index + " >= Size: " + size
            );
        }
    }

    /**
     * Removes the element at the specified position in this list. Shifts any
     * subsequent elements to the left (subtracts one from their indices).
     *
     * @param index the index of the element to removed.
     * @return the element that was removed from the list.
     * @throws IndexOutOfBoundsException if index out of range <code>(index
     * 		  &lt; 0 || index &gt;= size())</code>.
     */
    @Override
    public final E remove(final int index) {

        this.rangeCheck(index);

        modCount++;
        final E oldValue = elementData[index];
        final int numMoved = size - index - 1;
        if (numMoved > 0) {

            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;

        return oldValue;
    }

    /**
     * Removes the first (lowest-indexed) occurrence of the argument from this
     * list. If the object is found in this list, each component in the list
     * with an index greater or equal to the object's index is shifted downward
     * to have an index one smaller than the value it had previously.
     * <p>
     *
     * @param obj the component to be removed.
     * @return <code>true</code> if the argument was a component of this list;
     * <code>false</code> otherwise.
     */
    public final boolean remove(final Object obj) {

        modCount++;

        final int i = super.indexOf(obj);
        if (i >= 0) {
            this.remove(i);
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     * @return the element previously at the specified position.
     * @throws IndexOutOfBoundsException if index out of range
     * <code>(index &lt; 0 || index &gt;= size())</code>.
     */
    @Override
    public final E set(final int index, final E element) {

        this.rangeCheck(index);

        final E oldValue = elementData[index];
        elementData[index] = element;

        return oldValue;
    }

    /**
     * Returns the current size of <code>PRoArrayList</code>. Deprecated. Use
     * instead <code>getSize()</code>.
     *
     * @deprecated
     */
    @Deprecated
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns an array containing all of the elements in this list in the
     * correct order; the runtime type of the returned array is that of the
     * specified array. If the list fits in the specified array, it is returned
     * therein. Otherwise, a new array is allocated with the runtime type of the
     * specified array and the size of this list.<p>
     *
     * If the list fits in the specified array with room to spare (i.e., the
     * array has more elements than the list), the element in the array
     * immediately following the end of the collection is set to
     * <tt>null</tt>. This is useful in determining the length of the list
     * <i>only</i> if the caller knows that the list does not contain any
     * <tt>null</tt> elements.
     *
     * @param a the array into which the elements of the list are to be stored,
     * if it is big enough; otherwise, a new array of the same runtime type is
     * allocated for this purpose.
     * @return an array containing the elements of the list.
     * @throws ArrayStoreException if the runtime type of a is not a supertype
     * of the runtime type of every element in this list.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {

        T[] array = a;
        if (array.length < size) {
            array = (T[]) java.lang.reflect.Array.newInstance(
                    array.getClass().getComponentType(), size
            );
        }
        System.arraycopy(elementData, 0, array, 0, size);
        if (array.length > size) {
            array[size] = null;
        }

        return array;
    }

    /**
     * Trims the capacity of this <code>PRoArrayList</code> instance to be the
     * list's current size. An application can use this operation to minimize
     * the storage of an <code>PRoArrayList</code> instance.
     */
    @SuppressWarnings("unchecked")
    public void trimToSize() {

        modCount++;
        final int oldCapacity = elementData.length;

        if (size < oldCapacity) {

            final Object[] oldData = elementData;
            elementData = (E[]) new Object[size];
            System.arraycopy(oldData, 0, elementData, 0, size);
        }
    }
}

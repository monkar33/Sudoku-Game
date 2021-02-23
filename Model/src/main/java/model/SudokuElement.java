package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class containing one sudoku element.
 */
public abstract class SudokuElement implements Cloneable, Serializable {

    /**
     * Declared size of the list of values in the element.
     */
    private final int SIZE = 9;
    /**
     * Declared list of values in the element.
     */
    private final ArrayList<SudokuField> list = new ArrayList<SudokuField>();

    /**
     * Create new sudokuElement object.
     */
    public SudokuElement() {
        for (int i = 0; i < SIZE; i++) {
            list.add(new SudokuField());
        }
    }

    /**
     * @return Returns a copy of the list of values in the element.
     */
    public ArrayList<SudokuField> getList() {
        ArrayList<SudokuField> copyList = new ArrayList<SudokuField>();
        for (int i = 0; i < SIZE; i++) {
            copyList.add(list.get(i));
        }
        return copyList;
    }

    /**
     * Setting values in the list.
     *
     * @param list List of value which one we want to put
     *             in the list in this element.
     */
    public void setList(ArrayList<SudokuField> list) {
        for (int i = 0; i < SIZE; i++) {
            this.list.get(i).setValue(list.get(i).getValue());
        }
    }

    /**
     * Checking if the element is correct.
     *
     * @return Returns true if the element is correct. Otherwise return false.
     */
    public final boolean verify() {
        int count = 0;
        for (int i = 1; i <= SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (list.get(j).getValue() == i) {
                    count++;
                    break;
                }
            }
        }
        return count == SIZE;
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     *
     * @return a hash code value for this object.
     * @implSpec As far as is reasonably practical, the {@code hashCode} method defined
     * by class {@code Object} returns distinct integers for distinct objects.
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getList()).toHashCode();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return new EqualsBuilder().
                append(this.getList(), ((SudokuElement) obj).
                        getList()).isEquals();
    }

    /**
     * Creates and returns a copy of this object.  The precise meaning
     * of "copy" may depend on the class of the object. The general
     * intent is that, for any object {@code x}, the expression:
     *
     * @return a clone of this instance.
     * @throws CloneNotSupportedException if the object's class does not
     *                                    support the {@code Cloneable} interface. Subclasses
     *                                    that override the {@code clone} method can also
     *                                    throw this exception to indicate that an instance cannot
     *                                    be cloned.
     * @see Cloneable
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        for (int i = 0; i < SIZE; i++) {
            builder.append(this.getList().get(i).getValue());
        }
        return builder.toString();
    }
}

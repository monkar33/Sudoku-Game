package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class SudokuFieldTest {
    @Test
    public void SudokuFieldTest() {
        SudokuField field = new SudokuField();
        SudokuField field2 = new SudokuField(6);

        assertTrue(field.isFilled());
        assertTrue(field2.isFilled());
        assertEquals(field2.getValue(), 6);

    }

    @Test
    public void equalsTest() {
        SudokuField field = new SudokuField(6);
        SudokuField field2 = new SudokuField(6);

        assertEquals(field.getValue(), field2.getValue());
        assertTrue(field.equals(field2));
    }

    @Test
    public void toStringTest() {
        SudokuField field = new SudokuField(6);
        assertTrue(field.toString().contains("6"));
    }

    @Test
    public void hashCodeTest() {
        SudokuField field = new SudokuField(6);
        SudokuField field2 = new SudokuField(6);
        assertEquals(field.hashCode(), field2.hashCode());
    }

    @Test
    public void compareToTest () {
        SudokuField field = new SudokuField(6);
        SudokuField field2 = new SudokuField(6);
        assertEquals(field.compareTo(field2),0);
        field2.setValue(3);
        assertEquals(field.compareTo(field2),1);
        field2.setValue(9);
        assertEquals(field.compareTo(field2),-1);
    }

    @Test
    public void cloneTest () throws CloneNotSupportedException {
        SudokuField field = new SudokuField(6);
        assertEquals(field.clone(), field);
        System.out.println(getClass().getCanonicalName());
    }

}

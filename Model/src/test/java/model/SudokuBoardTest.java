package model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

class SudokuBoardTest {

    int boxLenght = 3;
    int rowSize = 9;

    @Test
    public void SudokuBoard() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard b = new SudokuBoard(solver);
        assertEquals(b.getBoard().size(),81);
    }

    @Test
    public void getBox(){
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard b = new SudokuBoard(solver);
        b.solveGame();
        ArrayList<SudokuField> newList = new ArrayList<>();
        for(int i = 0; i < boxLenght; i++) {
            for(int j = 0; j < boxLenght; j++) {
                newList.add(b.getBoard().get(i * rowSize + j));
            }
        }
        ArrayList<SudokuField> newList2 = b.getBox(0,0).getList();
        for(int i = 0; i < rowSize; i++) {
            assertEquals(newList.get(i).getValue(),b.getBox(0,0).getList().get(i).getValue());
        }


    }

    @Test
    public void getRow(){
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard b = new SudokuBoard(solver);
        b.solveGame();
        ArrayList<SudokuField> newList = new ArrayList<>();
        for(int i = 0; i < rowSize; i++) {
            newList.add(b.getBoard().get(i));
        }
        for(int i = 0; i < rowSize; i++) {
            assertEquals(newList.get(i).getValue(), b.getRow(0).getList().get(i).getValue());
        }
    }

    @Test
    public void getColumn(){
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard b = new SudokuBoard(solver);
        b.solveGame();
        ArrayList<SudokuField> newList = new ArrayList<>();
        for(int i = 0; i < rowSize; i++) {
            newList.add(b.getBoard().get(i*rowSize));
        }
        for(int i = 0; i < rowSize; i++) {
            assertEquals(newList.get(i).getValue(), b.getColumn(0).getList().get(i).getValue());
        }
    }

    @Test
    public void SudokuField() {
        SudokuField b = new SudokuField(9);
        assertEquals(b.getValue(),9);
    }

    @Test
    public void SudokuRow() {
        SudokuRow b = new SudokuRow();
        assertEquals(b.getList().size(),9);
    }

    @Test
    public void SudokuColumn() {
        SudokuColumn b = new SudokuColumn();
        assertEquals(b.getList().size(),9);
    }

    @Test
    public void SudokuBox() {
        SudokuBox b = new SudokuBox();
        assertEquals(b.getList().size(),9);
    }

    @Test
    void checkNumber() {

        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard b = new SudokuBoard(solver);

        int number = new Random().nextInt(8) + 1;
        int row = new Random().nextInt(9);
        int col = new Random().nextInt(9);

        b.set(row, col, number);

        for(int i=0; i<rowSize; i++){
            assertFalse(b.checkNumber(row,i,number));
            assertFalse(b.checkNumber(i,col,number));
        }

        int boxRowBegin = row - row % boxLenght;
        int boxColBegin = col - col % boxLenght;

        for (int i = boxRowBegin; i < boxRowBegin + boxLenght; i++) {
            for (int j = boxColBegin; j < boxColBegin + boxLenght; j++) {
                assertFalse(b.checkNumber(i,j,number));
            }
        }

        //////////////////////////////////////////////////////////////////////////////

        SudokuBoard c = new SudokuBoard(solver);

        number = new Random().nextInt(8) + 1;
        row = new Random().nextInt(9);
        col = new Random().nextInt(9);

        ArrayList<Integer> possibilities = new ArrayList<>();
        for(int i=1; i<=9; i++){
            if(i!=number) possibilities.add(i);
        }
        Collections.shuffle(possibilities);

        for(int i=0; i<rowSize; i++){
            if(i!=col){
                c.set(row,i,possibilities.get(0));
                possibilities.remove(0);
            }
        }
////////////////////////////////////////////////
        assertTrue(b.checkNumber(row,col,number));

    }
    @Test
    void verify(){
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard b = new SudokuBoard(solver);
        b.solveGame();
        assertTrue(b.getRow(0).verify());
        assertTrue(b.getColumn(0).verify());
        assertTrue(b.getBox(3,3).verify());
        b.set(0,0,b.get(0,1));
        assertFalse(b.getRow(0).verify());
    }

    @Test
    void checkBoard(){
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard b = new SudokuBoard(solver);
        b.solveGame();
        assertTrue(b.checkBoard());

        b.set(0,0,b.get(1,2));
        assertFalse(b.checkBoard());

        SudokuBoard c = new SudokuBoard(solver);
        c.solveGame();
        int value = c.get(0,1);
        c.set(0,0,value);
        assertFalse(c.checkBoard());

        SudokuBoard d = new SudokuBoard(solver);
        d.solveGame();
        int value1 = d.get(0,2);
        int value2 = d.get(0,0);
        d.set(0,0,value1);
        d.set(0,2,value2);
        assertFalse(d.checkBoard());

        SudokuBoard e = new SudokuBoard(solver);
        e.solveGame();
        int value3 = e.get(1,2);
        int value4 = e.get(0,0);
        e.set(1,2,value4);
        e.set(0,0,value3);
        assertFalse(e.checkBoard());

    }

    @Test
    void solveGame() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard b = new SudokuBoard(solver);
        b.solveGame();
        for(int i=0; i<9; i++){
            ArrayList<Integer> list = new ArrayList<>();
            for(int j=0; j<9; j++){
                list.add(b.get(i,j));
            }
            for(int j=1; j<10; j++){
                assertTrue(list.contains(j));
            }

        }

        for(int i=0; i<9; i++){
            ArrayList<Integer> list = new ArrayList<>();
            for(int j=0; j<9; j++){
                list.add(b.get(i,j));
            }
            for(int j=1; j<10; j++){
                assertTrue(list.contains(j));
            }

        }

        for(int x=0; x<9; x+=3){
            for(int y=0; y<9; y+=3){
                int boxRowBegin = x - x % 3;
                int boxColBegin = y - y % 3;
                ArrayList<Integer> list = new ArrayList<>();
                for (int i = boxRowBegin; i < boxRowBegin + 3; i++) {

                    for (int j = boxColBegin; j < boxColBegin + 3; j++) {
                        list.add(b.get(i,j));
                    }
                }
                for(int j=1; j<10; j++){
                    assertTrue(list.contains(j));
                }
            }
        }
    }

    @Test
    public void testEqualsField() {
        SudokuField x = new SudokuField();
        x.setValue(1);
        SudokuField y = new SudokuField();
        y.setValue(1);
        assertTrue(x.equals(y) && y.equals(x));
        assertEquals(x, x);
        assertNotEquals(new SudokuField(), x);
        try{
            assertNotEquals(x, null);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testHashCodeField() {
        SudokuField x = new SudokuField();
        x.setValue(1);
        SudokuField y = new SudokuField();
        y.setValue(1);
        assertEquals(y.hashCode(), x.hashCode());
    }

    @Test
    public void testToStringField() {
        SudokuField x = new SudokuField();
        x.setValue(1);
        String s = "1";
        assertTrue(x.toString().contains(s));
        //assertTrue(s == x.toString());
    }

    @Test
    public void testEqualsElement() {
        SudokuColumn x = new SudokuColumn();
        SudokuColumn y = new SudokuColumn();
        ArrayList<SudokuField> list = new ArrayList<>();
        for(int i = 1; i < 10; i++) {
            SudokuField f = new SudokuField();
            f.setValue(i);
            list.add(f);
        }
        x.setList(list);
        y.setList(list);
        assertTrue(x.equals(y) && y.equals(x));
        assertEquals(x, x);
        try{
            assertNotEquals(x, null);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testHashCodeElement() {
        SudokuColumn x = new SudokuColumn();
        SudokuColumn y = new SudokuColumn();
        ArrayList<SudokuField> list = new ArrayList<>();
        for(int i = 1; i < 10; i++) {
            SudokuField f = new SudokuField();
            f.setValue(i);
            list.add(f);
        }
        x.setList(list);
        y.setList(list);
        assertEquals(y.hashCode(), x.hashCode());
    }

    @Test
    public void testToStringElement() {
        SudokuColumn x = new SudokuColumn();
        ArrayList<SudokuField> list = new ArrayList<>();
        for(int i = 1; i < 10; i++) {
            SudokuField f = new SudokuField();
            f.setValue(i);
            list.add(f);
        }
        x.setList(list);
        assertTrue(x.toString().contains("1,2,3,4,5,6,7,8,9"));
    }

    @Test
    public void testToStringBoard() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard b = new SudokuBoard(solver);
        StringBuilder s = new StringBuilder();
        b.solveGame();
        for(int i = 0; i < b.getBoard().size(); i++) {
            s.append(b.getBoard().get(i).getValue());
            if(i != b.getBoard().size()-1) {
                s.append(",");
            }
        }
        assertTrue(b.toString().contains(s.toString()));
    }

    @Test
    public void testEqualsBoard() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard x = new SudokuBoard(solver);
        SudokuBoard y = new SudokuBoard(solver);
        x.solveGame();
        y.solveGame();
        assertFalse(x.equals(y) && y.equals(x));
        assertEquals(x, x);
        try{
            assertNotEquals(x, null);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }


    @Test
    public void cloneFieldTest() {
        SudokuField f1 = new SudokuField(8);

        try {
            SudokuField f2 =  (SudokuField) f1.clone();
            assertEquals(f1.getValue(), f2.getValue());
        } catch (CloneNotSupportedException e) {
            System.out.println("Clone Not Supported Exception");
        }
    }

    @Test
    public void cloneElementTest() {
        ArrayList<SudokuField> lista = new ArrayList<>();
        for(int i = 1; i <= 9; i++) {
            lista.add(new SudokuField(i));
        }
        SudokuElement r1 = new SudokuRow();
        r1.setList(lista);
        try {
            SudokuElement r2 = (SudokuElement) r1.clone();
            assertEquals(r2.getList(),r1.getList());
            assertNotSame(r1, r2);
        } catch (CloneNotSupportedException e) {
            System.out.println("Clone Not Supported Exception");
        }

    }

    @Test
    public void compareToFieldTest() {
        SudokuField f1 = new SudokuField(8);
        SudokuField f2 = new SudokuField(8);
        assertEquals(0,f1.compareTo(f2));
    }

}
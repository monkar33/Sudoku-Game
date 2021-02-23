package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

import java.util.ArrayList;

/**
 * Class containing and solving one sudoku board.
 */
public class SudokuBoard implements Serializable, Cloneable {

    /**
     * Declared size of the board.
     */
    private final int size = 81;
    /**
     * Declaration of the board.
     */
    private ArrayList<SudokuField> board = new ArrayList<SudokuField>();

    /**
     * Declare size of the collumn and row.
     */
    private final int colLenght = 9;

    /**
     * Declared size of the box.
     */
    private final int boxLenght = 3;

    /**
     * Declaration of the solving class.
     */
    private SudokuSolver solver;
    /**
     *
     */
    private static final Logger LOG_BOARD =
            LoggerFactory.getLogger(SudokuBoard.class.getName());

    /**
     * Create new sudokuBoard object and board with SudokuField objects.
     *
     * @param solve SudokuSolver object
     */
    public SudokuBoard(final SudokuSolver solve) {

        for (int i = 0; i < size; i++) {
            board.add(new SudokuField());
        }
        this.solver = solve;
        LOG_BOARD.info("New SudokuBoard created.");
    }

    /**
     * Getting value from board["x"]["y"].
     *
     * @param x row number
     * @param y column number
     * @return Return the value from the board
     */
    public final int get(final int x, final int y) {

        return this.board.get(x * colLenght + y).getValue();
    }

    /**
     * Seting "value" in board["x"]["y"].
     *
     * @param x     row number
     * @param y     column number
     * @param value value which one we want to put in the board
     */
    public final void set(final int x, final int y, final int value) {

        this.board.get(x * colLenght + y).setValue(value);
    }


    /**
     * Checkig the possibility of seting a "number" in board["row","col"].
     *
     * @param row    number of row
     * @param col    number of column
     * @param number examine number
     * @return Returns true when the "number" can be set in the board
     */
    public final boolean checkNumber(
            final int row, final int col, final int number) {
        for (int i = 0; i < colLenght; i++) {
            if (get(row, i) == number) {
                return false;
            }
            if (get(i, col) == number) {
                return false;
            }
        }
        int boxRowBegin = row - row % boxLenght;
        int boxColBegin = col - col % boxLenght;

        for (int i = boxRowBegin; i < boxRowBegin + boxLenght; i++) {
            for (int j = boxColBegin; j < boxColBegin + boxLenght; j++) {
                if (get(i, j) == number) {
                    return false;
                }
            }
        }

        return true;

    }


    /**
     * @return Returns true when the board is solve, otherwise return false
     */
    public final boolean solveGame() {
        return solver.solve(this);
    }


    /**
     * @return Returns copy of the board.
     */
    public final ArrayList<SudokuField> getBoard() {

        ArrayList<SudokuField> newBoard = new ArrayList<SudokuField>();
        for (int i = 0; i < size; i++) {
            newBoard.add(board.get(i));
        }
        return newBoard;
    }

    /**
     * Getting row y from board.
     *
     * @param y row number
     * @return Return the row from the board
     */

    public final SudokuRow getRow(final int y) {
        SudokuRow row = new SudokuRow();
        ArrayList<SudokuField> rowList = new ArrayList<>();
        for (int i = 0; i < colLenght; i++) {
            rowList.add(board.get(y * colLenght + i));
        }
        row.setList(rowList);
        return row;
    }

    /**
     * Getting colum x from board.
     *
     * @param x column number
     * @return Return the column from the board
     */
    public final SudokuColumn getColumn(final int x) {
        SudokuColumn col = new SudokuColumn();
        ArrayList<SudokuField> colList = new ArrayList<>();
        for (int i = 0; i < colLenght; i++) {
            colList.add(board.get(i * colLenght + x));
        }
        col.setList(colList);
        return col;
    }

    /**
     * Getting box from board with start from element in x row and y column.
     *
     * @param x row number
     * @param y col number
     * @return Return the box from the board which contain board[x][y]
     */
    public final SudokuBox getBox(final int x, final int y) {
        SudokuBox box = new SudokuBox();
        ArrayList<SudokuField> boxList = new ArrayList<>(colLenght);
        for (int i = 0; i < boxLenght; i++) {
            for (int j = 0; j < boxLenght; j++) {
                boxList.add(board.get((i + x) * colLenght + y + j));
            }
        }
        box.setList(boxList);
        return box;
    }

    /**
     * Checkig if the board is correct.
     *
     * @return true if the board is correct. Otherwise return false.
     */
    public final boolean checkBoard() {
        for (int i = 0; i < colLenght; i++) {
            for (int j = 0; j < colLenght; j += boxLenght) {
                if (i % boxLenght == 0 && !getBox(i, j).verify()) {
                    LOG_BOARD.warn("Board is incorrect");
                    return false;
                }
            }
            if (!getRow(i).verify()) {
                LOG_BOARD.warn("Board is incorrect");
                return false;
            }
            if (!getColumn(i).verify()) {
                LOG_BOARD.warn("Board is incorrect");
                return false;
            }
        }
        LOG_BOARD.warn("Board is correct");
        return true;
    }

    @Override
    public final String toString() {
        ToStringBuilder build = new ToStringBuilder(this);
        for (int i = 0; i < size; i++) {
            build.append(this.getBoard().get(i).getValue());
        }
        for (int i = 0; i < size; i++) {
            build.append(this.getBoard().get(i).isFilled());
        }
        return build.toString();
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public final int hashCode() {
        HashCodeBuilder build = new HashCodeBuilder(17, 37);
        build.append(board);
        return build.toHashCode();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     */
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EqualsBuilder build = new EqualsBuilder();
        build.append(this.board, ((SudokuBoard) obj).board);
        return build.isEquals();
    }

    /**
     * Creates and returns a copy of this object.  The precise meaning
     * of "copy" may depend on the class of the object.
     *
     * @return a clone of this instance.
     * @throws CloneNotSupportedException if the object's class does not
     *  support the {@code Cloneable} interface.
     * Subclasses that override the {@code clone} method can also
     *  throw this exception to indicate that an instance cannot
     *  be cloned.
     * @see Cloneable
     */
    @Override
    public final Object clone() throws CloneNotSupportedException {
        SudokuBoard cloneBoard = new SudokuBoard(this.solver);
        for (int i = 0; i < colLenght; i++) {
            for (int j = 0; j < colLenght; j++) {
                cloneBoard.set(i, j, this.get(i, j));
            }
        }
        return cloneBoard;
    }
}

package model;

/**
 * SudokuSolver interface.
 */
public interface SudokuSolver {

    /**
     * Solving sudoku board.
     *
     * @param board sudoku board to solve
     * @return true if SudokuBoard was solved
     */
    boolean solve(SudokuBoard board);
}

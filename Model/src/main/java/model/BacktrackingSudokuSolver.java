package model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class contains Sudoku Backtracking Algorithm.
 */
public class BacktrackingSudokuSolver implements SudokuSolver, Serializable {
    /**
     * Length of the board.
     */
    private static final int BOARDLENGTH = 9;
    /**
     * Solve the sudoku board.
     *
     * @param board sudokuBoard object
     * @return returns true when the sudokuBoard is solve,
     * otherwise return false
     */
    public final boolean solve(final SudokuBoard board) {
        for (int i = 0; i < BOARDLENGTH; i++) {
            for (int j = 0; j < BOARDLENGTH; j++) {
                if (board.get(i, j) == 0) {
                    ArrayList<Integer> possibilities = new ArrayList<Integer>();
                    for (int num = 1; num <= BOARDLENGTH; num++) {
                        if (board.checkNumber(i, j, num)) {
                            possibilities.add(num);
                        }
                    }
                    Collections.shuffle(possibilities);

                    while (possibilities.size() != 0) {
                        board.set(i, j, possibilities.get(0));
                        possibilities.remove(0);

                        if (board.solveGame()) {
                            return true;
                        } else {
                            board.set(i, j, 0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}

package dao;

import model.SudokuBoard;

/**
 * Class create new SudokuBoard Dao.
 */
public class SudokuBoardDaoFactory {
    /**
     * Returns new SudokuBoard Dao.
     *
     * @param filename Name of the input/output file.
     * @return FileSudokuBoardDao obj.
     */
    public final Dao<SudokuBoard> getFileDao(final String filename) {
        return new FileSudokuBoardDao(filename);
    }

}

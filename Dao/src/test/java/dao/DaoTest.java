package dao;

import org.junit.jupiter.api.Test;
import model.BacktrackingSudokuSolver;
import model.SudokuBoard;
import model.SudokuSolver;
import static org.junit.jupiter.api.Assertions.*;

public class DaoTest {

    @Test
    public void readAndWriteTest() throws  DaoException {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board1 = new SudokuBoard(solver);
        board1.solveGame();
        SudokuBoard board2 = new SudokuBoard(solver);

        Dao<SudokuBoard> sudokuBoardDao;
        sudokuBoardDao = factory.getFileDao("test1.txt");

        sudokuBoardDao.write(board1);
        board2 = sudokuBoardDao.read();
        assertEquals(board1, board2);


    }

    @Test
    public void readExceptionTest() {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> sudokuBoardDao;
        sudokuBoardDao = factory.getFileDao("Case2.txt");

        Exception exception = assertThrows(DaoFileException.class, sudokuBoardDao::read);
    }

    @Test
    public void writeExceptionTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        Dao<SudokuBoard> sudokuBoardDao;
        sudokuBoardDao = factory.getFileDao("/cos/bl#a#bla");
        Exception exception = assertThrows(DaoFileException.class, () -> {
            sudokuBoardDao.write(board);
        });
    }
}

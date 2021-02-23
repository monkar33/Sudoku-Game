package dao;

import model.SudokuBoard;

import java.io.*;

/**
 * Class implenents SudokuBoard Dao.
 */
public class FileSudokuBoardDao implements Dao<SudokuBoard>, AutoCloseable {
    /**
     * name of the input/output file.
     */
    private String fileName;

    /**
     * Constructor of the FileSudokuBoardDao.
     *
     * @param newFilename set the filename.
     */
    public FileSudokuBoardDao(final String newFilename) {
        this.fileName = newFilename;
    }

//    @Override
//    protected final void finalize() throws Throwable {
//        super.finalize();
//    }

    /**
     * Read SudokuBoard from file.
     *
     * @return SudokuBoard object.
     */
    @Override
    public final SudokuBoard read() throws DaoFileException {
        SudokuBoard obj = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream(fileName))) {
            obj = (SudokuBoard) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DaoFileException(e);
        }

        return obj;
    }

    /**
     * Write SudokuBoard object to file.
     *
     * @param obj to write.
     */
    @Override
    public final void write(final SudokuBoard obj) throws DaoFileException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(fileName))) {
            outputStream.writeObject(obj);
        } catch (IOException e) {
            throw new DaoFileException(e);
        }
    }

    /**
     * @throws Exception if this resource cannot be closed
     */
    @Override
    public void close() throws Exception {

    }
}

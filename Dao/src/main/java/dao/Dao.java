package dao;

/**
 * Interface Dao.
 *
 * @param <T> t object.
 */
public interface Dao<T> {

    /**
     * @return T obj.
     * @throws DaoException .
     */
    T read() throws DaoException;

    /**
     * @param obj to write.
     * @throws DaoException .
     */
    void write(T obj) throws DaoException;
}


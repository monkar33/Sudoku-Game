package dao;

import java.io.IOException;

/**
 * Class implement DaoException.
 */
public class DaoException extends IOException {
    /**
     * Constructor.
     *
     * @param cause of the exception.
     */
    public DaoException(final Throwable cause) {
        super(cause);
    }


}

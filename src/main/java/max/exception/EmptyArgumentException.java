package max.exception;

/**
 * Signals commands with empty arguments were supplied by the user.
 */
public class EmptyArgumentException extends MaxException {
    /**
     * Public constructor for EmptyArgumentException.
     *
     * @param msg Error message
     */
    public EmptyArgumentException(String msg) {
        super(msg);
    }
}

package util;

/** Thrown when a problem is infeasible. */
public class InfeasibleException extends Exception {

    private static final long serialVersionUID = 1L;

    public InfeasibleException(String message) {
        super(message);
    }

}

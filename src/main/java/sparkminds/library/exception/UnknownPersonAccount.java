package sparkminds.library.exception;

public class UnknownPersonAccount extends RuntimeException {

    public UnknownPersonAccount(String message) {
        super(message);
    }
}

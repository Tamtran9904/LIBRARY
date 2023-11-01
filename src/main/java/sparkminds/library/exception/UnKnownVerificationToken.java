package sparkminds.library.exception;

public class UnKnownVerificationToken extends RuntimeException{

    public UnKnownVerificationToken(String message) {
        super(message);
    }
}

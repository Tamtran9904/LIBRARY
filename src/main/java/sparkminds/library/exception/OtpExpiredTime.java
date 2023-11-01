package sparkminds.library.exception;

public class OtpExpiredTime extends RuntimeException{

    public OtpExpiredTime(String message) {
        super(message);
    }

}

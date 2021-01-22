package storage.exception;

public class ResumeAlreadyStoredException extends RuntimeException {
    public ResumeAlreadyStoredException(String message) {
        super(message);
    }
}

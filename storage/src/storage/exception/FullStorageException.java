package storage.exception;

public class FullStorageException extends RuntimeException {
    public FullStorageException(String message) {
        super(message);
    }
}

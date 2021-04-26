package storage.file.exception;

public class FileUpdateException extends RuntimeException {
    public FileUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUpdateException(String message) {
        super(message);
    }
}

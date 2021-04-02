package storage.file.exception;

public class FileCreationException extends RuntimeException {
    public FileCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileCreationException(String message) {
        super(message);
    }
}

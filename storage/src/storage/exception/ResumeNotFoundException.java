package storage.exception;

public class ResumeNotFoundException extends RuntimeException {
    public ResumeNotFoundException(String uuid) {
        super(String.format("Resume(uuid={%s}) was not found", uuid));
    }
}

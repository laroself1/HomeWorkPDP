package storage.exception;

public class ResumeAlreadyStoredException extends RuntimeException {
    public ResumeAlreadyStoredException(String uuid) {
        super(String.format("Resume(uuid={%s}) is already present in storage. It may be updated only.", uuid));
    }
}

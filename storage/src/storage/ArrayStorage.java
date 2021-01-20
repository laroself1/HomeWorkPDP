package storage;

import model.Resume;
import storage.exception.FullStorageException;
import storage.exception.ResumeAlreadyStoredException;
import storage.exception.ResumeNotFoundException;

import java.util.Arrays;
import java.util.Objects;

public abstract class ArrayStorage implements Storage {
    protected static final int NOT_EXISTING_INDEX = -1;
    protected static final int DEFAULT_MAX_STORAGE_SIZE = 100000;
    protected int maxSize;
    protected int size;

    protected Resume[] storage;

    public ArrayStorage() {
        maxSize = DEFAULT_MAX_STORAGE_SIZE;
    }

    protected ArrayStorage(int size) {
        this.storage = new Resume[size];
        this.maxSize = size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume r) {
        Objects.requireNonNull(r);
        validateFreeSpacePresence();
        int indexToStoreAt = getResumeIndex(r.getUuid());
        validateResumeIsNotPresent(indexToStoreAt, r.getUuid());

        store(r, indexToStoreAt);
        size++;
    }

    @Override
    public void update(Resume r) {
        Objects.requireNonNull(r);
        int index = getResumeIndex(r.getUuid());
        validateResumeIsPresent(index, r.getUuid());

        storage[index] = r;
    }

    @Override
    public void delete(String uuid) {
        Objects.requireNonNull(uuid);
        int index = getResumeIndex(uuid);
        validateResumeIsPresent(index, uuid);

        erase(index);
        size--;
    }

    @Override
    public Resume get(String uuid) {
        int index = getResumeIndex(uuid);
        validateResumeIsPresent(index, uuid);

        return storage[index];
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    private void validateFreeSpacePresence() {
        if (size == maxSize) {
            throw new FullStorageException("Storage max limit is reached. Please, clear storage to add new elements.");
        }
    }

    private void validateResumeIsNotPresent(int indexToStoreAt, String uuid) {
        if (isValidIndex(indexToStoreAt)) {
            String message = String.format("Resume(uuid={%s}) is already present in storage. It may be updated only.", uuid);
            throw new ResumeAlreadyStoredException(message);
        }
    }

    private void validateResumeIsPresent(int resumeIndex, String uuid) {
        if (!isValidIndex(resumeIndex)) {
            throw new ResumeNotFoundException(uuid);
        }
    }

    private boolean isValidIndex(int index) {
        return index > NOT_EXISTING_INDEX;
    }

    protected abstract int getResumeIndex(String uuid);

    protected abstract void store(Resume r, int index);

    protected abstract void erase(int index);
}

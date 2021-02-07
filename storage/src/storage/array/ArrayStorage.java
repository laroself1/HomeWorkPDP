package storage.array;

import model.Resume;
import storage.AbstractStorage;
import storage.exception.FullStorageException;
import storage.exception.ResumeAlreadyStoredException;
import storage.exception.ResumeNotFoundException;

import java.util.Arrays;

public abstract class ArrayStorage extends AbstractStorage {
    protected static final int DEFAULT_MAX_STORAGE_SIZE = 100000;
    protected int maximumSize;
    protected int currentSize;

    protected final Resume[] storage;

    protected ArrayStorage() {
        this.storage = new Resume[DEFAULT_MAX_STORAGE_SIZE];
        maximumSize = DEFAULT_MAX_STORAGE_SIZE;
    }

    protected ArrayStorage(int storageMaxSize) {
        this.storage = new Resume[storageMaxSize];
        this.maximumSize = storageMaxSize;
    }

    @Override
    protected void removeAll() {
        Arrays.fill(storage, 0, currentSize, null);
        currentSize = 0;
    }

    @Override
    public void store(Object key, Resume r) {
        validateFreeSpacePresence();
        store(r, (int) key);
        currentSize++;
    }

    @Override
    public void renew(Object key, Resume r) {
        storage[(int) key] = r;
    }

    @Override
    public void remove(Object key) {
        erase((int) key);
        currentSize--;
    }

    @Override
    public Resume find(Object key) {
        return storage[(int) key];
    }

    @Override
    public Resume[] findAll() {
        return Arrays.copyOfRange(storage, 0, currentSize);
    }


    public int length() {
        return currentSize;
    }

    private void validateFreeSpacePresence() {
        if (currentSize == maximumSize) {
            throw new FullStorageException("Storage max limit is reached. Please, clear storage to add new elements.");
        }
    }

    @Override
    protected Object tryGetNotExistingResumeStoringKey(Resume r) {
        int indexToStoreAt = getResumeIndex(r.getUuid());
        validateResumeIsNotPresent(indexToStoreAt, r.getUuid());
        return indexToStoreAt;
    }

    private void validateResumeIsNotPresent(int indexToStoreAt, String uuid) {
        if (isValidIndex(indexToStoreAt)) {
            throw new ResumeAlreadyStoredException(uuid);
        }
    }

    @Override
    protected Object tryGetExistingResumeKey(String uuid) {
        int resumeIndex = getResumeIndex(uuid);
        validateResumeIsPresent(resumeIndex, uuid);
        return resumeIndex;
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

    protected abstract void store(Resume r, Integer index);

    protected abstract void erase(int index);
}

package storage.array;

import model.Resume;
import storage.AbstractStorage;
import storage.exception.FullStorageException;

import java.util.Arrays;
import java.util.List;

public abstract class ArrayStorage extends AbstractStorage {
    protected static final int DEFAULT_MAX_STORAGE_SIZE = 100000;
    protected int maximumSize;
    protected int currentSize;

    protected final Resume[] storage;

    protected ArrayStorage() {
        this.storage = new Resume[DEFAULT_MAX_STORAGE_SIZE];
        this.maximumSize = DEFAULT_MAX_STORAGE_SIZE;
    }

    protected ArrayStorage(int storageMaxSize) {
        this.storage = new Resume[storageMaxSize];
        this.maximumSize = storageMaxSize;
    }

    @Override
    public void clear() {
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
    public List<Resume> copyData() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, currentSize));
    }

    @Override
    public int size() {
        return currentSize;
    }

    private void validateFreeSpacePresence() {
        if (currentSize == maximumSize) {
            throw new FullStorageException("Storage max limit is reached. Please, clear storage to add new elements.");
        }
    }

    @Override
    protected boolean isKeyPresent(Object key) {
        return (Integer) key >= 0;
    }

    protected abstract void store(Resume r, Integer index);

    protected abstract void erase(int index);

    protected abstract Integer getKey(String uuid);
}

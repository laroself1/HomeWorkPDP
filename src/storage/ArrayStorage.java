package storage;

import model.Resume;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

import static java.util.logging.Level.WARNING;

public abstract class ArrayStorage implements Storage {
    protected static final int NOT_EXISTING_INDEX = -1;
    protected static final int MAX_STORAGE_SIZE = 100000;

    private static final Logger log = Logger.getLogger(ArrayStorage.class.getSimpleName());

    protected Resume[] storage;

    protected ArrayStorage() {
        this.storage = new Resume[MAX_STORAGE_SIZE];
    }

    protected int size;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume r) {
        Objects.requireNonNull(r);
        if (hasFreeSpace()) {
            int indexToStoreAt = getResumeIndex(r.getUuid());
            if (isValidIndex(indexToStoreAt)) {
                log.log(WARNING, "Resume(uuid={0}) is already present in storage. It may be updated only.", r.getUuid());
            } else {
                store(r, indexToStoreAt);
                size++;
            }
        }
    }

    @Override
    public void update(Resume r) {
        Objects.requireNonNull(r);
        int index = getResumeIndex(r.getUuid());
        if (isValidIndex(index)) {
            storage[index] = r;
        } else {
            logNotFound(r.getUuid());
        }
    }

    @Override
    public void delete(String uuid) {
        Objects.requireNonNull(uuid);
        int index = getResumeIndex(uuid);
        if (isValidIndex(index)) {
            erase(index);
            size--;
        } else {
            logNotFound(uuid);
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getResumeIndex(uuid);
        if (isValidIndex(index)) {
            return storage[index];
        }
        logNotFound(uuid);
        return null;
    }

    protected void logNotFound(String uuid) {
        log.log(WARNING, "Resume(uuid={0}) was not found", uuid);
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    private boolean isValidIndex(int index) {
        return index > NOT_EXISTING_INDEX;
    }

    private boolean hasFreeSpace() {
        if (size == MAX_STORAGE_SIZE) {
            log.log(WARNING, "Storage max limit is reached. Please, clear storage to add new elements.");
            return false;
        }
        return true;
    }

    protected abstract int getResumeIndex(String uuid);

    protected abstract void store(Resume r, int index);

    protected abstract void erase(int index);
}

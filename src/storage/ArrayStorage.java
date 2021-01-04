package storage;

import model.Resume;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

import static java.util.logging.Level.WARNING;

public abstract class ArrayStorage implements Storage {
    protected static final int NOT_EXISTING_INDEX = -1;
    protected static final int MAX_STORAGE_SIZE = 100000;

    protected final Logger log = Logger.getLogger(getClass().getSimpleName());

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
        if (size == MAX_STORAGE_SIZE) {
            logStorageMaxLimit();
            return;
        }
        if (isPresent(r.getUuid())) {
            logSavePresentError(r.getUuid());
        } else {
            int indexToStoreAt = getResumeIndex(r.getUuid());
            store(r, indexToStoreAt);
            size++;
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
        if (isEmpty(uuid)) {
            logNotFound(uuid);
            return;
        }
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
        if (isEmpty(uuid)) {
            logEmptyUUID();
            return null;
        }
        int index = getResumeIndex(uuid);
        if (isValidIndex(index)) {
            return storage[index];
        }
        logNotFound(uuid);
        return null;
    }

    protected void logEmptyUUID() {
        log.log(WARNING, "Invalid input parameters, empty UUID");
    }

    protected void logNotFound(String uuid) {
        log.log(WARNING, "Resume(uuid={0}) was not found", uuid);
    }

    private void logStorageMaxLimit() {
        log.log(WARNING,"Storage max limit is reached. Please, clear storage to add new elements.");
    }

    protected void logSavePresentError(String uuid) {
        log.log(WARNING, "Resume(uuid={0}) is already present in storage. It may be updated only.", uuid);
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    private boolean isEmpty(String uuid) {
        return uuid == null || uuid.isEmpty();
    }

    private boolean isPresent(String uuid) {
        return getResumeIndex(uuid) > NOT_EXISTING_INDEX;
    }

    private boolean isValidIndex(int index) {
        return index > NOT_EXISTING_INDEX;
    }

    protected abstract int getResumeIndex(String uuid);

    protected abstract void store(Resume r, int index);

    protected abstract void erase(int index);
}

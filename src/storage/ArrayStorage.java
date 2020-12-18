package storage;

import model.Resume;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

public class ArrayStorage {
    private static final Logger log = Logger.getLogger("ArrayStorage");
    private static final int NOT_EXISTING_INDEX = -1;

    private static final int MAX_STORAGE_SIZE = 100000;
    private final Resume[] storage = new Resume[MAX_STORAGE_SIZE];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        Objects.requireNonNull(r);
        if (size == MAX_STORAGE_SIZE) {
            log.warning("Storage max limit is reached. Please, clear storage to add new elements.");
            return;
        }
        if (isPresent(r.getUuid())) {
            log.warning(String.format("Resume(uuid=%s) is already present in storage. It may be updated only.", r.getUuid()));
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void update(Resume r) {
        Objects.requireNonNull(r);
        int index = getResumeIndexById(r.getUuid());
        if (isValidIndex(index)) {
            storage[index] = r;
        } else {
            log.warning(String.format("Resume(uuid=%s) is not present in storage. Please, add it.", r.getUuid()));
        }
    }

    public void delete(String uuid) {
        if (isEmpty(uuid)) {
            log.warning(String.format("Delete impossible. Incorrect incoming parameter(uuid=%s)", uuid));
            return;
        }
        int index = getResumeIndexById(uuid);
        if (isValidIndex(index)) {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            log.warning(String.format("Delete impossible. Resume(uuid=%s) is not present in storage", uuid));
        }

    }

    public Resume get(String uuid) {
        if (isEmpty(uuid)) {
            log.warning(String.format("Invalid input parameters, empty UUID", uuid));
            return null;
        }
        int index = getResumeIndexById(uuid);
        if (isValidIndex(index)) {
            return storage[index];
        }
        log.warning(String.format("Resume(uuid=%s) is not present in storage. Return null.", uuid));
        return null;
    }

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
        return getResumeIndexById(uuid) > NOT_EXISTING_INDEX;
    }

    private boolean isValidIndex(int index) {
        return index > NOT_EXISTING_INDEX;
    }

    private int getResumeIndexById(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return NOT_EXISTING_INDEX;
    }

}

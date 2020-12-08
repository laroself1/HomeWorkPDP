package storage;

import model.Resume;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

public class ArrayStorage {
    private static final Logger log = Logger.getLogger("ArrayStorage Logger");

    private static final int MAX_STORAGE_SIZE = 100000;
    private final Resume[] storage = new Resume[MAX_STORAGE_SIZE];
    private int size;

    public void clear() {
        Arrays.fill(storage, null);
        size = 0;
    }

    public void save(Resume r) {
        Objects.requireNonNull(r);
        if (size == MAX_STORAGE_SIZE) {
            log.warning("Storage max limit is reached. Please, clear storage to add new elements.");
            return;
        }
        if (isPresent(r.getUuid())) {
            log.warning(String.format("Resume(uuid=%s) is already present in storage. It may be updated only.%n", r.getUuid()));
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void update(Resume r) {
        Objects.requireNonNull(r);
        if (isPresent(r.getUuid())) {
            storage[getResumeIndexById(r.getUuid())] = r;
        } else {
            log.warning(String.format("Resume(uuid=%s) is not present in storage. Please, add it.%n", r.getUuid()));
        }
    }

    public void delete(String uuid) {
        if (isNotEmpty(uuid) && isPresent(uuid)) {
            log.warning(String.format("Delete impossible. Resume(uuid=%s) is not present in storage%n", uuid));
            return;
        }
        int indexToDelete = getResumeIndexById(uuid);
        storage[indexToDelete] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    public Resume get(String uuid) {
        if (isNotEmpty(uuid) && isPresent(uuid)) {
            return getExistingResumeById(uuid);
        }
        log.warning(String.format("Resume(uuid=%s) is not present in storage. Return null.%n", uuid));
        return null;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private boolean isNotEmpty(String uuid) {
        return uuid != null && !uuid.isEmpty();
    }

    private boolean isPresent(String uuid) {
        return get(uuid) != null;
    }


    private Resume getExistingResumeById(String uuid) {
        return storage[getResumeIndexById(uuid)];
    }

    private int getResumeIndexById(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

}

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[100000];
    int size;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        if(isNotPresent(r.uuid)) {
            storage[getFirstNullCellIndex()] = r;
            size++;
        } else {
            System.out.printf("Resume(uuid=%s) is already present in storage. It may be updated only.%n", r.uuid);
        }
    }

    void update(Resume r) {
        if(isPresent(r.uuid)) {
            storage[getExistingResumeIndexById(r.uuid)] = r;
        } else {
            System.out.printf("Resume(uuid=%s) is not present in storage. Please, add it.%n", r.uuid);
        }
    }

    void delete(String uuid) {
        if (isNotPresent(uuid)) {
            System.out.printf("Delete impossible. Resume(uuid=%s) is not present in storage%n", uuid);
            return;
        }
        int indexToDelete = getExistingResumeIndexById(uuid);
        storage[indexToDelete] = storage[size - 1];
        storage[size - 1] = null;
        size--;
    }

    Resume get(String uuid) {
        if (isPresent(uuid)) {
            return getExistingResumeById(uuid);
        }
        System.out.printf("Resume(uuid=%s) is not present in storage. Return null.%n", uuid);
        return null;
    }

    Resume[] getAll() {
        int cropTo = getFirstNullCellIndex();
        if (cropTo >= 0) {
            Resume[] resumes = new Resume[cropTo];
            System.arraycopy(storage, 0, resumes, 0, cropTo);
            return resumes;
        }
        return storage.clone();
    }

    int size() {
        return size;
    }

    private boolean isNotPresent(String uuid) {
        return !isPresent(uuid);
    }

    private boolean isPresent(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return true;
            }
        }
        return false;
    }

    private Resume getExistingResumeById(String uuid) {
        return storage[getExistingResumeIndexById(uuid)];
    }

    private int getExistingResumeIndexById(String uuid) {
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private int getFirstNullCellIndex() {
        for (int i = 0; i < size; i++) {
            if (null == storage[i].uuid) {
                return i;
            }
        }
        System.out.println("Storage is full, return last element index");
        return size - 1;
    }
}

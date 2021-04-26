package storage.array.sorted;

import model.Resume;
import storage.array.ArrayStorage;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends ArrayStorage {
    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);


    public SortedArrayStorage(int size) {
        super(size);
    }

    public SortedArrayStorage() {
    }

    @Override
    protected Integer getKey(String uuid) {
        Resume resume = new Resume(uuid, "any");
        return Arrays.binarySearch(storage, 0, currentSize, resume, RESUME_COMPARATOR);
    }


    @Override
    protected void store(Resume r, Integer index) {
        int storeIndex = -index - 1;
        System.arraycopy(storage, storeIndex, storage, storeIndex + 1, currentSize - storeIndex);
        storage[storeIndex] = r;
    }

    @Override
    protected void erase(int index) {
        int erasedIndex = currentSize - index - 1;
        if (erasedIndex > 0) {
            System.arraycopy(storage, index + 1, storage, index, erasedIndex);
        }
    }
}

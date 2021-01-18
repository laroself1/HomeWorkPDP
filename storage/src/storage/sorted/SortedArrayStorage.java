package storage.sorted;

import model.Resume;
import storage.ArrayStorage;

import java.util.Arrays;

public class SortedArrayStorage extends ArrayStorage {

    public SortedArrayStorage(int size) {
        super(size);
    }

    @Override
    protected int getResumeIndex(String uuid) {
        Resume resume = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, resume);
    }

    @Override
    protected void store(Resume r, int index) {
        int storeIndex = -index - 1;
        System.arraycopy(storage, storeIndex, storage, storeIndex + 1, size - storeIndex);
        storage[storeIndex] = r;
    }

    @Override
    protected void erase(int index) {
        int erasedIndex = size - index - 1;
        if (erasedIndex > 0) {
            System.arraycopy(storage, index + 1, storage, index, erasedIndex);
        }
    }

}

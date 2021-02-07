package storage.array.simple;

import model.Resume;
import storage.array.ArrayStorage;
import storage.exception.ResumeAlreadyStoredException;
import storage.exception.ResumeNotFoundException;

public class SimpleArrayStorage extends ArrayStorage {

    public SimpleArrayStorage(int size) {
        super(size);
    }

    public SimpleArrayStorage() {
    }

    @Override
    protected int getResumeIndex(String uuid) {
        for (int i = 0; i < currentSize; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return NOT_EXISTING_INDEX;
    }

    @Override
    protected void store(Resume r, Integer index) {
        storage[currentSize] = r;
    }

    @Override
    protected void erase(int index) {
        storage[index] = storage[currentSize - 1];
        storage[currentSize - 1] = null;
    }
}

package storage.array;

import storage.array.sorted.SortedArrayStorage;

public class SortedArrayStorageTest extends ArrayStorageTest {

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage(MAX_TEST_STORAGE_SIZE));
    }
}

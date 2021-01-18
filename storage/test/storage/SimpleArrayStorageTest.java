package storage;

import storage.simple.SimpleArrayStorage;

public class SimpleArrayStorageTest extends ArrayStorageTest {

    public SimpleArrayStorageTest() {
        super(new SimpleArrayStorage(MAX_TEST_STORAGE_SIZE));
    }
}

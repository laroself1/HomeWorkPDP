package storage;

import storage.list.ArrayListStorage;

public class ArrayListStorageTest extends ArrayStorageTest {

    public ArrayListStorageTest() {
        super(new ArrayListStorage());
    }

    @Override
    void save_throwsAddToFullStorageException() {
        //No storage limit logic should be present for ListArrayStorage
    }
}

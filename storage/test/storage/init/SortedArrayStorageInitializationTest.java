package storage.init;

import storage.sorted.SortedArrayStorage;

public class SortedArrayStorageInitializationTest extends ArrayStorageInitializationTest{
    public SortedArrayStorageInitializationTest() {
        super(new SortedArrayStorage());
    }
}

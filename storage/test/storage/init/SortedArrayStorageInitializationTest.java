package storage.init;

import storage.array.sorted.SortedArrayStorage;

public class SortedArrayStorageInitializationTest extends ArrayStorageInitializationTest{
    public SortedArrayStorageInitializationTest() {
        super(new SortedArrayStorage());
    }
}

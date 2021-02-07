package storage.init;

import storage.map.MapStorage;

public class MapStorageInitializationTest extends ArrayStorageInitializationTest{
    public MapStorageInitializationTest() {
        super(new MapStorage());
    }
}

package storage;

import model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import storage.map.MapStorage;

public class HashMapStorageTest extends AbstractStorageTest {

    public HashMapStorageTest() {
        super(new MapStorage());
    }

    @Test
    void getAll_returnsAllResumes() {
        Resume[] expected = new Resume[] {
                new Resume(UUID_DEV_CV, "Developer-CV"),
                new Resume(UUID_MANAGER_CV, "Manager-CV"),
                new Resume(UUID_ADMIN_CV, "Admin-CV"),
        };
        Resume[] actual = testInstance.getAll();
        for (int i = 0; i < expected.length; i++) {
            Assertions.assertEquals(expected[i], actual[i]);
        }
        Assertions.assertEquals(expected.length, actual.length);
    }
}

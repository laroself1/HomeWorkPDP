package storage;

import model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import storage.map.MapStorage;

import java.util.List;

public class HashMapStorageTest extends AbstractStorageTest {

    public HashMapStorageTest() {
        super(new MapStorage());
    }

    @Test
    void getAll_returnsAllResumes() {
        List<Resume> expected = List.of(
                new Resume(UUID_ADMIN_CV, "Admin-CV"),
                new Resume(UUID_DEV_CV, "Developer-CV"),
                new Resume(UUID_MANAGER_CV, "Manager-CV")
        );
        List<Resume> actual = testInstance.getAllSorted();
        Assertions.assertEquals(expected, actual);
    }
}

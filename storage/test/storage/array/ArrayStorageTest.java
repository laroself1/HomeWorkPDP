package storage.array;

import model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import storage.AbstractStorageTest;
import storage.Storage;
import storage.exception.FullStorageException;

abstract class ArrayStorageTest extends AbstractStorageTest {
    public ArrayStorageTest(Storage testInstance) {
        super(testInstance);
    }

    @Test
    void save_throwsAddToFullStorageException() {
        testInstance.save(NOT_STORED);

        Assertions.assertThrows(FullStorageException.class, () -> testInstance.save(new Resume("999")));
    }
}

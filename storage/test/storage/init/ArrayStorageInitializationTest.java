package storage.init;

import model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import storage.Storage;

abstract class ArrayStorageInitializationTest {
    public ArrayStorageInitializationTest(Storage testInstance) {
        this.testInstance = testInstance;
    }

    private final Storage testInstance;

    @Test
    void noMaximumSizeParameterIsGiven_storageWorksAfterInitialization() {
        Resume resume = new Resume();
        testInstance.save(resume);
        Resume retrievedResume = testInstance.get(resume.getUuid());

        Assertions.assertEquals(resume, retrievedResume);
        Assertions.assertEquals(1, testInstance.size());
    }
}

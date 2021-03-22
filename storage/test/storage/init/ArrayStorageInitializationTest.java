package storage.init;

import model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import storage.Storage;
import storage.exception.ResumeNotFoundException;

abstract class ArrayStorageInitializationTest {
    public ArrayStorageInitializationTest(Storage testInstance) {
        this.testInstance = testInstance;
    }

    private final Storage testInstance;

    @Test
    void noMaximumSizeParameterIsGiven_storageWorksAfterInitialization() {
        String uuid = "abg-66s-s";
        Resume resume = new Resume(uuid);

        testInstance.save(resume);
        Resume retrievedResume = testInstance.get(resume.getUuid());

        String title = "Title";
        testInstance.update(new Resume(resume.getUuid(), title));
        String updatedTitle = testInstance.get(uuid).getFullName();

        Assertions.assertEquals(resume, retrievedResume);
        Assertions.assertEquals(1, testInstance.size());
        Assertions.assertEquals(title, updatedTitle);
        Assertions.assertThrows(ResumeNotFoundException.class, () -> {
            testInstance.delete(uuid);
            testInstance.get(uuid);
        });
    }
}

package storage;

import model.Resume;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.exception.ResumeAlreadyStoredException;
import storage.exception.ResumeNotFoundException;

abstract class ArrayStorageTest {
    protected static final int MAX_TEST_STORAGE_SIZE = 4;

    private String uuidDevCV = "ssj-34eh32";
    private String uuidManagerCV = "ewo-232hsj";
    private String uuidAdminCV = "qxb-238sdf";

    Resume devCV = new Resume(uuidDevCV, "Developer-CV");
    Resume managerCV = new Resume(uuidManagerCV, "Manager-CV");
    Resume adminCV = new Resume(uuidAdminCV, "Admin-CV");

    Resume notStoredInStorageResume = new Resume("123", "Alan");

    private final Storage testInstance;

    public ArrayStorageTest(Storage testInstance) {
        this.testInstance = testInstance;
    }

    @BeforeEach
    void addResumes() {
        testInstance.save(devCV);
        testInstance.save(managerCV);
        testInstance.save(adminCV);
    }

    @AfterEach
    void clear() {
        testInstance.clear();
    }

    @Test
    void get_success() {
        Resume actual = testInstance.get(uuidDevCV);
        Resume expected = new Resume(uuidDevCV, "Developer-CV");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void get_throwsNotFoundException() {
        Assertions.assertThrows(ResumeNotFoundException.class, () -> testInstance.get("NotExistingId"));
    }

    @Test
    void clearAll_deleteAllResumes() {
        testInstance.clear();

        Assertions.assertThrows(ResumeNotFoundException.class, () -> testInstance.get(uuidDevCV));
        Assertions.assertEquals(testInstance.size(), 0);
    }

    @Test
    void save_success() {
        testInstance.save(notStoredInStorageResume);
    }

    @Test
    void save_throwsResumeIsAlreadyStoredException() {
        Assertions.assertThrows(ResumeAlreadyStoredException.class, () -> testInstance.save(devCV));
    }

    @Test
    void save_throwsAddToFullStorageException() {
        testInstance.save(notStoredInStorageResume);

        Assertions.assertThrows(ResumeAlreadyStoredException.class, () -> testInstance.save(new Resume("123")));
    }

    @Test
    void delete_success() {
        testInstance.delete(uuidDevCV);
        testInstance.delete(uuidManagerCV);
        testInstance.delete(uuidAdminCV);

        Assertions.assertEquals(0, testInstance.size());
    }

    @Test
    void deleteThrowsResumeNotFound_whenTryToDeleteNotStoredResume() {
        Assertions.assertThrows(ResumeNotFoundException.class, () -> testInstance.delete(notStoredInStorageResume.getUuid()));
    }

}

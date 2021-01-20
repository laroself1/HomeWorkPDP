package storage;

import model.Resume;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.exception.FullStorageException;
import storage.exception.ResumeAlreadyStoredException;
import storage.exception.ResumeNotFoundException;

abstract class ArrayStorageTest {
    protected static final int MAX_TEST_STORAGE_SIZE = 4;

    private static final String UUID_DEV_CV = "ssj-34eh32";
    private static final String UUID_MANAGER_CV = "ewo-232hsj";
    private static final String UUID_ADMIN_CV = "qxb-238sdf";

    Resume notStoredInStorageResume = new Resume("123", "Alan");

    private final Storage testInstance;

    public ArrayStorageTest(Storage testInstance) {
        this.testInstance = testInstance;
    }

    @BeforeEach
    void addResumes() {
        testInstance.save(new Resume(UUID_DEV_CV, "Developer-CV"));
        testInstance.save(new Resume(UUID_MANAGER_CV, "Manager-CV"));
        testInstance.save(new Resume(UUID_ADMIN_CV, "Admin-CV"));
    }

    @AfterEach
    void clear() {
        testInstance.clear();
    }

    @Test
    void get_retrievesStoredResume() {
        Resume actual = testInstance.get(UUID_DEV_CV);
        Resume expected = new Resume(UUID_DEV_CV, "Developer-CV");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void get_throwsNotFoundException() {
        Assertions.assertThrows(ResumeNotFoundException.class, () -> testInstance.get("NotExistingId"));
    }

    @Test
    void clearAll_deleteAllResumes() {
        testInstance.clear();

        Assertions.assertThrows(ResumeNotFoundException.class, () -> testInstance.get(UUID_DEV_CV));
        Assertions.assertEquals(testInstance.size(), 0);
    }

    @Test
    void save_StoresResumeAndIncreasesSize() {
        int sizeBeforeSave = testInstance.size();
        testInstance.save(notStoredInStorageResume);
        int sizeAfterSave = testInstance.size();
        Assertions.assertEquals(notStoredInStorageResume, testInstance.get("123"));
        Assertions.assertEquals(sizeBeforeSave + 1, sizeAfterSave);
    }

    @Test
    void save_throwsResumeIsAlreadyStoredException() {
        Assertions.assertThrows(ResumeAlreadyStoredException.class, () -> testInstance.save(new Resume(UUID_DEV_CV)));
    }

    @Test
    void save_throwsAddToFullStorageException() {
        testInstance.save(notStoredInStorageResume);

        Assertions.assertThrows(FullStorageException.class, () -> testInstance.save(new Resume("999")));
    }

    @Test
    void delete_erasesResumesById() {
        testInstance.delete(UUID_DEV_CV);
        testInstance.delete(UUID_MANAGER_CV);
        testInstance.delete(UUID_ADMIN_CV);

        Assertions.assertEquals(0, testInstance.size());
    }

    @Test
    void deleteThrowsResumeNotFound_whenTryToDeleteNotStoredResume() {
        Assertions.assertThrows(ResumeNotFoundException.class, () -> testInstance.delete(notStoredInStorageResume.getUuid()));
    }

    @Test
    void update_updatesStoredResume() {
        String updatedTitle = "Developer-CV-updated";
        testInstance.update(new Resume(UUID_DEV_CV, updatedTitle));

        Assertions.assertEquals(updatedTitle, testInstance.get(UUID_DEV_CV).getTitle());
    }

    @Test
    void update_throwsResumeNotFoundException() {
        Assertions.assertThrows(ResumeNotFoundException.class, () -> testInstance.update(notStoredInStorageResume));
    }



}

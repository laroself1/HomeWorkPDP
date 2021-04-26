package storage;

import model.ContactType;
import model.ListSection;
import model.Organization;
import model.OrganizationSection;
import model.Position;
import model.Resume;
import model.SectionType;
import model.TextSection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storage.exception.ResumeAlreadyStoredException;
import storage.exception.ResumeNotFoundException;

import java.time.LocalDate;
import java.util.List;

public abstract class AbstractStorageTest {
    protected static final int MAX_TEST_STORAGE_SIZE = 4;

    static final String UUID_DEV_CV = "ssj-34eh32";
    static final String UUID_MANAGER_CV = "ewo-232hsj";
    static final String UUID_ADMIN_CV = "qxb-238sdf";

    protected static Resume NOT_STORED = new Resume("123", "Alan");

    protected final Storage testInstance;

    public AbstractStorageTest(Storage testInstance) {
        this.testInstance = testInstance;
    }

    @BeforeEach
    void addResumes() {
        testInstance.save(managerCV());
        testInstance.save(adminCV());
        testInstance.save(devCV());
    }

    Resume managerCV() {
        Resume resume = new Resume(UUID_MANAGER_CV, "Manager Manager");
        resume.addContact(ContactType.MOBILE, "+3809321238726");
        resume.addSection(SectionType.EDUCATION, new TextSection("University"));
        return resume;
    }

    Resume adminCV() {
        Resume resume = new Resume(UUID_ADMIN_CV, "Admin Admin");
        resume.addContact(ContactType.SKYPE, "admin.skype");
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(List.of("Qualification1", "Qualification2")));
        return resume;
    }

    Resume devCV() {
        Resume resume = new Resume(UUID_DEV_CV, "Dev Dev");
        resume.addContact(ContactType.HOME_PAGE, "www.home-page.com");
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusYears(1);
        List<Position> positions = List.of(new Position(startDate, endDate, "job", "responsibilities"));
        Organization company = new Organization("company", "www.site.co", positions);
        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(List.of(company)));
        return resume;
    }

    @AfterEach
    void clear() {
        testInstance.clear();
    }

    @Test
    void get_retrievesStoredResume() {
        Resume actual = testInstance.get(UUID_DEV_CV);
        Resume expected = devCV();
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
        testInstance.save(NOT_STORED);
        int sizeAfterSave = testInstance.size();
        Assertions.assertEquals(NOT_STORED, testInstance.get("123"));
        Assertions.assertEquals(sizeBeforeSave + 1, sizeAfterSave);
    }

    @Test
    void save_throwsResumeIsAlreadyStoredException() {
        Assertions.assertThrows(ResumeAlreadyStoredException.class, () -> testInstance.save(devCV()));
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
        Assertions.assertThrows(ResumeNotFoundException.class, () -> testInstance.delete(NOT_STORED.getUuid()));
    }

    @Test
    void update_updatesStoredResume() {
        String updatedTitle = "Developer-CV-updated";
        Resume updatedDevCV = devCV();
        String email = "mail@mail.com";
        updatedDevCV.addContact(ContactType.MAIL, email);
        testInstance.update(updatedDevCV);

        Assertions.assertEquals(email, testInstance.get(UUID_DEV_CV).getContact(ContactType.MAIL));
    }

    @Test
    void update_throwsResumeNotFoundException() {
        Assertions.assertThrows(ResumeNotFoundException.class, () -> testInstance.update(NOT_STORED));
    }

    @Test
    void getAll_returnsAllResumes() {
        List<Resume> expected = List.of(adminCV(), devCV(), managerCV());
        List<Resume> actual = testInstance.getAllSorted();
        Assertions.assertEquals(expected, actual);
    }

}

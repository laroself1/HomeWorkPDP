package main;

import model.Resume;
import storage.Storage;
import storage.simple.SimpleArrayStorage;
import storage.sorted.SortedArrayStorage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainArray {
    private static final Logger log = Logger.getLogger("MainArray Logger");
    static Storage sortedStorage = new SortedArrayStorage();
    static Storage simpleStorage = new SimpleArrayStorage();

    public static void main(String[] args) {
        testSortedStorage();
        testSimpleStorage();
    }

    private static void testSortedStorage() {
        Resume r1 = new Resume("aaaaa", "Developer-CV");
        Resume r2 = new Resume("bbbbb", "Manager-CV");
        Resume r3 = new Resume("ccccc", "Admin-CV");

        sortedStorage.save(r1);
        sortedStorage.save(r2);
        sortedStorage.save(r3);

        log.info("--------------Get-----------------");
        Resume resume = sortedStorage.get(r1.getUuid());
        log.log(Level.INFO, "Printing Resume: {0}", resume);

        log.info("--------------Update-----------------");
        r1.setTitle("Updated Title");
        sortedStorage.update(r1);
        Resume updatedResume = sortedStorage.get(r1.getUuid());
        log.log(Level.INFO, "Printing Resume: {0}", updatedResume);

        log.info("--------------Delete-----------------");
        sortedStorage.delete(r1.getUuid());
        Resume deletedResume = sortedStorage.get(r1.getUuid());
        log.log(Level.INFO, "Printing deleted Resume: {0}", deletedResume);

        log.info("--------------Clear-----------------");
        sortedStorage.clear();
        printAll(sortedStorage);
    }

    private static void testSimpleStorage() {
        Resume r1 = new Resume("dghd-67d8-dd", "Developer-CV");
        Resume r2 = new Resume("nbsn-8s74-bd", "Manager-CV");
        Resume r3 = new Resume("kshs-4432-sn", "Admin-CV");

        checkSave(r1, r2, r3);

        checkGet(r1);

        checkUpdate(r1);

        checkDelete(r1);

        checkClear();
    }

    private static Resume get(Resume r1) {
        return simpleStorage.get(r1.getUuid());
    }

    private static void checkSave(Resume r1, Resume r2, Resume r3) {
        log.info("--------------Save-----------------");
        simpleStorage.save(r1);
        simpleStorage.save(r2);
        simpleStorage.save(r3);
        printAll(simpleStorage);
    }

    static void printAll(Storage storage) {
        log.info("Get All");
        for (Resume r : storage.getAll()) {
            log.log(Level.INFO, "Printing Resume: {0}", r);
        }
         log.log(Level.INFO, "Array size: {0}", simpleStorage.size());
    }

    private static void checkClear() {
        log.info("--------------Clear-----------------");
        simpleStorage.clear();
        printAll(simpleStorage);
    }

    private static void checkDelete(Resume r1) {
        log.info("--------------Delete-----------------");
        log.log(Level.INFO, "Updating resume {0}", r1);
        simpleStorage.delete(r1.getUuid());
        printAll(simpleStorage);
    }

    private static void checkUpdate(Resume r1) {
        log.info("--------------Update-----------------");
        log.log(Level.INFO,"Updating resume {0}", r1);
        r1.setTitle("Devops-CV");
        simpleStorage.update(r1);
        log.log(Level.INFO,"Updated resume {0}", get(r1));
    }

    private static void checkGet(Resume r1) {
        log.info("--------------Get-----------------");
        Resume resume = get(r1);
        log.log(Level.INFO, "Printing Resume: {0}", resume);
    }
}

package main;

import model.Resume;
import storage.Storage;
import storage.array.simple.SimpleArrayStorage;
import storage.array.sorted.SortedArrayStorage;
import storage.exception.ResumeNotFoundException;
import storage.list.ArrayListStorage;
import storage.map.MapStorage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainArray {
    private static final Logger log = Logger.getLogger("MainArray Logger");
    private static final Resume r1 = new Resume("aaaaa", "Developer-CV");
    private static final Resume r2 = new Resume("bbbbb", "Manager-CV");
    private static final Resume r3 = new Resume("ccccc", "Admin-CV");

    static Storage simpleStorage = new SimpleArrayStorage();
    static Storage sortedStorage = new SortedArrayStorage();
    static Storage arrayListStorage = new ArrayListStorage();
    static Storage mapStorage = new MapStorage();

    public static void main(String[] args) {
        testStorage(simpleStorage);
        testStorage(sortedStorage);
        testStorage(arrayListStorage);
        testStorage(mapStorage);
    }

    private static void testStorage(Storage testedStorage) {
        logTestedStorage(testedStorage);

        testedStorage.save(r1);
        testedStorage.save(r2);
        testedStorage.save(r3);

        testGet(testedStorage);
        testUpdate(testedStorage);
        testDelete(testedStorage);
        testClear(testedStorage);
    }

    private static void logTestedStorage(Storage testedStorage) {
        log.info(String.format("--------------Testing %s -----------------", testedStorage.getClass().getSimpleName()));
    }

    private static void testClear(Storage testedStorage) {
        log.info("--------------Clear-----------------");
        testedStorage.clear();
        printAll(testedStorage);
    }

    private static void testDelete(Storage testedStorage) {
        log.info("--------------Delete-----------------");
        testedStorage.delete(r1.getUuid());
        try {
            testedStorage.get(r1.getUuid());
        } catch (ResumeNotFoundException e) {
            log.log(Level.INFO, "Printing deleted Resume: {0}", r1);
        }
    }

    private static void testUpdate(Storage testedStorage) {
        log.info("--------------Update-----------------");
        r1.setTitle("Updated Title");
        testedStorage.update(r1);
        Resume updatedResume = testedStorage.get(r1.getUuid());
        log.log(Level.INFO, "Printing Resume: {0}", updatedResume);
    }

    private static void testGet(Storage testedStorage) {
        log.info("--------------Get-----------------");
        Resume resume = testedStorage.get(r1.getUuid());
        log.log(Level.INFO, "Printing Resume: {0}", resume);
    }

    static void printAll(Storage storage) {
        log.info("Get All");
        for (Resume r : storage.getAll()) {
            log.log(Level.INFO, "Printing Resume: {0}", r);
        }
        log.log(Level.INFO, "Array size: {0}", simpleStorage.size());
    }

}

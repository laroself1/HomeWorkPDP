package main;

import model.Resume;
import storage.ArrayStorage;

import java.util.logging.Logger;

public class MainArray {
    private static final Logger log = Logger.getLogger("MainArray Logger");
    static ArrayStorage arrayStorage = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");

        checkSave(r1, r2, r3);
        checkGet(r1);
        checkUpdate(r1);

        log.info(String.format("Get r1: %s", arrayStorage.get(r1.getUuid())));
        log.info(String.format("Size: %d", arrayStorage.size()));

        printAll();

        checkDelete(r1);
        checkClear();

        log.info(String.format("Size: %d", arrayStorage.size()));
    }

    private static void get(Resume r1) {
        arrayStorage.get(r1.getUuid());
    }

    private static void checkSave(Resume r1, Resume r2, Resume r3) {
        arrayStorage.save(r1);
        arrayStorage.save(r2);
        arrayStorage.save(r3);
        printAll();
    }

    static void printAll() {
        log.info("Get All");
        for (Resume r : arrayStorage.getAll()) {
            log.info(r.toString());
        }
    }

    private static void checkClear() {
        arrayStorage.clear();
        printAll();
    }

    private static void checkDelete(Resume r1) {
        arrayStorage.delete(r1.getUuid());
        printAll();
    }

    private static void checkUpdate(Resume r1) {
        r1.setTitle("Exomi");
        arrayStorage.update(r1);
        get(r1);
    }

    private static void checkGet(Resume r1) {
        get(r1);
    }
}

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

        arrayStorage.save(r1);
        arrayStorage.save(r2);
        arrayStorage.save(r3);

        arrayStorage.update(r1);

        log.info("Get r1: " + arrayStorage.get(r1.getUuid()));
        log.info("Size: " + arrayStorage.size());

        printAll();
        arrayStorage.delete(r1.getUuid());
        printAll();
        arrayStorage.clear();
        printAll();

        log.info("Size: " + arrayStorage.size());
    }

    static void printAll() {
        log.info("Get All");
        for (Resume r : arrayStorage.getAll()) {
            log.info(r.toString());
        }
    }
}

package main;

import model.Resume;
import storage.ArrayStorage;

import java.util.logging.Logger;

public class MainArray {
    private static final Logger log = Logger.getLogger("MainArray Logger");
    static ArrayStorage arrayStorage = new ArrayStorage();

    public static void main(String[] args) {
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
        return arrayStorage.get(r1.getUuid());
    }

    private static void checkSave(Resume r1, Resume r2, Resume r3) {
        log.info("--------------Save-----------------");
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
        log.info("--------------Clear-----------------");
        arrayStorage.clear();
        printAll();
    }

    private static void checkDelete(Resume r1) {
        log.info("--------------Delete-----------------");
        log.info(String.format("Updating resume %s", r1));
        arrayStorage.delete(r1.getUuid());
        printAll();
    }

    private static void checkUpdate(Resume r1) {
        log.info("--------------Update-----------------");
        log.info(String.format("Updating resume %s", r1));
        r1.setTitle("Devops-CV");
        arrayStorage.update(r1);
        log.info(String.format("Updated resume %s", get(r1)));
    }

    private static void checkGet(Resume r1) {
        log.info("--------------Get-----------------");
        Resume resume = get(r1);
        log.info(String.format("Got resume %s", get(r1)));
    }
}

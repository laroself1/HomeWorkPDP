package storage.map;

import model.Resume;
import storage.AbstractStorage;
import storage.exception.ResumeAlreadyStoredException;
import storage.exception.ResumeNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void removeAll() {
        storage.clear();
    }

    @Override
    protected void store(Object key, Resume r) {
        storage.put((String) key, r);
    }

    @Override
    protected void renew(Object key, Resume r) {
        storage.put((String) key, r);
    }

    @Override
    protected void remove(Object key) {
        storage.remove(key);
    }

    @Override
    protected Resume find(Object key) {
        return storage.get(key);
    }

    @Override
    protected Resume[] findAll() {
        return storage.values().toArray(new Resume[length()]);
    }

    @Override
    protected int length() {
        return storage.size();
    }

    @Override
    protected Object tryGetNotExistingResumeStoringKey(Resume r) {
        if (storage.containsKey(r.getUuid())) {
            throw new ResumeAlreadyStoredException(r.getUuid());
        }
        return r.getUuid();
    }

    @Override
    protected Object tryGetExistingResumeKey(String uuid) {
        if (!storage.containsKey(uuid)) {
            throw new ResumeNotFoundException(uuid);
        }
        return uuid;
    }
}

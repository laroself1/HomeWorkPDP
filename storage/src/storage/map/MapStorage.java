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
    public void clear() {
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
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isKeyPresent(Object key) {
        return storage.containsKey(key);
    }

    @Override
    protected Object getKey(String uuid) {
        return uuid;
    }
}

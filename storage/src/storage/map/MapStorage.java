package storage.map;

import model.Resume;
import storage.AbstractStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void store(String key, Resume r) {
        storage.put(key, r);
    }

    @Override
    protected void renew(String key, Resume r) {
        storage.put(key, r);
    }

    @Override
    protected void remove(String key) {
        storage.remove(key);
    }

    @Override
    protected Resume find(String key) {
        return storage.get(key);
    }

    @Override
    public List<Resume> copyData() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected boolean isKeyPresent(String key) {
        return storage.containsKey(key);
    }

    @Override
    protected String getKey(String uuid) {
        return uuid;
    }
}

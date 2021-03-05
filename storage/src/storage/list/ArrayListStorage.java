package storage.list;

import model.Resume;
import storage.AbstractStorage;

import java.util.ArrayList;
import java.util.List;

public class ArrayListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected boolean isKeyPresent(Object key) {
        return key != null;
    }

    @Override
    protected Object getKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void store(Object key, Resume r) {
        storage.add(r);
    }

    @Override
    public void renew(Object key, Resume r) {
        storage.set((int) key, r);
    }

    @Override
    public void remove(Object key) {
        storage.remove((int) key);
    }

    @Override
    public Resume find(Object key) {
        return storage.get((int) key);
    }

    @Override
    public List<Resume> copyData() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }

}

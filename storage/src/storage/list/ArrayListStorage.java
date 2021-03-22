package storage.list;

import model.Resume;
import storage.AbstractStorage;

import java.util.ArrayList;
import java.util.List;

public class ArrayListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected boolean isKeyPresent(Integer key) {
        return key != null;
    }

    @Override
    protected Integer getKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    public void store(Integer key, Resume r) {
        storage.add(r);
    }

    @Override
    public void renew(Integer key, Resume r) {
        storage.set(key, r);
    }

    @Override
    public void remove(Integer key) {
        storage.remove((int) key);
    }

    @Override
    public Resume find(Integer key) {
        return storage.get(key);
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

package storage.list;

import model.Resume;
import storage.AbstractStorage;
import storage.exception.ResumeAlreadyStoredException;
import storage.exception.ResumeNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ArrayListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void removeAll() {
        storage.clear();
    }

    @Override
    public void store(Object key, Resume r) {
        storage.add((int) key, r);
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
    public Resume[] findAll() {
        return storage.toArray(new Resume[length()]);
    }

    @Override
    public int length() {
        return this.storage.size();
    }

    @Override
    protected Object tryGetNotExistingResumeStoringKey(Resume r) {
        if (storage.contains(r)) {
            throw new ResumeAlreadyStoredException(r.getUuid());
        }
        return storage.size();
    }

    @Override
    protected Object tryGetExistingResumeKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        throw new ResumeNotFoundException(uuid);
    }
}

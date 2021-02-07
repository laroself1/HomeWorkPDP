package storage;

import model.Resume;

import java.util.Objects;

public abstract class AbstractStorage implements Storage {
    protected static final int NOT_EXISTING_INDEX = -1;

    @Override
    public void clear() {
        removeAll();
    }

    @Override
    public void save(Resume r) {
        Objects.requireNonNull(r);
        Object key = tryGetNotExistingResumeStoringKey(r);
        store(key, r);
    }

    @Override
    public void update(Resume r) {
        Objects.requireNonNull(r);
        Object key = tryGetExistingResumeKey(r.getUuid());
        renew(key, r);
    }

    @Override
    public void delete(String uuid) {
        Objects.requireNonNull(uuid);
        Object key = tryGetExistingResumeKey(uuid);
        remove(key);
    }

    @Override
    public Resume get(String uuid) {
        Objects.requireNonNull(uuid);
        Object key = tryGetExistingResumeKey(uuid);
        return find(key);
    }

    @Override
    public Resume[] getAll() {
        return findAll();
    }

    @Override
    public int size() {
        return length();
    }

    protected abstract void removeAll();

    protected abstract void store(Object key, Resume r);

    protected abstract void renew(Object key, Resume r);

    protected abstract void remove(Object key);

    protected abstract Resume find(Object key);

    protected abstract Resume[] findAll();

    protected abstract int length();

    protected abstract Object tryGetNotExistingResumeStoringKey(Resume r);

    protected abstract Object tryGetExistingResumeKey(String uuid);

}

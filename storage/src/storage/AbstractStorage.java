package storage;

import model.Resume;

import java.util.Objects;

public abstract class AbstractStorage implements Storage {
    protected static final int NOT_EXISTING_INDEX = -1;

    @Override
    public void save(Resume r) {
        Objects.requireNonNull(r);
        Object key = getKeyIfNotExists(r);
        store(key, r);
    }

    @Override
    public void update(Resume r) {
        Objects.requireNonNull(r);
        Object key = getKeyIfExists(r.getUuid());
        renew(key, r);
    }

    @Override
    public void delete(String uuid) {
        Objects.requireNonNull(uuid);
        Object key = getKeyIfExists(uuid);
        remove(key);
    }

    @Override
    public Resume get(String uuid) {
        Objects.requireNonNull(uuid);
        Object key = getKeyIfExists(uuid);
        return find(key);
    }

    protected abstract void store(Object key, Resume r);

    protected abstract void renew(Object key, Resume r);

    protected abstract void remove(Object key);

    protected abstract Resume find(Object key);

    protected abstract Object getKeyIfNotExists(Resume r);

    protected abstract Object getKeyIfExists(String uuid);

}

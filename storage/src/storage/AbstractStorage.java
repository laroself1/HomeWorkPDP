package storage;

import model.Resume;
import storage.exception.ResumeAlreadyStoredException;
import storage.exception.ResumeNotFoundException;

import java.util.Objects;

public abstract class AbstractStorage implements Storage {
    protected static final int NOT_EXISTING_INDEX = -1;

    @Override
    public void save(Resume r) {
        Objects.requireNonNull(r);
        Object key = getKeyIfNotExists(r.getUuid());
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

    private Object getKeyIfNotExists(String uuid) {
        Object key = getKey(uuid);
        if (isKeyPresent(key)) {
            throw new ResumeAlreadyStoredException(uuid);
        }
        return key;
    }

    private Object getKeyIfExists(String uuid) {
        Object key = getKey(uuid);
        if (!isKeyPresent(key)) {
            throw new ResumeNotFoundException(uuid);
        }
        return key;
    }

    protected abstract boolean isKeyPresent(Object key);

    protected abstract Object getKey(String uuid);

    protected abstract void store(Object key, Resume r);

    protected abstract void renew(Object key, Resume r);

    protected abstract void remove(Object key);

    protected abstract Resume find(Object key);
}

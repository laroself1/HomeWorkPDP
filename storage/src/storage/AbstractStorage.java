package storage;

import model.Resume;
import storage.exception.ResumeAlreadyStoredException;
import storage.exception.ResumeNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public abstract class AbstractStorage<K> implements Storage {
    protected static final int NOT_EXISTING_INDEX = -1;

    private static final Comparator<Resume> SORT_BY_NAMES_FIRST = (r1, r2) -> {
        int titlesDifference = 0;
        if (r1.getFullName() != null && r2.getFullName() != null) {
            titlesDifference = r1.getFullName().compareTo(r2.getFullName());
        }
        if (titlesDifference == 0) {
            return r1.getUuid().compareTo(r2.getUuid());
        }
        return titlesDifference;
    };

    @Override
    public void save(Resume r) {
        Objects.requireNonNull(r);
        K key = getKeyIfNotExists(r.getUuid());
        store(key, r);
    }

    @Override
    public void update(Resume r) {
        Objects.requireNonNull(r);
        K key = getKeyIfExists(r.getUuid());
        renew(key, r);
    }

    @Override
    public void delete(String uuid) {
        Objects.requireNonNull(uuid);
        K key = getKeyIfExists(uuid);
        remove(key);
    }

    @Override
    public Resume get(String uuid) {
        Objects.requireNonNull(uuid);
        K key = getKeyIfExists(uuid);
        return find(key);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = copyData();
        resumes.sort(SORT_BY_NAMES_FIRST);
        return resumes;
    }

    private K getKeyIfNotExists(String uuid) {
        K key = getKey(uuid);
        if (isKeyPresent(key)) {
            throw new ResumeAlreadyStoredException(uuid);
        }
        return key;
    }

    private K getKeyIfExists(String uuid) {
        K key = getKey(uuid);
        if (!isKeyPresent(key)) {
            throw new ResumeNotFoundException(uuid);
        }
        return key;
    }

    protected abstract boolean isKeyPresent(K key);

    protected abstract K getKey(String uuid);

    protected abstract void store(K key, Resume r);

    protected abstract void renew(K key, Resume r);

    protected abstract void remove(K key);

    protected abstract List<Resume> copyData();

    protected abstract Resume find(K key);
}

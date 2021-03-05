package storage;

import model.Resume;
import storage.exception.ResumeAlreadyStoredException;
import storage.exception.ResumeNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public abstract class AbstractStorage implements Storage {
    protected static final int NOT_EXISTING_INDEX = -1;

    private static final Comparator<Resume> SORT_BY_NAMES_FIRST = (r1, r2) -> {
        int titlesDifference = 0;
        if (r1.getTitle() != null && r2.getTitle() != null) {
            titlesDifference = r1.getTitle().compareTo(r2.getTitle());
        }
        if (titlesDifference == 0) {
            return r1.getUuid().compareTo(r2.getUuid());
        }
        return titlesDifference;
    };

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

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = copyData();
        resumes.sort(SORT_BY_NAMES_FIRST);
        return resumes;
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

    protected abstract List<Resume> copyData();
}

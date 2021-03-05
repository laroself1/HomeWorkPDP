package storage;

import model.Resume;

public interface Storage {
    void clear();

    void save(Resume r);

    void update(Resume r);

    void delete(String uuid);

    Resume get(String uuid);

    Resume[] getAll();

    int size();
}

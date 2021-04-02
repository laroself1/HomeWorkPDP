package storage.file;

import model.Resume;
import storage.AbstractStorage;
import storage.file.exception.FileCreationException;
import storage.file.exception.FileDeletionException;
import storage.file.exception.FileNotFoundException;
import storage.file.exception.FileUpdateException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File>{
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }


    @Override
    protected boolean isKeyPresent(File key) {
        if (key == null) return false;
        return key.exists();
    }

    @Override
    protected File getKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void store(File key, Resume r) {
        try {
            boolean isCreated = key.createNewFile();
            if (!isCreated) {
                throw new FileCreationException(String.format("File %s was not created", key.getName()));
            }
        } catch (IOException e) {
            throw new FileCreationException(String.format("Couldn't create file %s", key.getName()), e);
        }
        renew(key, r);
    }

    @Override
    protected void renew(File key, Resume r) {
        try {
            write(r, key);
        } catch (IOException e) {
            throw new FileUpdateException(String.format("File %s error", r.getUuid()), e);
        }
    }

    @Override
    protected void remove(File key) {
        boolean isDeleted = key.delete();
        if (!isDeleted) {
            throw new FileDeletionException(String.format("File %s was not deleted", key.getName()));
        }
    }

    @Override
    protected List<Resume> copyData() {
        File[] files = directory.listFiles();
        if (files == null) {
            return Collections.emptyList();
        }
        List<Resume> list = new ArrayList<>();
        for (File file : files) {
            list.add(find(file));
        }
        return list;
    }

    @Override
    protected Resume find(File key) {
        try {
            return read(key);
        } catch (IOException e) {
            throw new FileNotFoundException(key.getName());
        }
    }

    protected abstract Resume read(File file) throws IOException;

    protected abstract Resume write(Resume resume, File file) throws IOException;

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                remove(file);
            }
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            return 0;
        }
        return list.length;
    }
}

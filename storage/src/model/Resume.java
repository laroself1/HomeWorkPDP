package model;

import java.util.Objects;

public class Resume implements Comparable<Resume>{

    private final String uuid;
    private String title;

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public Resume(String uuid, String title) {
        this.uuid = uuid;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public int compareTo(Resume o) {
        return this.uuid.compareTo(o.getUuid());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) && Objects.equals(title, resume.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, title);
    }
}

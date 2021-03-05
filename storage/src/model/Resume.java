package model;

import java.util.Objects;
import java.util.UUID;

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

    public Resume() {
        this.uuid = UUID.randomUUID().toString();
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
    public int hashCode() {
        return Objects.hash(uuid, title);
    }

    @Override
    public int compareTo(Resume o) {
        int titlesDifference = 0;
        if (this.title != null && o.title != null) {
            titlesDifference = this.title.compareTo(o.getTitle());
        }
        if (titlesDifference == 0) {
            return this.uuid.compareTo(o.uuid);
        }
        return titlesDifference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) &&
                Objects.equals(title, resume.title);
    }
}

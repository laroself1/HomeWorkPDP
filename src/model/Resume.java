package model;

public class Resume {

    private final String uuid;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Resume(String uuid) {
        this.uuid = uuid;
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
}

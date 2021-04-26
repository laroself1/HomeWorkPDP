package model;

import java.time.LocalDate;
import java.util.Objects;

public class Position {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String jobTitle;
    private final String description;

    public Position(LocalDate startDate, LocalDate endDate, String jobTitle, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(jobTitle, "title must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.jobTitle = jobTitle;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(startDate, position.startDate) &&
                Objects.equals(endDate, position.endDate) &&
                Objects.equals(jobTitle, position.jobTitle) &&
                Objects.equals(description, position.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, jobTitle, description);
    }

    @Override
    public String toString() {
        return "Position{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", jobTitle='" + jobTitle + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

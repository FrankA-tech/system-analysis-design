package tasks;

import java.util.Objects;

public class Task {
    private final String taskId;
    private String name;
    private String description;

    public Task(String taskId, String name, String description) {
        validateId(taskId);
        validateName(name);
        validateDescription(description);
        this.taskId = taskId;
        this.name = name;
        this.description = description;
    }

    private void validateId(String id) {
        if (id == null || id.length() > 10) {
            throw new IllegalArgumentException("taskId must be non-null and <= 10 chars");
        }
    }

    private void validateName(String name) {
        if (name == null || name.length() > 20) {
            throw new IllegalArgumentException("name must be non-null and <= 20 chars");
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.length() > 50) {
            throw new IllegalArgumentException("description must be non-null and <= 50 chars");
        }
    }

    public String getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Task)) return false;
        Task other = (Task) o;
        return Objects.equals(taskId, other.taskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }
}

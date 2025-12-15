package tasks;

import java.util.HashMap;
import java.util.Map;

public class TaskService {
    private final Map<String, Task> tasks = new HashMap<>();

    public void addTask(Task task) {
        if (task == null) throw new IllegalArgumentException("task cannot be null");
        String id = task.getTaskId();
        if (tasks.containsKey(id)) {
            throw new IllegalArgumentException("duplicate taskId: " + id);
        }
        tasks.put(id, task);
    }

    public void deleteTask(String taskId) {
        if (taskId == null) throw new IllegalArgumentException("taskId cannot be null");
        tasks.remove(taskId);
    }

    public Task getTask(String taskId) {
        if (taskId == null) throw new IllegalArgumentException("taskId cannot be null");
        return tasks.get(taskId);
    }

    public void updateName(String taskId, String newName) {
        Task t = getOrThrow(taskId);
        t.setName(newName);
    }

    public void updateDescription(String taskId, String newDescription) {
        Task t = getOrThrow(taskId);
        t.setDescription(newDescription);
    }

    private Task getOrThrow(String taskId) {
        Task t = getTask(taskId);
        if (t == null) throw new IllegalArgumentException("task not found: " + taskId);
        return t;
    }
}

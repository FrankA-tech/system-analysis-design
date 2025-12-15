package tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    @Test
    void addAndGetTask() {
        TaskService service = new TaskService();
        Task t = new Task("T1", "Name", "Desc");
        service.addTask(t);
        Task fetched = service.getTask("T1");
        assertNotNull(fetched);
        assertEquals("Name", fetched.getName());
    }

    @Test
    void duplicateIdThrows() {
        TaskService service = new TaskService();
        service.addTask(new Task("T1", "Name", "Desc"));
        assertThrows(IllegalArgumentException.class,
            () -> service.addTask(new Task("T1", "Other", "Other")));
    }

    @Test
    void deleteTaskRemovesIt() {
        TaskService service = new TaskService();
        service.addTask(new Task("T1", "Name", "Desc"));
        service.deleteTask("T1");
        assertNull(service.getTask("T1"));
    }

    @Test
    void updateFieldsViaService() {
        TaskService service = new TaskService();
        service.addTask(new Task("T1", "Name", "Desc"));
        service.updateName("T1", "New");
        service.updateDescription("T1", "NewDesc");
        Task t = service.getTask("T1");
        assertEquals("New", t.getName());
        assertEquals("NewDesc", t.getDescription());
    }

    @Test
    void updateNonexistentThrows() {
        TaskService service = new TaskService();
        assertThrows(IllegalArgumentException.class, () -> service.updateName("X", "New"));
    }
}

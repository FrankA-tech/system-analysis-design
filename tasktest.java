package tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    void validTaskConstruction() {
        Task t = new Task("T1", "Name", "Short description");
        assertEquals("T1", t.getTaskId());
        assertEquals("Name", t.getName());
        assertEquals("Short description", t.getDescription());
    }

    @Test
    void idTooLongThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> new Task("ABCDEFGHIJK", "Name", "Desc"));
    }

    @Test
    void nameTooLongThrows() {
        String longName = "A".repeat(21);
        assertThrows(IllegalArgumentException.class,
            () -> new Task("T1", longName, "Desc"));
    }

    @Test
    void descriptionTooLongThrows() {
        String longDesc = "A".repeat(51);
        assertThrows(IllegalArgumentException.class,
            () -> new Task("T1", "Name", longDesc));
    }

    @Test
    void updatesValidate() {
        Task t = new Task("T1", "Name", "Desc");
        t.setName("NewName");
        t.setDescription("NewDesc");
        assertEquals("NewName", t.getName());
        assertEquals("NewDesc", t.getDescription());

        assertThrows(IllegalArgumentException.class, () -> t.setName(null));
        assertThrows(IllegalArgumentException.class, () -> t.setName("A".repeat(21)));
        assertThrows(IllegalArgumentException.class, () -> t.setDescription(null));
        assertThrows(IllegalArgumentException.class, () -> t.setDescription("A".repeat(51)));
    }
}

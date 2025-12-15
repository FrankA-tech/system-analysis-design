package appointments;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Calendar;

public class AppointmentTest {

    private Date futureDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return cal.getTime();
    }

    private Date pastDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        return cal.getTime();
    }

    @Test
    void validAppointmentConstruction() {
        Appointment a = new Appointment("A1", futureDate(), "Checkup");
        assertEquals("A1", a.getAppointmentId());
        assertEquals("Checkup", a.getDescription());
        assertNotNull(a.getAppointmentDate());
    }

    @Test
    void idTooLongThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> new Appointment("ABCDEFGHIJK", futureDate(), "Desc"));
    }

    @Test
    void nullOrPastDateThrows() {
        assertThrows(IllegalArgumentException.class,
            () -> new Appointment("A1", null, "Desc"));
        assertThrows(IllegalArgumentException.class,
            () -> new Appointment("A1", pastDate(), "Desc"));
    }

    @Test
    void descriptionTooLongThrows() {
        String longDesc = "A".repeat(51);
        assertThrows(IllegalArgumentException.class,
            () -> new Appointment("A1", futureDate(), longDesc));
    }

    @Test
    void updatesValidate() {
        Appointment a = new Appointment("A1", futureDate(), "Desc");
        a.setDescription("NewDesc");
        assertEquals("NewDesc", a.getDescription());

        assertThrows(IllegalArgumentException.class, () -> a.setDescription(null));
        assertThrows(IllegalArgumentException.class, () -> a.setDescription("A".repeat(51)));
        assertThrows(IllegalArgumentException.class, () -> a.setAppointmentDate(pastDate()));
    }
}

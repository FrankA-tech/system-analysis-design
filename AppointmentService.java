package appointments;

import java.util.HashMap;
import java.util.Map;

public class AppointmentService {
    private final Map<String, Appointment> appointments = new HashMap<>();

    public void addAppointment(Appointment appointment) {
        if (appointment == null) throw new IllegalArgumentException("appointment cannot be null");
        String id = appointment.getAppointmentId();
        if (appointments.containsKey(id)) {
            throw new IllegalArgumentException("duplicate appointmentId: " + id);
        }
        appointments.put(id, appointment);
    }

    public void deleteAppointment(String appointmentId) {
        if (appointmentId == null) throw new IllegalArgumentException("appointmentId cannot be null");
        appointments.remove(appointmentId);
    }

    public Appointment getAppointment(String appointmentId) {
        if (appointmentId == null) throw new IllegalArgumentException("appointmentId cannot be null");
        return appointments.get(appointmentId);
    }

    public void updateDate(String appointmentId, java.util.Date newDate) {
        Appointment a = getOrThrow(appointmentId);
        a.setAppointmentDate(newDate);
    }

    public void updateDescription(String appointmentId, String newDescription) {
        Appointment a = getOrThrow(appointmentId);
        a.setDescription(newDescription);
    }

    private Appointment getOrThrow(String appointmentId) {
        Appointment a = getAppointment(appointmentId);
        if (a == null) throw new IllegalArgumentException("appointment not found: " + appointmentId);
        return a;
    }
}

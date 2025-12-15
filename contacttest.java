package contacts;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

    @Test
    void validContactConstruction() {
        Contact c = new Contact("12345", "John", "Doe", "1234567890", "123 Main St");
        assertEquals("12345", c.getContactId());
        assertEquals("John", c.getFirstName());
        assertEquals("Doe", c.getLastName());
        assertEquals("1234567890", c.getPhone());
        assertEquals("123 Main St", c.getAddress());
    }

    @Test
    void invalidIdTooLong() {
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("12345678901", "John", "Doe", "1234567890", "123 Main St"));
    }

    @Test
    void nullFirstName() {
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("1", null, "Doe", "1234567890", "123 Main St"));
    }

    @Test
    void firstNameTooLong() {
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("1", "ABCDEFGHIJK", "Doe", "1234567890", "123 Main St"));
    }

    @Test
    void lastNameTooLong() {
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("1", "John", "ABCDEFGHIJK", "1234567890", "123 Main St"));
    }

    @Test
    void phoneMustBeTenDigits() {
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("1", "John", "Doe", "123456789", "123 Main St"));
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("1", "John", "Doe", "abcdefghij", "123 Main St"));
    }

    @Test
    void addressTooLong() {
        String longAddress = "A".repeat(31);
        assertThrows(IllegalArgumentException.class,
            () -> new Contact("1", "John", "Doe", "1234567890", longAddress));
    }

    @Test
    void updateFieldsWorkAndValidate() {
        Contact c = new Contact("1", "John", "Doe", "1234567890", "123 Main St");
        c.setFirstName("Jane");
        c.setLastName("Roe");
        c.setPhone("0987654321");
        c.setAddress("456 Elm Ave");

        assertEquals("Jane", c.getFirstName());
        assertEquals("Roe", c.getLastName());
        assertEquals("0987654321", c.getPhone());
        assertEquals("456 Elm Ave", c.getAddress());

        assertThrows(IllegalArgumentException.class, () -> c.setFirstName(null));
        assertThrows(IllegalArgumentException.class, () -> c.setLastName("ABCDEFGHIJK"));
        assertThrows(IllegalArgumentException.class, () -> c.setPhone("123"));
        assertThrows(IllegalArgumentException.class, () -> c.setAddress("A".repeat(31)));
    }
}

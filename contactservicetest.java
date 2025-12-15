package contacts;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactServiceTest {

    @Test
    void addAndGetContact() {
        ContactService service = new ContactService();
        Contact c = new Contact("1", "John", "Doe", "1234567890", "123 Main St");
        service.addContact(c);

        Contact fetched = service.getContact("1");
        assertNotNull(fetched);
        assertEquals("John", fetched.getFirstName());
    }

    @Test
    void addDuplicateIdThrows() {
        ContactService service = new ContactService();
        service.addContact(new Contact("1", "John", "Doe", "1234567890", "123 Main St"));
        assertThrows(IllegalArgumentException.class,
            () -> service.addContact(new Contact("1", "Jane", "Roe", "0987654321", "456 Elm Ave")));
    }

    @Test
    void deleteContactRemovesIt() {
        ContactService service = new ContactService();
        service.addContact(new Contact("1", "John", "Doe", "1234567890", "123 Main St"));
        service.deleteContact("1");
        assertNull(service.getContact("1"));
    }

    @Test
    void updateFieldsViaService() {
        ContactService service = new ContactService();
        service.addContact(new Contact("1", "John", "Doe", "1234567890", "123 Main St"));

        service.updateFirstName("1", "Jane");
        service.updateLastName("1", "Roe");
        service.updatePhone("1", "0987654321");
        service.updateAddress("1", "456 Elm Ave");

        Contact c = service.getContact("1");
        assertEquals("Jane", c.getFirstName());
        assertEquals("Roe", c.getLastName());
        assertEquals("0987654321", c.getPhone());
        assertEquals("456 Elm Ave", c.getAddress());
    }

    @Test
    void updateNonexistentThrows() {
        ContactService service = new ContactService();
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("X", "Jane"));
    }
}

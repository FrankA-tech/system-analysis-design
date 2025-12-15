ackage contacts;

import java.util.Objects;

public class Contact {
    private final String contactId;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        validateId(contactId);
        validateFirstName(firstName);
        validateLastName(lastName);
        validatePhone(phone);
        validateAddress(address);

        this.contactId = contactId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    private void validateId(String id) {
        if (id == null || id.length() > 10) {
            throw new IllegalArgumentException("contactId must be non-null and <= 10 chars");
        }
    }

    private void validateFirstName(String firstName) {
        if (firstName == null || firstName.length() > 10) {
            throw new IllegalArgumentException("firstName must be non-null and <= 10 chars");
        }
    }

    private void validateLastName(String lastName) {
        if (lastName == null || lastName.length() > 10) {
            throw new IllegalArgumentException("lastName must be non-null and <= 10 chars");
        }
    }

    private void validatePhone(String phone) {
        if (phone == null || phone.length() != 10 || !phone.matches("\\d{10}")) {
            throw new IllegalArgumentException("phone must be exactly 10 digits");
        }
    }

    private void validateAddress(String address) {
        if (address == null || address.length() > 30) {
            throw new IllegalArgumentException("address must be non-null and <= 30 chars");
        }
    }

    public String getContactId() {
        return contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        validateFirstName(firstName);
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        validateLastName(lastName);
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        validatePhone(phone);
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        validateAddress(address);
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Contact)) return false;
        Contact other = (Contact) o;
        return Objects.equals(contactId, other.contactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactId);
    }
}

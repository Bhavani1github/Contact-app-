package contactapp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContactManager {
    private List<Contact> contacts;

    public ContactManager() {
        contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public Optional<Contact> findContactByName(String name) {
        return contacts.stream().filter(contact -> contact.getName().equalsIgnoreCase(name)).findFirst();
    }

    public void updateContact(String name, Contact updatedContact) {
        findContactByName(name).ifPresent(contact -> {
            contact.setName(updatedContact.getName());
            contact.setPhoneNumber(updatedContact.getPhoneNumber());
            contact.setEmail(updatedContact.getEmail());
        });
    }

    public void deleteContact(String name) {
        contacts.removeIf(contact -> contact.getName().equalsIgnoreCase(name));
    }
}


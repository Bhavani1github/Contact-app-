package contactapp;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static ContactManager contactManager;
    private static Scanner scanner;

    public static void main(String[] args) {
        contactManager = new ContactManager();
        scanner = new Scanner(System.in);

        // Load contacts from file
        try {
            List<Contact> contacts = FileHandler.loadContacts();
            contacts.forEach(contactManager::addContact);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No contacts found, starting with an empty list.");
        }

        while (true) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    viewContacts();
                    break;
                case 3:
                    updateContact();
                    break;
                case 4:
                    deleteContact();
                    break;
                case 5:
                    saveAndExit();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Contact Management System");
        System.out.println("1. Add Contact");
        System.out.println("2. View Contacts");
        System.out.println("3. Update Contact");
        System.out.println("4. Delete Contact");
        System.out.println("5. Save and Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addContact() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        Contact contact = new Contact(name, phoneNumber, email);
        contactManager.addContact(contact);
        System.out.println("Contact added successfully.");
    }

    private static void viewContacts() {
        List<Contact> contacts = contactManager.getAllContacts();
        contacts.forEach(System.out::println);
    }

    private static void updateContact() {
        System.out.print("Enter the name of the contact to update: ");
        String name = scanner.nextLine();
        Optional<Contact> contactOpt = contactManager.findContactByName(name);

        if (contactOpt.isPresent()) {
            System.out.print("Enter new name: ");
            String newName = scanner.nextLine();
            System.out.print("Enter new phone number: ");
            String newPhoneNumber = scanner.nextLine();
            System.out.print("Enter new email: ");
            String newEmail = scanner.nextLine();

            Contact updatedContact = new Contact(newName, newPhoneNumber, newEmail);
            contactManager.updateContact(name, updatedContact);
            System.out.println("Contact updated successfully.");
        } else {
            System.out.println("Contact not found.");
        }
    }

    private static void deleteContact() {
        System.out.print("Enter the name of the contact to delete: ");
        String name = scanner.nextLine();
        contactManager.deleteContact(name);
        System.out.println("Contact deleted successfully.");
    }

    private static void saveAndExit() {
        try {
            FileHandler.saveContacts(contactManager.getAllContacts());
            System.out.println("Contacts saved successfully. Exiting...");
        } catch (IOException e) {
            System.out.println("Failed to save contacts.");
        }
        System.exit(0);
    }
}

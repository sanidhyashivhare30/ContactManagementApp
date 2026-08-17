import java.util.List;
import java.util.Scanner;

public class ContactManagementApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ContactManager manager = new ContactManager();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("         CONTACT MANAGEMENT APP");
        System.out.println("========================================");

        while (true) {
            displayMenu();
            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1 -> addContact();
                case 2 -> viewContacts();
                case 3 -> searchContact();
                case 4 -> updateContact();
                case 5 -> deleteContact();
                case 6 -> {
                    System.out.println("\nThank you for using Contact Management App!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("\nInvalid choice. Please select 1 to 6.");
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private static void displayMenu() {
        System.out.println("\n--------------- MENU ----------------");
        System.out.println("1. Add Contact");
        System.out.println("2. View All Contacts");
        System.out.println("3. Search Contact");
        System.out.println("4. Update Contact");
        System.out.println("5. Delete Contact");
        System.out.println("6. Exit");
        System.out.println("--------------------------------------");
    }

    private static void addContact() {
        System.out.println("\n--- Add Contact ---");

        int id = readPositiveInt("Enter contact ID: ");

        if (manager.findById(id) != null) {
            System.out.println("A contact with this ID already exists.");
            return;
        }

        String name = readNonEmpty("Enter name: ");
        String phone = readPhone("Enter phone number: ");
        String email = readEmail("Enter email: ");

        Contact contact = new Contact(id, name, phone, email);

        if (manager.addContact(contact)) {
            System.out.println("Contact added successfully!");
        } else {
            System.out.println("Unable to add contact.");
        }
    }

    private static void viewContacts() {
        System.out.println("\n--- All Contacts ---");

        if (manager.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        List<Contact> contacts = manager.getAllContacts();

        for (Contact contact : contacts) {
            System.out.println(contact);
        }
    }

    private static void searchContact() {
        System.out.println("\n--- Search Contact ---");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");

        int choice = readInt("Enter choice: ");

        if (choice == 1) {
            int id = readPositiveInt("Enter contact ID: ");
            Contact contact = manager.findById(id);

            if (contact != null) {
                System.out.println("Contact found:");
                System.out.println(contact);
            } else {
                System.out.println("Contact not found.");
            }
        } else if (choice == 2) {
            String name = readNonEmpty("Enter name to search: ");
            List<Contact> results = manager.searchByName(name);

            if (results.isEmpty()) {
                System.out.println("No matching contacts found.");
            } else {
                System.out.println("Matching contacts:");
                for (Contact contact : results) {
                    System.out.println(contact);
                }
            }
        } else {
            System.out.println("Invalid search option.");
        }
    }

    private static void updateContact() {
        System.out.println("\n--- Update Contact ---");

        int id = readPositiveInt("Enter contact ID: ");
        Contact existing = manager.findById(id);

        if (existing == null) {
            System.out.println("Contact not found.");
            return;
        }

        System.out.println("Current details:");
        System.out.println(existing);

        String name = readNonEmpty("Enter new name: ");
        String phone = readPhone("Enter new phone number: ");
        String email = readEmail("Enter new email: ");

        if (manager.updateContact(id, name, phone, email)) {
            System.out.println("Contact updated successfully!");
        }
    }

    private static void deleteContact() {
        System.out.println("\n--- Delete Contact ---");

        int id = readPositiveInt("Enter contact ID: ");
        Contact contact = manager.findById(id);

        if (contact == null) {
            System.out.println("Contact not found.");
            return;
        }

        System.out.println("Contact to delete:");
        System.out.println(contact);
        System.out.print("Are you sure? (yes/no): ");

        String confirmation = scanner.nextLine().trim();

        if (confirmation.equalsIgnoreCase("yes")) {
            if (manager.deleteContact(id)) {
                System.out.println("Contact deleted successfully!");
            }
        } else {
            System.out.println("Delete operation cancelled.");
        }
    }

    private static int readPositiveInt(String message) {
        while (true) {
            int value = readInt(message);

            if (value > 0) {
                return value;
            }

            System.out.println("Please enter a positive number.");
        }
    }

    private static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    private static String readNonEmpty(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("This field cannot be empty.");
        }
    }

    private static String readPhone(String message) {
        while (true) {
            System.out.print(message);
            String phone = scanner.nextLine().trim();

            if (phone.matches("\\d{10}")) {
                return phone;
            }

            System.out.println("Please enter a valid 10-digit phone number.");
        }
    }

    private static String readEmail(String message) {
        while (true) {
            System.out.print(message);
            String email = scanner.nextLine().trim();

            if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                return email;
            }

            System.out.println("Please enter a valid email address.");
        }
    }
}

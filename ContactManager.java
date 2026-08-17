import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    private final List<Contact> contacts = new ArrayList<>();

    public boolean addContact(Contact contact) {
        if (findById(contact.getId()) != null) {
            return false;
        }
        contacts.add(contact);
        return true;
    }

    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }

    public Contact findById(int id) {
        for (Contact contact : contacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    public List<Contact> searchByName(String name) {
        List<Contact> results = new ArrayList<>();
        String search = name.toLowerCase();

        for (Contact contact : contacts) {
            if (contact.getName().toLowerCase().contains(search)) {
                results.add(contact);
            }
        }

        return results;
    }

    public boolean updateContact(int id, String name, String phone, String email) {
        Contact contact = findById(id);

        if (contact == null) {
            return false;
        }

        contact.setName(name);
        contact.setPhone(phone);
        contact.setEmail(email);
        return true;
    }

    public boolean deleteContact(int id) {
        Contact contact = findById(id);

        if (contact == null) {
            return false;
        }

        contacts.remove(contact);
        return true;
    }

    public boolean isEmpty() {
        return contacts.isEmpty();
    }
}

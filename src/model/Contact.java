package model;

/**
 * @Author Jack Compton
 */

public class Contact {

    private int contactId;
    private String contactName;
    private String email;

    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    public int getContactId() {
        return contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {   // overrides default Object Class behavior when added to a ComboBox
        return ("ID [" + getContactId() + "] " + getContactName());
    }
}

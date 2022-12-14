package model;

/**
 * @Author Jack Compton
 * Contact object used in View/Edit appointment, and is featured in a report to discern specific schedules for individual contacts
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

    /**
     *
     * overrides default Object Class behavior when added to a ComboBox
     */
    @Override
    public String toString() {
        return ("ID [" + getContactId() + "] " + getContactName());
    }
}

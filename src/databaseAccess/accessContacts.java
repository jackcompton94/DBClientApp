package databaseAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import java.sql.*;

/**
 * @Author Jack Compton
 */

public abstract class accessContacts {

    /**
     * provides all available rows in the contacts table
     *
     * @return
     * contactList
     */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> contactList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM contacts";                         // query to pull all records from contacts
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);   // assigns the database connection whilst using the query above to ps
            ResultSet rs = ps.executeQuery();                               // stores the result in rs

            while(rs.next()){                                               // sorting through rs to assign fields to values within the application from the database
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_name");
                String email = rs.getString("Email");
                Contact c = new Contact(contactId, contactName, email);
                contactList.add(c);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactList;
    }
}

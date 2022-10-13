package controller;

import databaseAccess.accessContacts;
import databaseAccess.accessCustomers;
import databaseAccess.accessUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.converter.LocalDateTimeStringConverter;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import org.w3c.dom.Text;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;

public class EditAppointment implements Initializable {

    @FXML
    private TextField appointmentId;

    @FXML
    private TextField title;

    @FXML
    private TextField description;

    @FXML
    private TextField location;

    @FXML
    private TextField type;

    @FXML
    private DatePicker date;

    @FXML
    private ComboBox<Integer> startHour;

    @FXML
    private ComboBox<String> startMinute;

    @FXML
    private ComboBox<Integer> endHour;

    @FXML
    private ComboBox<String> endMinute;

    @FXML
    private ComboBox<Contact> contact;

    @FXML
    private ComboBox<Customer> customer;

    @FXML
    private ComboBox<User> user;

    public void initTime() {
        ObservableList<Integer> hourTimes = FXCollections.observableArrayList();
        for (int i = 1; i <= 24; i++) {
            hourTimes.add(i);
        }
        startHour.setItems(hourTimes);
        endHour.setItems(hourTimes);

        ObservableList<String> minuteTimes = FXCollections.observableArrayList("00", "15", "30", "45");
        startMinute.setItems(minuteTimes);
        endMinute.setItems(minuteTimes);
    }

    public void sendAppointment(Appointment appointment) {
        appointmentId.setText(String.valueOf(appointment.getAppointmentId()));
        title.setText(appointment.getTitle());
        description.setText(appointment.getTitle());
        location.setText(appointment.getLocation());
        type.setText(appointment.getType());

        //date

        //startHour
        //startMinute
        //endHour
        //endMinute

        //contactID
        int contactId = appointment.getContactId();
        for (Contact c : accessContacts.getAllContacts()) {
            if (contactId == c.getContactId()){
                contact.setValue(c);
            }
        }

        //customerID
        int customerId = appointment.getCustomerId();
        for (Customer c : accessCustomers.getAllCustomers()) {
            if (customerId == c.getCustomerId()) {
                customer.setValue(c);
            }
        }

        //userID
        int userId = appointment.getUserId();
        for (User u : accessUsers.getAllUsers()) {
            if (userId == u.getUserId()) {
                user.setValue(u);
            }
        }

    }

    public void save(ActionEvent actionEvent) {
    }

    public void cancel(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initializes Contacts, Customers, and Users
        contact.setItems(accessContacts.getAllContacts());
        customer.setItems(accessCustomers.getAllCustomers());
        user.setItems(accessUsers.getAllUsers());

        // initializes Combo Boxes
        initTime();
    }
}

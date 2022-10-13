package controller;

import databaseAccess.accessContacts;
import databaseAccess.accessCustomers;
import databaseAccess.accessUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddAppointments implements Initializable {

    @FXML
    public Label successLabel;

    @FXML
    public TextField title;

    @FXML
    public TextField description;

    @FXML
    public TextField location;

    @FXML
    public TextField type;

    @FXML
    public DatePicker date;

    @FXML
    public ComboBox<String> startHour;

    @FXML
    public ComboBox<String> startMinute;

    @FXML
    public ComboBox<String> endHour;

    @FXML
    public ComboBox<String> endMinute;

    @FXML
    public ComboBox<Contact> contact;

    @FXML
    public ComboBox<Customer> customer;

    @FXML
    public ComboBox<User> user;

    // startHour/Minute & endHour/Minute ComboBox setup


    public void save(ActionEvent actionEvent) throws SQLException {
        String titleText = title.getText();
        String descriptionText = description.getText();
        String locationText = location.getText();
        String typeText = type.getText();

        //TODO: Date
        LocalDate dateSelection = date.getValue();

        //TODO: Start
        String sH = startHour.getValue();
        String sM = startMinute.getValue();

        //TODO: End
        String endHourValue = endHour.getValue();
        String endMinuteValue = endMinute.getValue();

        //TODO: dateTimeSelection test


        // ContactID, CustomerID, UserID
        Contact selectedContact = contact.getValue();
        int contactId = selectedContact.getContactId();

        Customer selectedCustomer = customer.getValue();
        int customerId = selectedCustomer.getCustomerId();

        User selectedUser = user.getValue();
        int userId = selectedUser.getUserId();

        // accessAppointments.insert(titleText, descriptionText, locationText, typeText, startDateTime, endDateTime, customerId, userId, contactId);
        successLabel.setVisible(true);
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        successLabel.setVisible(false);

        //initializes Contacts, Customers, and Users
        contact.setItems(accessContacts.getAllContacts());
        customer.setItems(accessCustomers.getAllCustomers());
        user.setItems(accessUsers.getAllUsers());


        //TODO: initialize Combo Boxes
    }
}

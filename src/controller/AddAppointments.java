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
    public ComboBox<Integer> startHour;

    @FXML
    public ComboBox<String> startMinute;

    @FXML
    public ComboBox<Integer> endHour;

    @FXML
    public ComboBox<String> endMinute;

    @FXML
    public ComboBox<Contact> contact;

    @FXML
    public ComboBox<Customer> customer;

    @FXML
    public ComboBox<User> user;


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

    public void save(ActionEvent actionEvent) throws SQLException {
        String titleText = title.getText();
        String descriptionText = description.getText();
        String locationText = location.getText();
        String typeText = type.getText();
        LocalDate dateSelection = date.getValue();

        //TODO: Start + Date
        LocalTime startTime = LocalTime.of(startHour.getValue(), Integer.valueOf(startMinute.getValue()));

        //TODO: End + Date
        LocalTime endTime = LocalTime.of(endHour.getValue(), Integer.valueOf(endMinute.getValue()));

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

        // initializes Combo Boxes
        initTime();
    }
}

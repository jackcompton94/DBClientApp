package controller;

import databaseAccess.accessAppointments;
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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AddAppointments implements Initializable {

    @FXML
    private Label successLabel;

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

    public void save(ActionEvent actionEvent) throws SQLException {

        // TODO: define business hours

        try {
            String titleText = title.getText();
            String descriptionText = description.getText();
            String locationText = location.getText();
            String typeText = type.getText();

            LocalDate dateSelection = date.getValue();                                                                      // Date

            LocalTime startTime = LocalTime.of(startHour.getValue(), Integer.parseInt(startMinute.getValue()), 00);
            LocalDateTime startDateTime = LocalDateTime.of(dateSelection, startTime);                                       // Start + Date

            LocalTime endTime = LocalTime.of(endHour.getValue(), Integer.parseInt(endMinute.getValue()), 00);
            LocalDateTime endDateTime = LocalDateTime.of(dateSelection, endTime);                                           // End + Date

            LocalDateTime createDate = LocalDateTime.now();
            String createdBy = User.currentUser;
            LocalDateTime lastUpdate = LocalDateTime.now();
            String lastUpdatedBy = User.currentUser;

            Contact selectedContact = contact.getValue();                                                                   // ContactID, CustomerID, UserID
            int contactId = selectedContact.getContactId();

            Customer selectedCustomer = customer.getValue();
            int customerId = selectedCustomer.getCustomerId();

            User selectedUser = user.getValue();
            int userId = selectedUser.getUserId();

            if (titleText.isBlank() || descriptionText.isBlank() || locationText.isBlank() || typeText.isBlank()) {
                Alert missingInfo = new Alert(Alert.AlertType.ERROR);
                missingInfo.setTitle("Format Error");
                missingInfo.setContentText("Unable to save appointment. Please enter missing information.");
                missingInfo.showAndWait();
            }

            else if (startDateTime.isAfter(endDateTime) || startDateTime.isEqual(endDateTime)) {
                Alert timeError = new Alert(Alert.AlertType.ERROR);
                timeError.setTitle("Format Error");
                timeError.setContentText("Unable to save appointment. Please confirm that your Start and End times are correct.");
                timeError.showAndWait();
            }

            else if (startDateTime.isBefore(LocalDateTime.now())) {
                Alert dateError = new Alert(Alert.AlertType.ERROR);
                dateError.setTitle("Format Error");
                dateError.setContentText("Unable to save appointment. The Start Time cannot be in the past.");
                dateError.showAndWait();
            }
            else {
                accessAppointments.insert(titleText, descriptionText, locationText, typeText, startDateTime, endDateTime, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
                successLabel.setVisible(true);
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Format Error");
            alert.setContentText("Unable to save appointment. Please enter missing information.");
            alert.showAndWait();
        }
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

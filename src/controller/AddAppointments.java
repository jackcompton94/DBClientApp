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
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.time.LocalDateTime;
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
    public ComboBox startHour;

    @FXML
    public ComboBox startMinute;

    @FXML
    public ComboBox endHour;

    @FXML
    public ComboBox endMinute;

    @FXML
    public ComboBox<Contact> contact;

    @FXML
    public ComboBox<Customer> customer;

    @FXML
    public ComboBox<User> user;

    // start & end ComboBox setup
    ObservableList<String> hourSelections = FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24");
    ObservableList<String> minuteSelections = FXCollections.observableArrayList("00","15","30","45");

    public void cancel(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void save(ActionEvent actionEvent) throws SQLException {

        // try
        String titleText = title.getText();
        String descriptionText = description.getText();
        String locationText = location.getText();
        String typeText = type.getText();

        // TODO: Date, Start, End
        String dateSelection = date.getEditor().getText();
        String startTime = startHour.getSelectionModel().toString() + ":" + startMinute.getSelectionModel().toString();
        String endTime = endHour.getSelectionModel().toString() + ":" + endMinute.getSelectionModel().toString();

        String startDateTime = dateSelection + " " + startTime;
        String endDateTime = dateSelection + " " + endTime;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        successLabel.setVisible(false);

        //initializes Contacts, Customers, and Users
        contact.setItems(accessContacts.getAllContacts());
        customer.setItems(accessCustomers.getAllCustomers());
        user.setItems(accessUsers.getAllUsers());

        //initializes Time
        startHour.setItems(hourSelections);
        endHour.setItems(hourSelections);
        startMinute.setItems(minuteSelections);
        endMinute.setItems(minuteSelections);

    }
}

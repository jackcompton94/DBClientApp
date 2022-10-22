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
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static java.time.ZoneOffset.UTC;

/**
 * @Author Jack Compton
 */

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

    /**
     * initializes the combo boxes used to capture the desired start and end time for new appointments
     *
     */
    public void initTime() {
        ObservableList<Integer> hourTimes = FXCollections.observableArrayList();
        for (int i = 0; i <= 23; i++) {
            hourTimes.add(i);
        }
        startHour.setItems(hourTimes);
        endHour.setItems(hourTimes);

        ObservableList<String> minuteTimes = FXCollections.observableArrayList("00", "15", "30", "45");
        startMinute.setItems(minuteTimes);
        endMinute.setItems(minuteTimes);
    }

    public void save(ActionEvent actionEvent) throws SQLException {

        try {
            String titleText = title.getText();
            String descriptionText = description.getText();
            String locationText = location.getText();
            String typeText = type.getText();

            // Date
            LocalDate dateSelection = date.getValue();

            // Start + Date
            LocalTime startTime = LocalTime.of(startHour.getValue(), Integer.parseInt(startMinute.getValue()), 00);
            LocalDateTime startDateTime = LocalDateTime.of(dateSelection, startTime);
            ZonedDateTime startUTC = ZonedDateTime.of(startDateTime, UTC);
            LocalDateTime finalStart = startUTC.toLocalDateTime();

            // End + Date
            LocalTime endTime = LocalTime.of(endHour.getValue(), Integer.parseInt(endMinute.getValue()), 00);
            LocalDateTime endDateTime = LocalDateTime.of(dateSelection, endTime);
            ZonedDateTime endUTC = ZonedDateTime.of(endDateTime, UTC);
            LocalDateTime finalEnd = endUTC.toLocalDateTime();

            LocalDateTime createDate = LocalDateTime.now();
            String createdBy = User.currentUser;
            LocalDateTime lastUpdate = LocalDateTime.now();
            String lastUpdatedBy = User.currentUser;

            // ContactID, CustomerID, UserID
            Contact selectedContact = contact.getValue();
            int contactId = selectedContact.getContactId();

            Customer selectedCustomer = customer.getValue();
            int customerId = selectedCustomer.getCustomerId();

            User selectedUser = user.getValue();
            int userId = selectedUser.getUserId();

            // define business hours
            ZoneId est = ZoneId.of("America/New_York");
            LocalTime ltOpen = LocalTime.of(8,0,0);
            LocalTime ltClose = LocalTime.of(22,0,0);

            // convert appointment time selections to current user time zone
            ZonedDateTime zdtStart = ZonedDateTime.of(startDateTime, ZoneId.systemDefault());
            ZonedDateTime zdtEnd = ZonedDateTime.of(endDateTime, ZoneId.systemDefault());

            // assign converted appointment times to ldt for evaluation of EST business hours
            LocalDateTime ldtOpen = LocalDateTime.of(LocalDate.from(zdtStart), ltOpen);
            LocalDateTime ldtClose = LocalDateTime.of(LocalDate.from(zdtEnd), ltClose);

            ZonedDateTime open = ZonedDateTime.of(ldtOpen, est);
            ZonedDateTime close = ZonedDateTime.of(ldtClose, est);

            // overlapping appointment checker
            for (Appointment a : accessAppointments.getAllAppointments()) {
                if (a.getCustomerId() == customerId) {
                    if (a.getStart().isBefore(startDateTime) && a.getEnd().isAfter(endDateTime) || a.getStart().isEqual(startDateTime) || a.getEnd().isEqual(endDateTime)) {
                        Alert timingError = new Alert(Alert.AlertType.ERROR);
                        timingError.setTitle("Timing Error");
                        timingError.setContentText("Unable to save appointment. \nThe selected appointment time overlaps with appointment ID: " + a.getAppointmentId());
                        timingError.showAndWait();
                        return;
                    }
                    else if (a.getStart().isBefore(startDateTime) && a.getEnd().isAfter(startDateTime) || a.getStart().isBefore(endDateTime) && a.getEnd().isAfter(endDateTime)) {
                        Alert timingError = new Alert(Alert.AlertType.ERROR);
                        timingError.setTitle("Timing Error");
                        timingError.setContentText("Unable to save appointment. \nThe selected appointment time overlaps with appointment ID: " + a.getAppointmentId());
                        timingError.showAndWait();
                        return;
                    }
                    else if (a.getStart().isAfter(startDateTime) && a.getEnd().isBefore(endDateTime)) {
                        Alert timingError = new Alert(Alert.AlertType.ERROR);
                        timingError.setTitle("Timing Error");
                        timingError.setContentText("Unable to save appointment. \nThe selected appointment time overlaps with appointment ID: " + a.getAppointmentId());
                        timingError.showAndWait();
                        return;
                    }
                }
            }

                if (titleText.isBlank() || descriptionText.isBlank() || locationText.isBlank() || typeText.isBlank()) {
                    Alert missingInfo = new Alert(Alert.AlertType.ERROR);
                    missingInfo.setTitle("Format Error");
                    missingInfo.setContentText("Unable to save appointment. Please enter missing information.");
                    missingInfo.showAndWait();
                } else if (startDateTime.isAfter(endDateTime) || startDateTime.isEqual(endDateTime)) {
                    Alert timeError = new Alert(Alert.AlertType.ERROR);
                    timeError.setTitle("Format Error");
                    timeError.setContentText("Unable to save appointment. Please confirm that your Start and End times are correct.");
                    timeError.showAndWait();
                } else if (startDateTime.isBefore(LocalDateTime.now())) {
                    Alert dateError = new Alert(Alert.AlertType.ERROR);
                    dateError.setTitle("Format Error");
                    dateError.setContentText("Unable to save appointment. The Start Time cannot be in the past.");
                    dateError.showAndWait();
                }
                // business hour validation (8am - 10pm including weekends)
                else if (zdtStart.isBefore(open) || zdtEnd.isAfter(close) || zdtStart.isBefore(open) || zdtEnd.isAfter(close)) {
                    Alert timingError = new Alert(Alert.AlertType.ERROR);
                    timingError.setTitle("Timing Error");
                    timingError.setContentText("Unable to save appointment. Appointment time must be during business hours 8:00 - 22:00 EST");
                    timingError.showAndWait();
                } else {
                    accessAppointments.insert(titleText, descriptionText, locationText, typeText, finalStart, finalEnd, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
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
        stage.centerOnScreen();
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

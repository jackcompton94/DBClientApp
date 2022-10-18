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
import javafx.util.converter.LocalDateTimeStringConverter;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Date;
import java.util.Optional;
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
        for (int i = 0; i <= 23; i++) {
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
        LocalDate d = appointment.getStart().toLocalDate();
        date.setValue(d);

        //startHour / startMinute
        LocalTime start = appointment.getStart().toLocalTime();
        int sHour = start.getHour();
        startHour.setValue(sHour);
        String sMinute = String.valueOf(start.getMinute());
        startMinute.setValue(sMinute);
        if (sMinute.contentEquals("0")) {                   // used to format Time correctly with '00' instead of '0'
            startMinute.setValue("00");
        }
        else {
            startMinute.setValue(sMinute);
        }

        //endHour
        //endMinute
        LocalTime end = appointment.getEnd().toLocalTime();
        int eHour = end.getHour();
        endHour.setValue(eHour);
        String eMinute = String.valueOf(end.getMinute());
        if (eMinute.contentEquals("0")) {                   // used to format Time correctly with '00' instead of '0'
            endMinute.setValue("00");
        }
        else {
            endMinute.setValue(eMinute);
        }

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

    public void save(ActionEvent actionEvent) throws IOException, SQLException {

        try {
            int appointmentIdText = Integer.parseInt(appointmentId.getText());
            String titleText = title.getText();
            String descriptionText = description.getText();
            String locationText = location.getText();
            String typeText = type.getText();

            // Date
            LocalDate dateSelection = date.getValue();

            // Start + Date
            LocalTime startTime = LocalTime.of(startHour.getValue(), Integer.parseInt(startMinute.getValue()), 0);
            LocalDateTime startDateTime = LocalDateTime.of(dateSelection, startTime);

            // End + Date
            LocalTime endTime = LocalTime.of(endHour.getValue(), Integer.parseInt(endMinute.getValue()), 0);
            LocalDateTime endDateTime = LocalDateTime.of(dateSelection, endTime);

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

            // clears previously set Start/End times for current appointment
            for (Appointment a : accessAppointments.getAllAppointments()) {
                if (a.getAppointmentId() == appointmentIdText) {
                    a.setStart(null);
                    a.setEnd(null);
                    continue;
                }

                // overlapping appointment checker
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

            // business hour validation (8am - 10pm including weekends)
            else if (zdtStart.isBefore(open)|| zdtEnd.isAfter(close) || zdtStart.isBefore(open) || zdtEnd.isAfter(close)) {
                Alert timingError = new Alert(Alert.AlertType.ERROR);
                timingError.setTitle("Timing Error");
                timingError.setContentText("Unable to save appointment. Appointment time must be during business hours 8:00 - 22:00 EST");
                timingError.showAndWait();
            }

            else {
                accessAppointments.update(titleText, descriptionText, locationText, typeText, startDateTime, endDateTime, lastUpdate, lastUpdatedBy, customerId, userId, contactId, appointmentIdText);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully updated appointment!");
                Optional<ButtonType> result = alert.showAndWait();

                // after update - takes user back to ViewAppointments
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointments.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
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
        //initializes Contacts, Customers, and Users
        contact.setItems(accessContacts.getAllContacts());
        customer.setItems(accessCustomers.getAllCustomers());
        user.setItems(accessUsers.getAllUsers());

        // initializes Combo Boxes
        initTime();
    }
}

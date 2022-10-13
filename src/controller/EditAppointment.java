package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class EditAppointment implements Initializable {

    @FXML
    public TextField appointmentId;

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

    public void sendAppointment(Appointment selectedItem) {
        appointmentId.setText(String.valueOf(Appointment.getAppointmentId()));
        title.setText(Appointment.getTitle());



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void save(ActionEvent actionEvent) {
    }

    public void cancel(ActionEvent actionEvent) {
    }
}

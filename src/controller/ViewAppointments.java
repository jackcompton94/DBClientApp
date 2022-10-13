package controller;

import databaseAccess.accessAppointments;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;

public class ViewAppointments implements Initializable {

    @FXML
    public TableView<Appointment> appointmentTableView;

    @FXML
    public TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    public TableColumn<Appointment, String> titleCol;

    @FXML
    public TableColumn<Appointment, String> descriptionCol;

    @FXML
    public TableColumn<Appointment, String> locationCol;

    @FXML
    public TableColumn<Appointment, String> typeCol;

    @FXML
    public TableColumn<Appointment, Date> startCol;

    @FXML
    public TableColumn<Appointment, Date> endCol;

    @FXML
    public TableColumn<Appointment, Date> createDateCol;

    @FXML
    public TableColumn<Appointment, Timestamp> lastUpdateCol;

    @FXML
    public TableColumn<Appointment, String> lastUpdatedByCol;

    @FXML
    public TableColumn<Appointment, Integer> customerIdCol;

    @FXML
    public TableColumn<Appointment, Integer> userIdCol;

    @FXML
    public TableColumn<Appointment, Integer> contactIdCol;

    Parent scene;

    public void back(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void addAppointment(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void editAppointment(ActionEvent actionEvent) throws IOException {
        Appointment selection = appointmentTableView.getSelectionModel().getSelectedItem();

        if (selection == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an Appointment to edit!");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/EditAppointment.fxml"));
            loader.load();

            // sends Appointment object to EditAppointment
            EditAppointment EditAppointmentController = loader.getController();
            EditAppointmentController.sendAppointment(appointmentTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    public void deleteAppointment(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // populates ViewAppointments with 'appointments' table upon initialization
        appointmentTableView.setItems(accessAppointments.getAllAppointments());
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    }
}

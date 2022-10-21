package controller;

import databaseAccess.accessAppointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;
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
    public TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    public TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    public TableColumn<Appointment, LocalDateTime> createDateCol;

    @FXML
    public TableColumn<Appointment, LocalDateTime> lastUpdateCol;

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

    public void deleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointment selection = appointmentTableView.getSelectionModel().getSelectedItem();

        if (selection == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an Appointment to delete!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Appointment?");
            Optional<ButtonType> result = alert.showAndWait();

            int appointmentToDelete = selection.getAppointmentId();

            if (result.get() == ButtonType.OK) {
                accessAppointments.delete(appointmentToDelete);
                appointmentTableView.setItems(accessAppointments.getAllAppointments()); // refreshes after delete to remove the appointment view from TableView

                // confirms the delete ID
                Alert verifyDelete = new Alert(Alert.AlertType.INFORMATION, "You have deleted Appointment ID: " + selection.getAppointmentId() + " Type: " + selection.getType());
                verifyDelete.showAndWait();
            }
        }
    }

    // TODO: improve this with lambda
    public void viewCurrentWeekAppointments(ActionEvent actionEvent) {
        ObservableList<Appointment> viewCurrentWeek = FXCollections.observableArrayList();

        for (Appointment a : accessAppointments.getAllAppointments()) {
            LocalDateTime today = LocalDateTime.now();

            if (today.getDayOfWeek() == DayOfWeek.SUNDAY) {
                LocalDateTime endOfCurrentWeek = today.plusDays(7);
                LocalDateTime startOfCurrentWeek = today.minusDays(0);
                if (a.getStart().isBefore(endOfCurrentWeek) && a.getStart().isAfter(startOfCurrentWeek)) {
                    viewCurrentWeek.add(a);
                }
            }

            else if (today.getDayOfWeek() == DayOfWeek.MONDAY) {
                LocalDateTime endOfCurrentWeek = today.plusDays(6);
                LocalDateTime startOfCurrentWeek = today.minusDays(1);
                if (a.getStart().isBefore(endOfCurrentWeek) && a.getStart().isAfter(startOfCurrentWeek)) {
                    viewCurrentWeek.add(a);
                }
            }
            else if (today.getDayOfWeek() == DayOfWeek.TUESDAY) {
                LocalDateTime endOfCurrentWeek = today.plusDays(5);
                LocalDateTime startOfCurrentWeek = today.minusDays(2);
                if (a.getStart().isBefore(endOfCurrentWeek) && a.getStart().isAfter(startOfCurrentWeek)) {
                    viewCurrentWeek.add(a);
                }
            }
            else if (today.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
                LocalDateTime endOfCurrentWeek = today.plusDays(4);
                LocalDateTime startOfCurrentWeek = today.minusDays(3);
                if (a.getStart().isBefore(endOfCurrentWeek) && a.getStart().isAfter(startOfCurrentWeek)) {
                    viewCurrentWeek.add(a);
                }
            }
            else if (today.getDayOfWeek() == DayOfWeek.THURSDAY) {
                LocalDateTime endOfCurrentWeek = today.plusDays(3);
                LocalDateTime startOfCurrentWeek = today.minusDays(4);
                if (a.getStart().isBefore(endOfCurrentWeek) && a.getStart().isAfter(startOfCurrentWeek)) {
                    viewCurrentWeek.add(a);
                }
            }
            else if (today.getDayOfWeek() == DayOfWeek.FRIDAY) {
                LocalDateTime endOfCurrentWeek = today.plusDays(2);
                LocalDateTime startOfCurrentWeek = today.minusDays(5);
                if (a.getStart().isBefore(endOfCurrentWeek) && a.getStart().isAfter(startOfCurrentWeek)) {
                    viewCurrentWeek.add(a);
                }
            }
            else if (today.getDayOfWeek() == DayOfWeek.SATURDAY) {
                LocalDateTime endOfCurrentWeek = today.plusDays(1);
                LocalDateTime startOfCurrentWeek = today.minusDays(6);
                if (a.getStart().isBefore(endOfCurrentWeek) && a.getStart().isAfter(startOfCurrentWeek)) {
                    viewCurrentWeek.add(a);
                }
            }
            appointmentTableView.setItems(viewCurrentWeek);
        }
    }

    public void viewCurrentMonthAppointments(ActionEvent actionEvent) {
        ObservableList<Appointment> viewCurrentMonth = FXCollections.observableArrayList();

        Month currentMonth = LocalDateTime.now().getMonth();

        for (Appointment a : accessAppointments.getAllAppointments()) {
            if (a.getStart().getMonth() == currentMonth) {
                viewCurrentMonth.add(a);
            }
        }
        appointmentTableView.setItems(viewCurrentMonth);
    }

    public void viewAllAppointments(ActionEvent actionEvent) {
        ObservableList<Appointment> allAppointments = accessAppointments.getAllAppointments();
        appointmentTableView.setItems(allAppointments);
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

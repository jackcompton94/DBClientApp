package controller;

import databaseAccess.accessMonthlyAppointments;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewAppointmentTypePerMonth implements Initializable {


    @FXML
    public TableView appointmentTypePerMonthTableView;

    @FXML
    public TableColumn typeCol;

    @FXML
    public TableColumn totalAppointmentsCol;

    @FXML
    public TableColumn dateCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentTypePerMonthTableView.setItems(accessMonthlyAppointments.getMonthlyAppointments());
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalAppointmentsCol.setCellValueFactory(new PropertyValueFactory<>("totalAppointments"));
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/ReportMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}

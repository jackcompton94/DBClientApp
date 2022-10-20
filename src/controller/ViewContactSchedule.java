package controller;

import databaseAccess.accessContactSchedule;
import databaseAccess.accessContacts;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewContactSchedule implements Initializable {


    @FXML
    public TableView contactScheduleTableView;

    @FXML
    public TableColumn contactIdCol;

    @FXML
    public TableColumn appointmentIdCol;

    @FXML
    public TableColumn titleCol;

    @FXML
    public TableColumn typeCol;

    @FXML
    public TableColumn descriptionCol;

    @FXML
    public TableColumn startCol;

    @FXML
    public TableColumn endCol;

    @FXML
    public TableColumn customerIdCol;

    @FXML
    public ComboBox contact;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactScheduleTableView.setItems(accessContactSchedule.getContactSchedule());
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        contact.setItems(accessContacts.getAllContacts());
    }

    public void back(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/ReportMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import reports.UserActivity;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Author Jack Compton
 */

public class ViewUserActivity implements Initializable {

    @FXML
    public TableView userActivityTableView;

    @FXML
    public TableColumn dateCol;

    @FXML
    public TableColumn timeCol;

    @FXML
    public TableColumn usernameCol;

    @FXML
    public TableColumn authenticationCol;


    public void back(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/ReportMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userActivityTableView.setItems(UserActivity.getUserActivityRecords());
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        authenticationCol.setCellValueFactory(new PropertyValueFactory<>("auth"));
    }
}

package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    public Spinner<Integer> start;

    @FXML
    public Spinner<Integer> end;

    @FXML
    public ComboBox contact;

    @FXML
    public ComboBox customerId;

    @FXML
    public ComboBox userId;

    SpinnerValueFactory<Integer> hourSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,24,1);
    SpinnerValueFactory<Integer> minuteSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,55,15);

    public void cancel(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/ViewAppointments.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void save(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        successLabel.setVisible(false);
        start.setValueFactory(hourSpinner);
        end.setValueFactory(minuteSpinner);
    }
}

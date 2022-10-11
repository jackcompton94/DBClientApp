package controller;

import com.mysql.cj.protocol.WatchableStream;
import databaseAccess.accessCountries;
import databaseAccess.accessCustomers;
import databaseAccess.accessDivisions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Division;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddCustomer implements Initializable {

    @FXML
    public TextField customerName;

    @FXML
    public TextField address;

    @FXML
    public TextField postalCode;

    @FXML
    public TextField phone;

    @FXML
    private ComboBox<Country> country;

    @FXML
    private ComboBox<Division> division;

    public void cancel(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/ViewCustomers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void save(ActionEvent actionEvent){
        Division selectedDivision = division.getSelectionModel().getSelectedItem();

        /*
         = customerName.getText();
         = address.getText();
         = postalCode.getText();
         = phone.getText();
         = selectedCountry.getCountry();
         = selectedDivision.getDivision();
         */

    }

    public void selectCountry(ActionEvent actionEvent) {
        division.setDisable(false);

        Country selectedCountry = country.getSelectionModel().getSelectedItem();
        int countryId = selectedCountry.getCountryId();

        for (Division d : division.getItems()) {
            if (countryId == d.getCountryId()) {
                division.setValue(d);
                //TODO: enable division ONLY where the condition above is met
            }
        }
    }

    public void selectDivision(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        country.setItems(accessCountries.getAllCountries());
        division.setItems(accessDivisions.getAllDivisions());
        division.setDisable(true); // initializes division ComboBox as disabled to force user to select a Country first
    }


}

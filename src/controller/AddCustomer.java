package controller;

import com.mysql.cj.protocol.WatchableStream;
import databaseAccess.accessCountries;
import databaseAccess.accessCustomers;
import databaseAccess.accessDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        division.setDisable(false);                                                         // enables division selection after country is selected
        division.setItems(accessDivisions.getAllDivisions());                               // enables all divisions to be selected

        Country selectedCountry = country.getSelectionModel().getSelectedItem();            // captures the selected country object
        int countryId = selectedCountry.getCountryId();                                     // captures the selected country ID for matching purposes
        ObservableList<Division> divisionsInCountry = FXCollections.observableArrayList();  // initializes the temporary available divisions per country selection
        divisionsInCountry.clear();

        for (Division d : division.getItems()) {                                            // loops through all divisions to match selectedCountryId with the d.CountryId and add those matches to our observableList ComboBox
            if (countryId == d.getCountryId()) {
                divisionsInCountry.add(d);
            }
            division.setItems(divisionsInCountry);
        }
    }


    public void selectDivision(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        country.setItems(accessCountries.getAllCountries());    // enables all countries to be selected
        division.setDisable(true);                              // initializes division ComboBox as disabled to force user to select a Country first
    }


}

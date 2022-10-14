package controller;

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
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Country;
import model.Division;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @FXML
    private Label successLabel;

    public void cancel(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/ViewCustomers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void save(ActionEvent actionEvent) throws SQLException {

        try {
            String customerNameText = customerName.getText();
            String addressText = address.getText();
            String postalCodeText = postalCode.getText();
            String phoneText = phone.getText();
            Timestamp createDate = Timestamp.valueOf(LocalDateTime.now());
            String createdBy = User.currentUser;
            Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            String lastUpdatedBy = User.currentUser;
            Division selectedDivision = division.getSelectionModel().getSelectedItem();    // captures the selected division
            Integer divisionIdText = selectedDivision.getDivisionId();                     // captures the select divisionID

            if (customerNameText.isBlank() || addressText.isBlank()|| postalCodeText.isBlank()|| phoneText.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Format Error");
                alert.setContentText("Unable to save customer. Please enter missing information.");
                alert.showAndWait();
            }
            else {
                accessCustomers.insert(customerNameText, addressText, postalCodeText, phoneText, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionIdText);
                successLabel.setVisible(true);
            }
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Format Error");
            alert.setContentText("Please select a division.");
            alert.showAndWait();
        }
    }

    public void selectCountry(ActionEvent actionEvent) {
        successLabel.setVisible(false);                                                     // hides successLabel on click, resets the label for confirmation of additional saves in one session
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        country.setItems(accessCountries.getAllCountries());    // enables all countries to be selected
        division.setDisable(true);                              // initializes division ComboBox as disabled to force user to select a Country first
        successLabel.setVisible(false);                         // hides successLabel notification
    }
}

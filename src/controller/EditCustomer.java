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
import model.Customer;
import model.Division;
import model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @Author Jack Compton
 */

public class EditCustomer implements Initializable {

    @FXML
    private TextField customerId;

    @FXML
    private TextField customerName;

    @FXML
    private TextField address;

    @FXML
    private TextField postalCode;

    @FXML
    private TextField phone;

    @FXML
    private ComboBox<Country> country;

    @FXML
    private ComboBox<Division> division;

    public void sendCustomer(Customer customer) {
        customerId.setText(String.valueOf(Integer.valueOf(customer.getCustomerId())));
        customerName.setText(String.valueOf(customer.getCustomerName()));
        address.setText(String.valueOf(customer.getAddress()));
        postalCode.setText(String.valueOf(customer.getPostalCode()));
        phone.setText(String.valueOf(customer.getPhone()));

        int customerDivisionId = customer.getDivisionId();              // capture selected customer divisionID
        for (Division d : accessDivisions.getAllDivisions()) {
            if (customerDivisionId == d.getDivisionId()) {              // looping through Division Objects to find a matching ID
                division.setValue(d);                                   // sets division ComboBox with the matched name value
                for (Country c : accessCountries.getAllCountries()) {
                    if (d.getCountryId() == c.getCountryId()) {         // nested loop used to continue search for Country name from the FK reference to the countries table
                        country.setValue(c);                            // sets country ComboBox with the matched name value
                    }
                }
            }
        }
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/ViewCustomers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void save(ActionEvent actionEvent) {
        try {
            String customerNameText = customerName.getText();
            String addressText = address.getText();
            String postalCodeText = postalCode.getText();
            String phoneText = phone.getText();
            Timestamp lastUpdate = Timestamp.valueOf(LocalDateTime.now());
            String lastUpdatedBy = User.currentUser;
            Division selectedDivision = division.getSelectionModel().getSelectedItem();    // captures the selected division
            Integer divisionIdText = selectedDivision.getDivisionId();                     // captures the select divisionID
            Integer customerIdText = Integer.valueOf(customerId.getText());

            if (customerNameText.isBlank() || addressText.isBlank()|| postalCodeText.isBlank()|| phoneText.isBlank()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Format Error");
                alert.setContentText("Unable to save customer. Please enter missing information.");
                alert.showAndWait();
            }
            else{
                accessCustomers.update(customerNameText, addressText, postalCodeText, phoneText, lastUpdate, lastUpdatedBy, divisionIdText, customerIdText);

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Successfully updated customer!");
                Optional<ButtonType> result = alert.showAndWait();

                // after update - takes user back to ViewCustomers
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/view/ViewCustomers.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (NullPointerException | SQLException | IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Format Error");
            alert.setContentText("Please select a division.");
            alert.showAndWait();
        }
    }

    public void selectCountry(ActionEvent actionEvent) {
        division.setDisable(false);                                                         // enables division selection after country is selected
        division.setValue(null);                                                            // logic control forcing users to reselect division

        division.setItems(accessDivisions.getAllDivisions());                               // enables all divisions to be selected

        Country selectedCountry = country.getValue();                                       // captures the selected country object
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
    }
}

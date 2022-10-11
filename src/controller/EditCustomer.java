package controller;

import databaseAccess.accessCountries;
import databaseAccess.accessDivisions;
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
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    private ComboBox country;

    @FXML
    private ComboBox division;

    public void sendCustomer(Customer customer) {
        customerId.setText(String.valueOf(Integer.valueOf(customer.getCustomerId())));
        customerName.setText(String.valueOf(customer.getCustomerName()));
        address.setText(String.valueOf(customer.getAddress()));
        postalCode.setText(String.valueOf(customer.getPostalCode()));
        phone.setText(String.valueOf(customer.getPhone()));

        int customerDivisionId = customer.getDivisionId();              // capture selected customer divisionID
        for (Division d : accessDivisions.getAllDivisions()) {
            if (customerDivisionId == d.getDivisionId()) {              // looping through Division Objects to find a matching ID
                String customerDivision = d.getDivision();              // set Division Name to customerDivision once match is found
                division.setValue(customerDivision);                    // sets division ComboBox with the matched name value
                for (Country c : accessCountries.getAllCountries()) {
                    if (d.getCountryId() == c.getCountryId()) {         // nested loop used to continue search for Country name from the FK reference to the countries table
                        String customerCountry = c.getCountry();        // sets matched country name to customerCountry
                        country.setValue(customerCountry);              // sets country ComboBox with the matched name value
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
    }

    public void selectDivision(ActionEvent actionEvent) {
    }

    public void selectCountry(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        country.setItems(accessCountries.getAllCountries());
        division.setItems(accessDivisions.getAllDivisions());
    }
}

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

        // take divisionID from customer and match to the division.divisionName
        // assign matched name to division.setValue()
        // take countryID from matched division.divisionName object
        // assign matched name to country.setValue()

        int customerDivisionId = customer.getDivisionId();
        for (Division d : accessDivisions.getAllDivisions()) {
            if (customerDivisionId == d.getDivisionId()) {
                String customerDivision = d.getDivision();
                division.setValue(customerDivision);
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

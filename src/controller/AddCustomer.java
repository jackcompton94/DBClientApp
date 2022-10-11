package controller;

import databaseAccess.accessCountries;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Country;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddCustomer implements Initializable {

    @FXML
    private ComboBox<Country> country;

    //TODO: when adding a customer, text fields are used to collect: customerName, address, postalCode, and phone
    //TODO: customerIds are auto-generated
    //TODO: first-level-division/country data are collected using separate combo-boxes

    public void cancel(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/ViewCustomers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void save(ActionEvent actionEvent){
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        country.setItems(accessCountries.getAllCountries());
    }
}

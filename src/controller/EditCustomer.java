package controller;

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
import model.Customer;

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
        //TODO: learn how to implement ComboBoxes
        // country
        // divisionId
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("/view/ViewCustomers.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void save(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}

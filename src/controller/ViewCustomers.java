package controller;

import databaseAccess.accessCustomers;
import javafx.collections.ObservableList;
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
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ResourceBundle;

public class ViewCustomers implements Initializable {

    @FXML
    public TableView<Customer> customerTableView;

    @FXML
    public TableColumn<Customer, String> customerIdCol;

    @FXML
    public TableColumn<Customer, String> customerNameCol;

    @FXML
    public TableColumn<Customer, String> addressCol;

    @FXML
    public TableColumn<Customer, String> postalCodeCol;

    @FXML
    public TableColumn<Customer, String> phoneCol;

    @FXML
    public TableColumn<Customer, Date> createDateCol;

    @FXML
    public TableColumn<Customer, String> createdByCol;

    @FXML
    public TableColumn<Customer, Timestamp> lastUpdateCol;

    @FXML
    public TableColumn<Customer, String> lastUpdatedByCol;

    @FXML
    public TableColumn<Customer, Integer> divisionIdCol;

    Parent scene;

    public void back(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public void addCustomer(ActionEvent actionEvent) {
        //TODO: when adding a customer, text fields are used to collect: customerName, address, postalCode, and phone
        //TODO: customerIds are auto-generated
        //TODO: first-level-division/country data are collected using separate combo-boxes
    }

    public void editCustomer(ActionEvent actionEvent) {
        //TODO: when updating a customer, customer data must auto-populate the UpdateCustomerForm
        //TODO: text fields are used to collect: customerName, address, postalCode, and phone
        //TODO: customerIds are disabled in UpdateCustomerForm
        //TODO: first-level-division/country data are collected using separate combo-boxes
    }

    public void deleteCustomer(ActionEvent actionEvent) {
        //TODO: when deleting a customer record, all of the customer's appointments must be deleted first, due to foreign key constraints
        //TODO: a custom message must display to confirm the deletion
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // populates ViewCustomers with 'customers' table upon initialization
        customerTableView.setItems(accessCustomers.getAllCustomers());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }


}

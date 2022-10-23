package controller;

import databaseAccess.accessAppointments;
import databaseAccess.accessCustomers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @Author Jack Compton
 */

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

    public void addCustomer(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     *
     * @param actionEvent
     * editCustomer when selected, takes the selected customer and "passes" the object into the EditCustomerController
     *
     * @throws IOException
     * Signals that an I/O exception of some sort has occurred
     *
     */
    public void editCustomer(ActionEvent actionEvent) throws IOException {
        Customer selection = customerTableView.getSelectionModel().getSelectedItem();

        if (selection == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a Customer to edit!");
            alert.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/EditCustomer.fxml"));
            loader.load();

            // sends Customer object to EditCustomer
            EditCustomer EditCustomerController = loader.getController();
            EditCustomerController.sendCustomer(customerTableView.getSelectionModel().getSelectedItem());

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.show();
        }
    }

    /**
     *
     * @param actionEvent
     * provides user with confirmation on delete of Customers
     *
     * @throws SQLException
     * An exception that provides information on a database access error or other errors.
     */
    public void deleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customer selection = customerTableView.getSelectionModel().getSelectedItem();

        if (selection == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a Customer to delete!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Customer? \nThis will delete ALL Appointments for this Customer.");
            Optional<ButtonType> result = alert.showAndWait();

            int customerToDelete = selection.getCustomerId();

            if (result.get() == ButtonType.OK) {

                // loop to parse all apartments and delete all appointments associated with the customerID selected
                for (Appointment a : accessAppointments.getAllAppointments()) {
                    if (customerToDelete == a.getCustomerId()) {
                        accessAppointments.delete(a.getAppointmentId());
                    }
                }
                accessCustomers.delete(customerToDelete);
                customerTableView.setItems(accessCustomers.getAllCustomers()); // refreshes after delete to remove the customer view from TableView

                // confirms the delete
                Alert verifyDelete = new Alert(Alert.AlertType.INFORMATION, "You have deleted Customer ID: " + selection.getCustomerId() + " Name: " + selection.getCustomerName());
                verifyDelete.showAndWait();
            }
        }
    }

    /**
     *
     * @param url
     * @param resourceBundle
     * initializes ViewCustomers with all customers available via accessCustomers()
     */
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

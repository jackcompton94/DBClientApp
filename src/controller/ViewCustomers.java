package controller;

import databaseAccess.accessCustomers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewCustomers implements Initializable {

    public TableView customerTableView;
    public TableColumn customerIdCol;
    public TableColumn customerNameCol;
    public TableColumn addressCol;
    public TableColumn postalCodeCol;
    public TableColumn phoneCol;
    public TableColumn createDateCol;
    public TableColumn createdByCol;
    public TableColumn lastUpdateCol;
    public TableColumn lastUpdatedByCol;
    public TableColumn divisionIdCol;
    Parent scene;

    public void back(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //TODO: learn how to populate customerTable with the observable list
        ObservableList<Customer> customerList = accessCustomers.getAllCustomers();
        for(Customer customers : customerList) {
            System.out.println(customers.getCustomerId());
            System.out.println(customers.getCustomerName());
            System.out.println(customers.getAddress());
            System.out.println(customers.getPostalCode());
            System.out.println(customers.getPhone());
            System.out.println(customers.getCreateDate());
            System.out.println(customers.getCreatedBy());
            System.out.println(customers.getLastUpdate());
            System.out.println(customers.getLastUpdatedBy());
            System.out.println(customers.getDivisionId());
        }
    }
}

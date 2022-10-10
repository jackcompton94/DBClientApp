package controller;

import dataAccess.accessCustomers;
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
import model.Customers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewCustomers implements Initializable {

    public TableView customerTable;
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

        customerTable.set
        ObservableList<Customers> customerList = accessCustomers.getAllCustomers();
        for(Customers customers : customerList) {
            customers.getCustomerId();
            customers.getCustomerName();
            customers.getAddress();
            customers.getPostalCode();
            customers.getPhone();
            customers.getCreateDate();
            customers.getCreatedBy();
            customers.getLastUpdate();
            customers.getLastUpdatedBy();
            customers.getDivisionId();
        }
    }
}

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    public void viewAppointments(ActionEvent actionEvent) {
        System.out.println("view appointments");
    }

    public void viewCustomers(ActionEvent actionEvent) {
        System.out.println("view customers");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}


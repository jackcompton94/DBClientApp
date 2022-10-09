package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button signInButton;

    @FXML
    private TextField userIdText;

    @FXML
    private PasswordField passwordText;


    public void onActionSignIn(ActionEvent actionEvent) {
        System.out.println("sign in button pressed");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("successfully loaded controller");
    }
}

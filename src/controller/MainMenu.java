package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
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

    @FXML
    private Label invalidLogin;

    @FXML
    private Label zoneId;

    // learn CSS events in Java

    public void onActionSignIn(ActionEvent actionEvent) {
        if ((userIdText.getText().equals("sqlUser")) && (passwordText.getText().equals("Passw0rd!"))) {
            System.out.println("valid credentials");
            invalidLogin.setVisible(false);

            //TODO: insert next scene
        }
        else if ((userIdText.getText().isEmpty()) || passwordText.getText().isEmpty()) {
            System.out.println("need credentials");
            invalidLogin.setVisible(true);
        }
        else {
            System.out.println("invalid credentials");
            invalidLogin.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        invalidLogin.setVisible(false);

        // sets ZoneID according to users locale
        Locale currentLocale = Locale.getDefault();
        zoneId.setText(currentLocale.getDisplayCountry());
    }
}

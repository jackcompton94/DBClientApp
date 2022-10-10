package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class SchedulerLogin implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField usernameText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Label invalidLogin;

    @FXML
    private Label zoneId;

    @FXML
    private Button signInButton;

    @FXML
    private Label schedulerLogin;

    @FXML
    private Label username;

    @FXML
    private Label password;

    public void onActionSignIn(ActionEvent actionEvent) throws IOException {
        // test username & password
        /*if ((usernameText.getText().equals("sqlUser")) && (passwordText.getText().equals("Passw0rd!"))) {
            System.out.println("valid credentials");
            invalidLogin.setVisible(false);*/

            // prompts MainMenu if login was successful
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }/*
        else if ((username.getText().isEmpty()) || passwordText.getText().isEmpty()) {
            System.out.println("need credentials");
            invalidLogin.setVisible(true);
        }
        else {
            System.out.println("invalid credentials");
            invalidLogin.setVisible(true);
        }
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // hides invalidLogin alert
        invalidLogin.setVisible(false);

        // sets ZoneID according to users OS locale
        Locale currentLocale = Locale.getDefault();
        zoneId.setText(currentLocale.getDisplayCountry());

        // language translator
        try {
            ResourceBundle rb = ResourceBundle.getBundle("languages/language", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("fr")) {
                schedulerLogin.setText((rb.getString("SchedulerLogin")));
                invalidLogin.setText(rb.getString("InvalidLogin"));
                username.setText(rb.getString("Username"));
                password.setText(rb.getString("Password"));
                signInButton.setText(rb.getString("SignIn"));
            }
        } catch(Exception e) {  }
    }
}

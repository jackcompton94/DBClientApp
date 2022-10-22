package controller;

import databaseAccess.accessAppointments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import reports.UserActivity;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @Author Jack Compton
 */

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
        String user = usernameText.getText();
        String pass = passwordText.getText();

        if (UserActivity.authUser(user, pass)) {

            // prompts MainMenu if login was successful
            stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

            // 15 minute appointment checker
            LocalDateTime currentTime = LocalDateTime.now();
            ZonedDateTime currentTimeZone = ZonedDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            // initialize upcomingAppointments list with Appointment objects
            ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();

            for (Appointment a : accessAppointments.getAllAppointments()) {
                LocalDateTime convertStart = LocalDateTime.from(a.getStart().atZone(ZoneId.from(currentTimeZone)));

                if (convertStart.minusMinutes(15).isBefore(currentTime) && convertStart.isAfter(currentTime)) {
                    upcomingAppointments.add(a);
                }
            }

            if (upcomingAppointments.isEmpty()) {
                Alert reminder = new Alert(Alert.AlertType.INFORMATION, "There are no upcoming appointments.");
                Optional<ButtonType> result = reminder.showAndWait();
            }
            else {
                // capture upcomingAppointments needed for 15 minute alert
                for (Appointment a : upcomingAppointments) {
                    Alert reminder = new Alert(Alert.AlertType.INFORMATION, "Appointment ID: " + a.getAppointmentId() + " is starting soon! \n\n" + a.getStart().format(dtf) + " " + currentTimeZone.getZone());
                    Optional<ButtonType> result = reminder.showAndWait();
                }
            }
        }
        else {
            invalidLogin.setVisible(true);
        }
    }

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

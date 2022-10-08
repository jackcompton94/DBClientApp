package controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("successfully loaded controller");
    }

    public void onActionSignIn(ActionEvent actionEvent) {
        System.out.println("sign in button pressed");
    }
}

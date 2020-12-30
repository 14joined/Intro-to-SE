package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EventFillInController implements Initializable {

    @FXML
    private Button switchButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void switchButtonOnAction(ActionEvent event) {

        Stage stage = (Stage) switchButton.getScene().getWindow();
//        Stage stage = new Stage();
        stage.setTitle("Add a voluntary event");

        Scene tableViewScene = null;
        try {
            tableViewScene = new Scene(FXMLLoader.load(getClass().getResource("EventFillInB.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(tableViewScene);
        stage.show();
    }

    @FXML
    public void switchButtonOnActionB(ActionEvent event) {

        Stage stage = (Stage) switchButton.getScene().getWindow();
//        Stage stage = new Stage();
        stage.setTitle("Add a general event");

        Scene tableViewScene = null;
        try {
            tableViewScene = new Scene(FXMLLoader.load(getClass().getResource("EventFillIn.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(tableViewScene);
        stage.show();
    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}

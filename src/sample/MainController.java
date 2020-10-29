package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView addImageView;
    @FXML
    private ImageView eventImageView;
    @FXML
    private ImageView managementImageView;
    @FXML
    private ImageView citizenImageView;
    @FXML
    private ImageView settingImageView;
    @FXML
    private MenuButton menuButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("images/MenuIconB.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        File addFile = new File("images/add.png");
        Image addImage = new Image(addFile.toURI().toString());
        addImageView.setImage(addImage);

        File eventFile = new File("images/events.png");
        Image eventImage = new Image(eventFile.toURI().toString());
        eventImageView.setImage(eventImage);

        File managementFile = new File("images/management.png");
        Image managementImage = new Image(managementFile.toURI().toString());
        managementImageView.setImage(managementImage);

        File citizenFile = new File("images/citizen.png");
        Image citizenImage = new Image(citizenFile.toURI().toString());
        citizenImageView.setImage(citizenImage);

        File settingFile = new File("images/settings.png");
        Image settingImage = new Image(settingFile.toURI().toString());
        settingImageView.setImage(settingImage);
    }

    @FXML
    public void listButtonOnAction(ActionEvent event) {
        System.out.println("Hello");
//        Stage stage = (Stage) menuButton.getScene().getWindow();
        Stage stage = new Stage();
        Parent tableViewParent = null;
        try {
            stage.setTitle("Add a general event");
            tableViewParent = FXMLLoader.load(getClass().getResource("EventFillIn.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene tableViewScene = new Scene(tableViewParent);

        stage.setScene(tableViewScene);
        stage.show();
    }

    @FXML
    public void listButtonOnActionB(ActionEvent event) {
        System.out.println("Hallo");
//        Stage stage = (Stage) menuButton.getScene().getWindow();
        Stage stage = new Stage();
        Parent tableViewParent = null;
        try {
            stage.setTitle("Add a voluntary event");
            tableViewParent = FXMLLoader.load(getClass().getResource("EventFillInB.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene tableViewScene = new Scene(tableViewParent);

        stage.setScene(tableViewScene);
        stage.show();
    }

    @FXML
    public void eventManagementOnAction(ActionEvent event) {

        Stage stage = (Stage) menuButton.getScene().getWindow();
//        Stage stage = new Stage();
        stage.setTitle("Workspace - Event Management");

        Scene tableViewScene = null;
        try {
            tableViewScene = new Scene(FXMLLoader.load(getClass().getResource("workspace.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(tableViewScene);
        stage.show();
    }

    @FXML
    public void citizenManagementOnAction(ActionEvent event) {

        Stage stage = (Stage) menuButton.getScene().getWindow();
//        Stage stage = new Stage();
        stage.setTitle("Workspace - Citizen Management");

        Scene tableViewScene = null;
        try {
            tableViewScene = new Scene(FXMLLoader.load(getClass().getResource("workspace.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setScene(tableViewScene);
        stage.show();
    }
}

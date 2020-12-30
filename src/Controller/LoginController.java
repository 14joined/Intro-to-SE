package Controller;

import Service.DatabaseConnection;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File brandingFile = new File("images/bear.jpg");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);

        File lockFile = new File("images/lock.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);

        loginButton.setDefaultButton(true);
        cancelButton.setCancelButton(true);
    }

    @FXML
    public void loginButtonOnAction(ActionEvent event) {

        if (!usernameTextField.getText().isBlank() && !enterPasswordField.getText().isBlank()) {
            if (validationLogin()) {
                loginMessageLabel.setText("Congratulation!");
                Task<Void> task = new Task<>() {
                    @Override
                    protected Void call() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                task.setOnSucceeded(new EventHandler<>() {
                    @Override
                    public void handle(WorkerStateEvent workerStateEvent) {
                        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                        Parent tableViewParent = null;
                        try {
                            String title = "Welcome, " + usernameTextField.getText() + "!";
                            window.setTitle(title);
                            tableViewParent = FXMLLoader.load(getClass().getResource("../View/updated_main.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene tableViewScene = new Scene(tableViewParent);

                        window.setScene(tableViewScene);
                        window.show();
                    }
                });
                new Thread(task).start();
            } else {
                loginMessageLabel.setText("Invalid login. Please try again.");
            }
        } else {
            loginMessageLabel.setText("Please enter username and password");
        }

    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public boolean validationLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT COUNT(username) FROM user_account WHERE username = '" +
                                usernameTextField.getText() + "' AND password = '" +
                                enterPasswordField.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while (queryResult.next()) {
                if (queryResult.getInt(1) >= 1) {
                    return true;
                }
            }

        } catch (Exception e) {
//            System.out.println(e.getClass().getSimpleName());
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

}

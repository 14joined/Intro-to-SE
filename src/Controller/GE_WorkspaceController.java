package Controller;

import Service.GEService;
import Model.GeneralEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class GE_WorkspaceController implements Initializable {

    @FXML
    private ImageView calendarImageView;
    @FXML
    private ImageView calculatorImageView;
    @FXML
    private ImageView eventImageView;
    @FXML
    private ImageView familyImageView;
    @FXML
    private ImageView plotImageView;
    @FXML
    private ImageView searchImageView;
    @FXML
    private ImageView undoImageView;
    @FXML
    private ImageView undoImageViewB;
    @FXML
    private ImageView addImageView;
    @FXML
    private ImageView deleteImageView;
    @FXML
    private ImageView listImageView;
    @FXML
    private ImageView calendarImageViewB;
    @FXML
    private ImageView editImageView;
    @FXML
    private ImageView deleteObImageView;
    @FXML
    private Button closeButton;
    @FXML
    private Button familyButton;
    @FXML
    private Button switchEventButton;

    @FXML
    private TableView<GeneralEvent> tableView;
    @FXML
    private TableColumn<GeneralEvent, Integer> idCol;
    @FXML
    private TableColumn<GeneralEvent, String> nameCol;
    @FXML
    private TableColumn<GeneralEvent, Integer> moneyCol;
    @FXML
    private TableColumn<GeneralEvent, String> noteCol;

    public static ObservableList<GeneralEvent> generalEvents;
    public static GeneralEvent selected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File calendarFile = new File("images/calendar.png");
        Image calendarImage = new Image(calendarFile.toURI().toString());
        calendarImageView.setImage(calendarImage);
        calendarImageViewB.setImage(calendarImage);

        File calculatorFile = new File("images/calculator.png");
        Image calculatorImage = new Image(calculatorFile.toURI().toString());
        calculatorImageView.setImage(calculatorImage);

        File eventFile = new File("images/eventsB.png");
        Image eventImage = new Image(eventFile.toURI().toString());
        eventImageView.setImage(eventImage);

        File familyFile = new File("images/familyB.png");
        Image familyImage = new Image(familyFile.toURI().toString());
        familyImageView.setImage(familyImage);

        File plotFile = new File("images/statistics.png");
        Image plotImage = new Image(plotFile.toURI().toString());
        plotImageView.setImage(plotImage);

        File searchFile = new File("images/search.png");
        Image searchImage = new Image(searchFile.toURI().toString());
        searchImageView.setImage(searchImage);

        File undoFile = new File("images/undoB.png");
        Image undoImage = new Image(undoFile.toURI().toString());
        undoImageView.setImage(undoImage);
        undoImageViewB.setImage(undoImage);

        File addFile = new File("images/add.png");
        Image addImage = new Image(addFile.toURI().toString());
        addImageView.setImage(addImage);

        File deleteFile = new File("images/delete.jpg");
        Image deleteImage = new Image(deleteFile.toURI().toString());
        deleteImageView.setImage(deleteImage);

        File listFile = new File("images/list.png");
        Image listImage = new Image(listFile.toURI().toString());
        listImageView.setImage(listImage);

        File editFile = new File("images/editB.png");
        Image editImage = new Image(editFile.toURI().toString());
        editImageView.setImage(editImage);

        File img11File = new File("images/deleteB.png");
        Image img11Image = new Image(img11File.toURI().toString());
        deleteObImageView.setImage(img11Image);

        generalEvents = FXCollections.observableArrayList();
        List<GeneralEvent> lst = GEService.getList();
        for(var s : lst){
            generalEvents.add(s);
        }
        idCol.setCellValueFactory(new PropertyValueFactory<GeneralEvent, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<GeneralEvent, String>("name"));
        moneyCol.setCellValueFactory(new PropertyValueFactory<GeneralEvent, Integer>("money"));
        noteCol.setCellValueFactory(new PropertyValueFactory<GeneralEvent, String>("note"));
        tableView.setItems(generalEvents);
    }

    @FXML
    // Note: This button behind the image view
    public void closeOnAction(ActionEvent event) {
        Stage window = (Stage) closeButton.getScene().getWindow();
        Parent tableViewParent = null;
        try {
            String title = "Phần mềm quản lý thu phí";
            window.setTitle(title);
            tableViewParent = FXMLLoader.load(getClass().getResource("../View/updated_main.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene tableViewScene = new Scene(tableViewParent);

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    public void addButtonOnAction(ActionEvent event) {
        Stage stage = new Stage();
        Parent tableViewParent = null;
        try {
            stage.setTitle("Thêm khoản phí bắt buộc");
            tableViewParent = FXMLLoader.load(getClass().getResource("../View/AddGeneralEvent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene tableViewScene = new Scene(tableViewParent);

        stage.setScene(tableViewScene);
        stage.show();
    }

    @FXML
    public void deleteButtonOnAction(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("Bạn cần chọn khoản phí muốn xóa");
            alert.show();
            return;
        }
        selected = tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText("Khoản phí: " + selected.getName());
        alert.setContentText("Bạn có thật sự muốn xóa dữ liệu của khoản phí này? (Không thể hoàn tác)");
        ButtonType buttonTypeYes = new ButtonType("Xóa", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == buttonTypeYes) {
            generalEvents.remove(selected);
            GEService.delete(selected);
        }
    }

    public void editButtonOnAction(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("Bạn cần chọn khoản phí muốn cập nhật dữ liệu");
            alert.show();
            return;
        }
        selected = tableView.getSelectionModel().getSelectedItem();
        Stage stage = new Stage();
        Parent tableViewParent = null;
        try {
            stage.setTitle("Cập nhật dữ liệu khoản phí");
            tableViewParent = FXMLLoader.load(getClass().getResource("../View/EditEvent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene tableViewScene = new Scene(tableViewParent);

        stage.setScene(tableViewScene);
        stage.show();
    }

    @FXML
    // Chuyển sang Family Management
    public void familyOnAction(ActionEvent event){
        Stage window = (Stage) familyButton.getScene().getWindow();
        Parent tableViewParent = null;
        try {
            String title = "Quản lý hộ gia đình";
            window.setTitle(title);
            tableViewParent = FXMLLoader.load(getClass().getResource("../View/familyworkspace.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene tableViewScene = new Scene(tableViewParent);

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    // Chuyển sang Event management
    public void switchEvent(ActionEvent event){
        Stage window = (Stage) switchEventButton.getScene().getWindow();
        Parent tableViewParent = null;
        try {
            String title = "Quản lý thu phí đóng góp";
            window.setTitle(title);
            tableViewParent = FXMLLoader.load(getClass().getResource("../View/VEworkspace.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene tableViewScene = new Scene(tableViewParent);

        window.setScene(tableViewScene);
        window.show();
    }
}

package Controller;

import Service.VEService;
import Model.VoluntaryEvent;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class VE_WorkspaceController implements Initializable {

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
    private Button addButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button familyButton;
    @FXML
    private TableView<VoluntaryEvent> tableView;
    @FXML
    private TableColumn<VoluntaryEvent, Integer> idCol;
    @FXML
    private TableColumn<VoluntaryEvent, String> nameCol;
    @FXML
    private TableColumn<VoluntaryEvent, Date> dateCol1;
    @FXML
    private TableColumn<VoluntaryEvent, Date> dateCol2;
    @FXML
    private TableColumn<VoluntaryEvent, String> noteCol;
    // News
    @FXML
    private GridPane tablePane;

    private ColumnConstraints detailConstraint;
    //

    public static ObservableList<VoluntaryEvent> voluntaryEventsList;
    public static VoluntaryEvent selected;

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

        // News
        ObservableList<ColumnConstraints> list = tablePane.getColumnConstraints();
        detailConstraint = list.get(1);
        //

        voluntaryEventsList = FXCollections.observableArrayList();
        VEService generalEventDAO = new VEService();
        List<VoluntaryEvent> lst = generalEventDAO.getList();
        for(var s : lst){
            voluntaryEventsList.add(s);
        }
        idCol.setCellValueFactory(new PropertyValueFactory<VoluntaryEvent, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<VoluntaryEvent, String>("name"));
        dateCol1.setCellValueFactory(new PropertyValueFactory<VoluntaryEvent, Date>("date1"));
        dateCol2.setCellValueFactory(new PropertyValueFactory<VoluntaryEvent, Date>("date2"));
        noteCol.setCellValueFactory(new PropertyValueFactory<VoluntaryEvent, String>("note"));
        tableView.setItems(voluntaryEventsList);

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

    public void detailButtonOnAction(ActionEvent event) {
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
            tableViewParent = FXMLLoader.load(getClass().getResource("../View/EditVoluntaryEvent.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene tableViewScene = new Scene(tableViewParent);

        stage.setScene(tableViewScene);
        stage.show();
    }

    // New function
    public void detailButtonOnMouseClicked(MouseEvent mouseEvent) {
        if (detailConstraint == null) return;
        if (detailConstraint.getPercentWidth() == 0) {
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(150)
                , new KeyValue(detailConstraint.percentWidthProperty(), 30)));
            timeline.play();
        } else {
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(150)
                    , new KeyValue(detailConstraint.percentWidthProperty(), 0)));
            timeline.play();
        }
    }
    //

    @FXML
    public void addButtonOnAction(ActionEvent event) {
        Stage stage = new Stage();
        Parent tableViewParent = null;
        try {
            stage.setTitle("Thêm khoản phí đóng góp");
            tableViewParent = FXMLLoader.load(getClass().getResource("../View/AddVoluntaryEvent.fxml"));
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
            voluntaryEventsList.remove(selected);
            VEService.delete(selected);
        }
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
    public void switchEvent(ActionEvent event) {
        Stage window = (Stage) familyButton.getScene().getWindow();
        Parent tableViewParent = null;
        try {
            String title = "Quản lý thu phí bắt buộc";
            window.setTitle(title);
            tableViewParent = FXMLLoader.load(getClass().getResource("../View/GEworkspace.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene tableViewScene = new Scene(tableViewParent);

        window.setScene(tableViewScene);
        window.show();
    }
}

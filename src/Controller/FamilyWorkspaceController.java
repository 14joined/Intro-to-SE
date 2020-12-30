package Controller;

import Service.CitizenService;
import Model.Citizen;
import Service.VE_Citizen;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FamilyWorkspaceController implements Initializable {

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
    private ImageView closeImageView;

    @FXML
    private Button closeButton;
    @FXML
    private Button familyButton;
    @FXML
    private Button eventButton;
    @FXML
    private Button addButton;

    @FXML
    private TableView <Citizen> tableView;

    @FXML
    private TableColumn <Citizen, Integer> idCol;

    @FXML
    private TableColumn<Citizen, String> nameCol;

    @FXML
    private TableColumn <Citizen, String> numCol;

    public static ObservableList<Citizen> citizenList; // Danh sách hộ gia đình trong TableView
    public static Citizen selected;                    // Hộ gia đình đang được chọn


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

        File img11File = new File("images/deleteB.png");
        Image img11Image = new Image(img11File.toURI().toString());
        deleteObImageView.setImage(img11Image);
        deleteImageView.setImage(img11Image);

        File deleteFile = new File("images/delete.jpg");
        Image deleteImage = new Image(deleteFile.toURI().toString());
        closeImageView.setImage(deleteImage);

        File listFile = new File("images/list.png");
        Image listImage = new Image(listFile.toURI().toString());
        listImageView.setImage(listImage);

        File editFile = new File("images/editB.png");
        Image editImage = new Image(editFile.toURI().toString());
        editImageView.setImage(editImage);

        closeButton.setCancelButton(true);
        eventButton.setTooltip(new Tooltip("Quản lý thu phí đóng góp"));
        familyButton.setTooltip(new Tooltip("Quản lý hộ gia đình"));

        tableView.setEditable(true);

        citizenList = FXCollections.observableArrayList();
        CitizenService citizenService = new CitizenService();
        List<Citizen> list = citizenService.getList();
        for(Citizen s : list){
            citizenList.add(s);
        }
        idCol.setText("Mã hộ");
        idCol.setCellValueFactory(new PropertyValueFactory<Citizen, Integer>("maHo"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Citizen, String>("tenChuHo"));
        numCol.setCellValueFactory(new PropertyValueFactory<Citizen, String>("soThanhVien"));

        nameCol.setCellFactory(TextFieldTableCell.<Citizen>forTableColumn());
        nameCol.setOnEditCommit((TableColumn.CellEditEvent<Citizen, String> event) ->{
                    TablePosition<Citizen, String> pos = event.getTablePosition();
                    String newName = event.getNewValue();
                    int row = pos.getRow();
                    Citizen citizen = event.getTableView().getItems().get(row);
                    citizen.setTenChuHo(newName);
                    CitizenService.editCitizen(citizen);
                }
        );

        numCol.setCellFactory(TextFieldTableCell.<Citizen>forTableColumn());
        numCol.setOnEditCommit((TableColumn.CellEditEvent<Citizen, String> event) ->{
                    TablePosition<Citizen, String> pos = event.getTablePosition();
                    String newNum = event.getNewValue();
                    int row = pos.getRow();
                    Citizen citizen = event.getTableView().getItems().get(row);
                    citizen.setSoThanhVien(newNum);
                    CitizenService.editCitizen(citizen);
                }
        );

        tableView.setItems(citizenList);
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
    // Chuyển sang Event management
    public void eventOnAction(ActionEvent event){
        Stage window = (Stage) eventButton.getScene().getWindow();
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

    @FXML
    // Chuyển sang Family Management
    public void familyOnAction(ActionEvent event){
        Stage window = (Stage) familyButton.getScene().getWindow();
        Parent tableViewParent = null;
        try {
            String title = "Workspace - Citizen Management";
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
    // Thêm một hộ gia đình
    public void addButtonOnAction(ActionEvent event) {
        AddCitizenController.string = "add";
        Stage stage = new Stage();
        Parent tableViewParent = null;
        try {
            stage.setTitle("Add a citizen");
            tableViewParent = FXMLLoader.load(getClass().getResource("../View/AddCitizen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene tableViewScene = new Scene(tableViewParent);

        stage.setScene(tableViewScene);
        stage.show();
    }

    @FXML
    // Xóa hộ gia đình
    public void deleteButtonOnAction(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("Bạn cần chọn hộ gia đình muốn xóa");
            alert.show();
            return;
        }
        selected = tableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận");
        alert.setHeaderText("Mã hộ            : " + selected.getMaHo() +
                            "\nTên chủ hộ     : " + selected.getTenChuHo() +
                            "\nSố nhân khẩu : " + selected.getSoThanhVien());
        alert.setContentText("Bạn có thật sự muốn xóa hộ gia đình này? (Không thể hoàn tác)");
        ButtonType buttonTypeYes = new ButtonType("Xóa", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Hủy", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == buttonTypeYes) {
            citizenList.remove(selected);
            CitizenService.deleteCitizen(selected);
        }
    }

    @FXML
    // Chỉnh sủa hộ gia đình
    private void editOnAction(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("Bạn cần chọn hộ gia đình muốn chỉnh sửa");
            alert.show();
            return;
        }
        selected = tableView.getSelectionModel().getSelectedItem();
        AddCitizenController.string = "edit";
        Stage stage = new Stage();
        Parent tableViewParent = null;
        try {
            stage.setTitle("Edit a citizen");
            tableViewParent = FXMLLoader.load(getClass().getResource("../View/AddCitizen.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene tableViewScene = new Scene(tableViewParent);

        stage.setScene(tableViewScene);
        stage.show();
    }
}

package Controller;

import Service.CitizenService;
import Model.Citizen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddCitizenController implements Initializable {

    @FXML
    private TextField idTF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField numTF;
    @FXML
    private Label saveMessageLabel;
    @FXML
    private Label titleLabel;

    @FXML
    private Button save;
    @FXML
    private Button cancel;

    public static String string;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        save.setDefaultButton(true);
        cancel.setCancelButton(true);
        if(string == "add") {
            titleLabel.setText("Thêm hộ gia đình");
        }
        if(string == "edit") {
            Citizen citizen = FamilyWorkspaceController.selected;
            idTF.setText(String.valueOf(citizen.getMaHo()));
            nameTF.setText(citizen.getTenChuHo());
            numTF.setText(String.valueOf(citizen.getSoThanhVien()));
            titleLabel.setText("Chỉnh sửa thông tin hộ");
        }
    }

    @FXML
    public void cancelOnAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saveOnAction(ActionEvent e) {
        if(idTF.getText().isBlank()) {
            saveMessageLabel.setText("Bạn chưa điền ID");
            return;
        }
        if(nameTF.getText().isBlank()) {
            saveMessageLabel.setText("Bạn chưa điền tên chủ hộ");
            return;
        }
        if(numTF.getText().isBlank()) {
            saveMessageLabel.setText("Bạn chưa diền số nhân khẩu");
            return;
        }
        String idText = idTF.getText();
        String numText = numTF.getText();
        Pattern pattern = Pattern.compile("\\d*");
        Matcher matcher = pattern.matcher(idText);
        if(!matcher.matches()) {
            saveMessageLabel.setText("ID phải là số nguyên dương");
            return;
        }
        matcher = pattern.matcher(numText);
        if(!matcher.matches()) {
            saveMessageLabel.setText("Số nhân khẩu phải là số nguyên dương");
            return;
        }
        int newID = Integer.parseInt(idText);
        if(string == "add" || (FamilyWorkspaceController.selected.getMaHo() != newID && string == "edit")) {
            if(CitizenService.exists(newID)) {
                saveMessageLabel.setText("ID đã tồn tại");
                return;
            }
        }

        Citizen citizen = new Citizen();
        citizen.setMaHo(newID);
        citizen.setTenChuHo(nameTF.getText());
        citizen.setSoThanhVien(numTF.getText());
        if(string == "edit") {
            if(newID != FamilyWorkspaceController.selected.getMaHo()) {
                saveMessageLabel.setText("Không thể thay đổi ID");
                return;
            }
            FamilyWorkspaceController.citizenList.remove(FamilyWorkspaceController.selected);
        }
        FamilyWorkspaceController.citizenList.add(citizen);
        try {
            if(string == "add") {
                CitizenService.addCitizen(citizen);
            }
            if(string == "edit") {
                CitizenService.editCitizen(citizen);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }
}

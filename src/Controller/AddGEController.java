package Controller;

import Service.GEService;
import Model.GeneralEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddGEController implements Initializable {
    @FXML
    private TextField idTF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextArea noteTA;
    @FXML
    private TextField moneyTF;
    @FXML
    private Label saveMessageLabel;

    @FXML
    private Button save;
    @FXML
    private Button cancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void cancelButtonOnAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saveOnAction(ActionEvent event) {
        if(idTF.getText().isBlank()) {
            saveMessageLabel.setText("Bạn chưa điền ID");
            return;
        }
        if(nameTF.getText().isBlank()) {
            saveMessageLabel.setText("Bạn chưa điền tên khoản phí");
            return;
        }
        if(moneyTF.getText().isBlank()) {
            saveMessageLabel.setText("Bạn chưa điền số tiền");
            return;
        }
        String idText = idTF.getText();
        String moneyText = moneyTF.getText();
        Pattern pattern = Pattern.compile("\\d*");
        Matcher matcher = pattern.matcher(idText);
        if(!matcher.matches()) {
            saveMessageLabel.setText("ID phải là số nguyên dương");
            return;
        }
        matcher = pattern.matcher(moneyText);
        if(!matcher.matches()) {
            saveMessageLabel.setText("Số tiền phải là số nguyên dương");
            return;
        }
        if(GEService.exists(Integer.parseInt(idText))) {
            saveMessageLabel.setText("ID đã tồn tại");
            return;
        }
        GeneralEvent generalEvent = new GeneralEvent();
        generalEvent.setId(Integer.parseInt(idTF.getText()));
        generalEvent.setName(nameTF.getText());
        generalEvent.setNote(noteTA.getText());
        generalEvent.setMoney(Integer.parseInt(moneyTF.getText()));
        GE_WorkspaceController.generalEvents.add(generalEvent);
        Stage stage = (Stage) save.getScene().getWindow();
        GEService.add(generalEvent);
        stage.close();
    }
}

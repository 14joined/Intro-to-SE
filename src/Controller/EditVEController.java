package Controller;

import Service.VE_Citizen;
import Service.VEService;
import Model.VoluntaryEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class EditVEController implements Initializable {

    @FXML
    private Label idLabel;
    @FXML
    private TextField nameTF;
    @FXML
    private TextArea noteTA;
    @FXML
    private DatePicker datePicker1;
    @FXML
    private DatePicker datePicker2;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<VE_Citizen, Integer> idCol;
    @FXML
    private TableColumn<VE_Citizen, String> nameCol;
    @FXML
    private TableColumn<VE_Citizen, String> moneyCol;
    @FXML
    private TableColumn<VE_Citizen, String> noteCol;

    @FXML
    private Label tongSoTienLabel;
    @FXML
    private Label soHoDongGopLabel;

    @FXML
    private Button save;
    @FXML
    private Button cancel;
    @FXML
    private Button update;

    public static ObservableList<VE_Citizen> veCitizens;
    public static VE_Citizen selected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int tongSoTien = 0, soHoDongGop = 0;
        tableView.setEditable(true);
        save.setDefaultButton(true);
        cancel.setCancelButton(true);
        VoluntaryEvent voluntaryEvent = VE_WorkspaceController.selected;
        idLabel.setText(String.valueOf(voluntaryEvent.getId()));
        nameTF.setText(voluntaryEvent.getName());
        noteTA.setText(voluntaryEvent.getNote());
        if(voluntaryEvent.getDate1() != null) {
            datePicker1.setValue(LocalDate.parse(voluntaryEvent.getDate1().toString()));
        }
        if(voluntaryEvent.getDate2() != null) {
            datePicker2.setValue(LocalDate.parse(voluntaryEvent.getDate2().toString()));
        }

        veCitizens = FXCollections.observableArrayList();
        List<VE_Citizen> list = VE_Citizen.getDataFromVEId(voluntaryEvent.getId());
        for(var i : list) {
            veCitizens.add(i);
            tongSoTien += Integer.parseInt(i.getSoTien());
            if(Integer.parseInt(i.getSoTien()) > 0) {
                soHoDongGop++;
            }
        }
        tongSoTienLabel.setText(String.valueOf(tongSoTien));
        soHoDongGopLabel.setText(String.valueOf(soHoDongGop));
        idCol.setCellValueFactory(new PropertyValueFactory<VE_Citizen, Integer>("MaHo"));
        nameCol.setCellValueFactory(new PropertyValueFactory<VE_Citizen, String>("tenChuHo"));
        moneyCol.setCellValueFactory(new PropertyValueFactory<VE_Citizen, String>("soTien"));
        noteCol.setCellValueFactory(new PropertyValueFactory<VE_Citizen, String>("ghiChu"));
        moneyCol.setCellFactory(TextFieldTableCell.<VE_Citizen>forTableColumn());
        moneyCol.setOnEditCommit((TableColumn.CellEditEvent<VE_Citizen, String> event) ->{
            TablePosition<VE_Citizen, String> pos = event.getTablePosition();
            String newMoney = event.getNewValue();
            int row = pos.getRow();
            VE_Citizen ve_citizen = event.getTableView().getItems().get(row);
            ve_citizen.setSoTien(newMoney);
            VE_Citizen.editVE_Citizen(ve_citizen);
        }
        );

        noteCol.setCellFactory(TextFieldTableCell.<VE_Citizen>forTableColumn());
        noteCol.setOnEditCommit((TableColumn.CellEditEvent<VE_Citizen, String> event) ->{
                    TablePosition<VE_Citizen, String> pos = event.getTablePosition();
                    String newNote = event.getNewValue();
                    int row = pos.getRow();
                    VE_Citizen ve_citizen = event.getTableView().getItems().get(row);
                    ve_citizen.setGhiChu(newNote);
                    VE_Citizen.editVE_Citizen(ve_citizen);
                }
        );

        tableView.setItems(veCitizens);
    }

    @FXML
    public void cancelOnAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saveOnAction(javafx.event.ActionEvent event) {
        VoluntaryEvent voluntaryEvent = new VoluntaryEvent();
        voluntaryEvent.setId(Integer.parseInt(idLabel.getText()));
        voluntaryEvent.setName(nameTF.getText());
        if(datePicker1.getValue() != null) {
            voluntaryEvent.setDate1(java.sql.Date.valueOf(datePicker1.getValue()));
        }
        if(datePicker2.getValue() != null) {
            voluntaryEvent.setDate2(java.sql.Date.valueOf(datePicker2.getValue()));
        }
        voluntaryEvent.setNote(noteTA.getText());
        VE_WorkspaceController.voluntaryEventsList.remove(VE_WorkspaceController.selected);
        VE_WorkspaceController.voluntaryEventsList.add(voluntaryEvent);
        VEService.edit(voluntaryEvent);
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void updateOnAction(ActionEvent event) throws IOException {
        int tongSoTien = 0, soHoDongGop = 0;
        VoluntaryEvent voluntaryEvent = VE_WorkspaceController.selected;
        List<VE_Citizen> list = VE_Citizen.getDataFromVEId(voluntaryEvent.getId());
        for(var i : list) {
            tongSoTien += Integer.parseInt(i.getSoTien());
            if(Integer.parseInt(i.getSoTien()) > 0) {
                soHoDongGop++;
            }
        }
        tongSoTienLabel.setText(String.valueOf(tongSoTien));
        soHoDongGopLabel.setText(String.valueOf(soHoDongGop));
    }
}

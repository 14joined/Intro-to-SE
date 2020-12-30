package Controller;

import Service.GEService;
import Service.GE_Citizen;
import Model.GeneralEvent;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditGEController implements Initializable {
    @FXML
    private Label idLabel;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField moneyTF;
    @FXML
    private TextArea noteTA;
    @FXML
    private Button save;
    @FXML
    private Button cancel;

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<GE_Citizen, Integer> idCol;
    @FXML
    private TableColumn<GE_Citizen, String> nameCol;
    @FXML
    private TableColumn<GE_Citizen, String> moneyCol;
    @FXML
    private TableColumn<GE_Citizen, Boolean> isDoneCol;

    @FXML
    private Label tongTienCanThuLabel;
    @FXML
    private Label tongTienDaThuLabel;
    @FXML
    private Label tongTienConThieuLabel;
    @FXML
    private Label soHoDaThuLabel;
    @FXML
    private Label soHoChuaThuLabel;

    public static ObservableList<GE_Citizen> ge_citizens;
    public static GE_Citizen selected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.setEditable(true);
        GeneralEvent generalEvent = GE_WorkspaceController.selected;
        idLabel.setText(String.valueOf(generalEvent.getId()));
        nameTF.setText(generalEvent.getName());
        moneyTF.setText(String.valueOf(generalEvent.getMoney()));
        noteTA.setText(generalEvent.getNote());

        int tongTienCanThu = 0;
        int tongTienDaThu = 0;
        int soHoDaThu = 0;
        int soHoChuaThu = 0;

        ge_citizens = FXCollections.observableArrayList();
        List<GE_Citizen> list = GE_Citizen.getDataFromGEId(generalEvent.getId(), generalEvent.getMoney());
        for(var i : list) {
            ge_citizens.add(i);
            tongTienCanThu += Integer.parseInt(i.getSoTien());
            if(i.isDaNop()) {
                tongTienDaThu += Integer.parseInt(i.getSoTien());
                soHoDaThu++;
            } else {
                soHoChuaThu++;
            }
        }
        tongTienCanThuLabel.setText(String.valueOf(tongTienCanThu));
        tongTienDaThuLabel.setText(String.valueOf(tongTienDaThu));
        tongTienConThieuLabel.setText(String.valueOf(tongTienCanThu-tongTienDaThu));
        soHoDaThuLabel.setText(String.valueOf(soHoDaThu));
        soHoChuaThuLabel.setText(String.valueOf(soHoChuaThu));
        idCol.setCellValueFactory(new PropertyValueFactory<GE_Citizen, Integer>("MaHo"));
        nameCol.setCellValueFactory(new PropertyValueFactory<GE_Citizen, String>("tenChuHo"));
        moneyCol.setCellValueFactory(new PropertyValueFactory<GE_Citizen, String>("soTien"));

        isDoneCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GE_Citizen, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<GE_Citizen, Boolean> param) {
                GE_Citizen geCitizen = param.getValue();
                SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty(geCitizen.isDaNop());
                booleanProperty.addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                        geCitizen.setDaNop(t1);
                        GE_Citizen.edit(geCitizen);
                    }
                });
                return booleanProperty;
            }
        });

        isDoneCol.setCellFactory(new Callback<TableColumn<GE_Citizen, Boolean>, TableCell<GE_Citizen, Boolean>>() {
            @Override
            public TableCell<GE_Citizen, Boolean> call(TableColumn<GE_Citizen, Boolean> ge_citizenBooleanTableColumn) {
                CheckBoxTableCell<GE_Citizen, Boolean> cell = new CheckBoxTableCell<GE_Citizen, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });

        tableView.setItems(ge_citizens);
    }

    @FXML
    public void cancelOnAction(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saveOnAction(javafx.event.ActionEvent event) {
        GeneralEvent generalEvent = new GeneralEvent();
        generalEvent.setId(Integer.parseInt(idLabel.getText()));
        generalEvent.setName(nameTF.getText());
        generalEvent.setMoney(Integer.parseInt(moneyTF.getText()));
        generalEvent.setNote(noteTA.getText());
        Stage stage = (Stage) save.getScene().getWindow();
        GE_WorkspaceController.generalEvents.remove(GE_WorkspaceController.selected);
        GE_WorkspaceController.generalEvents.add(generalEvent);
        GEService.edit(generalEvent);
        stage.close();
    }

    @FXML
    public void updateOnAction(ActionEvent event) {
        int tongTienCanThu = 0;
        int tongTienDaThu = 0;
        int soHoDaThu = 0;
        int soHoChuaThu = 0;

        GeneralEvent generalEvent = GE_WorkspaceController.selected;
        List<GE_Citizen> list = GE_Citizen.getDataFromGEId(generalEvent.getId(), generalEvent.getMoney());
        for(var i : list) {
            tongTienCanThu += Integer.parseInt(i.getSoTien());
            if(i.isDaNop()) {
                tongTienDaThu += Integer.parseInt(i.getSoTien());
                soHoDaThu++;
            } else {
                soHoChuaThu++;
            }
        }
        tongTienCanThuLabel.setText(String.valueOf(tongTienCanThu));
        tongTienDaThuLabel.setText(String.valueOf(tongTienDaThu));
        tongTienConThieuLabel.setText(String.valueOf(tongTienCanThu-tongTienDaThu));
        soHoDaThuLabel.setText(String.valueOf(soHoDaThu));
        soHoChuaThuLabel.setText(String.valueOf(soHoChuaThu));
        idCol.setCellValueFactory(new PropertyValueFactory<GE_Citizen, Integer>("MaHo"));
        nameCol.setCellValueFactory(new PropertyValueFactory<GE_Citizen, String>("tenChuHo"));
        moneyCol.setCellValueFactory(new PropertyValueFactory<GE_Citizen, String>("soTien"));
    }
}

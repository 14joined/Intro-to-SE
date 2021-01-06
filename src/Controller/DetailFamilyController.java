package Controller;

import Model.Citizen;
import Model.VoluntaryEvent;
import Service.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DetailFamilyController implements Initializable {

    @FXML
    private Label name;
    @FXML
    private TableView tableView1;
    @FXML
    private TableView tableView2;
    @FXML
    private TableColumn<VE, Integer> idCol1;
    @FXML
    private TableColumn<VE, String> nameCol1;
    @FXML
    private TableColumn<VE, String> moneyCol1;
    @FXML
    private TableColumn<VE, String> noteCol1;
    @FXML
    private TableColumn<GE, Integer> idCol2;
    @FXML
    private TableColumn<GE, String> nameCol2;
    @FXML
    private TableColumn<GE, String> moneyCol2;

    public static ObservableList<VE> veList;
    public static ObservableList<GE> geList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Citizen citizen = FamilyWorkspaceController.selected;
        name.setText(citizen.getTenChuHo());
        veList = FXCollections.observableArrayList();
        List<VE> list = VE.getList(citizen.getMaHo());
        for(var i : list) {
            veList.add(i);
        }
        idCol1.setCellValueFactory(new PropertyValueFactory<VE, Integer>("id"));
        nameCol1.setCellValueFactory(new PropertyValueFactory<VE, String>("name"));
        moneyCol1.setCellValueFactory(new PropertyValueFactory<VE, String>("money"));
        noteCol1.setCellValueFactory(new PropertyValueFactory<VE, String>("note"));
        tableView1.setItems(veList);

        geList = FXCollections.observableArrayList();
        List<GE> list2 = GE.getList(citizen);
        for(var i : list2) {
            geList.add(i);
        }
        idCol2.setCellValueFactory(new PropertyValueFactory<GE, Integer>("id"));
        nameCol2.setCellValueFactory(new PropertyValueFactory<GE, String>("name"));
        moneyCol2.setCellValueFactory(new PropertyValueFactory<GE, String>("money"));
        tableView2.setItems(geList);

    }

    public static class VE {
        int id;
        String name;
        String money;
        String note;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public static List<VE> getList(int id) {
            List<VE> list = new ArrayList<>();
            Connection connection = DBConnection.getConnection();
            String sql = "select MaKhoan, Ten, SoTien, donggop.GhiChu from donggop, thuphidonggop\n" +
                    "where MaHo = "+ id +" and MaKhoan = thuphidonggop.Ma and SoTien > 0;";
            try{
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    VE ve = new VE();
                    ve.setId(rs.getInt(1));
                    ve.setName(rs.getString(2));
                    ve.setMoney(String.valueOf(rs.getInt(3)));
                    ve.setNote(rs.getString(4));
                    list.add(ve);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }
    }

    public static class GE{
        int id;
        String name;
        String money;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public static List<GE> getList(Citizen citizen) {
            List<GE> list = new ArrayList<>();
            Connection connection = DBConnection.getConnection();
            String sql = "select MaKhoan, Ten, Tien from dongphi, thuphibatbuoc\n" +
                    "where MaHo = "+ citizen.getMaHo() +" and MaKhoan = Ma and TrangThai = 1;";
            try{
                PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    GE ge = new GE();
                    ge.setId(rs.getInt(1));
                    ge.setName(rs.getString(2));
                    ge.setMoney(String.valueOf(Integer.parseInt(citizen.getSoThanhVien())* rs.getInt(3)) );
                    list.add(ge);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
        }
    }
}

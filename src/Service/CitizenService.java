package Service;

import Model.Citizen;
import Model.VoluntaryEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// Class làm việc với cơ sở dữ liệu
public class CitizenService{

    public List<Citizen> getList() {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * from hogiadinh";
        List<Citizen> list = new ArrayList<>();
        try{
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Citizen citizen = new Citizen();
                citizen.setMaHo(rs.getInt(1));
                citizen.setSoThanhVien(String.valueOf(rs.getInt(3)));
                citizen.setTenChuHo(rs.getString(2));
                list.add(citizen);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean exists(int id) {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT COUNT(idhogiadinh) FROM hogiadinh WHERE idhogiadinh = " + id;

        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if(rs.getInt(1) == 1) {
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public static void addCitizen(Citizen citizen) throws SQLException {
        String sql = "INSERT INTO `quanlythutien`.`hogiadinh` (`idhogiadinh`, `tenchuho`, `sothanhvien`) VALUES " +
                "('"+ citizen.getMaHo() +"', '" + citizen.getTenChuHo() + "', '" + citizen.getSoThanhVien() + "')";
        DBConnection.execute(sql);
        VE_Citizen veCitizen = new VE_Citizen();
        veCitizen.setSoTien("0");
        veCitizen.setGhiChu("");
        veCitizen.setTenChuHo(citizen.getTenChuHo());
        VEService VEService = new VEService();
        List<VoluntaryEvent> list = VEService.getList();
        for(var i : list) {
            sql = "INSERT INTO `quanlythutien`.`donggop` (`MaKhoan`, `MaHo`) VALUES " +
                    "('" + i.getId() +"', '"+ citizen.getMaHo() +"');";
            DBConnection.execute(sql);
        }
    }

    public static void deleteCitizen(Citizen citizen) {
        String sql = "DELETE FROM `quanlythutien`.`hogiadinh` WHERE (idhogiadinh = "+ citizen.getMaHo() + ");";
        DBConnection.execute(sql);
    }

    public static void editCitizen(Citizen citizen) {
        String sql = "UPDATE `quanlythutien`.`hogiadinh` SET"
                    +" `tenchuho` = '"+ citizen.getTenChuHo()
                    + "', `sothanhvien` = '"+ citizen.getSoThanhVien()
                    +"' WHERE (`idhogiadinh` = '"+ citizen.getMaHo() +"');";
        DBConnection.execute(sql);
    }

    public static void main(String[] args) {
        /*
        Citizen citizen = new Citizen(3, "Le Van C", 7);
        Connection connection = DBConnection.getConnection();
        String sql = "INSERT INTO `quanlythutien`.`hogiadinh` (`idhogiadinh`, `tenchuho`, `sothanhvien`) VALUES " +
                "("+ citizen.getMaHo() +", '" + citizen.getTenChuHo() + "', " + citizen.getSoThanhVien() + ")";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
        System.out.println(exists(1));
    }
}

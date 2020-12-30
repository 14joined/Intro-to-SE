package Service;

import Model.Citizen;
import Model.VoluntaryEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VEService {

    public List<VoluntaryEvent> getList() {
        Connection connection = DBConnection.getConnection();
        String sql = "Select * from thuphidonggop";
        List<VoluntaryEvent> list = new ArrayList<>();
        try{
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                VoluntaryEvent voluntaryEvent = new VoluntaryEvent();
                voluntaryEvent.setId(rs.getInt(1));
                voluntaryEvent.setName(rs.getString(2));
                voluntaryEvent.setDate1(rs.getDate(3));
                voluntaryEvent.setDate2(rs.getDate(4));
                voluntaryEvent.setNote(rs.getString(5));
                list.add(voluntaryEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void add(VoluntaryEvent voluntaryEvent) {
        Date date1 = voluntaryEvent.getDate1();
        Date date2 = voluntaryEvent.getDate2();
        String sql = "INSERT INTO `quanlythutien`.`thuphidonggop` (`Ma`, `Ten`";
        if(date1 != null) {
            sql += ", `NgayBatDau`";
        }
        if(date2 != null) {
            sql += ", `NgayKetThuc`";
        }
        sql += ", `GhiChu`)\n VALUES ('" + voluntaryEvent.getId() + "', '" + voluntaryEvent.getName() + "', ";
        if(date1 != null) {
            sql += "'" + date1.toString() + "', ";
        }
        if(date2 != null) {
            sql += "'" + date2.toString() + "', ";
        }
        sql += "'" + voluntaryEvent.getNote() + "');";
        DBConnection.execute(sql);
        CitizenService citizenService = new CitizenService();
        List<Citizen> list = citizenService.getList();
        for(var i : list) {
            sql = "INSERT INTO `quanlythutien`.`donggop` (`MaKhoan`, `MaHo`) " +
                    "VALUES ('"+ voluntaryEvent.getId() +"', '"+ i.getMaHo() +"');";
            DBConnection.execute(sql);
        }
    }

    public static void delete(VoluntaryEvent voluntaryEvent) {
        String sql = "DELETE FROM `quanlythutien`.`donggop` WHERE (MaKhoan = "+ voluntaryEvent.getId() + ");";
        DBConnection.execute(sql);
        sql = "DELETE FROM `quanlythutien`.`thuphidonggop` WHERE (Ma = "+ voluntaryEvent.getId() + ");";
        DBConnection.execute(sql);
    }

    public static void edit(VoluntaryEvent voluntaryEvent) {
        Date date1 = voluntaryEvent.getDate1();
        Date date2 = voluntaryEvent.getDate2();
        String sql = "UPDATE `quanlythutien`.`thuphidonggop` SET"
                + " `Ma` = '"+ voluntaryEvent.getId()+"',"
                + " `Ten` = '"+ voluntaryEvent.getName() +"',";
        if(date1 != null) {
            sql += " `NgayBatDau` = '"+ date1.toString() +"',";
        }
        if(date2 != null) {
            sql += " `NgayKetThuc` = '"+ date2.toString() +"',";
        }
        sql += " `GhiChu` = 'ghkv'" + " WHERE (`Ma` = '"+ voluntaryEvent.getId() + "');";
        DBConnection.execute(sql);
    }

    public static boolean exists(int id) {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT COUNT(Ma) FROM thuphidonggop WHERE Ma = " + id;

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

    public static void main(String[] args) {
        VEService generalEventDAOImpl = new VEService();
        List<VoluntaryEvent> lst = generalEventDAOImpl.getList();
        for(var s : lst){
            System.out.println(s.getId() + " " + s.getName() + " " + s.getDate1() + " " + s.getDate2() + " " + s.getNote());
        }
    }
}

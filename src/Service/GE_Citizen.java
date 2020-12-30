package Service;

import Controller.GE_WorkspaceController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GE_Citizen {
    private int maHo;
    private String tenChuHo;
    private String soTien;
    private boolean daNop;

    public int getMaHo() {
        return maHo;
    }

    public boolean isDaNop() {
        return daNop;
    }

    public void setDaNop(boolean daNop) {
        this.daNop = daNop;
    }

    public void setMaHo(int maHo) {
        this.maHo = maHo;
    }

    public String getTenChuHo() {
        return tenChuHo;
    }

    public void setTenChuHo(String tenChuHo) {
        this.tenChuHo = tenChuHo;
    }

    public String getSoTien() {
        return soTien;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }

    public static List<GE_Citizen> getDataFromGEId(int id, int money) {
        List<GE_Citizen> list = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        String sql = "select MaHo, tenchuho, TrangThai, sothanhvien from dongphi, hogiadinh\n" +
                "where MaHo = idhogiadinh and MaKhoan = " + id;
        try{
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                GE_Citizen geCitizen = new GE_Citizen();
                geCitizen.setMaHo(rs.getInt(1));
                geCitizen.setTenChuHo(rs.getString(2));
                if(rs.getInt(3) == 1) {
                    geCitizen.setDaNop(true);
                } else {
                    geCitizen.setDaNop(false);
                }
                geCitizen.setSoTien(String.valueOf(money*rs.getInt(4)));
                list.add(geCitizen);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void edit(GE_Citizen ge_citizen) {
        int x = 0;
        if(ge_citizen.isDaNop()) x = 1;
        String sql = "UPDATE `quanlythutien`.`dongphi` SET " +
                "`TrangThai` = '"+ x +"' WHERE (" +
                "`MaKhoan` = '"+ GE_WorkspaceController.selected.getId() +"') and (" +
                "`MaHo` = '"+ ge_citizen.getMaHo() +"');";
        DBConnection.execute(sql);
    }

    public static void main(String[] args) {
        List<GE_Citizen> list = getDataFromGEId(1, 72000);
        for(var i : list) {
            System.out.println(i.getTenChuHo() + " " + i.getTenChuHo());
        }
    }
}

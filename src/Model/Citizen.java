package Model;

public class Citizen {
    private int maHo;
    private String tenChuHo;
    private String soThanhVien;

    public Citizen(int id, String name, String num){
        this.maHo = id;
        this.tenChuHo = name;
        this.soThanhVien = num;
    }

    public Citizen(){

    }

    public int getMaHo() {
        return maHo;
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

    public String getSoThanhVien() {
        return soThanhVien;
    }

    public void setSoThanhVien(String soThanhVien) {
        this.soThanhVien = soThanhVien;
    }
}

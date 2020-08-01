package com.example.doandidong.Get;

public class Sanpham {
    public int masp;
    public String tensp;
    public double gia;
    public String hinhanh;
    public String thongtin;
    public int maloaisp;
    public int math;

    public Sanpham(int masp, String tensp, double gia, String hinhanh, String thongtin, int maloaisp, int math) {
        this.masp = masp;
        this.tensp = tensp;
        this.gia = gia;
        this.hinhanh = hinhanh;
        this.thongtin = thongtin;
        this.maloaisp = maloaisp;
        this.math = math;
    }

    public int getMasp() {
        return masp;
    }

    public String getTensp() {
        return tensp;
    }

    public double getGia() {
        return gia;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public String getThongtin() {
        return thongtin;
    }

    public int getMaloaisp() {
        return maloaisp;
    }

    public int getMath() {
        return math;
    }
}

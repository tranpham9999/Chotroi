package com.example.doandidong.Helper;


public class Product_Trangchu {
    private int id;
    private String title, shortdesc;
    private double rating, price;
    private String image;
    private  String tenngdung;
    private String avt;
    private String tenth;
    private String tinhtrang;
    private String diachi;
    private String tenloaisp;
    private String tendangnhapnguoiban;

    public String getTendangnhapnguoiban() {
        return tendangnhapnguoiban;
    }

    public String getTinhtrang() {
        return tinhtrang;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getTenloaisp() {
        return tenloaisp;
    }

    public String getTenth() {
        return tenth;
    }

    public String getTenngdung() {
        return tenngdung;
    }

    public String getAvt() {
        return avt;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public double getRating() {
        return rating;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public Product_Trangchu(int id, String title, String shortdesc, double rating, double price, String image, String tenngdung, String avt, String tinhtrang, String diachi, String tenloaisp, String tendangnhapnguoiban) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.rating = rating;
        this.price = price;
        this.image = image;
        this.tenngdung = tenngdung;
        this.avt = avt;
        this.tinhtrang = tinhtrang;
        this.diachi = diachi;
        this.tenloaisp = tenloaisp;
        this.tendangnhapnguoiban = tendangnhapnguoiban;

    }
}

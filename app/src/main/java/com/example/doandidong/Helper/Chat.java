package com.example.doandidong.Helper;

public class Chat {
    private int id;
    private String imageAvatar;
    private String tennguoidung;
    private String tendangnhap;
    private String message;

    public int getId() {
        return id;
    }

    public String getImageAvatar() {
        return imageAvatar;
    }

    public String getTennguoidung() {
        return tennguoidung;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public String getMessage() {
        return message;
    }

    public Chat(int id, String imageAvatar, String tennguoidung, String tendangnhap, String message) {
        this.id = id;
        this.imageAvatar = imageAvatar;
        this.tennguoidung = tennguoidung;
        this.tendangnhap = tendangnhap;
        this.message = message;
    }
}

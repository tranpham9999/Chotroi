package com.example.doandidong.Helper;

public class Comment {
    private int macmt;
    private String avatar;
    private String tenngdung;
    private String tendangnhap;
    private String content;
    private String time;

    public int getMacmt() {
        return macmt;
    }

    public void setMacmt(int macmt) {
        this.macmt = macmt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTenngdung() {
        return tenngdung;
    }

    public void setTenngdung(String tenngdung) {
        this.tenngdung = tenngdung;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Comment(int macmt, String avatar, String tenngdung, String tendangnhap, String content, String time) {
        this.macmt =macmt;
        this.avatar = avatar;
        this.tenngdung = tenngdung;
        this.tendangnhap = tendangnhap;
        this.content = content;
        this.time = time;
    }
}

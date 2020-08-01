package com.example.doandidong.Helper;

public class Message {
    private int id;
    private String message;
    private String sentAt;
    private String name;
    private String tendangnhap;
    private String sendtotendangnhap;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public String getSendtotendangnhap() {
        return sendtotendangnhap;
    }

    public void setSendtotendangnhap(String sendtotendangnhap) {
        this.sendtotendangnhap = sendtotendangnhap;
    }

    public Message(int id, String message, String sentAt, String name, String tendangnhap, String sendtotendangnhap) {
        this.id = id;
        this.message = message;
        this.sentAt = sentAt;
        this.name = name;
        this.tendangnhap = tendangnhap;
        this.sendtotendangnhap = sendtotendangnhap;
    }
}

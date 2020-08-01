package com.example.doandidong.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sinhvien {

@SerializedName("MANGDUNG")
@Expose
private String mANGDUNG;
@SerializedName("TENDANGNHAP")
@Expose
private String tENDANGNHAP;
@SerializedName("MATKHAU")
@Expose
private String mATKHAU;

public String getMANGDUNG() {
return mANGDUNG;
}

public void setMANGDUNG(String mANGDUNG) {
this.mANGDUNG = mANGDUNG;
}

public String getTENDANGNHAP() {
return tENDANGNHAP;
}

public void setTENDANGNHAP(String tENDANGNHAP) {
this.tENDANGNHAP = tENDANGNHAP;
}

public String getMATKHAU() {
return mATKHAU;
}

public void setMATKHAU(String mATKHAU) {
this.mATKHAU = mATKHAU;
}

}
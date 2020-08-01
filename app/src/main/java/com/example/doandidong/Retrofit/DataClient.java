package com.example.doandidong.Retrofit;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface DataClient {

    @FormUrlEncoded
    @POST("login.php")
    Call<List<Sinhvien>> Logindata(@Field("taikhoan") String taikhoan,@Field("matkhau") String matkhau);

}

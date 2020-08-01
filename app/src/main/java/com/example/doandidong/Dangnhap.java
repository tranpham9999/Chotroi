package com.example.doandidong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.doandidong.Get.SessionHandler;
import com.example.doandidong.Get.User;
import com.example.doandidong.Retrofit.API;
import com.example.doandidong.Retrofit.DataClient;
import com.example.doandidong.Retrofit.Sinhvien;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dangnhap extends AppCompatActivity {


    String taikhoan, matkhau;
    private SessionHandler session;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangnhap);

        session = new SessionHandler(getApplicationContext());
        loading = findViewById(R.id.loading_login);
        ImageButton goback = (ImageButton)findViewById(R.id.ibGoback);
        final Button Login = (Button)findViewById(R.id.btLogin);
        final EditText editTextTaikhoan = (EditText)findViewById(R.id.etUsername_Dangnhap);
        final EditText editTextMatkhau = (EditText)findViewById(R.id.etPassword_DangNhap);


        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dangnhap.this, MainActivity.class);
                startActivity(intent);

                //animation
//                Animatoo.animateSwipeRight(Dangnhap.this);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taikhoan = editTextTaikhoan.getText().toString();
                matkhau = editTextMatkhau.getText().toString();
                loading.setVisibility(View.VISIBLE);
                Login.setVisibility(View.GONE);
                if(taikhoan.length()>0 && matkhau.length()>0){
                    DataClient dataClient = API.getData();
                    Call<List<Sinhvien>> callback = dataClient.Logindata(taikhoan, matkhau);
                    callback.enqueue(new Callback<List<Sinhvien>>() {
                        @Override
                        public void onResponse(Call<List<Sinhvien>> call, Response<List<Sinhvien>> response) {
                            ArrayList<Sinhvien> mangtaikhoan = (ArrayList<Sinhvien>) response.body();
                            if(mangtaikhoan.size()>0){
                                session.loginUser(taikhoan);
                                Intent i= new Intent(Dangnhap.this, News.class);
                                startActivity(i);
                                Animatoo.animateZoom(Dangnhap.this);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Sinhvien>> call, Throwable t) {
                            Toast.makeText(Dangnhap.this, "Sai tên đăng nhập hoặc mật khẩu!!!", Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            Login.setVisibility(View.VISIBLE);
                        }
                    });
                }
                else if(matkhau.length()<=0){
                    editTextMatkhau.setError("Mật khẩu không được trống");
                    loading.setVisibility(View.GONE);
                    Login.setVisibility(View.VISIBLE);
                }
                else if(taikhoan.length()<=0){
                    editTextTaikhoan.setError("Tài khoản không được trống");
                    loading.setVisibility(View.GONE);
                    Login.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(Dangnhap.this, "Có lỗi xảy ra! Vui lòng nhập lại!!!", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    Login.setVisibility(View.VISIBLE);
                }



            }
        });


    }




}

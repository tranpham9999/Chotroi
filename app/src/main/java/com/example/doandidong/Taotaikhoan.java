package com.example.doandidong;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doandidong.Helper.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;

import retrofit2.http.FormUrlEncoded;

public class Taotaikhoan extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText mDisplayDate, etHovaten, etUsername, etPassword, etNgaysinh, etComfirmpassword;
    private Button btRegister;
    ImageButton btBack;

    private ProgressBar loading;
    private static final String KEY_EMPTY = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taotaikhoan);

        btBack = findViewById(R.id.ibGoback_taotk);
        mDisplayDate = findViewById(R.id.etNgaysinh);
        loading=findViewById(R.id.loading);
        etHovaten=findViewById(R.id.etHovaten) ;
        etUsername=findViewById(R.id.etUsername) ;
        etNgaysinh=findViewById(R.id.etNgaysinh) ;
        etPassword=findViewById(R.id.etPassword) ;
        etComfirmpassword=findViewById(R.id.etComfirmpassword) ;
        btRegister = findViewById(R.id.btRegister);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username = etUsername.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                final String hovaten = etHovaten.getText().toString().trim();
                final String ngaysinh = etNgaysinh.getText().toString().trim();
                final String confirmPassword = etComfirmpassword.getText().toString().trim();
                //check value
                if(validateInputs(username, password, hovaten, ngaysinh, confirmPassword)){
                    Regist(username, password, hovaten, ngaysinh);
                }

            }
        });

        findViewById(R.id.btSelectday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDaialog();
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void showDatePickerDaialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        //because month start from 0 to 11;
        month+=1;
        String date =  year + "-" + month + "-" + day;
        mDisplayDate.setText(date);

    }

    private void Regist(final String username, final String password, final String hovaten, final String ngaysinh){
        loading.setVisibility(View.VISIBLE);
        btRegister.setVisibility(View.GONE);



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_Register,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(Taotaikhoan.this,"Đăng kí thành công!!!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Taotaikhoan.this, Dangnhap.class);
                                startActivity(i);
                            }
                            else {
                                Toast.makeText(Taotaikhoan.this,"Tên đăng nhập đã tồn tại!!!" , Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btRegister.setVisibility(View.VISIBLE);
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Taotaikhoan.this,"error " +e.toString() , Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btRegister.setVisibility(View.VISIBLE);
                        }
                    }
                    },
                    new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Taotaikhoan.this,"Đăng kí that bai " + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btRegister.setVisibility(View.VISIBLE);
                    }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", username);
                params.put("matkhau", password);
                params.put("hovaten", hovaten);
                params.put("ngaysinh", ngaysinh);

                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private boolean validateInputs(String username, String password, String hovaten, String ngaysinh, String confirmPassword) {
        if(KEY_EMPTY.equals(username)){
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(password)){
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(hovaten)){
            etHovaten.setError("Họ và tên cannot be empty");
            etHovaten.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(ngaysinh)){
            etNgaysinh.setError("Ngày sinh cannot be empty");
            etNgaysinh.requestFocus();
            return false;
        }
        if(etPassword.getText().toString().length()<6){
            etPassword.setError("Mật khẩu phải có trên 6 ký tự");
            etPassword.requestFocus();
            return false;
        }
        if(etUsername.getText().toString().length()<6){
            etUsername.setError("Tài khoản phải có trên 6 ký tự");
            etUsername.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(confirmPassword)){
            etComfirmpassword.setError("Confirm Password cannot be empty");
            etComfirmpassword.requestFocus();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            etComfirmpassword.setError("Password and Confirm Password does not match");
            etComfirmpassword.requestFocus();
            return false;
        }

        return true;
    }
}

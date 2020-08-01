package com.example.doandidong;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doandidong.Fragment.TaiKhoanFragment;
import com.example.doandidong.Fragment.TrangChuFragment;
import com.example.doandidong.Get.SessionHandler;
import com.example.doandidong.Helper.URL;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Editprofile extends AppCompatActivity {
    private EditText tennguoidung, ngaysinh, diachi, sodienthoai, email;
    private CheckBox gioitinh_nam, gioitinh_nu;
    private Button btSelectday_edit, btHoantat_edit;
    private TextView tvDoimatkhau_edit, tvDoiavatar_edit;
    private CircleImageView avatar;
    private String tendangnhap;
    private Bitmap bitmap;
    private Toolbar toolbar;
    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;
    private int Request_Code_Image =123;

//    private String matkhau;
//    private EditText matkhaucu;
//    private EditText matkhaumoi;
//    private EditText nhaplaimatkhau;
//    private RequestQueue requestPass;
//    private TextView loidoipass;
//    private SessionHandler session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);



        toolbar = findViewById(R.id.toolbar_editprofile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Init();
        GetIntent(); // get intent ve
        CheckCb(); //check only one checkbox is checked

        tvDoiavatar_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, Request_Code_Image);
            }
        });


        btSelectday_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDaialog();
            }
        });

        btHoantat_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Showing progress dialog at user registration time.
//                progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
//                progressDialog.show();
//                Toast.makeText(getApplicationContext(), "Hmhmhm" , Toast.LENGTH_SHORT).show();

                // Calling method to get value from EditText.

                // Creating string request with post method.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_Chinhsuathongtin,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response == null){
                                    Toast.makeText(getApplicationContext(), "Không thể lấy về thông tin người dùng. Vui long thử lại.", Toast.LENGTH_LONG).show();
                                    return;
                                }


                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String Response = jsonObject.getString("message");
                                    if(Response.equals("success")) {
                                        Toast.makeText(getApplicationContext(), Response, Toast.LENGTH_SHORT).show();

                                        finish();

                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), Response, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), e.toString()+" abc", Toast.LENGTH_SHORT).show();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                                Toast.makeText(getApplicationContext(),"Failed!!! " + volleyError.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {

                        // Creating Map String Params.
                        Map<String, String> params = new HashMap<String, String>();
                        if(gioitinh_nam.isChecked()) {
                            params.put("gioitinh", "1");
                        }
                        else {
                            params.put("gioitinh", "0");
                        }
                        // Adding All values to Params.
                        params.put("tendangnhap", tendangnhap);
                        params.put("tennguoidung", tennguoidung.getText().toString().trim());
                        params.put("ngaysinh", ngaysinh.getText().toString().trim());
                        params.put("diachi", diachi.getText().toString().trim());
                        params.put("sodienthoai", sodienthoai.getText().toString().trim());
                        params.put("email", email.getText().toString().trim());
                        params.put("hinhanh", imageToString(bitmap));
//                        Log.i("img", imageToString(bitmap));
                        return params;
                    }

                };

                // Creating RequestQueue.
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                // Adding the StringRequest object into requestQueue.
                requestQueue.add(stringRequest);



            }
        });



    }


    private void CheckCb() {
        gioitinh_nam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gioitinh_nam.isChecked()){
                    gioitinh_nu.setChecked(false);
                }
                else{
                    gioitinh_nu.setChecked(true);
                }
            }
        });
        gioitinh_nu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gioitinh_nu.isChecked()){
                    gioitinh_nam.setChecked(false);
                }
                else{
                    gioitinh_nam.setChecked(true);
                }
            }
        });
    }

    private void GetIntent() {
        //get Intent
        Intent i = getIntent();
        Bundle b = i.getExtras();

        tendangnhap = b.getString("tendangnhap");

        tennguoidung.setText(b.getString("tennguoidung"));
        ngaysinh.setText(b.getString("ngaysinh"));
        diachi.setText(b.getString("diachi"));
        sodienthoai.setText(b.getString("sodienthoai"));
        email.setText(b.getString("email"));
//        Log.i("avt", b.getString("avatar"));
        if(b.getString("avatar").equals("")){
            Picasso.with(getApplicationContext()).load(R.drawable.ic_error_outline_black_24dp).into(avatar);
        }else {
            Picasso.with(getApplicationContext()).load(b.getString("avatar")).error(R.drawable.ic_error_avatar_120dp).placeholder(R.drawable.ic_fragment_toi_avatar_120dp).into(avatar);
        }

        if(b.getString("gioitinh").equals("Nam")){
            gioitinh_nam.setChecked(true);
        }
        else {
            gioitinh_nu.setChecked(true);
        }

        avatar.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) avatar.getDrawable();
        bitmap = drawable.getBitmap();


    }

    private void showDatePickerDaialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month+=1;
                        String date =  year + "-" + month + "-" + dayOfMonth;
                        ngaysinh.setText(date);
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void Init() {
        tennguoidung =  findViewById(R.id.etTennguoidung_edit);
        ngaysinh =  findViewById(R.id.etNgaysinh_eidt);
        diachi =  findViewById(R.id.etDiachi_edit);
        sodienthoai =  findViewById(R.id.etSodienthoai_edit);
        email =  findViewById(R.id.etEmail_edit);
        gioitinh_nam =  findViewById(R.id.cbNam);
        gioitinh_nu =  findViewById(R.id.cbNu);
        avatar = findViewById(R.id.ivAvatar_edit);
        btSelectday_edit =  findViewById(R.id.btSelectday_edit);
        btHoantat_edit = findViewById(R.id.btn_hoantat);
        tvDoiavatar_edit = findViewById(R.id.tvDoiAvatar_edit);

    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Request_Code_Image && resultCode == -1 && data!=null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                avatar.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}

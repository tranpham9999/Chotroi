package com.example.doandidong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Dialog;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doandidong.Get.SessionHandler;
import com.example.doandidong.Get.User;
import com.example.doandidong.Helper.Comment;
import com.example.doandidong.Helper.CommentAdapter;
import com.example.doandidong.Helper.URL;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectItem extends AppCompatActivity {
    private EditText et_comment;
    private Button btn_xong, btCall, btSMS, btChat;
    private Dialog dialog;
    private ImageButton btComment;
    private RecyclerView recyclerView;

    private Toolbar toolbar;
    private String tendangnhapnguoiban;

    private List<Comment> productList;
    private CommentAdapter adapter;

    private String cmt;
    private int masp;
    private String sdt = "";

    private SessionHandler session;

    private ImageView imageView;
    private TextView tensp, giasp, chitietsanpham, userbanhang, diachi, loaisp, tinhtrang;
    private CircleImageView viewyourprofile, imgAvt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item);

        productList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycleview_Comment);
        recyclerView.setHasFixedSize(true);

        adapter = new CommentAdapter(getApplication(), productList, new CommentAdapter.OnItemListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        toolbar = (Toolbar)findViewById(R.id.toolbar_selectitem);

        //setting toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        imageView = (ImageView)findViewById(R.id.imgXampleimg);
        tensp = (TextView)findViewById(R.id.etTensanpham);
        giasp= (TextView)findViewById(R.id.etGiasanpham);
        chitietsanpham = (TextView)findViewById(R.id.tvChitietsanpham);
        userbanhang = (TextView)findViewById(R.id.tvUserBanhang);
        diachi = (TextView)findViewById(R.id.tvLocation);
        loaisp = (TextView)findViewById(R.id.tvLoaisp);
        tinhtrang = (TextView)findViewById(R.id.tvTinhtrang);
        btCall = findViewById(R.id.btCall);
        btSMS = findViewById(R.id.btSMS);
        btChat = findViewById(R.id.btChat);
        viewyourprofile = findViewById(R.id.profile_image_banhang);
        viewyourprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = getIntent();
                final Bundle b = ii.getExtras();

                Intent i = new Intent(SelectItem.this, Profile.class);
                i.putExtra("tennguoibanhang", userbanhang.getText().toString().trim());
                i.putExtra("avatar", (String) b.get("avatar"));
                i.putExtra("tendangnhapnguoiban", tendangnhapnguoiban);
                startActivity(i);
            }
        });

        btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+sdt));
                startActivity(i);
            }
        });

        btSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("smsto:"+sdt));
                startActivity(i);
            }
        });
        btChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectItem.this, Chat.class);

                Intent ii = getIntent();
                final Bundle b = ii.getExtras();

                i.putExtra("MASP", masp);
                i.putExtra("TENDANGNHAPNGBAN", tendangnhapnguoiban);
                i.putExtra("tennguoibanhang", userbanhang.getText().toString().trim());
                i.putExtra("avatar", (String) b.get("avatar"));
                i.putExtra("tendangnhapnguoiban", tendangnhapnguoiban);
                startActivity(i);

            }
        });
        imgAvt = findViewById(R.id.profile_image_banhang);

        Getintent();

        Cmt();

        Getdulieu(masp);
        GetSdt(masp);



    }

    private void GetSdt(final int masp) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_getsdt,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response == null){
                            Toast.makeText(SelectItem.this, "Error", Toast.LENGTH_LONG).show();
                            return;
                        }
                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            sdt = jsonObject.getString("SDT");
                            Log.i("SDT", sdt);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(SelectItem.this,"Failed!!! " + volleyError.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {


                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.

                params.put("MASP", String.valueOf(masp));


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(SelectItem.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void Getintent() {
        Intent ii = getIntent();
        final Bundle b = ii.getExtras(); //Lấy intent về

        if(b!=null){

            //Lấy dữ liệu được truyền từ intent ra bằng hàm get
            String data = (String)b.get("Title");
            String shortdesc = (String)b.get("Shortdesc");
            String gia = b.get("Price").toString();
            final String image = (String) b.get("Img");
            String avt = (String)b.get("avatar");
            String tenngbanhang = (String)b.get("tenngban");
            String ttrang = (String)b.get("tinhtrang");
            String location = (String)b.get("diachi");
            String loaisanpham = (String)b.get("tenloaisp");
            tendangnhapnguoiban = (String)b.get("tendangnhapnguoiban");
            masp = b.getInt("masp");
            Log.i("avt", avt);


            //Hiển thị lên textview và imgview
            tensp.setText(data);
//            giasp.setText(gia+ " Đ");
            giasp.setText(String.format("%,.0f", Float.valueOf(gia))+ " Đ");
            Picasso.with(getApplicationContext()).load(image).into(imageView);
            if(avt.equals("")){
                Picasso.with(getApplicationContext()).load(R.drawable.ic_error_avatar_120dp).placeholder(R.drawable.ic_cloud_download_black_24dp).error(R.drawable.ic_error_outline_black_24dp).into(imgAvt);
            }
            else {
                Picasso.with(getApplicationContext()).load(avt).placeholder(R.drawable.ic_cloud_download_black_24dp).error(R.drawable.ic_error_outline_black_24dp).into(imgAvt);
            }


            userbanhang.setText(tenngbanhang);
            chitietsanpham.setText(shortdesc);
            tinhtrang.setText(ttrang);
            diachi.setText(location);
            loaisp.setText(loaisanpham);
//            thuonghieu.setText(tenthuonghieu);

        }
    }

    private void Cmt() {
        btComment =  findViewById(R.id.imageButtonComment);
        btComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(SelectItem.this);
                dialog.setContentView(R.layout.dialog_cmt);
                dialog.show();
                et_comment = (EditText) dialog.findViewById(R.id.et_comment);
                btn_xong = (Button) dialog.findViewById(R.id.btXong);
                btn_xong.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        cmt = et_comment.getText().toString();

                        if(cmt.isEmpty())
                        {
                            et_comment.setError("Vui lòng nhập comment!");
                            return;
                        }

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_postcomment,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        if(response == null){
                                            Toast.makeText(SelectItem.this, "Error", Toast.LENGTH_LONG).show();
                                            return;
                                        }
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            String Response = jsonObject.getString("message");
                                            if(Response.equals("success")){
                                                Toast.makeText(SelectItem.this, "Comment thành công!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                                return;
                                            }
                                            if(Response.equals("false")){
                                                Toast.makeText(SelectItem.this, "Comment thất bại!", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {

                                        Toast.makeText(SelectItem.this,"Failed!!! " + volleyError.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() {

                                session = new SessionHandler(getApplicationContext());
                                User user = session.getUserDetails();

                                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                                // Creating Map String Params.
                                Map<String, String> params = new HashMap<String, String>();
                                // Adding All values to Params.
                                params.put("CMT", cmt);
                                params.put("TENDANGNHAP", user.getTendangnhap());
                                params.put("MASP", String.valueOf(masp));
                                params.put("THOIGIAN", currentDate);

                                Log.i("time", currentDate);

                                return params;
                            }

                        };

                        // Creating RequestQueue.
                        RequestQueue requestQueue = Volley.newRequestQueue(SelectItem.this);

                        // Adding the StringRequest object into requestQueue.
                        requestQueue.add(stringRequest);

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        productList.clear();
                                        adapter.notifyDataSetChanged();
                                        Getdulieu(masp);
                                        dialog.dismiss();
//                                        dialog.cancel();

                                    }
                                }, 1500);



                    }
                });
            }
        });
    }

    private void Getdulieu(final int masp) {

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_getcomment, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("nnn", response);
                if (response != null) {
                    int macmt;
                    String avt, content, tenngcmt, time, tendangnhapngcmt;
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            macmt = jsonObject.getInt("MACMT");
                            avt = jsonObject.getString("AVATAR");
                            content = jsonObject.getString("CONTENT");
                            tenngcmt = jsonObject.getString("TENNGDUNG");
                            time = jsonObject.getString("THOIGIAN");
                            tendangnhapngcmt = jsonObject.getString("TENDANGNHAP");


                            productList.add(new Comment(macmt, avt, tenngcmt, tendangnhapngcmt, content, time));
                            adapter.notifyDataSetChanged();     //cập nhật lại mỗi lần reload
//                            Log.i("testtt", String.valueOf(adapter.getItemCount()));
//                            if (adapter.getItemCount() >= 1){
//                                recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, adapter.getItemCount() - 1);
//                                Log.i("testtt", "go");
//                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("MASP", String.valueOf(masp));
                return params;

            }
        };
        requestQueue.add(stringRequest);
    }
}

package com.example.doandidong;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doandidong.Fragment.ListItemFragment;
import com.example.doandidong.Get.SessionHandler;
import com.example.doandidong.Get.User;
import com.example.doandidong.Helper.URL;
import com.google.gson.JsonObject;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    private ImageButton btback;
    private CircleImageView avatar;
    private TextView tenngdung, soluongbaidang, soluongdaban, tvDanhgia, tvDiachi;
    private Toolbar toolbar;
    private LinearLayout llDangban, llDaban;
    private SessionHandler session;
    private RatingBar ratingBar;

    private Float TrungbinhRating = 0f;
    private String tendangnhapnguoiban = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //setting toolbar
        toolbar = findViewById(R.id.toolbar_profile);
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


        //Init
        llDangban = findViewById(R.id.llDangban_profile);
        avatar = findViewById(R.id.imgAvatar_viewprofile);
        tenngdung = findViewById(R.id.tvTenngdung_viewprofile);
        soluongbaidang = findViewById(R.id.tvSoluongdangban_Profile);
        tvDanhgia = findViewById(R.id.tvDanhgia_viewprofile);
        ratingBar = findViewById(R.id.Ratingbar_viewprofile);
        tvDiachi = findViewById(R.id.tvDiachi_viewprofile);

        tvDanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                View layout= null;
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layout = inflater.inflate(R.layout.rating, null);
                final RatingBar ratingBar = (RatingBar)layout.findViewById(R.id.ratingBar);
                builder.setTitle("Đánh giá người dùng này");
                builder.setMessage("Hãy chọn số sao phù hợp với chất lượng mặt hàng của người dùng này.");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Float value = ratingBar.getRating();
                        postRating(value);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.setView(layout);
                builder.show();
            }
        });


        //get Intent
        Intent ii = getIntent();
        Bundle b = ii.getExtras();
        String avt = (String)b.get("avatar");
        //load avatar
        if(avt.equals("")){
            Picasso.with(getApplicationContext()).load(R.drawable.ic_error_avatar_120dp).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_cloud_download_black_24dp).error(R.drawable.ic_error_outline_black_24dp).into(avatar);

        }else{
            Picasso.with(getApplicationContext()).load((String)b.get("avatar")).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_cloud_download_black_24dp).error(R.drawable.ic_error_outline_black_24dp).into(avatar);

        }
        tenngdung.setText((String)b.get("tennguoibanhang"));
        tendangnhapnguoiban = (String)b.get("tendangnhapnguoiban");
        //get số lượng sp đang bán
        getspdangban(tendangnhapnguoiban);
        //get Rating của người bán
        getRating();
        //get dia chi
        getDiachi();
        //Setting fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_profile, new ListItemFragment(tendangnhapnguoiban)).commit();
        llDangban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_profile, new ListItemFragment(tendangnhapnguoiban)).commit();

            }
        });



    }

    private void getDiachi() {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_Getnguoidung, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        tvDiachi.setText(jsonObject.getString("DIACHI"));

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
                params.put("taikhoan", getIntent().getExtras().getString("tendangnhapnguoiban"));
                return params;

            }
        };
        requestQueue.add(stringRequest);
    }

    //Hàm làm tròn 0.5
    public static float roundToHalf(float f) {
        return Math.round(f * 2) / 2.0f;
    }

    //Lấy về Rating của người bán
    private void getRating() {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_getrating, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Float temp = 0f;
                            temp = Float.parseFloat(jsonObject.getString("RATING"));
                            TrungbinhRating+= temp;

                        }
                        TrungbinhRating = TrungbinhRating/jsonArray.length();
                        Log.i("Star", TrungbinhRating.toString());
                        ratingBar.setRating(roundToHalf(TrungbinhRating));
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
                params.put("nguoiduocrate", getIntent().getExtras().getString("tendangnhapnguoiban"));
                return params;

            }
        };
        requestQueue.add(stringRequest);

    }

    //Post Rating đã vote lên
    private void postRating(final Float value) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_rating,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            return;
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String Response = jsonObject.getString("message");
                            if(Response.equals("success")){
                                Toast.makeText(Profile.this,"Đánh giá : "+value,Toast.LENGTH_LONG).show();

                            }
                            else {
                                Toast.makeText(getApplicationContext(), Response, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Vote thất bại vui lòng vote lại!", Toast.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Vote thất bại vui lòng vote lại!", Toast.LENGTH_LONG).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                session = new SessionHandler(getApplicationContext());
                User user = session.getUserDetails();

                //Gửi tendangnhap lên file PHP để bỏ vào câu sql lấy dữ liệu về
                Map<String, String> params = new HashMap<>();
                params.put("nguoiduocrate", getIntent().getExtras().getString("tendangnhapnguoiban"));
                params.put("nguoirate", user.getTendangnhap());
                params.put("rating", value.toString());

                return params;

            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest);
    }

    //Lấy về danh sách sp đã bán
    private void getspdaban(final String tendangnhap) {
        final String t = tendangnhap;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_getspdaban,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Không thể lấy về thông tin người dùng. Vui long thử lại.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        try {
                            //Response nhận về là 1 JsonArray nên mình phải bỏ nó vô Array
                            JSONArray jsonArray = new JSONArray(response);

//                            Log.i("QQQ", "["+jsonObject+"]");
                            //Lấy về số lượng bài đăng
                            int Soluong = 0;
                            Log.i("soluong", response);
                            for (int i = 0; i < jsonArray.length(); i++){
                                Soluong++;
                            }
                            String sl = Integer.toString(Soluong);
                            soluongdaban.setText(sl);
                        } catch (JSONException e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                //Gửi tendangnhap lên file PHP để bỏ vào câu sql lấy dữ liệu về
                Map<String, String> params = new HashMap<>();
                params.put("tendangnhapnguoiban", t);
                return params;

            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest);
    }

    //Lấy về dánh sách sp đang bán
    private void getspdangban(final String tendangnhap) {
        final String t = tendangnhap;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_getproductforuser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Không thể lấy về thông tin người dùng. Vui long thử lại.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        try {
                            //Response nhận về là 1 JsonArray nên mình phải bỏ nó vô Array
                            JSONArray jsonArray = new JSONArray(response);

//                            Log.i("QQQ", "["+jsonObject+"]");
                            //Lấy về số lượng bài đăng
                            int Soluong = 0;
                            Log.i("soluong", response);
                            for (int i = 0; i < jsonArray.length(); i++){
                                Soluong++;
                            }
                            String sl = Integer.toString(Soluong);
                            soluongbaidang.setText(sl);
                        } catch (JSONException e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                //Gửi tendangnhap lên file PHP để bỏ vào câu sql lấy dữ liệu về
                Map<String, String> params = new HashMap<>();
                params.put("tendangnhapnguoiban", t);
                return params;

            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        requestQueue1.add(stringRequest);
    }
}

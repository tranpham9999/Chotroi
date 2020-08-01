package com.example.doandidong.Fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doandidong.Dangnhap;
import com.example.doandidong.Editprofile;
import com.example.doandidong.Forsale;
import com.example.doandidong.Get.SessionHandler;
import com.example.doandidong.Get.User;
import com.example.doandidong.Helper.URL;
import com.example.doandidong.MainActivity;
import com.example.doandidong.R;

import com.example.doandidong.Taotaikhoan;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class TaiKhoanFragment extends Fragment {

    private static final String TAG = TaiKhoanFragment.class.getSimpleName();



    private String img;
    private TextView tennguoidung;
    private TextView tendangnhap;
    private TextView ngaysinh;
    private TextView gioitinh;
    private TextView diachi;
    private TextView sodienthoai;
    private TextView email;
    private TextView soluongbaidang;
    private TextView soluongchoxetduyet;
    private TextView soluongdaban;

    private TextView doipass;

    private CircleImageView avatar;
    private Button btEditprofile;
    private Button btLogout;
    private LinearLayout Dangban;
    private RelativeLayout Choxetduyet;
    private RatingBar ratingBar;
    private Float TrungbinhRating = 0f;


    private SessionHandler session;



    public static TaiKhoanFragment newInstance(String param1, String param2) {
        TaiKhoanFragment fragment = new TaiKhoanFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.i("test", "onCreate");

//
//        Toolbar mToolbar = (Toolbar) ((AppCompatActivity)getActivity()).findViewById(R.id.toolbar_trangchu);
//        TextView txtTitle =((TextView)mToolbar.findViewById(R.id.tvToolbar));
//        txtTitle.setText("Profile");

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Log.i("test", "onCreateView");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_taikhoan, container, false);


        Choxetduyet = view.findViewById(R.id.rlChoxetduyet);
        doipass = view.findViewById(R.id.txt_doipass);
        Dangban = view.findViewById(R.id.llDangban);
        soluongbaidang = view.findViewById(R.id.tvSoluongdangban);
        soluongchoxetduyet = view.findViewById(R.id.tvSoluongchoxetduyet);
        avatar = view.findViewById(R.id.avatar);
        tennguoidung = (TextView) view.findViewById(R.id.ten_nguoi_dung);
        tendangnhap = (TextView) view.findViewById(R.id.ten_dang_nhap);
        ngaysinh = (TextView) view.findViewById(R.id.ngay_sinh);
        gioitinh = (TextView) view.findViewById(R.id.gioi_tinh);
        diachi = (TextView) view.findViewById(R.id.dia_chi);
        sodienthoai = (TextView) view.findViewById(R.id.so_dien_thoai);
        email = (TextView) view.findViewById(R.id.email);
        btEditprofile = view.findViewById(R.id.bt_EditProfile);
        btLogout = view.findViewById(R.id.bt_Logout);
        ratingBar = view.findViewById(R.id.Ratingbar);


        getspdangban();
        getspchoxetduyet();
        getRatingbar();
        postTaikhoandanglogin();

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
                startActivity(i);
            }
        });



        Choxetduyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Forsale.class);
                i.putExtra("luachon", "Choxetduyet");
                startActivity(i);
            }
        });
        Dangban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Forsale.class);
                i.putExtra("luachon", "Dangban");
                startActivity(i);
            }
        });

        doipass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(getActivity());
                View customDialogView = li.inflate(R.layout.dialog_doipass, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setView(customDialogView);

                final EditText matkhaucu = (EditText) customDialogView.findViewById(R.id.et_matkhaucu);
                final EditText matkhaumoi = (EditText) customDialogView.findViewById(R.id.et_matkhaumoi1);
                final EditText nhaplaimatkhau = (EditText) customDialogView.findViewById(R.id.et_nhaplaimatkhau);

                alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(matkhaumoi.getText().toString().equals(nhaplaimatkhau.getText().toString()))
                                {

                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_Doipass,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {

                                                    if(response == null){
                                                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                                                        return;
                                                    }


                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response);
                                                        String Response = jsonObject.getString("message");
                                                        if(Response.equals("success")) {
                                                            Toast.makeText(getActivity(), Response, Toast.LENGTH_SHORT).show();
                                                        }
                                                        else {
                                                            Toast.makeText(getActivity(), Response, Toast.LENGTH_SHORT).show();
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                        Toast.makeText(getActivity(), e.toString()+" abc", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError volleyError) {

                                                    Toast.makeText(getActivity(),"Failed!!! " + volleyError.toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            }) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            // Creating Map String Params.
                                            if(isAdded()) {
                                                session = new SessionHandler(getActivity().getApplicationContext());
                                            }
                                            User user = session.getUserDetails();
                                            Map<String, String> params = new HashMap<String, String>();
                                            // Adding All values to Params.
                                            params.put("tendangnhap", user.getTendangnhap());
                                            params.put("matkhaucu", matkhaucu.getText().toString());
                                            params.put("matkhau", matkhaumoi.getText().toString());
                                            return params;
                                        }

                                    };

                                    // Creating RequestQueue.
                                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                                    // Adding the StringRequest object into requestQueue.
                                    requestQueue.add(stringRequest);
                                }
                                else
                                {
                                    Toast.makeText(getActivity(), "Mật khẩu mới không khớp" , Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        btEditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Editprofile.class);
                i.putExtra("tennguoidung", tennguoidung.getText().toString().trim());
                i.putExtra("tendangnhap", tendangnhap.getText().toString().trim());
                i.putExtra("ngaysinh", ngaysinh.getText().toString().trim());
                i.putExtra("gioitinh", gioitinh.getText().toString().trim());
                i.putExtra("diachi", diachi.getText().toString().trim());
                i.putExtra("sodienthoai", sodienthoai.getText().toString().trim());
                i.putExtra("email", email.getText().toString().trim());
                i.putExtra("avatar", img);

                startActivity(i);

            }
        });

        return view;
    }

    //Hàm làm tròn 0.5
    public static float roundToHalf(float f) {
        return Math.round(f * 2) / 2.0f;
    }

    private void getRatingbar() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

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
                        TrungbinhRating = roundToHalf(TrungbinhRating);
                        Log.i("Star", TrungbinhRating.toString());
                        ratingBar.setRating(TrungbinhRating);
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

                if(isAdded()) {
                    session = new SessionHandler(getActivity().getApplicationContext());
                }
                User user = session.getUserDetails();

                Map<String, String> params = new HashMap<>();
                params.put("nguoiduocrate", user.getTendangnhap());
                return params;

            }
        };
        requestQueue.add(stringRequest);
    }

//    private void getspdaban() {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_getspdaban,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        if (response == null) {
//                            Toast.makeText(getActivity(), "Không thể lấy về thông tin người dùng. Vui long thử lại.", Toast.LENGTH_LONG).show();
//                            return;
//                        }
//                        try {
//                            //Response nhận về là 1 JsonArray nên mình phải bỏ nó vô Array
//                            JSONArray jsonArray = new JSONArray(response);
//
//                            //Lấy về số lượng bài đăng
//                            int Soluong = 0;
//                            Log.i("soluong", response);
//                            for (int i = 0; i < jsonArray.length(); i++){
//                                Soluong++;
//                            }
//                            String sl = Integer.toString(Soluong);
//                            soluongdaban.setText(sl);
//                        } catch (JSONException e) {
//
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                    }
//                })
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                //Lấy tendangnhap của thằng đang login ra từ Class user đã tạo và đã lưu ở Dangnhap.activity
//                if(isAdded()) {
//                    session = new SessionHandler(getActivity().getApplicationContext());
//                }
//                User user = session.getUserDetails();
//                //Gửi tendangnhap lên file PHP để bỏ vào câu sql lấy dữ liệu về
//                Map<String, String> params = new HashMap<>();
//                params.put("tendangnhapnguoiban", user.getTendangnhap().toString());
//                return params;
//
//            }
//        };
//        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
//        requestQueue1.add(stringRequest);
//    }


    private void getspchoxetduyet() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_getspchoxetduyet,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            Toast.makeText(getActivity(), "Không thể lấy về thông tin người dùng. Vui long thử lại.", Toast.LENGTH_LONG).show();
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
                            soluongchoxetduyet.setText(sl);
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
                //Lấy tendangnhap của thằng đang login ra từ Class user đã tạo và đã lưu ở Dangnhap.activity
                if(isAdded()) {
                    session = new SessionHandler(getActivity().getApplicationContext());
                }
                User user = session.getUserDetails();
                //Gửi tendangnhap lên file PHP để bỏ vào câu sql lấy dữ liệu về
                Map<String, String> params = new HashMap<>();
                params.put("tendangnhapnguoiban", user.getTendangnhap().toString());
                return params;

            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
        requestQueue1.add(stringRequest);
    }

    private void getspdangban() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_Getsoluongsp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            Toast.makeText(getActivity(), "Không thể lấy về thông tin người dùng. Vui long thử lại.", Toast.LENGTH_LONG).show();
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
                //Lấy tendangnhap của thằng đang login ra từ Class user đã tạo và đã lưu ở Dangnhap.activity
                if(isAdded()) {
                    session = new SessionHandler(getActivity().getApplicationContext());
                }
                User user = session.getUserDetails();
                //Gửi tendangnhap lên file PHP để bỏ vào câu sql lấy dữ liệu về
                Map<String, String> params = new HashMap<>();
                params.put("tendangnhapnguoiban", user.getTendangnhap().toString());
                return params;

            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
        requestQueue1.add(stringRequest);
    }


    @Override
    public void onResume() {
        postTaikhoandanglogin();
        super.onResume();
        Log.i("test", "onResume");
        //update fragment when activity Editprofile finish();


    }


    private void postTaikhoandanglogin() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_Getnguoidung,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response == null) {
                            Toast.makeText(getActivity(), "Không thể lấy về thông tin người dùng. Vui long thử lại.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        try {
                            //Response nhận về là 1 JsonArray nên mình phải bỏ nó vô Array
                            JSONArray jsonArray = new JSONArray(response);

                            JSONObject jsonObject = jsonArray.getJSONObject(0);
//                            Log.i("QQQ", "["+jsonObject+"]");

                            img=jsonObject.getString("AVATAR");
                            if(img.equals("")){
                                if(isAdded()) {
                                    Picasso.with(getActivity().getApplicationContext()).load(R.drawable.ic_error_outline_black_24dp).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(avatar);
                                }
                            }
                            else {
                                if(isAdded()) {
                                    Picasso.with(getActivity().getApplicationContext())
                                            .load(jsonObject.getString("AVATAR")).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.ic_fragment_toi_avatar_120dp)
                                            .error(R.drawable.ic_error_avatar_120dp)
                                            .into(avatar);
                                }
                            }
                            tennguoidung.setText(jsonObject.getString("TENNGDUNG"));
                            tendangnhap.setText(jsonObject.getString("TENDANGNHAP"));
                            ngaysinh.setText(jsonObject.getString("NGAYSINH"));
                            if(jsonObject.getString("GIOITINH").equals("1"))
                            {
                                gioitinh.setText("Nam");

                            }
                            if(jsonObject.getString("GIOITINH").equals("0"))
                            {
                                gioitinh.setText("Nữ");
                            }
                            diachi.setText(jsonObject.getString("DIACHI"));
                            sodienthoai.setText(jsonObject.getString("SODIENTHOAI"));
                            email.setText(jsonObject.getString("EMAIL"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error: "+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Lấy tendangnhap của thằng đang login ra từ Class user đã tạo và đã lưu ở Dangnhap.activity
                if(isAdded()) {
                    session = new SessionHandler(getActivity().getApplicationContext());
                }
                User user = session.getUserDetails();
                //Gửi tendangnhap lên file PHP để bỏ vào câu sql lấy dữ liệu về
                Map<String, String> params = new HashMap<>();
                params.put("taikhoan", user.getTendangnhap().toString());
                return params;

            }
        };
        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
        requestQueue1.add(stringRequest);

    }


}
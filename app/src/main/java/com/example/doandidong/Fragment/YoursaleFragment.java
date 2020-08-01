package com.example.doandidong.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.doandidong.Helper.ProductAdapter_Yoursale;
import com.example.doandidong.Helper.Product_Trangchu;
import com.example.doandidong.Helper.URL;
import com.example.doandidong.R;
import com.example.doandidong.SelectItem;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YoursaleFragment extends Fragment implements ProductAdapter_Yoursale.OnItemListener {
    private View v;
    private List<Product_Trangchu> productList;
    private RecyclerView recyclerView;

    private int masp;
    private String tensp;
    private double gia ;
    private String hinhanh ;
    private String thongtin ;
    private int maloaisp ;
    private String tenngdung;
    private String avt;
    private String tinhtrang;
    private String tenloaisp;
    private String diachi;
    private String tendangnhap = "";
    private int Luachon = 0;

    private ProductAdapter_Yoursale adapter;


    public  YoursaleFragment(){

    }

    //lay ve nhung san pham dang ban neu i = 0;
    //lay ve nhung san pham dang cho xet duyet neu i = 1;
    //lay ve nhung san pham da ban neu i = 2;

    public  YoursaleFragment(String tendangnhap, int i){
        this.tendangnhap = tendangnhap;
        this.Luachon = i;
    }





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productList = new ArrayList<>();
        setHasOptionsMenu(true);

        Getdulieu();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void Getdulieu() {
        if(Luachon==0) {
            Log.i("ham111", tendangnhap);


            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_getproductforuser, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("rp1111", response);
                    if (response != null) {
                        masp = 0;
                        tensp = "";
                        gia = 0.0;
                        hinhanh = "";
                        thongtin = "";
                        maloaisp = 0;
                        tinhtrang = "";
                        diachi = "";
                        tenloaisp = "";
                        tenngdung = "";
                        avt = "";
                        String tendn = "";
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                masp = jsonObject.getInt("MASP");
                                tensp = jsonObject.getString("TENSP");
                                gia = jsonObject.getDouble("GIA");
                                hinhanh = jsonObject.getString("HINHANH");
                                thongtin = jsonObject.getString("THONGTIN");
//                            maloaisp = jsonObject.getInt("MALOAISP");
                                tenngdung = jsonObject.getString("TENNGDUNG");
                                avt = jsonObject.getString("AVATAR");
                                tinhtrang = jsonObject.getString("TINHTRANG");
                                diachi = jsonObject.getString("DIACHI");
                                tenloaisp = jsonObject.getString("TENLOAISP");
                                tendn = jsonObject.getString(("TENDANGNHAP"));

                                productList.add(new Product_Trangchu(masp, tensp, thongtin, 4.3, gia, hinhanh, tenngdung, avt, tinhtrang, diachi, tenloaisp, tendn));
                                adapter.notifyDataSetChanged();     //cập nhật lại mỗi lần reload
                                Toast.makeText(getActivity(), "succes", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                            Log.i("tendangnhap", e.toString());
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    Log.i("tendangnhap", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();
                    Log.i("ham", "coput");
                    params.put("tendangnhapnguoiban", tendangnhap);
                    return params;

                }
            };
            requestQueue.add(stringRequest);
        }
        // Lay ve nhung san pham dang cho xet duyet
        if(Luachon==1){
            final RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());

            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, URL.URL_getspchoxetduyet, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("rp1111", response);
                    if (response != null) {
                        masp = 0;
                        tensp = "";
                        gia = 0.0;
                        hinhanh = "";
                        thongtin = "";
                        maloaisp = 0;
                        tinhtrang = "";
                        diachi = "";
                        tenloaisp = "";
                        tenngdung = "";
                        avt = "";
                        String tendn="";
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                masp = jsonObject.getInt("MASP");
                                tensp = jsonObject.getString("TENSP");
                                gia = jsonObject.getDouble("GIA");
                                hinhanh = jsonObject.getString("HINHANH");
                                thongtin = jsonObject.getString("THONGTIN");
//                            maloaisp = jsonObject.getInt("MALOAISP");
                                tenngdung = jsonObject.getString("TENNGDUNG");
                                avt = jsonObject.getString("AVATAR");
                                tinhtrang = jsonObject.getString("TINHTRANG");
                                diachi = jsonObject.getString("DIACHI");
                                tenloaisp = jsonObject.getString("TENLOAISP");
                                tendn = jsonObject.getString(("TENDANGNHAP"));

                                productList.add(new Product_Trangchu(masp, tensp, thongtin, 4.3, gia, hinhanh, tenngdung, avt, tinhtrang, diachi, tenloaisp, tendn));
                                adapter.notifyDataSetChanged();     //cập nhật lại mỗi lần reload
                                Toast.makeText(getActivity(), "succes", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                            Log.i("tendangnhap", e.toString());
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    Log.i("tendangnhap", error.toString());
                }
            }){
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();
                    Log.i("ham", "coput");
                    params.put("tendangnhapnguoiban", tendangnhap);
                    return params;

                }
            };
            requestQueue1.add(stringRequest1);

        }else if(Luachon == 2){
            final RequestQueue requestQueue2 = Volley.newRequestQueue(getActivity());

            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, URL.URL_getspdaban, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("rp1111", response);
                    if (response != null) {
                        masp = 0;
                        tensp = "";
                        gia = 0.0;
                        hinhanh = "";
                        thongtin = "";
                        maloaisp = 0;
                        tinhtrang = "";
                        diachi = "";
                        tenloaisp = "";
                        tenngdung = "";
                        avt = "";
                        String tendn="";
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                masp = jsonObject.getInt("MASP");
                                tensp = jsonObject.getString("TENSP");
                                gia = jsonObject.getDouble("GIA");
                                hinhanh = jsonObject.getString("HINHANH");
                                thongtin = jsonObject.getString("THONGTIN");
//                            maloaisp = jsonObject.getInt("MALOAISP");
                                tenngdung = jsonObject.getString("TENNGDUNG");
                                avt = jsonObject.getString("AVATAR");
                                tinhtrang = jsonObject.getString("TINHTRANG");
                                diachi = jsonObject.getString("DIACHI");
                                tenloaisp = jsonObject.getString("TENLOAISP");
                                tendn = jsonObject.getString(("TENDANGNHAP"));

                                productList.add(new Product_Trangchu(masp, tensp, thongtin, 4.3, gia, hinhanh, tenngdung, avt, tinhtrang, diachi, tenloaisp, tendn));
                                adapter.notifyDataSetChanged();     //cập nhật lại mỗi lần reload
                                Toast.makeText(getActivity(), "succes", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                            Log.i("tendangnhap", e.toString());
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    Log.i("tendangnhap", error.toString());
                }
            }){
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> params = new HashMap<>();
                    Log.i("ham", "coput");
                    params.put("tendangnhapnguoiban", tendangnhap);
                    return params;

                }
            };
            requestQueue2.add(stringRequest2);

        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_smartphone, container, false);



        recyclerView = v.findViewById(R.id.recycleview_Smartphone); //Khai báo Recyclerview
        recyclerView.setHasFixedSize(true);



        adapter = new ProductAdapter_Yoursale(getContext(), productList, this);
        adapter.notifyDataSetChanged();

        //Thông qua adapter để lấy dữ liệu từ mảng hiển thị lên

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return v;
    }



    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(getActivity(), SelectItem.class);
        //Khởi tạo các biến lưu dữ liệu cần truyền qua activity tiếp theo
        String tensp = productList.get(position).getTitle();
        double price = productList.get(position).getPrice();
        String image = productList.get(position).getImage();
        String shortdesc = productList.get(position).getShortdesc();
        String tenngban = productList.get(position).getTenngdung();
        String avatar = productList.get(position).getAvt();
        String ttrang = productList.get(position).getTinhtrang();
        String location = productList.get(position).getDiachi();
        String loaisanpham = productList.get(position).getTenloaisp();
        String tendangnhapnguoiban = productList.get(position).getTendangnhapnguoiban();
//        String tenthuonghieu = productList.get(position).getTenth();


        //Put dữ liệu vào intent để truyền qua activity SelectItem
        i.putExtra("Title", tensp);
        i.putExtra("Shortdesc", shortdesc);
        i.putExtra("Price", price);
        i.putExtra("Img", image);
        i.putExtra("tenngban", tenngban);
        i.putExtra("avatar", avatar);
        i.putExtra("tinhtrang", ttrang);
        i.putExtra("diachi", location);
        i.putExtra("tenloaisp", loaisanpham);
        i.putExtra("tendangnhapnguoiban", tendangnhapnguoiban);
        i.putExtra("masp", productList.get(position).getId());


        adapter.notifyDataSetChanged();
//        i.putExtra("tenth", tenthuonghieu);

        startActivity(i);

    }

}


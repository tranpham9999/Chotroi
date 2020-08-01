package com.example.doandidong.Fragment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doandidong.Helper.ProductAdapter_Trangchu;
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

public class ListItemFragment extends Fragment implements ProductAdapter_Trangchu.OnItemListener{

    private View v;
    private List<Product_Trangchu> productList;
    private RecyclerView recyclerView;

    private SearchView.OnQueryTextListener queryTextListener;
    private SearchView searchView = null;

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
    private String search;
    private String tendangnhap = "";

    private int position = 0;

    ProductAdapter_Trangchu adapter;



    public ListItemFragment(){

    }

    public ListItemFragment(int i){
        position = i;
    }

    public ListItemFragment(String i){
        tendangnhap = i;
    }
    public ListItemFragment(String i, int a){
        tendangnhap = i;
        this.position = a;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setFocusable(false);
            searchView.setIconifiedByDefault(true);
            searchView.clearFocus();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    productList.clear();
                    search=searchView.getQuery().toString();

                    final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                            R.style.Theme_AppCompat_DayNight_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Đang xử lý...");
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                    //                Log.d(TAG,"Register");
                    GetKetQuaSearch();
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    progressDialog.dismiss();
                                }
                            }, 2000);

                    productList.clear();
//                    adapter.notifyDataSetChanged();


                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productList = new ArrayList<>();

        setHasOptionsMenu(true);

        Getdulieu();


    }



    private void Getdulieu() {
            if(position==1) {
                final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL.URL_getsmartphone, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
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
                                    tendangnhap = jsonObject.getString(("TENDANGNHAP"));

                                    productList.add(new Product_Trangchu(masp, tensp, thongtin, 4.3, gia, hinhanh, tenngdung, avt, tinhtrang, diachi, tenloaisp, tendangnhap));
                                    adapter.notifyDataSetChanged();     //cập nhật lại mỗi lần reload

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonArrayRequest);
            }
            else if(position==2){
                final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL.URL_getlaptop, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
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
                                    tendangnhap = jsonObject.getString(("TENDANGNHAP"));

                                    productList.add(new Product_Trangchu(masp, tensp, thongtin, 4.3, gia, hinhanh, tenngdung, avt, tinhtrang, diachi, tenloaisp, tendangnhap));
                                    adapter.notifyDataSetChanged();     //cập nhật lại mỗi lần reload

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonArrayRequest);
            }else if(position == 4){ //get san pham da ban
                final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_getspdaban, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                requestQueue.add(stringRequest);
            }
            else if(!tendangnhap.equals("")) {
                final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_getproductforuser, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                requestQueue.add(stringRequest);
            }
            else{
                final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL.URL_getcamera, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
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
                                    tendangnhap = jsonObject.getString(("TENDANGNHAP"));

                                    productList.add(new Product_Trangchu(masp, tensp, thongtin, 4.3, gia, hinhanh, tenngdung, avt, tinhtrang, diachi, tenloaisp, tendangnhap));
                                    adapter.notifyDataSetChanged();     //cập nhật lại mỗi lần reload

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonArrayRequest);
            }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_smartphone, container, false);


        recyclerView = v.findViewById(R.id.recycleview_Smartphone); //Khai báo Recyclerview
        recyclerView.setHasFixedSize(true);

        adapter = new ProductAdapter_Trangchu(getContext(), productList, this);
        adapter.notifyDataSetChanged();


        //Thông qua adapter để lấy dữ liệu từ mảng hiển thị lên

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);



        return v;
    }

    private void GetKetQuaSearch() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_search, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if(response.equals("[]")){
                        Toast.makeText(getContext(), "Không tìm thấy kết quả!", Toast.LENGTH_SHORT).show();
                    }
                    masp = 0;
                    tensp = "";
                    gia = 0.0;
                    hinhanh = "";
                    thongtin = "";
                    maloaisp = 0;
                    tinhtrang="";
                    diachi="";
                    tenloaisp="";
                    tenngdung="";
                    avt="";
                    for (int i = 0; i<jsonArray.length(); i++){
                        try {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            masp = jsonObject.getInt("MASP");
                            tensp = jsonObject.getString("TENSP");
                            gia = jsonObject.getDouble("GIA");
                            hinhanh =jsonObject.getString("HINHANH");
                            thongtin = jsonObject.getString("THONGTIN");
//                            maloaisp = jsonObject.getInt("MALOAISP");
                            tenngdung = jsonObject.getString("TENNGDUNG");
                            avt = jsonObject.getString("AVATAR");
                            tinhtrang=jsonObject.getString("TINHTRANG");
                            diachi=jsonObject.getString("DIACHI");
                            tenloaisp=jsonObject.getString("TENLOAISP");
                            tendangnhap = jsonObject.getString(("TENDANGNHAP"));

                            productList.add(new Product_Trangchu(masp,tensp,thongtin,4.3,gia,hinhanh,tenngdung,avt, tinhtrang, diachi, tenloaisp, tendangnhap));
                            adapter.notifyDataSetChanged();     //cập nhật lại mỗi lần reload

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("search", search);
                Log.i("srcv", search);
//                Toast.makeText(getContext(), search, Toast.LENGTH_SHORT).show();
                return params;
            }
        };
        requestQueue.add(stringRequest);
//        Toast.makeText(getContext(), search, Toast.LENGTH_SHORT).show();

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


//        i.putExtra("tenth", tenthuonghieu);

        startActivity(i);
    }
}

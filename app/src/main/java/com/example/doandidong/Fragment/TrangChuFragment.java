package com.example.doandidong.Fragment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.doandidong.Smartphone;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrangChuFragment extends Fragment implements ProductAdapter_Trangchu.OnItemListener {
    private View v;
    private List<Product_Trangchu> productList;
    private RecyclerView recyclerView;
    private ViewFlipper viewFlipper;
    private Button btSmartphone, btLaptop, btCamera;

    private SearchView.OnQueryTextListener queryTextListener;
    private SearchView searchView = null;

    private int masp;
    private String tensp;
    private double gia ;
    private String hinhanh ;
    private String thongtin ;
    private int maloaisp ;
    private String tenth ;
    private String tenngdung;
    private String avt;
    private String tinhtrang;
    private String tenloaisp;
    private String diachi;
    private String search;
    private String tendangnhap = "";

    ProductAdapter_Trangchu adapter;


    public  TrangChuFragment(){

    }
    public  TrangChuFragment(String tendangnhap){
        this.tendangnhap = tendangnhap;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchItem.expandActionView();
        searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.onActionViewExpanded();
            searchView.setIconifiedByDefault(false);
            searchView.setIconified(false);
            searchView.setQueryHint("Search here....");
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

    private void Actionviewflipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://scontent-hkg3-2.xx.fbcdn.net/v/t1.0-9/72950225_2741521642525316_281018958558003200_o.jpg?_nc_cat=110&_nc_oc=AQkU-9sc1ORgJwIQH0WXdLdaiDg3iC39P6TIMu6p67ecRrUr3IX5ThtaQiC11qUavhQ&_nc_ht=scontent-hkg3-2.xx&oh=86f50d7f7277d4ce2f8d56e4ad793555&oe=5E85D4ED");
        mangquangcao.add("https://cdn.tgdd.vn/2019/11/banner/thu-cu-note10-800-300-800x300-(4).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/11/banner/800-300-800x300-(25).png");
        mangquangcao.add("https://cdn.cellphones.com.vn/media/ltsoft/promotion/rog-phone-2-_512gb_-1600x600_911.png");
        mangquangcao.add("https://cdn.cellphones.com.vn/media/ltsoft/promotion/sliding_iphone_xs_blackfriday_2011.png");


        for (int i=0; i<mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getActivity());
            Picasso.with(getActivity()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setId(i);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int imageId = v.getId();
                    if(imageId==0){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.viettablet.com/xiaomi-redmi-k20-pro-prime"));
                        startActivity(browserIntent);
                    }
                    else if(imageId==1){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.thegioididong.com/thu-cu-doi-moi"));
                        startActivity(browserIntent);
                    }
                    else if(imageId==2){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.thegioididong.com/dtdd/samsung-galaxy-fold"));
                        startActivity(browserIntent);
                    }
                    else if(imageId==3){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://cellphones.com.vn/asus-rog-phone-2.html"));
                        startActivity(browserIntent);
                    }
                    else if(imageId==4){
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://cellphones.com.vn/apple-iphone-xs-64-gb-chinh-hang-vn.html"));
                        startActivity(browserIntent);
                    }
                }
            });
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3300);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);

    }


    private void Getdulieu() {

        if(tendangnhap.equals("")) {
            Log.i("ham", "1");

            final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL.URL_getsp, new Response.Listener<JSONArray>() {
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
        }else {
            Log.i("ham", "2");

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
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_trangchu, container, false);


        viewFlipper = v.findViewById(R.id.viewFlipper);
        Actionviewflipper();

        recyclerView = v.findViewById(R.id.recycleview); //Khai báo Recyclerview
        recyclerView.setHasFixedSize(true);

        //Thông qua adapter để lấy dữ liệu từ mảng hiển thị lên
        adapter = new ProductAdapter_Trangchu(getContext(), productList, this);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        btCamera = v.findViewById(R.id.btCamera);
        btLaptop = v.findViewById(R.id.btLaptop);
        btSmartphone = v.findViewById(R.id.btSmartphone);

        btSmartphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Smartphone.class);
                i.putExtra("position", 1);
                startActivity(i);
            }
        });
        btLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Smartphone.class);
                i.putExtra("position", 2);
                startActivity(i);
            }
        });
        btCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Smartphone.class);
                i.putExtra("position", 3);
                startActivity(i);
            }
        });

        v.clearFocus();
        return v;
    }

    private void GetKetQuaSearch() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_search, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("response11", response);
                try {
                    if(response.equals("[]")){
                        Toast.makeText(getContext(), "Không tìm thấy kết quả!", Toast.LENGTH_SHORT).show();
                    }
                    JSONArray jsonArray = new JSONArray(response);
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

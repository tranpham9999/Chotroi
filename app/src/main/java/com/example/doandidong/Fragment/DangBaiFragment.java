package com.example.doandidong.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doandidong.Forsale;
import com.example.doandidong.Get.SessionHandler;
import com.example.doandidong.Get.User;
import com.example.doandidong.Helper.URL;
import com.example.doandidong.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class DangBaiFragment extends Fragment {
    View v;
    ImageView imageView_upimage;
    ImageView imageView_upimage1;
    Button btUpload;
    Bitmap bitmap;
    EditText etGia, etTensp, etMota, etDiachi, etSDT;

    private SessionHandler session;

    private String strTinhTrang[]={"Mới","Như mới","Tốt","Khá","Tệ"};
    private String strLoaisp[]={"Laptop","Điện thoại","Máy ảnh"};

    private Spinner spnTinhTrang;
    private Spinner spnLoaiSanPham;

    private String Tinh_Trang;
    private String maloaisp;
    private String KEY_EMPTY = "";


    private int Request_Code_Image =123;
//    private static String URL_Upload="http://192.168.1.3/Server/uploadbaidang.php";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_dangbai, container, false);
        session = new SessionHandler(getActivity().getApplicationContext());
        User user = session.getUserDetails();

        imageView_upimage = v.findViewById(R.id.imageView_UpImage);
        imageView_upimage1 = v.findViewById(R.id.imageView_UpImage1);
        imageView_upimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, Request_Code_Image);
            }
        });
        btUpload=v.findViewById(R.id.btUpload);
        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uploadbaidang();
            }
        });

        etGia = v.findViewById(R.id.etGiaban);
        etTensp = v.findViewById(R.id.etTitle);
        etMota = v.findViewById(R.id.etDecribe);
        etDiachi = v.findViewById(R.id.etLocation);
        etSDT = v.findViewById(R.id.etSdt);



        setSpinner_Tinhtrang();
        setSpinner_Loaisp();




        return v;
    }

    public void setSpinner_Tinhtrang(){
        spnTinhTrang = (Spinner) v.findViewById(R.id.tinh_trang);
        ArrayAdapter<String> adapterTinhTrang = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, strTinhTrang);
        adapterTinhTrang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTinhTrang.setAdapter(adapterTinhTrang);

        spnTinhTrang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3)
            {
                ((TextView) arg0.getChildAt(0)).setTextSize(15);
                Tinh_Trang = arg0.getItemAtPosition(position).toString();
//                Toast.makeText(arg0.getContext(), Tinh_Trang, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });
    }
    public void setSpinner_Loaisp(){
        spnLoaiSanPham = (Spinner) v.findViewById(R.id.loai_sp);
        ArrayAdapter<String> adapterLoaisp = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, strLoaisp);
        adapterLoaisp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLoaiSanPham.setAdapter(adapterLoaisp);

        spnLoaiSanPham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long arg3)
            {

                ((TextView) arg0.getChildAt(0)).setTextSize(15);
//                Loai_Sp = arg0.getItemAtPosition(position).toString();
//                Toast.makeText(arg0.getContext(), Loai_Sp, Toast.LENGTH_SHORT).show();
                if(position==0){
                    maloaisp= Integer.toString(2); //laptop

                }
                else if(position==1){
                    maloaisp= Integer.toString(1); //dt

                }
                else if(position==3){
                    maloaisp= Integer.toString(3);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0)
            {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Request_Code_Image && resultCode == -1 && data!=null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imageView_upimage1.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean validateInputs(String diachi, String sdt, String gia, String title, String describe) {
        if(KEY_EMPTY.equals(diachi)){
            etDiachi.setError("Username cannot be empty");
            etDiachi.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(sdt)){
            etSDT.setError("Password cannot be empty");
            etSDT.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(gia)){
            etGia.setError("Họ và tên cannot be empty");
            etGia.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(title)){
            etTensp.setError("Ngày sinh cannot be empty");
            etTensp.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(describe)){
            etMota.setError("Confirm Password cannot be empty");
            etMota.requestFocus();
            return false;
        }

        return true;
    }

    private void Uploadbaidang(){
        if(!validateInputs(etDiachi.getText().toString().trim(), etSDT.getText().toString().trim(), etGia.getText().toString().trim(), etTensp.getText().toString().trim(), etMota.getText().toString().trim())){

            Toast.makeText(getActivity(), "Bạn phải điền đầy đủ dữ liệu!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_Upload, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
//                        Log.i("tagconvertstr", "[" + response + "]");


                        JSONObject jsonObject = new JSONObject(response);
                        String Response = jsonObject.getString("message");
                        if (Response.equals("success")) {

                            final Toast toast = Toast.makeText(getActivity(), "Đăng bài thành công!!! Bạn sẽ phải chờ tối thiểu 2 giờ để đước xét duyệt", Toast.LENGTH_LONG);
                            toast.show();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast.cancel();
                                    Intent i = new Intent(getActivity(), Forsale.class);
                                    i.putExtra("luachon", "Choxetduyet");
                                    startActivity(i);
                                }
                            }, 3000);


                        } else {
                            Toast.makeText(getActivity(), "Thất bại!!!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.toString() + "Có lỗi xảy ra!!!", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    session = new SessionHandler(getActivity().getApplicationContext());
                    User user = session.getUserDetails();

                    Map<String, String> params = new HashMap<>();

                    params.put("hinhanh", imageToString(bitmap));
                    params.put("tensp", etTensp.getText().toString().trim());
                    params.put("gia", etGia.getText().toString().trim());
                    params.put("thongtin", etMota.getText().toString().trim());
                    params.put("tendangnhap", user.getTendangnhap().toString());
                    params.put("maloaisp", maloaisp.toString());
                    params.put("tinhtrang", Tinh_Trang.toString());
                    params.put("diachi", etDiachi.getText().toString().trim());
                    params.put("sdt", etSDT.getText().toString().trim());

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toolbar mToolbar = (Toolbar) ((AppCompatActivity)getActivity()).findViewById(R.id.toolbar_trangchu);
//        TextView txtTitle =((TextView)mToolbar.findViewById(R.id.tvToolbar));
//        txtTitle.setText("Post");

    }

    private String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }
}

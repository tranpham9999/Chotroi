package com.example.doandidong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.doandidong.Fragment.ListItemFragment;
import com.example.doandidong.Fragment.YoursaleFragment;
import com.example.doandidong.Get.SessionHandler;
import com.example.doandidong.Get.User;

public class Forsale extends AppCompatActivity {
    private Toolbar toolbar;
    private String Luachon = "";
    private TextView txtTitle;
    private RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forsale);

        relativeLayout = findViewById(R.id.relaytiveLayoutForsale);

        toolbar = findViewById(R.id.toolbar_yoursale);
        setSupportActionBar(toolbar);
        txtTitle =((TextView)toolbar.findViewById(R.id.tvToolbar_yoursale));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        SessionHandler sessionHandler = new SessionHandler(getApplicationContext());
        User user = sessionHandler.getUserDetails();
        Log.i("user", user.toString());



        if(getIntent().getExtras().getString("luachon").equals("Dangban")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_yoursale, new YoursaleFragment(user.getTendangnhap(), 0)).commit();
            txtTitle.setText("Đang Bán");

        }
        else if(getIntent().getExtras().getString("luachon").equals("Choxetduyet")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_yoursale, new YoursaleFragment(user.getTendangnhap(), 1)).commit();
            txtTitle.setText("Chờ Xét Duyệt");
        }
        else if(getIntent().getExtras().getString("luachon").equals("Daban")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_yoursale, new YoursaleFragment(user.getTendangnhap(), 2)).commit();
            txtTitle.setText("Đã bán");
        }



    }
}

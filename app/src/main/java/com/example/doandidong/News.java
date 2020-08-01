package com.example.doandidong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.doandidong.Fragment.DangBaiFragment;
import com.example.doandidong.Fragment.TaiKhoanFragment;
import com.example.doandidong.Fragment.TinNhanFragment;
import com.example.doandidong.Fragment.TrangChuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class News extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        toolbar = (Toolbar)findViewById(R.id.toolbar_trangchu);

        //setting toolbar
        setSupportActionBar(toolbar);
        txtTitle =((TextView)toolbar.findViewById(R.id.tvToolbar));

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TrangChuFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =  new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId()){
                case R.id.navigation_trangchu:
                    selectedFragment = new TrangChuFragment();
                    txtTitle.setText("");
                    break;
                case R.id.navigation_tinnhan:
                    selectedFragment = new TinNhanFragment();
                    txtTitle.setText("Chat");
                    break;
                case R.id.navigation_dangbai:
                    selectedFragment = new DangBaiFragment();
                    txtTitle.setText("Post");
                    break;
                case R.id.navigation_taikhoan:
                    selectedFragment = new TaiKhoanFragment();
                    txtTitle.setText("Profile");
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };
}

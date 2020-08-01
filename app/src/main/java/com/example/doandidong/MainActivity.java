package com.example.doandidong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_dangnhap = (Button)findViewById(R.id.btDangNhap);
        final Button bt_taotaikhoan = (Button)findViewById(R.id.btTaoTaiKhoan) ;

        bt_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inDangnhap = new Intent(MainActivity.this, Dangnhap.class);
                startActivity(inDangnhap);
            }
        });

        bt_taotaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inTaotaikhoan = new Intent(MainActivity.this, Taotaikhoan.class);
                startActivity(inTaotaikhoan);
            }
        });
    }
}

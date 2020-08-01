package com.example.doandidong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.doandidong.Fragment.ListItemFragment;

public class Smartphone extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView txtTitle;
    private int position = 0;

    public Smartphone(){
        //default
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smartphone);


        toolbar = (Toolbar)findViewById(R.id.toolbar_Smartphone);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        txtTitle =((TextView)toolbar.findViewById(R.id.tvToolbar));

        Intent ii = getIntent();
        Bundle b = ii.getExtras();
         position = (int)b.get("position");

        if(position == 1){
            txtTitle.setText("Điện Thoại");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_Smartphone, new ListItemFragment(1)).commit();
        }
        else if(position == 2){
            txtTitle.setText("Laptop");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_Smartphone, new ListItemFragment(2)).commit();
        }
        else {
            txtTitle.setText("Máy ảnh");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_Smartphone, new ListItemFragment(3)).commit();
        }


    }

}

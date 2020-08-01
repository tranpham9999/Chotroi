package com.example.doandidong;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.doandidong.Get.SessionHandler;
import com.example.doandidong.Get.User;
import com.example.doandidong.Helper.Message;
import com.example.doandidong.Helper.ThreadAdapter;
import com.example.doandidong.Helper.URL;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.emitter.Emitter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btSend;
    private EditText etMessage;
    private RecyclerView recyclerView;
    private CircleImageView avatar;
    private TextView tvUsername;

    private ThreadAdapter threadAdapter;
    private List<Message> messageList;

    private String Tendangnhap = "";
    private String Userid= "";

    private SessionHandler session;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(URL.ROOT_Chat);
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("new message", onNewMessage);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        messageList = new ArrayList<>();

        GetoldMessage();

        session = new SessionHandler(getApplicationContext());
        final User user = session.getUserDetails();

        Userid = user.getTendangnhap();

        mSocket.on(user.getTendangnhap(), onNewMessage);
        mSocket.on("server_gui_message", onNewMessage);
        mSocket.connect();

        Intent ii = getIntent();
        final Bundle b = ii.getExtras();


        btSend = findViewById(R.id.buttonSend);
        etMessage = findViewById(R.id.editTextMessage);
        recyclerView = findViewById(R.id.recycleview_Chat);
        toolbar = findViewById(R.id.toolbar_chatp2p);



        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = layoutInflater.inflate(R.layout.custom_chat_bar, null);
        actionBar.setCustomView(actionBarView);


        avatar = findViewById(R.id.custom_profile_Image);
        tvUsername = findViewById(R.id.custom_profile_name);

//        Log.i("name11", b.getString("tennguoibanhang"));
        Picasso.with(getApplicationContext()).load(getIntent().getExtras().getString("avatar")).into(avatar);
        tvUsername.setText(b.getString("tennguoibanhang").toString());


        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Chat.this, Profile.class);
                i.putExtra("tennguoibanhang", tvUsername.getText().toString().trim());
                i.putExtra("avatar", (String) b.get("avatar"));
                i.putExtra("tendangnhapnguoiban", b.getString("TENDANGNHAPNGBAN"));
                startActivity(i);
            }
        });
        tvUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Chat.this, Profile.class);
                i.putExtra("tennguoibanhang", tvUsername.getText().toString().trim());
                i.putExtra("avatar", (String) b.get("avatar"));
                i.putExtra("tendangnhapnguoiban", b.getString("TENDANGNHAPNGBAN"));
                startActivity(i);
            }
        });



        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));





        mSocket.emit("client_gui_sourceid", user.getTendangnhap());

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = etMessage.getText().toString().trim();
                if(TextUtils.isEmpty(message)){
                    return;
                }
                etMessage.setText("");

                String from = user.getTendangnhap();
                String to = b.getString("TENDANGNHAPNGBAN");
                String sentat = getTimeStamp();


                mSocket.emit("client_gui_message",from, to, message, sentat);
            }
        });

    }

    public static String getTimeStamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    private void scrollToBottom() {

        threadAdapter.notifyDataSetChanged();
        if (threadAdapter.getItemCount() > 1)
            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, threadAdapter.getItemCount() - 1);
    }
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String message ="";
                    String from ="";
                    String to ="";
                    String sentat ="";
                    String name = "";


                    try {
                        message = data.getString("message");
                        from = data.getString("tu");
                        to = data.getString("den");
                        sentat = data.getString("time");
                        name = data.getString("name");

//                        Log.i("noidung", noidung);


//                        messageList.add(new Message(1, noidung, "2", "zzz", "zzz", "zzz"));
//                        threadAdapter = new ThreadAdapter(getApplicationContext(), messageList, "1");
                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view

                    messageList.add(new Message(1, message, sentat, name, from, to));
                    threadAdapter = new ThreadAdapter(getApplicationContext(), messageList, Userid);
                    recyclerView.setAdapter(threadAdapter);
                    scrollToBottom();
                }
            });
        }
    };
    private void GetoldMessage() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_getoldmessage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("response1", response);

                        if(response == null){
                            Toast.makeText(Chat.this, "Error", Toast.LENGTH_LONG).show();
                            return;
                        }
                        try {

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                messageList.add(new Message(jsonObject.getInt("ID"),
                                                            jsonObject.getString("MESSAGE"),
                                                            jsonObject.getString("SENTAT"),
                                                            jsonObject.getString("TENNGDUNG"),
                                                            jsonObject.getString("TENDANGNHAP"),
                                                            jsonObject.getString("SENDTOTENDANGNHAP")));
                                threadAdapter = new ThreadAdapter(getApplicationContext(), messageList, Userid);
                                recyclerView.setAdapter(threadAdapter);
                                scrollToBottom();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(Chat.this,"Failed!!! " + volleyError.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Intent ii = getIntent();
                final Bundle b = ii.getExtras();

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.

                params.put("sendfrom", Userid);
                params.put("sendto", b.getString("TENDANGNHAPNGBAN"));


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(Chat.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }
}

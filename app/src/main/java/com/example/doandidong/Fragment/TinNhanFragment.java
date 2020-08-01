package com.example.doandidong.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.example.doandidong.Get.SessionHandler;
import com.example.doandidong.Get.User;
import com.example.doandidong.Helper.Chat;
import com.example.doandidong.Helper.ChatAdapter;
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

public class TinNhanFragment extends Fragment implements ChatAdapter.OnItemListener{

    private View v;
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private List<Chat> productlist;
    private SessionHandler session;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productlist = new ArrayList<>();
        Getdulieu();

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_tinnhan, container, false);

        recyclerView = v.findViewById(R.id.recycleview_tinnhan);
        recyclerView.setHasFixedSize(true);

        adapter = new ChatAdapter(getContext(), productlist, this);
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);


        return v;
    }

    private void Getdulieu() {
        session = new SessionHandler(getContext());
        final User user = session.getUserDetails();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_getinbox,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response != null) {
                            JSONArray jsonArray = null;
                            try {
                                jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    if(jsonObject.getString("TENDANGNHAP").equals(user.getTendangnhap())){
                                        productlist.add(new Chat(jsonObject.getInt("ID"), jsonObject.getString("AVATAR"), jsonObject.getString("TENNGDUNG"), jsonObject.getString("SENDTOTENDANGNHAP"), jsonObject.getString("MESSAGE")));
                                        adapter.notifyDataSetChanged();
                                    }
                                    else {
                                        productlist.add(new Chat(jsonObject.getInt("ID"), jsonObject.getString("AVATAR"), jsonObject.getString("TENNGDUNG"), jsonObject.getString("TENDANGNHAP"), jsonObject.getString("MESSAGE")));
                                        adapter.notifyDataSetChanged();
                                    }
                                      //cập nhật lại mỗi lần reload


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                session = new SessionHandler(getContext());
                User user = session.getUserDetails();

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();
                // Adding All values to Params.

                params.put("tendangnhap", user.getTendangnhap());


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);


    }

    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(getActivity(), com.example.doandidong.Chat.class);
        i.putExtra("TENDANGNHAPNGBAN", productlist.get(position).getTendangnhap());
        i.putExtra("tennguoibanhang", productlist.get(position).getTennguoidung());
        i.putExtra("avatar", productlist.get(position).getImageAvatar());
        i.putExtra("tendangnhapnguoiban", productlist.get(position).getTendangnhap());
        startActivity(i);

    }
}

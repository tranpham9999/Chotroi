package com.example.doandidong.Retrofit;

import com.example.doandidong.Helper.URL;

import retrofit2.Retrofit;

public class API {


    public static DataClient getData(){
        return Client.getClient(URL.ROOT_URL).create(DataClient.class);
    }
}

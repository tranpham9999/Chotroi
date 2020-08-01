package com.example.doandidong.Get;
import android.content.Context;
import android.content.SharedPreferences;

public class SessionHandler {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USERNAME = "username";

//    private static final String KEY_FULL_NAME = "full_name";
//    private static final String KEY_EMPTY = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;

    public SessionHandler(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    public void loginUser(String username) {
        mEditor.putString(KEY_USERNAME, username);
        mEditor.commit();
    }


    /**
     * Fetches and returns user details
     *
     * @return user details
     */
    public User getUserDetails() {

        User user = new User();
        user.setTendangnhap(mPreferences.getString(KEY_USERNAME, "tranpham7777"));


        return user;
    }



}


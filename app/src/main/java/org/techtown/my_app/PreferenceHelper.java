package org.techtown.my_app;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper
{
    private final String INTRO = "intro";
    private final String id = "id";
    private SharedPreferences app_prefs;
    private Context context;

    public PreferenceHelper(Context context)
    {
        app_prefs = context.getSharedPreferences("shared", 0);
        this.context = context;
    }

    public void putIsLogin(boolean loginOrOut)
    {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putBoolean(INTRO, loginOrOut);
        edit.apply();
    }

    public void put_id(String loginOrOut)
    {
        SharedPreferences.Editor edit = app_prefs.edit();
        edit.putString(id, loginOrOut);
        edit.apply();
    }

}

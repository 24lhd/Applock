package com.lhd.sql;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;

import com.lhd.module.Config;

import java.util.ArrayList;

/**
 * Created by D on 8/17/2017.
 */

public class MySQL implements MySQLInter {
    DuongSQLite duongSQLite;

    Context context;

    @Override
    public void insertOneItemApp(String itemApp) {
        runQuery("INSERT INTO `app_locked`(`stt`,`package`) VALUES (NULL,'" + itemApp + "');");
    }

    public MySQL(Context context) {
        this.context = context;
        duongSQLite = new DuongSQLite();
    }

    @Override
    public void initDatabase(String name) {

        duongSQLite.createOrOpenDataBases(context, name);
    }

    @Override
    public void runQuery(String query) {
        duongSQLite.getDatabase().execSQL(query);
    }

    @Override
    public void removeOneItemApp(String itemApp) {

    }

    @Override
    public ArrayList<String> getAllItemApp() {
        Cursor cursor = duongSQLite.getDatabase().query(Config.TABLE_LIST, null, null, null, null, null, null);
        cursor.moveToFirst();
        ArrayList<String> strings = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            strings.add(cursor.getString(cursor.getColumnIndex(Config.CL_LIST_LOCKED)));
            cursor.moveToNext();
        }
        return strings;
    }

    @Override
    public boolean isExistsItemApp(String itemApp, ArrayList<String> itemApps) {
        return itemApps.contains(itemApp);
    }

    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;


}

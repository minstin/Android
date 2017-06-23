package com.example.home.finalproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by home on 2017-06-17.
 */

public class DeviceEventReceiver extends BroadcastReceiver {
    private DBHelper dbHelper;
    @Override
    public void onReceive(Context context, Intent intent) {
        dbHelper = new DBHelper(context);

        final String action = intent.getAction();

        if(Intent.ACTION_TIME_CHANGED.equals(action) || Intent.ACTION_TIMEZONE_CHANGED.equals(action)){
            dbHelper.drop2();
            Toast.makeText(context, "하루 누적 금액 초기화", Toast.LENGTH_LONG).show();
        }
    }
}

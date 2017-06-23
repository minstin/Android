package com.example.home.finalproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by home on 2017-05-28.
 */

public class SMSBroadcastReceiver extends BroadcastReceiver{
    public static final String TAG = "SMSBroadcastReceiver";
    Context context;

    Vibrator mVibe;

    public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onReceive(Context context, Intent intent) {
        mVibe = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);

        Log.i(TAG, "onReceive() 메소드 호출됨");

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Log.i(TAG, "SMS를 수신하였습니다.");

            Bundle bundle = intent.getExtras();
            Object[] objs = (Object[])bundle.get("pdus");
            SmsMessage[] messages = new SmsMessage[objs.length];

            int smsCount = objs.length;
            for(int i = 0; i < smsCount; i++){
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    String format = bundle.getString("format");
                    messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
                }else{
                    messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
                }
            }

            Date receivedDate = new Date(messages[0].getTimestampMillis());
            Log.i(TAG, "SMS received date : " + receivedDate.toString());

            String sender = messages[0].getOriginatingAddress();
            Log.i(TAG, "SMS sender : " + sender);

            String contents = messages[0].getMessageBody().toString();
            Log.i(TAG, "SMS contents : " + contents);


            Pattern pattern = Pattern.compile("[0-9]+[,]+\\d{3}+[원]");
            Matcher matcher = pattern.matcher(contents);

            Pattern pattern2 = Pattern.compile("[원]+[[A-Z가-힣0-9]+\\s]+[사]+[용]");
            Matcher matcher2 = pattern2.matcher(contents);

            Pattern pattern3 = Pattern.compile("[A-Z가-힣]+[체]+[크]");
            Matcher matcher3 = pattern3.matcher(contents);

            String authNumber = null;
            String spot = null;
            String cardname = null;

            if(matcher.find() && matcher2.find() && matcher3.find()){
                authNumber = matcher.group(0);
                spot = matcher2.group(0);
                cardname = matcher3.group(0);

            }
            authNumber = authNumber.replace("원", "");

            authNumber = authNumber.replace(",", "");
            spot = spot.replace("원\n", "");
            spot = spot.replace("사용", "");
            cardname = cardname.replace("체크", "");
            String cash = authNumber;



            //Intent myIntent = new Intent(context, InsertActivity.class);
            Intent myIntent = new Intent(context, EnterPasswordActivity.class);

            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);

            Resources res = context.getResources();

            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            builder.setContentTitle("체크카드 사용 !!")
                    .setContentText(spot + "//   금액 : " +cash)
                    .setSmallIcon(R.drawable.asd2)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.asd))
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .setWhen(System.currentTimeMillis())
                    .setDefaults(Notification.DEFAULT_ALL);



            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                builder.setCategory(Notification.CATEGORY_MESSAGE)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setVisibility(Notification.VISIBILITY_PUBLIC);
            }

            NotificationManager nm = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
            mVibe.vibrate(800);
            nm.notify(1234, builder.build());


            myIntent.putExtra("sender", sender);
            myIntent.putExtra("authNumber", cash);
            myIntent.putExtra("spot", spot);
            myIntent.putExtra("receivedDate", format.format(receivedDate));
            myIntent.putExtra("cardname", cardname);
            context.startActivity(myIntent);


        }
    }
}

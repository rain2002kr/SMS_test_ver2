package com.example.sms_test_ver2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmSReceiver extends BroadcastReceiver {
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Toast.makeText(context, "broadcast 문자받음 start", Toast.LENGTH_LONG).show();
            Bundle bundle = intent.getExtras();
            SmsMessage[] messages = parseSmsMessage(bundle);

            if (messages.length > 0) {
                String sender = messages[0].getOriginatingAddress();
                String contents = messages[0].getMessageBody().toString();
                Date receivedDate = new Date(messages[0].getTimestampMillis());

                Log.d(this.getClass().getName(), "sender: " + sender);
                Log.d(this.getClass().getName(), "contents: " + contents);
                Log.d(this.getClass().getName(), "received date: " + receivedDate);

                String[] regitSender = {"15888700", "15881688", "6505551212", "01046973907"};

                for(int i = 0; i < regitSender.length ; i++) {
                    if (regitSender[i].equals(sender)) {
                    //if(regitSender[i] == sender){
                        sendToActivity( context, sender, contents, format.format( receivedDate ) + " from broadCast" );
                        Toast.makeText( context, "broad send Sms " + sender + " : " + contents, Toast.LENGTH_LONG ).show();
                    }
                }
            }
        }
       // Toast.makeText(context, "broadcast 문자 못받음.", Toast.LENGTH_LONG).show();

    }

    private void sendToActivity(Context context, String sender, String contents, String receivedDate){
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("sender", sender);
        intent.putExtra("contents", contents);
        intent.putExtra("receivedDate", receivedDate );

        context.startActivity(intent);
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];
        for (int i = 0; i < objs.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }
        return messages;
    }
}

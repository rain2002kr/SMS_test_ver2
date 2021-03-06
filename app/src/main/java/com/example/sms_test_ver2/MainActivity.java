package com.example.sms_test_ver2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btStart,btStop,btPermission,btSend,btPerRecv,btPerSend,btPerRead,btVersion;
    EditText editphone,editsms;
    TextView textView;
    String phoneNo,message;
    String sender, contents, receivedDate;
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS =1 ;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =2 ;
    private static final int MY_PERMISSIONS_REQUEST_READ_SMS =3 ;
    private static final String CurVersion = "1.0.2";
    private static final String update = "20.01.10";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserfindViewid();
        UserPermission();
        UserSendSMS();
        UserReadSMS();
        UserSendSmSfromBroadCast();

    }

    public void UserfindViewid(){
        btPermission =(Button) findViewById(R.id.btPermission);
        btPerRecv =(Button) findViewById(R.id.btPerRecv);
        btPerSend =(Button) findViewById(R.id.btPerSend);
        btVersion =(Button) findViewById(R.id.btVersion);

        btSend =(Button) findViewById(R.id.btSend);
        editphone =(EditText) findViewById(R.id.editphone);
        editsms =(EditText) findViewById(R.id.editsms);
        textView =(TextView) findViewById(R.id.textView);

    }
    public void  UserPermission(){
        btPerRecv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPermissionCheck(MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        });
        btPerSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPermissionCheck(MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        });
        btVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                println("Current Version : "+ CurVersion +" Date :" + update);
            }
        });

    }

    public void UserSendSMS(){


        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNo = editphone.getText().toString();
                message = editsms.getText().toString();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, message, null, null);
                println("전송번호 : "+ phoneNo );
                println("내용 :" +message);

            }
        });

    }
    public void UserReadSMS(){
     ;

    }

    public void UserSendSmSfromBroadCast(){
        String telNo = "01056874135";
        Intent intent = getIntent();
        sender = intent.getStringExtra("sender");
        contents = intent.getStringExtra("contents");
        receivedDate = intent.getStringExtra("receivedDate");

        println("받은번호 : "+ sender );
        println("내용 :" +contents);
        println("전송번호 : "+ telNo);
        println("전송시간 : " +receivedDate);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        String telNo = "01056874135";
        sender = intent.getStringExtra("sender");
        contents = intent.getStringExtra("contents");
        receivedDate = intent.getStringExtra("receivedDate");
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(telNo, null, contents, null, null);

        println("받은번호 : "+ sender );
        println("내용 :" +contents);
        println("전송번호 : "+ telNo);
        println("전송시간 : " +receivedDate);

        super.onNewIntent(intent);

    }

    public void callPermissionCheck(int requestCode){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS :
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECEIVE_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.RECEIVE_SMS)) {
                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.RECEIVE_SMS
                                },
                                MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
                    }
                }
            case MY_PERMISSIONS_REQUEST_SEND_SMS :
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.SEND_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.SEND_SMS)) {
                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.SEND_SMS
                                },
                                MY_PERMISSIONS_REQUEST_SEND_SMS);
                    }
                }
            case MY_PERMISSIONS_REQUEST_READ_SMS :
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.READ_SMS)) {
                    } else {
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.READ_SMS
                                },
                                MY_PERMISSIONS_REQUEST_READ_SMS);
                    }
                }
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "SMS RECEIVE 권한 허용됨.",Toast.LENGTH_LONG).show();
                    println("SMS RECEIVE 권한 허용됨.");
                } else {
                    Toast.makeText(getApplicationContext(),"SMS RECEIVE 권한 없음.", Toast.LENGTH_LONG).show();
                    println("SMS RECEIVE 권한 없음.");
                    return;
                }
            }
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "SMS 전송 권한 허용됨.",Toast.LENGTH_LONG).show();
                    println("SMS 전송 권한 허용됨.");
                } else {
                    Toast.makeText(getApplicationContext(),"SMS 전송 권한 없음.", Toast.LENGTH_LONG).show();
                    println("SMS 전송 권한 없음.");
                    return;
                }
            }
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "SMS 읽기 권한 허용됨.",Toast.LENGTH_LONG).show();
                    println("SMS 읽기 권한 허용됨.");
                } else {
                    Toast.makeText(getApplicationContext(),"SMS 읽기 권한 없음.", Toast.LENGTH_LONG).show();
                    println("SMS 읽기 권한 없음.");
                    return;
                }
            }
        }
    }

    public void println(String data){
        textView.append(data + "\n");
    }



}

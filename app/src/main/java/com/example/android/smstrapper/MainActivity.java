package com.example.android.smstrapper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //Here in MainActivity we will write code for asking permission
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 0;


        private Button mButton;
        String[] nolist = {
            "+919355712808",
            "+91879456132",
            "+919729729273",
            "+919810871554",
            "+9198786449978"
    };
     EditText loc;
     EditText phn;  Context context = this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loc = (EditText) findViewById( R.id.loc);
        phn = (EditText) findViewById(R.id.phn);


        final Button btnYourButton = (Button) findViewById(R.id.sendButton);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                //TODO Set your button auto perform click.
                btnYourButton.performClick();
            }
        }, 5000);

        //check if the permission is not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            //if the permission is not been granted then check if the user has denied the permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
                //Do nothing as user has denied
            } else {
                //a pop up will appear asking for required permission i.e Allow or Deny
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
            }
        }

//Declare the timer
        Timer t = new Timer();
//Set the schedule function and rate
        t.scheduleAtFixedRate( new TimerTask() {
            private Handler updateUI = new Handler(){
                @Override
                public void dispatchMessage(Message msg) {
                    super.dispatchMessage(msg);
                    int noOfFunRun =0;
                     for (int b = 0; b < 5; b++) {
                         int xyz=0;
                         boolean noMatchCondition= false;
                        if (nolist[b].equals(MyReceiver.phoneNo)) {
                            noMatchCondition = true;
                        }

                    if (noMatchCondition==true){

                        TextView myTextView = (TextView) findViewById(R.id.textView);
                        myTextView.setText(MyReceiver.msg);
                        loc.setText(MyReceiver.phoneNo);
                        phn.setText(MyReceiver.msg);


                        break;
                    }

                     }


                }
            };
            public void run() {
                try {
                    updateUI.sendEmptyMessage(0);
                } catch (Exception e) {e.printStackTrace(); }
            }
        }, 2000,3000);
    }

    //after getting the result of permission requests the result will be passed through this method
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        //will check the requestCode
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS: {
                //check whether the length of grantResults is greater than 0 and is equal to PERMISSION_GRANTED
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Now broadcastreceiver will work in background
                    Toast.makeText(this, "Thankyou for permitting!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Well I can't do anything until you permit me", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    public void sendBtn(View view) {
        String locVar = loc.getText().toString();
        String phnVar = phn.getText().toString();
        loc.setText(MyReceiver.phoneNo);
        phn.setText(MyReceiver.msg);
        background bg = new background(this);
        bg.execute(locVar,phnVar);
    }


}

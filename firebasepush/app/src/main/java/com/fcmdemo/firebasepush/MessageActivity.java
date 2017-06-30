package com.fcmdemo.firebasepush;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

/**
 * Created by ss on 22.6.2017.
 */

public class MessageActivity extends Activity {

    public static boolean isActivityState = false;
    public static String isActivityName = "ActivityName";
    public static BroadcastReceiver mRegistrationBroadcastReceiver;

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        textView = (TextView) findViewById(R.id.messageText);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);

        String TokenId = FirebaseInstanceId.getInstance().getToken().toString();
        getFireBaseMessage(getApplicationContext());
    }


    @Override
    protected void onStart() {
        super.onStart();

        isActivityState = true;
        isActivityName = this.getLocalClassName();

        Toast.makeText(this, "Activity Name : " + isActivityName, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActivityState = false;
        isActivityName = "Null";

        Toast.makeText(this, "Activity Name : " + isActivityName, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter("PUSH_NOTIFICATION"));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    //BroadcastReceiver Message
    public static void getFireBaseMessage(Context context) {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

               /* if (intent.getAction().equals(FireBaseConfig.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(FireBaseConfig.TOPIC_GLOBAL);

                } else */
                if (intent.getAction().equals("PUSH_NOTIFICATION")) {
                    String message = intent.getStringExtra("message");
                    Toast.makeText(context, "Push notification: " + message, Toast.LENGTH_LONG).show();
                    //}
                }
            }

        };
    }
}
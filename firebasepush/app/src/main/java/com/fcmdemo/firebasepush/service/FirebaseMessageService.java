package com.fcmdemo.firebasepush.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.fcmdemo.firebasepush.MainActivity;
import com.fcmdemo.firebasepush.MessageActivity;
import com.fcmdemo.firebasepush.R;
import com.fcmdemo.firebasepush.content.FireBaseApiService;
import com.fcmdemo.firebasepush.content.RetrofitClient;
import com.fcmdemo.firebasepush.model.FirebaseData;
import com.fcmdemo.firebasepush.model.Message;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ss on 22.6.2017.
 */

public class FirebaseMessageService extends FirebaseMessagingService {

    FirebaseData firebaseDataS = new FirebaseData();
    public static int jobId = 1;
    public static int messageId = 2;
    public static final String KEY_NOTIFICATION_REPLY = "KEY_NOTIFICATION_REPLY";


    private static final String EOL = "\n";
    private static final String READ_ACTION =
            "com.example.android.messagingservice.ACTION_MESSAGE_READ";
    public static final String REPLY_ACTION =
            "com.example.android.messagingservice.ACTION_MESSAGE_REPLY";
    public static final String CONVERSATION_ID = "conversation_id";
    public static final String EXTRA_REMOTE_REPLY = "extra_remote_reply";
    public static final int MSG_SEND_NOTIFICATION = 1;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            String jsonData = remoteMessage.getData().toString();
            try {
                firebaseDataS = new FirebaseData();
                Gson gson = new Gson();

                firebaseDataS = gson.fromJson(jsonData, FirebaseData.class);
                if (firebaseDataS.getData().getPageActivity().equals("MessageActivity")) {
                    getMessageRegister(firebaseDataS);
                }

                handleDataMessage(firebaseDataS);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void handleDataMessage(FirebaseData firebaseData) {
        try {
            if (MessageActivity.isActivityState) {

                if (firebaseData.getData().getPageActivity().equals(MessageActivity.isActivityName)) {
                    if (jobId != messageId) {
                        getNotificationImage(firebaseData);
                    } else {
                        Intent pushNotification = new Intent("PUSH_NOTIFICATION");
                        pushNotification.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        pushNotification.putExtra("message", firebaseData.getData().getMessage());
                        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                    }
                } else {
                    sendNotificationMessage(firebaseData, null);
                }
            } else {
                if (firebaseData.getData().getPageActivity().equals("MessageActivity")) {
                    getNotificationImage(firebaseData);
                } else {
                    sendNotificationMessage(firebaseData, null);
                }

            }
        } catch (IllegalStateException e) {
            Log.e("FireBaseMessage", "Exception: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ImageDownload Method
    public void getNotificationImage(final FirebaseData firebaseData) throws Exception {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(firebaseData.getData().getImage())
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                System.out.println("request failed: " + e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                try {
                    Bitmap bmBitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    sendNotificationMessage(firebaseData, bmBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    // Send Notification Message
    private void sendNotificationMessage(FirebaseData firebaseData, Bitmap bitmap) {
        Class<?> className = MainActivity.class;

        try {
            className = Class.forName(getPackageName() + "." + firebaseData.getData().getPageActivity());

        } catch (Exception e) {
            Log.e("TAG", "Error: " + e.getStackTrace());
        }

        Intent intent = new Intent(this, className);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        /*  Message Intent  */
        Intent intnt = new Intent(this, MessageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingMessageIntent = PendingIntent.getActivity(this, 0, intnt,
                PendingIntent.FLAG_ONE_SHOT);

        /*  ReplyAction */
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_NOTIFICATION_REPLY)
                .setLabel("Reply")
                .build();
        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(
                android.R.drawable.ic_menu_save, "Provide ID", pendingMessageIntent)
                .addRemoteInput(remoteInput)
                .build();

        long[] pattern = {500, 500, 500, 500};

        if (bitmap != null) {
            Bitmap circleBitmap = getCircularBitmap(bitmap);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(circleBitmap)
                    .setContentTitle(firebaseData.getData().getTitle())
                    .setContentText(firebaseData.getData().getMessage())
                    .setAutoCancel(true)
                    .setVibrate(pattern)
                    .setContentIntent(pendingIntent)
                    .addAction(replyAction)
                    .addAction(android.R.drawable.ic_menu_compass, "Main", pendingIntent)
                    .addAction(android.R.drawable.ic_menu_directions, "Message", pendingMessageIntent);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());

        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(firebaseData.getData().getTitle())
                    .setContentText(firebaseData.getData().getMessage())
                    .setAutoCancel(true)
                    .setVibrate(pattern)
                    .setContentIntent(pendingIntent)
                    .addAction(replyAction)
                    .addAction(android.R.drawable.ic_menu_compass, "Main", pendingIntent)
                    .addAction(android.R.drawable.ic_menu_directions, "Message", pendingMessageIntent);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());

        }

    }

    // Retrofit Message Register
    public void getMessageRegister(FirebaseData firebaseData) {
        FireBaseApiService fireBaseApiService = RetrofitClient.getGroupApiService();
        Call<Message> messageCall = fireBaseApiService.getInsertMessaging(firebaseData.getData().getTitle(), firebaseData.getData().getMessage());

        messageCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.body().getSuccess() == 1) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }

                if (response.body().getSuccess() == 0) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public static Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

}

package com.example.xinuaut;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import androidx.core.app.NotificationCompat;

public class NotiHandler{
    private static final String CHANNEL_ID = "carpart_notification_channel";
    private final int notiID = 0;
    private NotificationManager man;
    private Context context;

    public NotiHandler(Context context) {
        this.context = context;
        this.man = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
    }

    private void createChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "CarPartShop Noti", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setDescription("A XINU alkalmazás értesítései!");
            this.man.createNotificationChannel(channel);
        }
    }

    public void send(String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("XINU Autóalkatrészek")
                .setContentText(message)
                .setSmallIcon(R.drawable.notixinu);

        this.man.notify(notiID, builder.build());
    }
}
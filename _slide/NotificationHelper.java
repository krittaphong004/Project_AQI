package com.example.project_aqi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
public class NotificationHelper {
    public static final String CHANNEL_ID =
            "AQI_CHANNEL";
    public static void createChannel(Context context){

        if(Build.VERSION.SDK_INT
                >= Build.VERSION_CODES.O){

            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "AQI Alert",
                            NotificationManager.IMPORTANCE_HIGH
                    );
            NotificationManager manager =
                    context.getSystemService(
                            NotificationManager.class);

            if(manager != null){

                manager.createNotificationChannel(
                        channel);
            }
        }
    }
    public static void showNotification(
            Context context,
            int aqi){

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(
                        context,
                        CHANNEL_ID)

                        .setSmallIcon(
                                android.R.drawable.ic_dialog_alert)

                        .setContentTitle(
                                "AQI Warning")

                        .setContentText(
                                "AQI สูง : " + aqi)
                        .setPriority(
                                NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat.from(context)
                .notify(1, builder.build());
    }
}
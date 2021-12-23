package com.example.tripa;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmReceiver extends BroadcastReceiver {

    static MediaPlayer mediaPlayer;


    @Override
    public void onReceive(Context context, Intent intent) {

        mediaPlayer=MediaPlayer.create(context,R.raw.ring);
        mediaPlayer.start();
        NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            String name="REMINDER NOTIFICATION";
            String description="It's Trip Time";
            int importance=NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel =new NotificationChannel("Tripa",name,importance);
            channel.setDescription(description);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"Tripa")
                .setSmallIcon(R.drawable.ic_baseline_alarm_24)
                .setContentTitle("You Are Waiting For a Trip")
                .setContentText("Tripa")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Intent i =new Intent(context,SecondFragment.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_ONE_SHOT);
        builder.setContentIntent(pendingIntent);
        manager.notify(1,builder.build());

    }
}

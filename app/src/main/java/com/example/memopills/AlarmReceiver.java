package com.example.memopills;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.memopills.Activities.MainActivity;
import com.example.memopills.Activities.Principal;

public class AlarmReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        long[] vibrate = new long[] { 1000, 1000, 1000, 1000, 1000 };
        Bundle b = intent.getExtras();
        String id = b.getString("id");
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"foxandroid")
                .setSmallIcon(R.drawable.logo1)
                .setContentTitle("MemoPills")
                .setContentText("Recuerde tomarse la pastilla")
                .setAutoCancel(true)
                .setVibrate(vibrate)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        MediaPlayer mp = MediaPlayer.create(context, R.raw.audio);
        mp.start();
        notificationManagerCompat.notify(123,builder.build());
    }
}

package br.ufc.caps.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import br.ufc.caps.R;
import br.ufc.caps.activity.LocalDetail;
import br.ufc.caps.geofence.Local;

/**
 * Created by messias on 6/7/16.
 *
 * @author Messias Lima
 */
public class NotificationUtil {
    static NotificationManager notificationManager;

    private static NotificationManager getNotificationManager(Context context) {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

    public static void sendNotification(String title, String text, Context context, Local local) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Intent intent = new Intent(context, LocalDetail.class);
        intent.putExtra(Local.KEY, local);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(text)
                .setVibrate(new long[]{500, 1000, 500, 1000, 500})
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        getNotificationManager(context).notify((int) Math.random() * 10, builder.build());
    }
}

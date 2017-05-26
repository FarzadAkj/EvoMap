package layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Random;

import ir.evoteam.evomap.MapsActivity;
import ir.evoteam.evomap.R;

/**
 * Implementation of App Widget functionality.
 */
public class EvoMapWidget extends AppWidgetProvider {
    public static final String ReadyClick = "ready" ;
    public static final String OnWayClick = "onWay" ;
    public static final String RestClick = "resting" ;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;


        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            String number = String.format(" MOSTAFA %03d", (new Random().nextInt(900) + 100));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.evo_map_widget);
            remoteViews.setOnClickPendingIntent(R.id.readyButton, getPendingSelfIntent(context, ReadyClick));
            remoteViews.setOnClickPendingIntent(R.id.onWayButton, getPendingSelfIntent(context, OnWayClick));
            remoteViews.setOnClickPendingIntent(R.id.restButton, getPendingSelfIntent(context, RestClick));
            Intent intent = new Intent(context, EvoMapWidget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (ReadyClick.equals(intent.getAction())) {

            MapsActivity.widgetService.changeStatus(ReadyClick);
            Toast.makeText(context, "آماده برای سرویس دهی دوباره", Toast.LENGTH_SHORT).show();

        } else if (OnWayClick.equals(intent.getAction())) {
            MapsActivity.widgetService.changeStatus(OnWayClick);

            Toast.makeText(context, "در مسیر سوار کردن مسافر", Toast.LENGTH_SHORT).show();

        } else if (RestClick.equals(intent.getAction())) {

            MapsActivity.widgetService.changeStatus(RestClick);
            Toast.makeText(context, "خارج از دسترس", Toast.LENGTH_SHORT).show();

        }
    }


}


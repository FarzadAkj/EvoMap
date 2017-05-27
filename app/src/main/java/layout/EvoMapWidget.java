package layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.Toast;

import ir.evoteam.evomap.R;
import ir.evoteam.evomap.WidgetService;

import static ir.evoteam.evomap.MapsActivity.isBound;
import static ir.evoteam.evomap.MapsActivity.widgetService;

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

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.evo_widget_second);
            remoteViews.setOnClickPendingIntent(R.id.readyButton2, getPendingSelfIntent(context, ReadyClick));
            remoteViews.setOnClickPendingIntent(R.id.onWayButton2, getPendingSelfIntent(context, OnWayClick));
            remoteViews.setOnClickPendingIntent(R.id.restButton2, getPendingSelfIntent(context, RestClick));
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

            widgetService.changeStatus(ReadyClick);
            Toast.makeText(context, "آماده برای سرویس دهی دوباره", Toast.LENGTH_SHORT).show();

        } else if (OnWayClick.equals(intent.getAction())) {
            widgetService.changeStatus(OnWayClick);

            Toast.makeText(context, "در مسیر سوار کردن مسافر", Toast.LENGTH_SHORT).show();

        } else if (RestClick.equals(intent.getAction())) {

            widgetService.changeStatus(RestClick);
            Toast.makeText(context, "خارج از دسترس", Toast.LENGTH_SHORT).show();

        }
    }

    //creating the connection
    public ServiceConnection widgetConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            WidgetService.MyLocalBinder binder = (WidgetService.MyLocalBinder) service;
            widgetService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };//the service

}


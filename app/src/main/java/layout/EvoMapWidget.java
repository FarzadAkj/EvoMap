//package layout;
//
//import android.app.PendingIntent;
//import android.appwidget.AppWidgetManager;
//import android.appwidget.AppWidgetProvider;
//import android.content.Context;
//import android.content.Intent;
//import android.widget.RemoteViews;
//import android.widget.Toast;
//
//import ir.evoteam.evomap.R;
//
//import static ir.evoteam.evomap.MapsActivity.widgetService;
//
///**
// * Implementation of App Widget functionality.
// */
//public class EvoMapWidget extends AppWidgetProvider {
//    public static final String ReadyClick = "ready" ;
//    public static final String OnWayClick = "onWay" ;
//    public static final String RestClick = "resting" ;
//
//    @Override
//    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        final int count = appWidgetIds.length;
//
//
//        for (int i = 0; i < count; i++) {
//            int widgetId = appWidgetIds[i];
//
//            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
//                    R.layout.evo_widget_second);
//            remoteViews.setOnClickPendingIntent(R.id.readyButton2, getPendingSelfIntent(context, ReadyClick));
//            remoteViews.setOnClickPendingIntent(R.id.onWayButton2, getPendingSelfIntent(context, OnWayClick));
//            remoteViews.setOnClickPendingIntent(R.id.restButton2, getPendingSelfIntent(context, RestClick));
//            Intent intent = new Intent(context, EvoMapWidget.class);
//            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//            appWidgetManager.updateAppWidget(widgetId, remoteViews);
//        }
//    }
//
//    protected PendingIntent getPendingSelfIntent(Context context, String action) {
//        Intent intent = new Intent(context, getClass());
//        intent.setAction(action);
//        return PendingIntent.getBroadcast(context, 0, intent, 0);
//    }
//
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        super.onReceive(context, intent);
//        try {//exception handling
//            if (ReadyClick.equals(intent.getAction())) {
//
//                widgetService.changeStatus(ReadyClick);
//                Toast.makeText(context, "آماده برای سرویس دهی دوباره", Toast.LENGTH_SHORT).show();
//
//            } else if (OnWayClick.equals(intent.getAction())) {
//                widgetService.changeStatus(OnWayClick);
//
//                Toast.makeText(context, "در مسیر سوار کردن مسافر", Toast.LENGTH_SHORT).show();
//
//            } else if (RestClick.equals(intent.getAction())) {
//
//                widgetService.changeStatus(RestClick);
//                Toast.makeText(context, "خارج از دسترس", Toast.LENGTH_SHORT).show();
//
//            }
//        } catch (Exception e){
//            Toast.makeText(context, "لطفا ابتدا برنامه را اجرا کنید.", Toast.LENGTH_SHORT).show();
//        }//exception handling
//    }
//
//}
//
package layout;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import ir.evoteam.evomap.R;

import static ir.evoteam.evomap.MapsActivity.widgetService;

/**
 * Implementation of App Widget functionality.
 */
public class EvoMapWidget extends AppWidgetProvider {
    public static final String ReadyClick = "ready" ;
    public static final String OnWayClick = "onWay" ;
    public static final String RestClick = "resting" ;

    private int [] appWidgetId = null ;
    AppWidgetManager mAppWidgetManager = null ;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        appWidgetId = appWidgetIds ;
        this.mAppWidgetManager = appWidgetManager ;

        final int count = appWidgetIds.length;
        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.evo_widget_second);
            remoteViews.setOnClickPendingIntent(R.id.readyButton2, getPendingSelfIntent(context, ReadyClick));
            remoteViews.setOnClickPendingIntent(R.id.onWayButton2, getPendingSelfIntent(context, OnWayClick));
            remoteViews.setOnClickPendingIntent(R.id.restButton2, getPendingSelfIntent(context, RestClick));
            Intent intent = new Intent(context, EvoMapWidget.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
            Log.d("Mostafa", "onUpdate: In the fucking onUpdate");
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }


    @Override
    public void onReceive(Context context, Intent intent) {


        super.onReceive(context, intent) ;
        try {//exception handling
            Log.d("Mostafa", "onRecieve: In the fucking onRecieve");
            if (ReadyClick.equals(intent.getAction())) {
                widgetService.changeStatus(ReadyClick);
                Toast.makeText(context,  "  آماده برای سرویس دهی دوباره  ", Toast.LENGTH_SHORT).show();
            } else if (OnWayClick.equals(intent.getAction())) {
                widgetService.changeStatus(OnWayClick);
                Toast.makeText(context, "در مسیر سوار کردن مسافر" , Toast.LENGTH_SHORT).show();
            } else if (RestClick.equals(intent.getAction())) {
                widgetService.changeStatus(RestClick);
                Toast.makeText(context, "خارج از دسترس" , Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            System.out.print (e.getStackTrace()) ;
            Toast.makeText(context, "لطفا ابتدا برنامه را اجرا کنید.", Toast.LENGTH_SHORT).show();
        }//exception handling

    }

}
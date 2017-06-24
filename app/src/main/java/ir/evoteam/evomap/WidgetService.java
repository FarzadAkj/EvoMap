package ir.evoteam.evomap;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import static ir.evoteam.evomap.MapsActivity.sharedPreferences;

public class WidgetService extends Service {

    private final IBinder myBinder = new MyLocalBinder();//this object is the bridge between client and the service

    public WidgetService() {
//        Log.d("im_in_widget_service_constructor", String.valueOf(MapsActivity.isBound));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public class MyLocalBinder extends Binder {

        public WidgetService getService(){
            return WidgetService.this;
        }
    }

    public void changeStatus(String s) throws Exception{


        String TAG = "Mostafa" ;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (s) {
            case "onWay" :
                editor.putInt(Constant.Driver_STATE_PREF_KEY, Constant.State_ONSERVICE);
                editor.commit();

                break ;
            case "ready" :
                editor.putInt(Constant.Driver_STATE_PREF_KEY, Constant.State_ONTHEWAY);
                editor.commit();

                break ;
            case "resting" :
                editor.putInt(Constant.Driver_STATE_PREF_KEY, Constant.State_REST);
                editor.commit();

                break ;
            default:
                Log.d(TAG, "changeStatus: default");
                break ;
        }
    }

}

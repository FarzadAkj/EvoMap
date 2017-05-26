package ir.evoteam.evomap;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class WidgetService extends Service {

    private final IBinder myBinder = new MyLocalBinder();//this object is the bridge between client and the service

    public WidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public class MyLocalBinder extends Binder {

        public WidgetService getService(  ){
            return WidgetService.this;
        }
    }
    //commit



    public void changeStatus(String s){

        String TAG = "Mostafa" ;
        SharedPreferences.Editor editor = MapsActivity.sharedPreferences.edit();
        switch (s) {
            case "onWay" :
                editor.putInt(Constant.Driver_STATE_PREF_KEY, Constant.State_ONTHEWAY);
                editor.commit();
                Log.d(TAG, "changeStatus: " + "onWay" );
                break ;
            case "ready" :
                editor.putInt(Constant.Driver_STATE_PREF_KEY, Constant.State_ONSERVICE);
                editor.commit();
                Log.d(TAG, "changeStatus: " + "ready" );
                break ;
            case "resting" :
                editor.putInt(Constant.Driver_STATE_PREF_KEY, Constant.State_REST);
                editor.commit();
                Log.d(TAG, "changeStatus: " + "resting" );
                break ;
            default:
                Log.d(TAG, "changeStatus: defaut");
                break ;
        }
    }

}

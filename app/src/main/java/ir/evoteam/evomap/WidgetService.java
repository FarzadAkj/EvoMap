package ir.evoteam.evomap;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

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
        //mostafa job
    }
}

package ir.evoteam.evomap;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by shahr on 4/12/2017.
 */

public class AsyncSendData extends AsyncTask<Void, Void, Void> {

    private HttpConnectionManager mHttpConnectionManager;
    private Runnable sendDataRunnable;
    private Context appContext;

    public AsyncSendData(Context context) {
        mHttpConnectionManager = new HttpConnectionManager("http://192.168.1.4:3000/api");
        appContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        sendDataRunnable = new Runnable() {
            @Override
            public void run() {

                if (mHttpConnectionManager.isOnline(appContext)) {
                    String singleRowOfDb;

                    singleRowOfDb = taxiDriverDB.getTaxiDriverDBInstance(appContext).getTaxiStatesRowInJsonFormat();
                    if (singleRowOfDb!= null )
                    Log.i("SingleRowDB",singleRowOfDb);
                    //&& !singleRowOfDb.toLowerCase().contains("null")
                    if (singleRowOfDb!= null )
                        mHttpConnectionManager.postDataHttpUrlConnection(Constant.PositionServerUrl,singleRowOfDb);
                    SystemClock.sleep(100);
                    run();
                }

                SystemClock.sleep(10000);
                run();
            }
        };
        Thread sendDataThread = new Thread(sendDataRunnable);
        sendDataThread.start();


        return null;
    }
}

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
        mHttpConnectionManager = new HttpConnectionManager();
        appContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        sendDataRunnable = new Runnable() {
            @Override
            public void run() {

                if (mHttpConnectionManager.isOnline(appContext)) {
                    String singleRowOfDb;

                    singleRowOfDb = LocationServiceManager.mTaxiDriverDB.getTaxiStatesRowInJsonFormat();
                    Log.i("SingleRowDB",singleRowOfDb);
                    if (!singleRowOfDb.toLowerCase().contains("null"))
                        mHttpConnectionManager.postDataHttpUrlConnection(singleRowOfDb);
                    SystemClock.sleep(50);
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

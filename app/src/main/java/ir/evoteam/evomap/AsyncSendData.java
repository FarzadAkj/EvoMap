package ir.evoteam.evomap;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;

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

                    singleRowOfDb = taxiDriverDB.getTaxiDriverDBInstance(appContext).getTaxiStatesRowInJsonFormat();
//                    Log.i("SingleRowDB",singleRowOfDb);
                    if (singleRowOfDb!= null && !singleRowOfDb.toLowerCase().contains("null"))
                        mHttpConnectionManager.postDataHttpUrlConnection(Constant.PositionServerUrl,singleRowOfDb);
                    SystemClock.sleep(5000);
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

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
        mHttpConnectionManager = new HttpConnectionManager("http://192.168.1.4:3000/api");
        appContext = context;

    }

    @Override
    protected Void doInBackground(Void... params) {

        sendDataRunnable = new Runnable() {
            @Override
            public void run() {

                int size = taxiDriverDB.getTaxiDriverDBInstance(appContext).getTotalRowNumbers();

                if (mHttpConnectionManager.isOnline(appContext) && size != 0) {
                    String rowsOfDb;

                    rowsOfDb = taxiDriverDB.getTaxiDriverDBInstance(appContext).sendJsonData().toString();

                    if (rowsOfDb.length() != 0)
                        mHttpConnectionManager.postDataHttpUrlConnection(Constant.PositionServerUrl,rowsOfDb);
                    SystemClock.sleep(10000);
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

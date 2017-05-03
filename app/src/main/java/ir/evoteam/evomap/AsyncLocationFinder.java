//package ir.evoteam.evomap;
///**
// * Created by root on 3/28/17.
// */
//
//
//import android.os.AsyncTask;
//
///**
// * Created by root on 3/26/17.
// */
//
//public class AsyncLocationFinder extends AsyncTask<Void, Void, Void> {
//
//    @Override
//    protected Void doInBackground(Void... params) {
//
//
////        Runnable runnable = new Runnable() {
////
////            @Override
////            public void run()
////            {
////                locations[0] = locationServiceManager.getlatitude();
////                locations[1] = locationServiceManager.getlongtitude();
////
////                Log.i("gh_location",String.format("%f",locations[0]));
////                Log.i("gh_location",String.format("%f",locations[1]));
////
////                publishProgress();
////                // send location to server
////
////                SystemClock.sleep(Integer.parseInt(MapsActivity.Update_time));
////                run();
////
////            }
////        };
////        Thread thread = new Thread(runnable);
////        thread.start();
////        HttpConnectionManager managerHttp = new HttpConnectionManager();
////        List<Bundle> driver_Pos = LocationServiceManager.mTaxiDriverDB.getTotalState();
////        String temp = "\"[";
////        for (int i = 0; i < driver_Pos.size(); i++) {
////            //"" + driver_Pos.get(i).getString(Constant.DB_key_Driver_State)+"\""
////            temp = temp.concat("{\"Driver_ID\":"+4);
////            temp = temp.concat(",\"Longtitude\":\"" + driver_Pos.get(i).getString(Constant.DB_key_Longitude)+"\"" );
////            temp = temp.concat(",\"Latitude\":\"" + driver_Pos.get(i).getString(Constant.DB_key_Latitude)+"\"" );
////            temp = temp.concat(",\"Driver_State\":" + driver_Pos.get(i).getString(Constant.DB_key_Driver_State));
////            temp = temp.concat(",\"Date_time\":\"" + driver_Pos.get(i).getString(Constant.DB_key_DateTime)+"\"");
////           if (driver_Pos.size()>1)
////            temp = temp.concat("},");
////            else
////               temp = temp.concat("}");
////
////        }
////        temp = temp.concat("]\"");
////        Log.i("temp",temp);
//
////        int resPonseCode=    managerHttp.postDataHttpUrlConnection(temp);
////        Log.i("response",res+"6");
//
//        return null;
//    }
//
//    @Override
//    protected void onProgressUpdate(Void... values) {
////        super.onProgressUpdate(values);
//
////        Log.d("GhMap_debug", "onProgressUpdate");
//
////        mMap.clear();
//
////        mMap.addMarker(new MarkerOptions().position(new LatLng(locations[0],locations[1])));
//
//
//    }
//
//}

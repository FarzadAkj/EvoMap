package ir.evoteam.evomap;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import static ir.evoteam.evomap.MapsActivity.User_ID;
import static ir.evoteam.evomap.MapsActivity.sharedPreferences;

/**
 * Created by shahr on 3/29/2017.
 */

public class AuthenticateAsync extends AsyncTask<Object, Dialog, String> {


    private Activity appActivity;
    private boolean result = false;
    private final ProgressDialog authProgressDialog;
    private HttpConnectionManager mHttpConnectionManager;
    private String responseAfterAll;
    String userName;
    String passWord;
//    private final String serverUrl = "http://192.168.1.3/auth.php";

    public AuthenticateAsync(Activity appActivity) {
        this.appActivity = appActivity;
        authProgressDialog = new ProgressDialog(appActivity);
        mHttpConnectionManager = new HttpConnectionManager("http://192.168.1.4:3000/api/login");
    }

    @Override
    protected String doInBackground(Object... params) {

//username and password
        userName = (String) params[0];
        passWord = (String) params[1];
        if (mHttpConnectionManager.isOnline(appActivity.getApplicationContext())) {
            String tempAuth = "[{\"User_id\":" + "\"" + userName + "\"" + ",\"User_Pass\":" + "\"" + passWord + "\"" + "}]";

            String response ;


            response = mHttpConnectionManager.postDataHttpUrlConnection(Constant.LoginServerUrl, tempAuth);



            if (response != null && response.equals("Password is incorrect")){
                Log.d("888888888","not password");
                result = false;
                responseAfterAll = "notUserOrPass";
                return responseAfterAll;
            }

            else if (response != null && response.equals("Username not found")){

                result = false;
                responseAfterAll = "notUserOrPass";
                return responseAfterAll;
            }
            else if (response != null) {
                User_ID  = response;
                result = true;
                sharedPreferences = appActivity.getSharedPreferences
                        (Constant.PREFERENCES_KEY, 0);
                sharedPreferences.edit().putString(Constant.USER_ID_PREF_KEY,response).commit();


            }
            else
                result = false;


        }
        responseAfterAll = "ok";
        return responseAfterAll;

//        try {

//            //openconnection with server
//            URL url = new URL(serverUrl);
//             urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setConnectTimeout(5000);
//            urlConnection.setDoInput(true);
//            urlConnection.setDoOutput(true);
//            //check url security
//            if (android.os.Build.VERSION.SDK_INT > 9) {
//                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                StrictMode.setThreadPolicy(policy);
//            }
////            sendData(urlConnection,userName,passWord);
//            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                if (getResponseFromServer(urlConnection.getInputStream()).equals("\"ACCESS GRANTED\""))
//                    result = true;
//            }
//        } catch (MalformedURLException e) {
//            result = false;
//        } catch (IOException e) {
//            result = false;
//        } catch (IllegalArgumentException e) {
//            result = false;
//        }
////        finally {
////            urlConnection.disconnect();
//        }
    }


    @Override
    protected void onPreExecute() {
        authProgressDialog.setMessage(appActivity.getString(R.string.communication_dialog));
        authProgressDialog.show();
    }

    @Override
    protected void onPostExecute(String result) {
        if (result == "notUserOrPass") {
            Toast.makeText(appActivity, "نام کاربری یا رمز عبور اشتباه وارد شده است.", Toast.LENGTH_LONG).show();
            authProgressDialog.cancel();

        } else {

            sharedPreferences = appActivity.getSharedPreferences
                    (Constant.PREFERENCES_KEY, 0);
            sharedPreferences.edit().putBoolean(Constant.ISLOGEDIN_PREF_KEY , true).commit();

            SharedPreferences.Editor editor = MapsActivity.sharedPreferences.edit();
            editor.putString(Constant.USER_NAME_PREF_KEY, userName);
            editor.commit();

            authProgressDialog.cancel();
            Toast.makeText(appActivity, appActivity.getString(R.string.authentication_success), Toast.LENGTH_LONG).show();
            Intent startMapActivityIntent = new Intent(appActivity.getApplicationContext(), MapsActivity.class);
            startMapActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK) ;
            appActivity.startActivity(startMapActivityIntent);
            //start maps activity when authorized user
//            Intent mapsIntent = new Intent(appActivity,//MapsActivty.class)
//            appActivity.startActivity(mapsIntent);

        }
    }
//
//    public String getResponseFromServer(InputStream inputStreamparam) {
//        InputStream inputStream = new BufferedInputStream(inputStreamparam);
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//        String inputLine = "";
//        StringBuffer temp = new StringBuffer();
//        try {
//            while ((inputLine = bufferedReader.readLine()) != null) {
//                temp.append(inputLine);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return temp.toString();
//    }
//
//    public void sendData(HttpURLConnection urlConnection,String user,String pass)
//    {
//        String temp  = user+"/"+pass;
//        try {
//            urlConnection.setRequestMethod("POST");
//            urlConnection.setFixedLengthStreamingMode(temp.getBytes().length);
//            urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
//            urlConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
//            urlConnection.connect();
//            OutputStream os = new BufferedOutputStream(urlConnection.getOutputStream());
//            os.write(temp.getBytes());
//            os.flush();
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}



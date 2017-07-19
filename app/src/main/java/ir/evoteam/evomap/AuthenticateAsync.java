package ir.evoteam.evomap;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            JSONArray tempArray = new JSONArray();
            JSONObject tempAuth = new JSONObject();
            try {
                tempAuth.put("User_id", userName);
                tempAuth.put("User_Pass", passWord);
                tempArray.put(tempAuth);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String JsonToSend = tempArray.toString();

            String response = mHttpConnectionManager.postDataHttpUrlConnection(Constant.LoginServerUrl, JsonToSend);
            Log.d("user********",response);


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
                Log.d("user", "U are here");
                User_ID  = response;
                result = true;
                sharedPreferences = appActivity.getSharedPreferences
                        (Constant.PREFERENCES_KEY, 0);
                Log.d("user",response);
                sharedPreferences.edit().putString(Constant.USER_ID_PREF_KEY,response).commit();
                return "ok";


            }
            else
                result = false;


        }
        responseAfterAll = "notUserOrPass";
        return responseAfterAll;


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

        } else if (result == "ok") {

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

        }
    }

}



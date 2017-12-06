package ir.evoteam.evomap;
/**
 * Created by root on 3/28/17.
 */


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import static ir.evoteam.evomap.MapsActivity.sharedPreferences;

/**
 * Created by root on 3/26/17.
 */

public class NewMarkerDialog {

    public static Dialog dialog;
    public static LatLng latLng;
    private Activity activity;
    private Button camcel_dialogButton;
    private Button ok_dialogButton;
    public static EditText newTitle_dialogEditTxt;

    public void showDialog(final LatLng latLng, final Activity activity){
        this.activity=activity;
        this.latLng=latLng;
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.newmarker_dialog_layout);

        TextView text = (TextView) dialog.findViewById(R.id.newmarker_dialog_txtview);
        text.setText("plz set a title");

        camcel_dialogButton =
                (Button) dialog.findViewById(R.id.newmarker_dialog_cancel_btn);
        ok_dialogButton =
                (Button) dialog.findViewById(R.id.newmarker_dialog_ok_btn);
        newTitle_dialogEditTxt =
                (EditText) dialog.findViewById(R.id.newmarker_dialog_edittxt);


        camcel_dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("GhMap_debug","cancel bttn clicked");
                dialog.dismiss();
            }
        });

        ok_dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("GhMap_debug","ok bttn clicked");

                final String newTitle= newTitle_dialogEditTxt.getText().toString();
                if (newTitle.equals(null)|| newTitle.equals("")){
                    Toast.makeText(activity,"لطفا نامی برای این نشانه وارد کنید.",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(activity,"نشانه اضافه شد.",Toast.LENGTH_LONG).show();

                    MarkerOptions marker = new MarkerOptions().position(
                            new LatLng(latLng.latitude, latLng.longitude)).title(newTitle);

                    MapsActivity.MarkedLocationsList.add
                            (new Marker(latLng.latitude , latLng.longitude , newTitle));

                    MapsActivity.mMap.addMarker(marker);

                    Bundle markerBundle=new Bundle();

                    markerBundle.putString(Constant.DB_key_Mark_Title , newTitle );
                    markerBundle.putString(Constant.DB_key_Mark_Longitude , String.format("%f",latLng.longitude));
                    markerBundle.putString(Constant.DB_key_Mark_Latitude , String.format("%f",latLng.latitude));

                    LocationServiceManager.mTaxiDriverDB.addMark(markerBundle);

                    Thread sendmark = new Thread(new Runnable() {
                        @Override
                        public void run() {
                         HttpConnectionManager connectionManager = new HttpConnectionManager(Constant.MarksServerUrl);

                            String now = System.currentTimeMillis()/1000 + "";

                            sharedPreferences = activity.getSharedPreferences
                                    (Constant.PREFERENCES_KEY, 0);
                            String userid= sharedPreferences.getString(Constant.USER_ID_PREF_KEY,Constant.DEFAULT_USER_ID);

                            JSONObject markASJson = new JSONObject();
                            try {
                                markASJson.put(Constant.DB_key_Mark_Longitude, latLng.longitude);
                                markASJson.put(Constant.DB_key_Mark_Latitude, latLng.latitude);
                                markASJson.put(Constant.DB_key_Mark_Title, newTitle);
                                markASJson.put("Date_time", now);
                                markASJson.put("User_id", userid);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            connectionManager.postDataHttpUrlConnection(Constant.MarksServerUrl,markASJson.toString());

                        }
                    });
                    sendmark.start();

                    dialog.dismiss();
                }
            }
        });

        dialog.setCancelable(true);
        dialog.show();
    }

}

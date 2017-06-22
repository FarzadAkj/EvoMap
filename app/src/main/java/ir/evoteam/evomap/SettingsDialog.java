package ir.evoteam.evomap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * Created by root on 3/27/17.
 */

public class SettingsDialog {
    private Context context;

    public SettingsDialog(Context context) {
        this.context = context;
    }

    public static Dialog SettingsDialog;
    private ListView Settings_listView;
    private Button save_updateTime_changes;
    private Button save_updateDistance_changes;

    private EditText updateTime_edittxt;
    private EditText updateDistance_edittxt;

    public void showDialog(Activity activity) {
        SettingsDialog = new Dialog(activity);
        SettingsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SettingsDialog.setCancelable(false);
        SettingsDialog.setContentView(R.layout.setting_dialog_layout);


        Settings_listView = (ListView) SettingsDialog.findViewById(R.id.settings_dialog_listview);

        final Settings_Adapter settings_adapter =
                new Settings_Adapter(context, MapsActivity.Settings_list);
        Settings_listView.setAdapter(settings_adapter);
        Settings_listView.setTextFilterEnabled(true);

        SettingsDialog.setCancelable(true);

        Window window = SettingsDialog.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.y = -80000;
        param.x = 20000;
        window.setAttributes(param);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        SettingsDialog.show();

        // Bind onclick event handler
        Settings_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                     public void onItemClick(AdapterView<?> parent, View view,
                                                                             int position, long id) {

                                                         Log.d("Ghmap_debug", "item " + position + " clicked");

                                                         if (position == 0) {

                                                             MapsActivity.isLogedin = false;
                                                             SharedPreferences.Editor editor = MapsActivity.sharedPreferences.edit();
                                                             editor.clear();
                                                             editor.commit();
                                                             Intent intent = new Intent(context, LoginActivity.class);
                                                             context.startActivity(intent);
                                                         }
                                                     }
                                                 });
    }


}

//                    //TIME
//
//                    final Dialog changeUpdate_time_dialog = new Dialog(context);
//                    changeUpdate_time_dialog.setContentView(R.layout.change_updatetime_layout);
//                    save_updateTime_changes = (Button) changeUpdate_time_dialog.findViewById(R.id.updateTime_saveChanges_bttn);
//                    updateTime_edittxt = (EditText) changeUpdate_time_dialog.findViewById(R.id.updateTime_edittxt);
//
//                    updateTime_edittxt.setHint(MapsActivity.Update_time+" mili seconds");
//
//                    save_updateTime_changes.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            String tmp=updateTime_edittxt.getText().toString().trim();
//
//                            if(tmp.isEmpty() ||
//                                    tmp.length() == 0 ||
//                                    tmp.equals("") ||
//                                    tmp == null){
//
//                                Log.d("GhMap_debug", "new update distance is null");
//
//                                Toast.makeText(context,"field can not be empty",Toast.LENGTH_SHORT).show();
//
//                                Animation anim = AnimationUtils.loadAnimation(context, R.anim.animation);
//                                updateTime_edittxt.startAnimation(anim);
//                                Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//                                v.vibrate(100);
//                            }
//
//                            else{
//
//
//                                if(Integer.parseInt(updateTime_edittxt.getText().toString())<=5000
//                                        && Integer.parseInt(updateTime_edittxt.getText().toString())>=1000)
//                                {
//
//                                    MapsActivity.Update_time = updateTime_edittxt.getText().toString();
//
//                                    Log.d("GhMap_debug", "new update time is valid");
//
//
//                                    SharedPreferences.Editor editor = MapsActivity.sharedPreferences.edit();
//                                    editor.putString(Constant.UPDATE_TIME_PREF_KEY, MapsActivity.Update_time);
//                                    editor.commit();
//
//                                    Toast.makeText(context, "update time changed", Toast.LENGTH_SHORT).show();
//
//
//                                    Log.d("GhMap_debug", "update time commited = "
//                                            + MapsActivity.sharedPreferences.getString(Constant.UPDATE_TIME_PREF_KEY,
//                                            Constant.DEFAULT_UPDATE_TIME));
//
//                                    changeUpdate_time_dialog.dismiss();
//                                }
//                                else {
//
//                                    //invalid update time...
//                                    Log.d("GhMap_debug", "new update time is invalid");
//
//                                    Toast.makeText(context, "invalid update time", Toast.LENGTH_SHORT).show();
//
//                                    updateTime_edittxt.setText("");
//
//                                    Animation anim = AnimationUtils.loadAnimation(context, R.anim.animation);
//                                    updateTime_edittxt.startAnimation(anim);
//                                    Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//                                    v.vibrate(100);
//
//                                }
//
//                            }
//
//
//
//
//                        }// listener
//                    });
//                    changeUpdate_time_dialog.show();
//
//                }
//
//
//                if (position == 1) {
//
//                    //DISTANCE
//
//                    final Dialog changeUpdate_diatance_dialog =
//                            new Dialog(context);
//                    changeUpdate_diatance_dialog.setContentView(R.layout.change_updatedistance_layout);
//                    save_updateDistance_changes =
//                            (Button) changeUpdate_diatance_dialog.findViewById(R.id.updateDiastance_saveChanges_bttn);
//                    updateDistance_edittxt =
//                            (EditText) changeUpdate_diatance_dialog.findViewById(R.id.updateDistance_edittxt);
//
//                    updateDistance_edittxt.setHint(MapsActivity.Update_distance+" meters");
//
//                    save_updateDistance_changes.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            String tmp=updateDistance_edittxt.getText().toString().trim();
//
//
//                            if(tmp.isEmpty() ||
//                                    tmp.length() == 0 ||
//                                    tmp.equals("") ||
//                                    tmp == null){
//
//                                Log.d("GhMap_debug", "new update distance is null");
//
//                                Toast.makeText(context,"field can not be empty",Toast.LENGTH_SHORT).show();
//
//                                Animation anim = AnimationUtils.loadAnimation(context, R.anim.animation);
//                                updateDistance_edittxt.startAnimation(anim);
//                                Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//                                v.vibrate(100);
//                            }
//
//                            else{
//
//
//                                if (Integer.parseInt(updateDistance_edittxt.getText().toString())<=15
//                                        && Integer.parseInt(updateDistance_edittxt.getText().toString())>=1) {
//
//                                    MapsActivity.Update_distance = updateDistance_edittxt.getText().toString();
//
//                                    Log.d("GhMap_debug", "new update distance is valid");
//
//                                    SharedPreferences.Editor editor = MapsActivity.sharedPreferences.edit();
//                                    editor.putString(Constant.UPDATE_DISTANCE_PREF_KEY, MapsActivity.Update_distance);
//                                    editor.commit();
//                                    Toast.makeText(context, "update distance changed", Toast.LENGTH_SHORT).show();
//
//                                    Log.d("GhMap_debug", "update distance commited = "
//                                            + MapsActivity.sharedPreferences.getString(Constant.UPDATE_DISTANCE_PREF_KEY,
//                                            Constant.DEFAULT_UPDATE_DISTANCE));
//
//                                    changeUpdate_diatance_dialog.dismiss();
//                                }
//                                else {
//
//                                    //invalid update distance...
//                                    Log.d("GhMap_debug", "new update distance is invalid");
//
//                                    Toast.makeText(context, "invalid update distance", Toast.LENGTH_SHORT).show();
//
//                                    updateDistance_edittxt.setText("");
//
//                                    Animation anim = AnimationUtils.loadAnimation(context, R.anim.animation);
//                                    updateDistance_edittxt.startAnimation(anim);
//                                    Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
//                                    v.vibrate(100);
//
//                                }
//
//
//                            }
//
//
//                        }// listener
//                    });
//                    changeUpdate_diatance_dialog.show();
//
//
//                }
//                if (position == 2) {
//
//                }
//
//            }
//        });
//
//
//


package ir.evoteam.evomap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import za.co.riggaroo.materialhelptutorial.TutorialItem;
import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;

import static ir.evoteam.evomap.R.id.map;


public class MapsActivity extends FragmentActivity implements
        View.OnClickListener,
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMarkerClickListener

{

    private static final int REQUEST_CODE =1234 ;
    public static List<ir.evoteam.evomap.Marker> MarkedLocationsList;
    public static String Update_time;
    public static String Update_distance;
    public static SharedPreferences sharedPreferences;
    public static GoogleMap mMap;
    public static LocationServiceManager locationServiceManager;
    public static double[] locations = new double[2];
    private ImageButton SettingImgBttn;
    private ImageButton MoveToMyLocationImgBttn;
    public static List<String> Settings_list;
    public static String User_ID = Constant.DEFAULT_USER_ID;
    public static boolean isLogedin;


    public static int driverState;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //Start Sending Data
//        AsyncSendData mSendDataAsyncTask = new AsyncSendData(getApplicationContext());
//        mSendDataAsyncTask.execute();

        loadTutorial();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        MarkedLocationsList = new ArrayList<>();
        sharedPreferences = getSharedPreferences
                (Constant.PREFERENCES_KEY, 0);

        Update_distance = (sharedPreferences.getString
                (Constant.UPDATE_DISTANCE_PREF_KEY, Constant.DEFAULT_UPDATE_DISTANCE));

        Update_time = (sharedPreferences.getString
                (Constant.UPDATE_TIME_PREF_KEY, Constant.DEFAULT_UPDATE_TIME));

        User_ID = (sharedPreferences.getString
                (Constant.USER_ID_PREF_KEY, Constant.DEFAULT_USER_ID));

        driverState = (sharedPreferences.getInt
                (Constant.Driver_STATE_PREF_KEY , Constant.State_REST ));

        isLogedin= (sharedPreferences.getBoolean(Constant.ISLOGEDIN_PREF_KEY , false));


        SettingImgBttn = (ImageButton) findViewById(R.id.settingImgBttn);
        MoveToMyLocationImgBttn = (ImageButton) findViewById(R.id.myLocBttn);


        locationServiceManager =
                new LocationServiceManager(getApplicationContext(), MapsActivity.this);

        Settings_list = new ArrayList<>();
        Settings_list.add(getApplicationContext().getString(R.string.Update_Time).toString());
        Settings_list.add(getApplicationContext().getString(R.string.Update_Distance).toString());
        Settings_list.add(getApplicationContext().getString(R.string.Log_Out).toString());
        Settings_list.add(getApplicationContext().getString(R.string.ETC).toString());

        SettingImgBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d("ghmap_debug", "Setting imgBttn clicked");

                SettingsDialog settingsDialog = new SettingsDialog(MapsActivity.this);
                settingsDialog.showDialog(MapsActivity.this);

            }
        });

        MoveToMyLocationImgBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LatLng currentLatLng =
                        new LatLng(locationServiceManager.getlatitude(),
                                locationServiceManager.getlongtitude());

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
            }
        });


        FloatingActionMenu floatingActionMenu = (FloatingActionMenu) findViewById(R.id.floating_menu);
//        floatingActionMenu.getMenuIconView().setImageResource(R.mipmap.ic_launcher);
        floatingActionMenu.setClosedOnTouchOutside(true);

//        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
//        floatingActionMenu .setBackground(drawable);


        FloatingActionButton programFab1 = (FloatingActionButton) findViewById(R.id.floating_bttn1);
        programFab1.setButtonSize(FloatingActionButton.SIZE_MINI);
        programFab1.setLabelText(Constant.FLOATING_BTTN1);
        programFab1.setImageResource(R.drawable.ic_nav_item);


        FloatingActionButton programFab2 = (FloatingActionButton) findViewById(R.id.floating_bttn2);
        programFab2.setButtonSize(FloatingActionButton.SIZE_MINI);
        programFab2.setLabelText(Constant.FLOATING_BTTN2);
        programFab2.setImageResource(R.drawable.ic_nav_item);
        programFab2.setOnClickListener(this);


        FloatingActionButton programFab3 = (FloatingActionButton) findViewById(R.id.floating_bttn3);
        programFab3.setButtonSize(FloatingActionButton.SIZE_MINI);
        programFab3.setLabelText(Constant.FLOATING_BTTN3);
        programFab3.setImageResource(R.drawable.ic_nav_item);
        programFab3.setOnClickListener(this);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }//onCreate

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Log.d("GhMap_debug", "onMapReady");

        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerDragListener(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
//         LatLngBounds ADELAIDE = new LatLngBounds(
//                 new LatLng(29.47, 52.59),new LatLng(29.86, 52.46));
//        mMap.setLatLngBoundsForCameraTarget(ADELAIDE);


        mMap.setMyLocationEnabled(true);

        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        addDBmarkers(LocationServiceManager.mTaxiDriverDB);

        Log.d("GhMap_debug", "add marker");


    } //onMapReady


    @Override
    public void onMapClick(LatLng latLng) {
        Log.d("GhMap_debug", "onMapClick");
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Log.d("GhMap_debug", "onMapLongClick");

        NewMarkerDialog newMarkerDialog = new NewMarkerDialog();
        newMarkerDialog.showDialog(latLng, this);

    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        Log.d("GhMap_debug", "onMarkerClick");

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MapsActivity.this);
        alertDialog.setTitle("Delet marker?");
        alertDialog.setMessage("Are you sure you want to delet this marker");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                marker.remove();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();

        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        Log.d("GhMap_debug", "onMarkerDragStart");
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        Log.d("GhMap_debug", "onMarkerDrag");
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Log.d("GhMap_debug", "onMarkerDragEnd");
    }

    @Override
    public void onClick(View v) {
        Log.d("GhMap_debug", "floating menu clicked");
        switch (v.getId()) {

            case R.id.floating_bttn1: {
                Log.d("GhMap_debug", "floating bttn1 clicked");

                MapsActivity.driverState=Constant.State_REST;

                SharedPreferences.Editor editor = MapsActivity.sharedPreferences.edit();
                editor.putInt(Constant.Driver_STATE_PREF_KEY, Constant.State_REST);
                editor.commit();

                Toast.makeText(MapsActivity.this, "driver state is "+MapsActivity.driverState
                        , Toast.LENGTH_SHORT).show();

            }
            break;

            case R.id.floating_bttn2: {
                Log.d("GhMap_debug", "floating bttn2 clicked");

                MapsActivity.driverState=Constant.State_ONSERVICE;

                SharedPreferences.Editor editor = MapsActivity.sharedPreferences.edit();
                editor.putInt(Constant.Driver_STATE_PREF_KEY, Constant.State_ONSERVICE);
                editor.commit();

                Toast.makeText(MapsActivity.this, "driver state is "+MapsActivity.driverState
                        , Toast.LENGTH_SHORT).show();

            }
            break;

            case R.id.floating_bttn3: {
                Log.d("GhMap_debug", "floating bttn3 clicked");

                MapsActivity.driverState=Constant.State_ONTHEWAY;

                SharedPreferences.Editor editor = MapsActivity.sharedPreferences.edit();
                editor.putInt(Constant.Driver_STATE_PREF_KEY, Constant.State_ONTHEWAY);
                editor.commit();

                Toast.makeText(MapsActivity.this, "driver state is "+MapsActivity.driverState
                        , Toast.LENGTH_SHORT).show();
            }

            break;

        }

    }// onClick floating menu

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Maps Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }




    public void addDBmarkers(taxiDriverDB taxiDriverDB)

    {

        List<Bundle> markers = new ArrayList<>();
        Log.i("add markers","Invoked");
        markers=taxiDriverDB.getTotalMarks();

        for (int i = 0; i < markers.size(); i++) {

            double latitude= (Double.parseDouble(markers.get(i).getString(Constant.DB_key_Mark_Latitude)));
            double longitude= (Double.parseDouble(markers.get(i).getString(Constant.DB_key_Mark_Longitude)));


            String titleName= markers.get(i).getString(Constant.DB_key_Mark_Title);

            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude,longitude)).title(titleName));

        }



    }


    public void loadTutorial() {
        Intent mainAct = new Intent(this, MaterialTutorialActivity.class);
        mainAct.putParcelableArrayListExtra(MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS, getTutorialItems(this));
        startActivityForResult(mainAct, REQUEST_CODE);

    }

    private ArrayList<TutorialItem> getTutorialItems(Context context) {
//        TutorialItem tutorialItem1 = new TutorialItem(R.string.slide_1_Welcome, R.string.EvoMap,
//                R.color.slide_3, R.drawable.taxi2);
//
//        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
//        tutorialItems.add(tutorialItem1);
//        TutorialItem tutorialItem1 = new TutorialItem(R.string.slide_1_Welcome, R.string.EvoMap,
//                R.color.slide_3, R.drawable.taxi2  ,  R.drawable.taxi2);

//        TutorialItem tutorialItem2 =new TutorialItem(R.string.slide_1_Welcome, R.string.EvoMap,
//                R.color.slide_3, R.drawable.taxi2  ,  R.drawable.taxi2);

        TutorialItem tutorialItem3 = new TutorialItem(context.getString(R.string.slide_1_Welcome), context.getString(
                R.string.EvoMap),
                R.color.slide_3, R.drawable.taxi2);

        TutorialItem tutorialItem4 = new TutorialItem(R.string.slide_1_Welcome, R.string.EvoMap,
                R.color.slide_2, R.color.transparent  ,  R.drawable.taxi1);

        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
//        tutorialItems.add(tutorialItem1);
//        tutorialItems.add(tutorialItem2);
        tutorialItems.add(tutorialItem3);
        tutorialItems.add(tutorialItem4);
        return tutorialItems;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //    super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            Toast.makeText(this, "Tutorial finished", Toast.LENGTH_LONG).show();

        }
    }
}
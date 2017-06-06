package ir.evoteam.evomap;
/**
 * Created by root on 3/29/17.
 */

public class Constant {



    public static final String PREFERENCES_KEY="evoMap_pref";
    public static final String UPDATE_DISTANCE_PREF_KEY="updateDistance";
    public static final String UPDATE_TIME_PREF_KEY="UpdateTime";
    public static final String Driver_STATE_PREF_KEY="DriverState";
    public static final String ISLOGEDIN_PREF_KEY = "is loged in";

    public static final String DEFAULT_UPDATE_TIME="2000";
    public static final String DEFAULT_UPDATE_DISTANCE="10";

    public static final String FLOATING_BTTN1="at Rest";
    public static final String FLOATING_BTTN2="on Service";
    public static final String FLOATING_BTTN3="on the Way";

//    public static final String SETTING_ITEM1="Update time";
//    public static final String SETTING_ITEM2="Update distance";
//    public static final String SETTING_ITEM3="Log out";
//    public static final String SETTING_ITEM4="etc...";

    public static final String DEFAULT_USER_ID="user #";
    public static final String USER_ID_PREF_KEY="user id";

    public static final int State_REST=1;
    public static final int State_ONTHEWAY=3;
    public static final int State_ONSERVICE=2;

    public static final String DB_key_Latitude = "Latitude_DB_key" ;
    public static final String DB_key_Longitude = "Longitude_DB_key" ;
    public static final String DB_key_DateTime = "DateTime_DB_key" ;
    public static final String DB_key_Mark_Latitude = "Mark_Latitude_DB_key" ;
    public static final String DB_key_Mark_Longitude = "Mark_Longitude_DB_key" ;
    public static final String DB_key_Mark_Title = "Mark_Title_DB_key" ;
    public static final String DB_key_Driver_State = "Driver_State_DB_key" ;

    public static final String PositionServerUrl = "http://198.143.181.24:3000/api";
    public static final String LoginServerUrl =    "http://198.143.181.24:3000/api/login";
    public static final String MarksServerUrl =    "http://198.143.181.24:3000/api/insert/tag";


    public static final String CURRENT_ZOOM_PTEF_KEY = "current zoom preference";
    public static final Float DEFAULT_ZOOM = 15f;
    public static final String USER_NAME_PREF_KEY = "loged in user name";
    public static final String DEFAULT_USER_NAME = "not set";
//    public static final boolean DEFAULT_IS_LOGED_IN= false;

    public static final String WidgetOnWayClick = "onWay";
    public static final String WidgetReadyClick = "ready";
    public static final String WidgetRestClick = "resting";

}

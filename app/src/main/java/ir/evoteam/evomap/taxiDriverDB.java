package ir.evoteam.evomap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ir.evoteam.evomap.taxiDriverSchema.driverStateTable;
import ir.evoteam.evomap.taxiDriverSchema.marksTable;

import static ir.evoteam.evomap.MapsActivity.User_ID;

/**
 * Created by programmer on 4/7/2017.
 */

public class taxiDriverDB {
    private static Context mContext ;
    private static  SQLiteDatabase mDatabase ;
   private static taxiDriverDB taxiDriverDBInstance = new taxiDriverDB();

    private taxiDriverDB()
    {

    }
    public static taxiDriverDB getTaxiDriverDBInstance(Context context)
    {
        mContext  = context;
        try {
            mDatabase = new taxiTableHelper(mContext).getWritableDatabase() ;

        }
        catch (SQLiteCantOpenDatabaseException e)
        {

        }
        return taxiDriverDBInstance;
    }
//    private static final String cordinateY = "Latitude" ;
//    private static final String cordinateX = "Longtitude" ;
//    private static final String DateTime = "DateTime" ;
//    private static final String MarkCordY = "Mark_Latitude" ;
//    private static final String MarkCordX = "Mark_Longtitude" ;
//    private static final String MarkTitle = "Mark_Title" ;
//    private static final String driverState = "Driver_State" ;


//    public taxiDriverDB (Context context) {
//        mContext = context.getApplicationContext();
//        mDatabase = new taxiTableHelper(mContext).getWritableDatabase() ;
//    }

    private static ContentValues getDriverStatesValue (Bundle state) {
        ContentValues value = new ContentValues() ;
        value.put(driverStateTable.Cols.CORDINATE_X , state.getString(Constant.DB_key_Longitude));
        value.put(driverStateTable.Cols.CORDINATE_Y , state.getString(Constant.DB_key_Latitude));
        value.put(driverStateTable.Cols.DATETIME , state.getString(Constant.DB_key_DateTime));
        value.put(driverStateTable.Cols.STATE , state.getString(Constant.DB_key_Driver_State));

        return value ;
    }

    public void addState (Bundle state ){
        ContentValues values = getDriverStatesValue(state) ;
        Log.i("test",values.toString());
        mDatabase.insert(driverStateTable.NAME , null , values) ;
    }
    private TaxiStateCusorWrapper queryTaxiState (String WhereClause , String [] whereArgs) {
        Cursor cursor = mDatabase.query(driverStateTable.NAME ,
                null ,
                WhereClause ,
                whereArgs ,
                null,
                null,
                null
        ) ;
        return new TaxiStateCusorWrapper(cursor) ;
    }
    public List<Bundle> getTotalState () {
        List<Bundle> taxiStates = new ArrayList<>() ;

        TaxiStateCusorWrapper cursor = queryTaxiState(null , null) ;

        try {
            cursor.moveToFirst() ;
            while (!cursor.isAfterLast()){
                taxiStates.add(cursor.getTaxiState());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
        deleteTaxiState();
        return taxiStates ;
    }

    private void deleteTaxiState () {
        mDatabase.delete(driverStateTable.NAME, null, null) ;
    }


    private static ContentValues getMarksValue (Bundle mark) {
        ContentValues value = new ContentValues() ;

        value.put(marksTable.Cols.CORDINATE_X , mark.getString(Constant.DB_key_Mark_Longitude));
        value.put(marksTable.Cols.CORDINATE_Y , mark.getString(Constant.DB_key_Mark_Latitude));
        value.put(marksTable.Cols.TITLE , mark.getString(Constant.DB_key_Mark_Title));

        return value ;
    }

    public void addMark (Bundle mark ){
        ContentValues values = getMarksValue(mark) ;

        mDatabase.insert(marksTable.NAME , null , values) ;
    }
    private markesCusorWrapper queryMark (String WhereClause , String [] whereArgs) {
        Cursor cursor = mDatabase.query(marksTable.NAME ,
                null ,
                WhereClause ,
                whereArgs ,
                null,
                null,
                null
        ) ;
        return new markesCusorWrapper(cursor) ;
    }
    public List<Bundle> getTotalMarks () {
        List<Bundle> marks = new ArrayList<>() ;

        markesCusorWrapper cursor = queryMark(null , null) ;

        try {
            cursor.moveToFirst() ;
            while (!cursor.isAfterLast()){
                marks.add(cursor.getmarks());
                cursor.moveToNext();
            }
        }
        finally {
            cursor.close();
        }
//        deletemark();
        return marks ;
    }

    private void deletemark () {
        mDatabase.delete(marksTable.NAME, null, null) ;
    }


    public String getTaxiStatesRow() {
        String state = new String("\"[");

        TaxiStateCusorWrapper cursor = queryTaxiState(null, null);

        Bundle temp ;

        try {
            cursor.moveToFirst();
            temp = cursor.getTaxiState();

            state = state.concat(String.format( "{ \"%s\" : \"%s\" , \"%s\" : \"%s\" , \"%s\" : \"%s\" , \"%s\" : \"%s\" \"%s\" : \"%s\" } ",
                    "Driver_ID", User_ID,
                     "Longtitude"   , temp.get(Constant.DB_key_Longitude),
                     "Latitude"     , temp.get(Constant.DB_key_Latitude),
                     "Driver_State" , temp.get(Constant.DB_key_Driver_State),
                     "Date_time"    , temp.get(Constant.DB_key_DateTime))
            );

        } finally {
            cursor.close();
        }
        state = state.concat("]\"");
        mDatabase.delete(driverStateTable.NAME , cursor.getTaxiState().getString(Constant.DB_key_Longitude) + "=" + temp.getString(Constant.DB_key_Longitude) , null) ;
        return state ;
    }

    public String getTotalStateInJsonFormat() {
        String states = new String("\"[");

        TaxiStateCusorWrapper cursor = queryTaxiState(null, null);

        try {
            cursor.moveToFirst();

            String state = new String();
            Bundle temp = cursor.getTaxiState();

            state = String.format("{ \"%s\" : \"%s\" , \"%s\" : \"%s\" , \"%s\" : \"%s\" , \"%s\" : \"%s\" \"%s\" : \"%s\" } ",
                    "Driver_ID", User_ID,
                     "Longtitude"       , temp.get(Constant.DB_key_Longitude),
                     "Latitude"         , temp.get(Constant.DB_key_Latitude),
                     "Driver_State"     , temp.get(Constant.DB_key_Driver_State),
                     "Date_time"        , temp.get(Constant.DB_key_DateTime));
            cursor.moveToNext();

            states = states.concat(state);

            while (!cursor.isAfterLast()) {
                state = new String();
                temp = cursor.getTaxiState();
                state = String.format(", { \"%s\" : \"%s\" , \"%s\" : \"%s\" , \"%s\" : \"%s\" , \"%s\" : \"%s\" \"%s\" : \"%s\" } ",
                        "Driver_ID", User_ID,
                         "Longtitude"     , temp.get(Constant.DB_key_Longitude),
                         "Latitude"       , temp.get(Constant.DB_key_Latitude),
                         "Driver_State"   , temp.get(Constant.DB_key_Driver_State),
                         "Date_time"      , temp.get(Constant.DB_key_DateTime));
                states = states.concat(state);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        states = states.concat("]\"");
        deleteTaxiState();
        return states;
    }

    public String getTaxiStatesRowInJsonFormat() {
        String state = new String("[");

        TaxiStateCusorWrapper cursor = queryTaxiState(null, null);

        Bundle temp ;

        try {
            cursor.moveToFirst();
            temp = cursor.getTaxiState();

            state = state.concat(String.format("{ \"%s\" : \"%s\" , \"%s\" : \"%s\" , \"%s\" : \"%s\" , \"%s\" : \"%s\" ,\"%s\" : \"%s\" } ",
                    "Driver_ID", User_ID,
                    "Longtitude"    , temp.get(Constant.DB_key_Longitude),
                    "Latitude"      , temp.get(Constant.DB_key_Latitude),
                    "Driver_State"  , temp.get(Constant.DB_key_Driver_State),
                    "Date_time"     , temp.get(Constant.DB_key_DateTime))
            );

            state = state.concat("]");

            //"[{"Driver_ID":4,"Longtitude":"null","Latitude":"null","Driver_State":null,"Date_time":"null"}]"
            //mDatabase.delete(driverStateTable.NAME , cursor.getTaxiState().getString(Constant.DB_key_Longitude) + "=" + temp.getString(Constant.DB_key_Longitude) , null) ;
            cursor.moveToFirst() ;
//            while (!cursor.isAfterLast()) {
//                mDatabase.delete(driverStateTable.NAME, cursor.getTaxiState().getString(Constant.DB_key_Longitude) + "=" + temp.getString(Constant.DB_key_Longitude), null);
//                cursor.moveToNext() ;
//            }
            mDatabase.execSQL("delete from "+driverStateTable.NAME+" where "+"X"+"='"+temp.getString(Constant.DB_key_Longitude)+"'");
        }
        catch (CursorIndexOutOfBoundsException e)
        {
            return null;
        }
        catch (SQLiteCantOpenDatabaseException exception)
        {
            return null;
        }

        finally {
            cursor.close();
        }
        mDatabase.close();
        return state ;
    }

}






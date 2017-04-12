package ir.evoteam.evomap;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ir.evoteam.evomap.taxiDriverSchema.*;

/**
 * Created by programmer on 4/7/2017.
 */

public class taxiTableHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1 ;
    private static final String DATABASE_NAME = "TaxiDriver.db" ;

    public taxiTableHelper (Context context) {
        super(context , DATABASE_NAME , null , VERSION) ;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + driverStateTable.NAME  + "(" +
                " _id integer primary key autoincrement, " +
                driverStateTable.Cols.STATE + ", " +
                driverStateTable.Cols.CORDINATE_X + ", " +
                driverStateTable.Cols.CORDINATE_Y + ", " +
                driverStateTable.Cols.DATETIME  +
                ")"
        );
        db.execSQL("create table " + marksTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                marksTable.Cols.TITLE + ", " +
                marksTable.Cols.CORDINATE_X + ", " +
                marksTable.Cols.CORDINATE_Y +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

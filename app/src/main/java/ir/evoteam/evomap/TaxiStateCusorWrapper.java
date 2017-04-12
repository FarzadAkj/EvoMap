package ir.evoteam.evomap;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Bundle;

import ir.evoteam.evomap.taxiDriverSchema.*;

/**
 * Created by programmer on 4/8/2017.
 */

public class TaxiStateCusorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public TaxiStateCusorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Bundle getTaxiState () {
        String state = getString(getColumnIndex(driverStateTable.Cols.STATE)) ;
        String cordinateX = getString(getColumnIndex(driverStateTable.Cols.CORDINATE_X)) ;
        String cordinateY = getString(getColumnIndex(driverStateTable.Cols.CORDINATE_Y)) ;
        String dateTime = getString(getColumnIndex(driverStateTable.Cols.DATETIME)) ;

        Bundle data = new Bundle() ;
        data.putString(Constant.DB_key_Driver_State , state);
        data.putString(Constant.DB_key_Longitude , cordinateX);
        data.putString(Constant.DB_key_Mark_Latitude , cordinateY);
        data.putString(Constant.DB_key_DateTime , dateTime);

        return data ;
    }
}

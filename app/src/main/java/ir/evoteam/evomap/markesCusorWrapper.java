package ir.evoteam.evomap;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Bundle;

/**
 * Created by programmer on 4/8/2017.
 */

public class markesCusorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public markesCusorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Bundle getmarks () {
        String title = getString(getColumnIndex(taxiDriverSchema.marksTable.Cols.TITLE)) ;
        String cordinateX = getString(getColumnIndex(taxiDriverSchema.marksTable.Cols.CORDINATE_X)) ;
        String cordinateY = getString(getColumnIndex(taxiDriverSchema.marksTable.Cols.CORDINATE_Y)) ;

        Bundle data = new Bundle() ;
        data.putString(Constant.DB_key_Mark_Title , title);
        data.putString(Constant.DB_key_Mark_Longitude , cordinateX);
        data.putString(Constant.DB_key_Mark_Latitude , cordinateY);

        return data ;
    }

}

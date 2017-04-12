package ir.evoteam.evomap;
/**
 * Created by programmer on 4/7/2017.
 */

public class taxiDriverSchema {
    public static final class driverStateTable {
        public static final String NAME = "driverStates" ;

        public static final class Cols {
            public static final String STATE = "State" ;
            public static final String CORDINATE_X = "X" ;
            public static final String CORDINATE_Y = "Y" ;
            public static final String DATETIME = "Date_Time" ;
        }
    }
    public static final class marksTable {
        public static final String NAME = "Marks" ;

        public static final class Cols {
            public static final String TITLE = "Title" ;
            public static final String CORDINATE_X = "X" ;
            public static final String CORDINATE_Y = "Y" ;
        }
    }
}

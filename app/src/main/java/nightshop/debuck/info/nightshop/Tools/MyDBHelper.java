package nightshop.debuck.info.nightshop.Tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created on 12/21/2016.
 */
public class MyDBHelper extends SQLiteOpenHelper {

    /* Contract class */
    public final class MyDBContract {

        /* Types */
        private static final String TEXT_TYPE = " TEXT";
        private static final String INT_TYPE = " INTEGER";
        private static final String REAL_TYPE = " REAL";
        private static final String DATE_TYPE = " DATETIME";
        private static final String TIME = " TIME";
        private static final String COMMA_SEP = ", ";
        private static final String DEFAULT = " DEFAULT ";

        /* SQL Queries */



        /**
         * table: Schedule       this table will contain all the session and its values
         *
         * --------------------
         * id               INT
         * name             TEXT
         * date             TEXT
         * room             TEXT
         * fk_buildings_id  INT     // This will point to a mBuilding id
         * code             INT
         * expected         INT
         */
        private static final String SQL_CREATE_ENTRIES_SCHEDULE =
                "CREATE TABLE " + Schedule.TABLE_SCHEDULE_NAME + " (" +
                        Schedule.COLUMN_NAME_SCHEDULE_ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT," +
                        Schedule.COLUMN_NAME_SCHEDULE_TIME_START + TIME + COMMA_SEP +
                        Schedule.COLUMN_NAME_SCHEDULE_TIME_END + TIME + COMMA_SEP +
                        Schedule.COLUMN_NAME_SCHEDULE_FK_BUILDINGS_ID + INT_TYPE + " )";


        /**
         * table Building           This table will contains all mBuilding information
         *
         * ------------------
         * id           INT
         * name         TEXT
         */
        private static final String SQL_CREATE_ENTRIES_BUILDING =
                "CREATE TABLE " + Building.TABLE_BUILDING_NAME + " (" +
                        Building.COLUMN_NAME_BUILDING_ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT, " +
                        Building.COLUMN_NAME_BUILDING_NAME + TEXT_TYPE + COMMA_SEP +
                        Building.COLUMN_NAME_BUILDING_ADDRESS + TEXT_TYPE + COMMA_SEP +
                        Building.COLUMN_NAME_BUILDING_LAT + REAL_TYPE + COMMA_SEP +
                        Building.COLUMN_NAME_BUILDING_LNG + REAL_TYPE + COMMA_SEP +
                        Building.COLUMN_NAME_BUILDING_DESCRIPTION + TEXT_TYPE + " )";


        /* DROP Schedule */
        private static final String SQL_DELETE_ENTRIES_SCHEDULE =
                "DROP TABLE IF EXISTS " + Schedule.TABLE_SCHEDULE_NAME;
        /* DROP Building */
        private static final String SQL_DELETE_ENTRIES_BUILDING =
                "DROP TABLE IF EXISTS " + Building.TABLE_BUILDING_NAME;
        /* DROP GENERAL */
        private static final String SQL_DELETE_ENTRIES_TABLE =
                "DROP TABLE IF EXISTS ";

        // To prevent someone from accidentally instantiating the contract class,
        // make the constructor private.
        private MyDBContract() {}

        /* Inner class that defines the table contents */
        public static final String COLUMN_ID = "id";




        /* Schedule table */
        public class Schedule implements BaseColumns{
            public static final String TABLE_SCHEDULE_NAME = "Schedule";
            public static final String COLUMN_NAME_SCHEDULE_ID = "id";
            public static final String COLUMN_NAME_SCHEDULE_TIME_START = "time_start";
            public static final String COLUMN_NAME_SCHEDULE_TIME_END = "time_end";
            public static final String COLUMN_NAME_SCHEDULE_FK_BUILDINGS_ID = "fk_buildings_id";
        }

        /* Building table */
        public class Building implements BaseColumns{
            public static final String TABLE_BUILDING_NAME = "Building";
            public static final String COLUMN_NAME_BUILDING_ID = "id";
            public static final String COLUMN_NAME_BUILDING_NAME = "name";
            public static final String COLUMN_NAME_BUILDING_ADDRESS = "address";
            public static final String COLUMN_NAME_BUILDING_LAT = "lat";
            public static final String COLUMN_NAME_BUILDING_LNG = "lng";
            public static final String COLUMN_NAME_BUILDING_DESCRIPTION = "description";
        }
    }

    /* DB Helper */
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "MyDB.db";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* This function erases all data from database */
    public void eraseData(SQLiteDatabase db){
        db.execSQL(MyDBContract.SQL_DELETE_ENTRIES_SCHEDULE);
        db.execSQL(MyDBContract.SQL_DELETE_ENTRIES_BUILDING);
        onCreate(db);
    }

    /* This function erases all data from the selected table */
    public void eraseDataFromTable(SQLiteDatabase db, String tableName){
        db.delete(tableName, null, null);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(MyDBContract.SQL_CREATE_ENTRIES_SCHEDULE);
        db.execSQL(MyDBContract.SQL_CREATE_ENTRIES_BUILDING);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        /*db.execSQL(MyDBContract.SQL_DELETE_ENTRIES_TICKET);
        db.execSQL(MyDBContract.SQL_DELETE_ENTRIES_CHECKINGONLINE);
        db.execSQL(MyDBContract.SQL_DELETE_ENTRIES_CHECKINGBUFFER);
        db.execSQL(MyDBContract.SQL_DELETE_ENTRIES_SCHEDULE);
        db.execSQL(MyDBContract.SQL_DELETE_ENTRIES_BUILDING);
        onCreate(db);*/
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

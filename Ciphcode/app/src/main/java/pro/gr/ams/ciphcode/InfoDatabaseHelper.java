package pro.gr.ams.ciphcode;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cuya Jeh on 29 Apr 2018.
 */

public class InfoDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Ciphcode.db";
    public static final String TABLE_INFO = "Info";
    public static final String TABLE_REG = "Registration";

    public InfoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase infodb) {
        infodb.execSQL("CREATE TABLE "+TABLE_INFO+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,CiphCode TEXT,FirstTime TEXT)");
        infodb.execSQL("CREATE TABLE "+TABLE_REG+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,XternAccount TEXT,RegistrationKey TEXT,TimeAndDate TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" +TABLE_INFO);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS" +TABLE_REG);
        onCreate(db);
    }
}
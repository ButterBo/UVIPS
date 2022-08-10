package sg.edu.rp.c346.id21044912.uvips;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "singaporeData.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_UV = "uv_list";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_VALUE = "value";
    private static final String COLUMN_TIME = "time";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUVTableSql = "CREATE TABLE " + TABLE_UV + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_VALUE + " INTEGER, "
                + COLUMN_TIME +" TEXT ) ";
        db.execSQL(createUVTableSql);

        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("ALTER TABLE " + TABLE_UV + " ADD COLUMN  module_name TEXT ");
        onCreate(db);
    }

    public long insertUV(int value, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_VALUE, value);
        values.put(COLUMN_TIME, time);
        long result = db.insert(TABLE_UV, null, values);
        db.close();
        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<UVLight> getAllUVI(){
        ArrayList<UVLight> uv = new ArrayList<UVLight>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_VALUE, COLUMN_TIME};
        Cursor cursor = db.query(TABLE_UV, columns, null, null,
                null, null, COLUMN_VALUE, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int value = cursor.getInt(1);
                String time = cursor.getString(2);

                UVLight meEye = new UVLight(value, time);
                uv.add(meEye);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return uv;
    }

}

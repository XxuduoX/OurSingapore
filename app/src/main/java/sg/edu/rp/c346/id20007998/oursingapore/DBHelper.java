package sg.edu.rp.c346.id20007998.oursingapore;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sgIsland.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ISLAND = "island";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_SQUARE = "square";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createIslandTableSql="CREATE TABLE " + TABLE_ISLAND + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_SQUARE + " INTEGER, " + COLUMN_STARS + " REAL ) ";
        db.execSQL(createIslandTableSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ISLAND);
        onCreate(db);

    }

    public long insertIsland(String name,String description,int square,float stars){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME,name);
        values.put(COLUMN_DESCRIPTION,description);
        values.put(COLUMN_SQUARE,square);
        values.put(COLUMN_STARS,stars);
        long result=db.insert(TABLE_ISLAND,null,values);
        db.close();
        return result;
    }

    public ArrayList<island> getAllIsland(){
        ArrayList<island> islands = new ArrayList<island>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME +","
                + COLUMN_DESCRIPTION+","
                +COLUMN_SQUARE+","
                +COLUMN_STARS+ " FROM " + TABLE_ISLAND;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do{
                int id=cursor.getInt(0);
                String name=cursor.getString(1);
                String description=cursor.getString(2);
                int square=cursor.getInt(3);
                float stars=cursor.getFloat(4);
                island island=new island(id,name,description,square,stars);
                islands.add(island);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return islands;

    }
    public ArrayList<island> getIslandByFilter(){
        ArrayList<island> islands = new ArrayList<island>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns={COLUMN_ID,COLUMN_NAME,COLUMN_DESCRIPTION,COLUMN_SQUARE,COLUMN_STARS};
        String condition=COLUMN_STARS+ " = 5";
        Cursor cursor = db.query(TABLE_ISLAND,columns,condition,null, null,null,null);
        if (cursor.moveToFirst()) {
            do{
                int id=cursor.getInt(0);
                String name=cursor.getString(1);
                String description=cursor.getString(2);
                int square=cursor.getInt(3);
                float stars=cursor.getFloat(4);
                island island=new island(id,name,description,square,stars);
                islands.add(island);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return islands;

    }
    public int updateIsland(island data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_SQUARE, data.getSquare());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_ISLAND, values, condition, args);
        db.close();
        return result;
    }
    public int deleteIsland(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_ISLAND, condition, args);
        db.close();
        return result;
    }
}

package DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import Model.Movies;

/**
 * Database adapter class for creating table and relevant methods
 * Created by Anwar on 16-Oct-16.
 */

public class DataBaseAdapter extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Magine";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_MOVIES = "CREATE TABLE "
            + Movies.TABLE_NAME + "(" + Movies.COLUMN_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
            + Movies.COLUMN_SUBTITLE + " TEXT , " + Movies.COLUMN_THUMB + " TEXT , " + Movies.COLUMN_IMAGE + " TEXT , " + Movies.COLUMN_TITLE + " TEXT UNIQUE" +
            " ," +
            Movies.COLUMN_STUDIO + " TEXT , " + Movies.COLUMN_SOURCE + " TEXT )  ;";
    private SQLiteDatabase db;

    public DataBaseAdapter(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
    insertion method or movies row
     */
    public void insertInMovies(Movies movies) {

        insert(Movies.TABLE_NAME, movies.fields, movies.getValues(), Movies.COLUMN_ID);
        this.getReadableDatabase().close();
    }

    /**
     * method to retrieve all movies in database
     *
     * @return array list contains all movies item
     */
    public ArrayList<Movies> getAllMovies() {
        ArrayList<Movies> res = new ArrayList<>();
        db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + Movies.TABLE_NAME + ";";

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {

            do {

                Movies temp = new Movies();
//                temp.setId(c.getInt(c.getColumnIndex(Movies.COLUMN_ID)));
                temp.setImage(c.getString(c.getColumnIndex(Movies.COLUMN_IMAGE)));
                temp.setThumb(c.getString(c.getColumnIndex(Movies.COLUMN_THUMB)));
                temp.setStudio(c.getString(c.getColumnIndex(Movies.COLUMN_STUDIO)));
                temp.setTitle(c.getString(c.getColumnIndex(Movies.COLUMN_TITLE)));
                temp.setSubtitle(c.getString(c.getColumnIndex(Movies.COLUMN_SUBTITLE)));
                temp.setSourceUrl(c.getString(c.getColumnIndex(Movies.COLUMN_SOURCE)));
                res.add(temp);

            } while (c.moveToNext());
        }
        c.close();
        this.getReadableDatabase().close();
        return res;
    }

    /**
     * creating tha table
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIES);

    }

    /**
     * insert method
     *
     * @param table
     * @param fields
     * @param values
     * @param whereCause
     * @return
     */
    public Cursor insert(String table, String[] fields, String[] values, String whereCause) {
        Cursor c = null;
        try {

            SQLiteDatabase db = null;
            db = this.getReadableDatabase();
            ContentValues vals = new ContentValues();
            for (int i = 0; i < fields.length; i++)
                vals.put(fields[i], values[i]);

            long dd = db.insert(table, null, vals);
            dd = dd;
            String query = " Select " + whereCause + " from " + table + " order by " + whereCause + " DESC limit 1 ;";
            c = db.rawQuery(query, null);

        } catch (Exception e) {
            Log.e("Magine", "Exception", e);
        }

        return c;
    }

    /**
     * Delete table of exists
     *
     * @param db
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + Movies.TABLE_NAME);
        // create new tables
        onCreate(db);
    }
}

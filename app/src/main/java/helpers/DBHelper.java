package helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.Word;

/**
 * Created by aomidi on 6/30/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public final static String TAG = "wordsCardDatabase";

    private static String DATABASE_NAME = "wordsDatabase";
    private static int DATABASE_VERSION = 1;

    private static String WORDS_TABLE_NAME = "wordsTable";
    private static String COLUMN_ID = "id";
    private static String COLUMN_WORD = "word";
    private static String COLUMN_DESCRIPTION = "description";
    private static String COLUMN_CAPTURE_DATE = "captureDate";

    private static String CREATE_WORD_TABLE_SQL = "CREATE TABLE " +
            WORDS_TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_WORD + " TEXT, " +
            COLUMN_DESCRIPTION + " TEXT, " +
            COLUMN_CAPTURE_DATE + " INTEGER)";

    public DBHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WORD_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addNewWord(String newWord) {
        addNewWord(newWord, null);
    }

    public void addNewWord(String newWord, String description) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_WORD, newWord);
            values.put(COLUMN_DESCRIPTION, description);
            values.put(COLUMN_CAPTURE_DATE, System.currentTimeMillis());
            long row = db.insert(WORDS_TABLE_NAME, null, values);
            if (row == -1) {
                Log.e(TAG, "Word was not inserted!");
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Word> getAllWords() {
        List<Word> resultWords = new ArrayList<>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + WORDS_TABLE_NAME, null);
            if (c.moveToFirst()) {
                do {
                    long id = c.getLong(c.getColumnIndex(COLUMN_ID));
                    String word = c.getString(c.getColumnIndex(COLUMN_WORD));
                    String des = c.getString(c.getColumnIndex(COLUMN_DESCRIPTION));
                    long date = c.getLong(c.getColumnIndex(COLUMN_CAPTURE_DATE));
                    resultWords.add(new Word(id, word, des, date));
                } while (c.moveToNext());
            }
            c.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultWords;
    }

    public boolean updateWord(String word, String description) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_DESCRIPTION, description);
            db.update(WORDS_TABLE_NAME, values, COLUMN_WORD + "=?" , new String[]{word});
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public ArrayList<String> getNoneTranslatedList(){
        ArrayList<String> resultList = new ArrayList<>();
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + WORDS_TABLE_NAME + " WHERE " + COLUMN_DESCRIPTION + " IS NULL", null);
            if(c.moveToFirst()){
                do{
                    String word = c.getString(c.getColumnIndex(COLUMN_WORD));
                    resultList.add(word);
                }while (c.moveToNext());
            }
            c.close();
            db.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return resultList;
    }

    public boolean deleteWord(String word){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(WORDS_TABLE_NAME, COLUMN_WORD + "=?", new String[]{word});
            db.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}

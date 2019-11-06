package vn.sunasterisk.buoi15_sqlite.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import vn.sunasterisk.buoi15_sqlite.data.model.New;

public class NewsDatabaseHelper extends SQLiteOpenHelper {

    private Context mContext;
    private static final String DATABASE_NAME = "news.db";
    private static final int DATABASE_VERSION = 1;

    //sql tạo bảng
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NewsContract.NewEntry.TABLE_NAME + "(" +
                    NewsContract.NewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NewsContract.NewEntry.COLUMN_TITLE + " TEXT," +
                    NewsContract.NewEntry.COLUMN_CONTENT + " TEXT" +
                    ")";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NewsContract.NewEntry.TABLE_NAME;

    public NewsDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insertNew(New aNew) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NewsContract.NewEntry.COLUMN_TITLE, aNew.getTitle());
        values.put(NewsContract.NewEntry.COLUMN_CONTENT, aNew.getContent());

        long newRowId = db.insert(NewsContract.NewEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            //trả về -1 khi insert lỗi.
            Toast.makeText(mContext, "Insert Failure!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Insert Success!", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    public List<New> getAllNews() {
        List<New> news = new ArrayList<>();

        String selectAllQuery = "SELECT * FROM " + NewsContract.NewEntry.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        //Lấy ra một thằng Cusor
        Cursor c = db.rawQuery(selectAllQuery, null);
        if (c == null) {
            return news;
        }

        //di chuyển con trỏ về đầu bảng
        c.moveToFirst();

        // Khi mà cusor chưa ở cuối thì chúng ta vẫn đọc
        while (!c.isAfterLast()) {
            //đọc từng hàng

            int id = c.getInt(c.getColumnIndex(NewsContract.NewEntry._ID));
            String title = c.getString(c.getColumnIndex(NewsContract.NewEntry.COLUMN_TITLE));
            String content = c.getString(c.getColumnIndex(NewsContract.NewEntry.COLUMN_CONTENT));

            New aNew = new New(id, title, content);
            news.add(aNew);
            // next xuong hang duoi de doc tiep
            c.moveToNext();
        }

        c.close();
        db.close();
        return news;
    }

    public New selectNew(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String tableName = NewsContract.NewEntry.TABLE_NAME;
        String[] columns = {
                NewsContract.NewEntry._ID,
                NewsContract.NewEntry.COLUMN_TITLE,
                NewsContract.NewEntry.COLUMN_CONTENT
        };

        String selection = NewsContract.NewEntry._ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        Cursor c = db.query(
                tableName,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null
        );

        if (c == null) {
            return null;
        }

        c.moveToFirst();
        String title = c.getString(c.getColumnIndex(NewsContract.NewEntry.COLUMN_TITLE));
        String content = c.getString(c.getColumnIndex(NewsContract.NewEntry.COLUMN_CONTENT));

        New aNew = new New(id, title, content);
        c.close();
        db.close();

        return aNew;
    }

    public void updateNew(int id, String newTitle, String newContent) {
        SQLiteDatabase db = this.getWritableDatabase();

        String tableName = NewsContract.NewEntry.TABLE_NAME;
        ContentValues values = new ContentValues();
        values.put(NewsContract.NewEntry.COLUMN_TITLE, newTitle);
        values.put(NewsContract.NewEntry.COLUMN_CONTENT, newContent);
        String selection = NewsContract.NewEntry._ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        // tra ve so dong update
        int count = db.update(
                tableName,
                values,
                selection,
                selectionArgs
        );

        if (count > 0) {
            Toast.makeText(mContext, "Update Success!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Update Failure!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void deleteNew(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String tableName = NewsContract.NewEntry.TABLE_NAME;
        String selection = NewsContract.NewEntry._ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};

        int deletedRow = db.delete(tableName, selection, selectionArgs);

        if (deletedRow > 0) {
            Toast.makeText(mContext, "Deleted!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Did not delete!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}

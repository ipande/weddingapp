package desipride.socialshaadi.shadidata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by parth.mehta on 10/10/15.
 */
public class NewsFeedDataSource {
    private static final String TAG = NewsFeedDataSource.class.getSimpleName();

    private static String NewdFeedColumns[] = {
            DbContract.NewsFeedTable._ID,
            DbContract.NewsFeedTable.COLUMN_NAME_URL,
            DbContract.NewsFeedTable.COLUMN_NAME_CAPTION,
            DbContract.NewsFeedTable.COLUMN_NAME_MEDIATYPE,
    };

    private static final String SELECT_ALL_QUERY = "SELECT * FROM " + DbContract.NewsFeedTable.TABLE_NAME;

    public static long insertNewsFeedItem(NewsFeedItem newsFeedItem, Context context) {
        AppDbHelper dbHelper = new AppDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbContract.NewsFeedTable._ID, newsFeedItem.getId());
        values.put(DbContract.NewsFeedTable.COLUMN_NAME_URL, newsFeedItem.getUrl());
        values.put(DbContract.NewsFeedTable.COLUMN_NAME_CAPTION, newsFeedItem.getCaption());
        values.put(DbContract.NewsFeedTable.COLUMN_NAME_MEDIATYPE, newsFeedItem.getDimentions());

        long newRowId;
        newRowId = db.insertWithOnConflict (
                DbContract.NewsFeedTable.TABLE_NAME,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    return newRowId;
    }

    public static void queryAllNewsFeedItems(Context context) {
        AppDbHelper dbHelper = new AppDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL_QUERY, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NewsFeedItem item = cursorToNewsFeedItem(cursor);
            Log.d(TAG, item.toString());
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
    }

    public static Cursor queryAllNewsFeedItemsGetCursor(Context context) {
        AppDbHelper dbHelper = new AppDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DbContract.NewsFeedTable.TABLE_NAME, NewdFeedColumns, null, null, null, null, DbContract.NewsFeedTable._ID + " DESC");
        cursor.moveToFirst();
        db.close();
        return cursor;
    }

    public static NewsFeedItem cursorToNewsFeedItem(Cursor cursor) {
        NewsFeedItem item = new NewsFeedItem();
        item.setId(cursor.getLong(0));
        item.setUrl(cursor.getString(1));
        item.setCaption(cursor.getString(2));
        item.setDimentions(cursor.getString(3));
        return item;
    }
}

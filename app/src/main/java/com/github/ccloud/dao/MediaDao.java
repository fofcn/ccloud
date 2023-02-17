package com.github.ccloud.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MediaDao {

    private final MediaSQLiteOpenHelper mediaSQLiteOpenHelper;

    private final String tableName;

    public MediaDao(final Context context, final String tableName) {
        this.mediaSQLiteOpenHelper = new MediaSQLiteOpenHelper(context);
        this.tableName = tableName;
    }

    public void addMedia(Media media) {
        SQLiteDatabase mediaDB = mediaSQLiteOpenHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("file_name", media.getFileName());
        values.put("store_path", media.getStorePath());
        values.put("file_last_modified", media.getFileLast_modified().toString());
        values.put("media_type", media.getMediaType());
        values.put("sync", media.getSync());
        values.put("sync_version", media.getSync());
        values.put("sync_time", media.getSyncTime().toString());
        values.put("create_time", media.getCreateTime().toString());
        mediaDB.insert(tableName, null, values);
        mediaDB.close();
    }

    public boolean isContainsMedia(String mediaName, int mediaType) {
        boolean isHasRecord = false;
        SQLiteDatabase mediaDB = mediaSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = mediaDB.query(tableName, new String[]{ "id" }, "file_name=? and media_type=?", new String[] { mediaName, mediaType + "" }, null, null, null);
        if (cursor.getCount() > 0) {
            isHasRecord = true;
        }
        mediaDB.close();
        return isHasRecord;
    }

    public int delete(String mediaName, int mediaType) {
        SQLiteDatabase mediaDB = mediaSQLiteOpenHelper.getWritableDatabase();
        int d = mediaDB.delete(tableName, "file_name=? and media_type=?", new String[] { mediaName, mediaType + "" });
        mediaDB.close();
        return d;
    }

}

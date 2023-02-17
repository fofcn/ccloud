package com.github.ccloud.common.sync.progress.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.ccloud.common.sync.meta.dao.FileMetaSQLiteOpenHelper;
import com.github.ccloud.common.sync.progress.Progress;
import com.github.ccloud.common.sync.progress.ProgressManager;


public class SyncProgressDao {

    private final FileMetaSQLiteOpenHelper fileMetaSQLiteOpenHelper;

    private final String tableName;

    public SyncProgressDao(final FileMetaSQLiteOpenHelper fileMetaSQLiteOpenHelper, final String tableName) {
        this.fileMetaSQLiteOpenHelper = fileMetaSQLiteOpenHelper;
        this.tableName = tableName;
    }

    @SuppressLint("Range")
    public Progress getProgress() {
        Progress progress = new Progress();
        String query = "select last_offset from `sync_progress`";
        SQLiteDatabase db = fileMetaSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            long lastOffset = cursor.getLong(cursor.getColumnIndex("last_offset"));
            progress.setLastOffset(lastOffset);
        }

        return progress;
    }

    public void updateProgress(long lastOffset) {
        String update = "update `sync_progress` set last_offset=" + lastOffset;
        SQLiteDatabase db = fileMetaSQLiteOpenHelper.getWritableDatabase();
        db.execSQL(update);
    }
}

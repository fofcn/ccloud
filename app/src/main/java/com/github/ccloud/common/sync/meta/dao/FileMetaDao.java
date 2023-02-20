package com.github.ccloud.common.sync.meta.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.github.ccloud.common.date.DateUtil;
import com.github.ccloud.common.sync.meta.FileMeta;
import com.github.ccloud.dao.Media;
import com.github.ccloud.dao.MediaSQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileMetaDao {

    private final FileMetaSQLiteOpenHelper fileMetaSQLiteOpenHelper;

    private final String tableName;

    public FileMetaDao(final FileMetaSQLiteOpenHelper fileMetaSQLiteOpenHelper, final String tableName) {
        this.fileMetaSQLiteOpenHelper = fileMetaSQLiteOpenHelper;
        this.tableName = tableName;
    }

    public void addFileMetaList(List<FileMeta> fileMetaList) {
        SQLiteDatabase fileMetaDB = fileMetaSQLiteOpenHelper.getWritableDatabase();
        fileMetaDB.beginTransaction();

        for (FileMeta fileMeta : fileMetaList) {
            ContentValues values = new ContentValues();
            values.put("id", fileMeta.getId());
            values.put("name", fileMeta.getName());
            values.put("abs_path", fileMeta.getAbsPath());
            values.put("size", fileMeta.getSize());
            values.put("file_last_modified_time", DateUtil.dateToString(fileMeta.getFileLastModifyTime()));
            values.put("sync", fileMeta.getSync());
            values.put("sync_action", fileMeta.getSyncAction());
            values.put("sync_result", fileMeta.getSyncResult());
            values.put("sync_version", fileMeta.getSyncVersion());
            values.put("sync_time", fileMeta.getSyncTime() == null ? null : DateUtil.dateToString(fileMeta.getSyncTime()));
            values.put("create_time", DateUtil.dateToString(fileMeta.getCreateTime()));
            fileMetaDB.insert(tableName, null, values);
        }

        fileMetaDB.setTransactionSuccessful();
        fileMetaDB.endTransaction();
    }

    @SuppressLint("Range")
    public List<FileMeta> getNotSyncFileMetaList(long offset) {
        List<FileMeta> fileMetaList = new ArrayList<>();
        SQLiteDatabase mediaDB = fileMetaSQLiteOpenHelper.getReadableDatabase();
        String query = "select id, name, abs_path, size, sync_action, file_last_modified_time from file_meta where sync=0 limit " + offset + ", 10";
        Cursor cursor = mediaDB.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                FileMeta fileMeta = new FileMeta();
                fileMeta.setId(cursor.getString(cursor.getColumnIndex("id")));
                fileMeta.setName(cursor.getString(cursor.getColumnIndex("name")));
                fileMeta.setAbsPath(cursor.getString(cursor.getColumnIndex("abs_path")));
                fileMeta.setSize(cursor.getLong(cursor.getColumnIndex("size")));
                fileMeta.setSyncAction(cursor.getInt(cursor.getColumnIndex("sync_action")));
                fileMeta.setFileLastModifyTime(DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex("file_last_modified_time"))));
                fileMetaList.add(fileMeta);
            } while (cursor.moveToNext());
        }

        return fileMetaList;
    }

    public void updateSyncState(FileMeta fileMeta) {
        String query = "update `file_meta` set sync=?, sync_action=?, sync_result=?, sync_version=?, sync_time=? where id=?";
        SQLiteDatabase mediaDB = fileMetaSQLiteOpenHelper.getReadableDatabase();
        mediaDB.execSQL(query, new Object[]{fileMeta.getSync(), fileMeta.getSyncAction(), fileMeta.getSyncResult(), fileMeta.getSyncVersion(), fileMeta.getSyncVersion(), fileMeta.getId()});
    }
}

package com.github.ccloud.common.sync.meta.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.ccloud.util.ContextHolder;

public class FileMetaSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String FILE_META_DB_NAME = "filemeta.db";

    private static final int FILE_META_DB_VERSION = 100;

    private static final String FILE_META_TABLE_DDL = "create table if not exists `file_meta`(id varchar(32) not null primary key,name varchar(128) not null,abs_path varchar(256) not null,size int not null,file_last_modified_time datetime not null,sync int not null,sync_action int not null,sync_result int,sync_version int, sync_time datetime,create_time datetime not null)";

    private static final String FILE_SYNC_PROGRESS_DDL = "create table if not exists `sync_progress` (" +
            "  last_offset int not null DEFAULT 0 " +
            ");";

    private volatile static FileMetaSQLiteOpenHelper instance;

    public FileMetaSQLiteOpenHelper(Context context) {
        super(context, FILE_META_DB_NAME, null, FILE_META_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FILE_META_TABLE_DDL);
        db.execSQL(FILE_SYNC_PROGRESS_DDL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

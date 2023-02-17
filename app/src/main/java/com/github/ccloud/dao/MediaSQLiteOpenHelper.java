package com.github.ccloud.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MediaSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String MEDIA_DB_NAME = "media.db";

    private static final int MEDIA_DB_VERSION = 100;

    private static final String FILE_SYNC_TABLE_SQL = "create table if not exists `sync_file`(`id` integer primary key autoincrement not null,`file_name` varchar(128) not null, `store_path` varchar(256) not null);";
    private static final String PHOTO_FILE_TABLE_SQL = "create table if not exists `photo`(`id` integer primary key autoincrement not null,`file_name` varchar(128) not null, `store_path` varchar(128) not null,`file_last_modified` datetime not null,`sync` tinyint not null,`sync_version` int not null,`sync_time` datetime null,`create_time` datetime not null)";
    private static final String VIDEO_FILE_TABLE_SQL = "create table if not exists `video`(`id` integer primary key autoincrement not null,`file_name` varchar(128) not null, `store_path` varchar(128) not null,`file_last_modified` datetime not null,`sync` tinyint not null,`sync_version` int not null,`sync_time` datetime null,`create_time` datetime not null)";

    public MediaSQLiteOpenHelper(Context context) {
        super(context, MEDIA_DB_NAME, null, MEDIA_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FILE_SYNC_TABLE_SQL);
        db.execSQL(PHOTO_FILE_TABLE_SQL);
        db.execSQL(VIDEO_FILE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

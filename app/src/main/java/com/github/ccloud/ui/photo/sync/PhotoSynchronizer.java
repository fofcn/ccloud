package com.github.ccloud.ui.photo.sync;

import static android.content.ContentValues.TAG;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.github.ccloud.common.sync.Synchronizer;
import com.github.ccloud.util.ContextHolder;

public class PhotoSynchronizer extends Synchronizer {

    @Override
    protected void doSync() {
        String sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " DESC ";
        Cursor cursor = ContextHolder.getContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, sortOrder);
        if (null != cursor) {
            int count = cursor.getCount();
            Log.d(TAG, "共有图片: " + count + " 个");
            while (cursor.moveToNext()) {
                //获取图片的名称
                int nameIdx = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
                String name = cursor.getString(nameIdx);
                //获取图片的生成日期
                int createDateIdx = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                byte[] data = cursor.getBlob(createDateIdx);

                // 照片路径
                int pathIdx = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                String path = cursor.getString(pathIdx);

                //获取图片的详细信息
                int descIdx = cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION);
                String desc = cursor.getString(descIdx);

                Log.i(TAG, "photo name: " + name + ", path: " + path + ", desc: " + desc);
            }
        }
    }
}

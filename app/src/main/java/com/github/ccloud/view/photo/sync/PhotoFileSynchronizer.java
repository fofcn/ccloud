package com.github.ccloud.view.photo.sync;

import static android.content.ContentValues.TAG;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.github.ccloud.common.date.DateUtil;
import com.github.ccloud.common.sync.fetcher.FileFetcher;
import com.github.ccloud.common.sync.meta.FileMeta;
import com.github.ccloud.common.sync.upload.FileUploader;
import com.github.ccloud.common.sync.upload.UploadResult;
import com.github.ccloud.entity.Response;
import com.github.ccloud.http.api.FileHttpApi;
import com.github.ccloud.http.factory.HttpApiFactory;
import com.github.ccloud.util.AuthUtil;
import com.github.ccloud.util.ContextHolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class PhotoFileSynchronizer implements FileFetcher, FileUploader {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public List<FileMeta> fetch(int pageSize, int offset) {
        List<FileMeta> fileMetaList = new ArrayList<>();
        String[] projection = createProjection();
        Bundle queryArgs = createQueryArgs(pageSize, offset);
        Cursor cursor = ContextHolder.getContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                queryArgs,
                null);
        if (null != cursor) {
            int count = cursor.getCount();
            Log.d(TAG, "共有图片: " + count + " 个");
            while (cursor.moveToNext()) {
                FileMeta fileMeta = new FileMeta();
                //获取图片的名称
                int nameIdx = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
                String name = cursor.getString(nameIdx);
                fileMeta.setName(name);

                // 照片路径
                int pathIdx = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                String path = cursor.getString(pathIdx);
                fileMeta.setAbsPath(path);

                //获取图片的详细信息
                int descIdx = cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION);
                String desc = cursor.getString(descIdx);

                Log.i(TAG, "photo name: " + name + ", path: " + path + ", desc: " + desc);

                File photo = new File(path);
                Date photoCreateTime = new Date(photo.lastModified());
                fileMeta.setFileLastModifyTime(photoCreateTime);
                fileMeta.setSize(photo.length());
                fileMetaList.add(fileMeta);
            }
            cursor.close();
        }
        return fileMetaList;
    }


    @Override
    public UploadResult upload(FileMeta fileMeta) {
        String createTimeStr = DateUtil.dateToString(fileMeta.getFileLastModifyTime());
        MultipartBody body = createMultipartFile(createTimeStr, fileMeta.getAbsPath()) ;
        String token = AuthUtil.getToken();
        FileHttpApi fileApi = HttpApiFactory.getInstance().createHttpApi(FileHttpApi.class);
        Call<Response<Void>> call = fileApi.upload(body, token);
        try {
            retrofit2.Response<Response<Void>> response = call.execute();
            if (response.isSuccessful() && response.body().isSuccess()) {
                Log.i(TAG, "Upload file success, file name: " + fileMeta.getName());
                return UploadResult.success();
            } else {
                Log.i(TAG, "Upload file failed, file name: " + response.message());
                return UploadResult.failed("");
            }
        } catch (IOException e) {
            Log.i(TAG, "Upload file failed, file name: " + e.getMessage());
            return UploadResult.failed("");
        }

    }

    private MultipartBody createMultipartFile(String createTime, String filePath) {
        File toUploadFile = new File(filePath);
        RequestBody fileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), toUploadFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", toUploadFile.getName(), fileRequest);
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        return bodyBuilder
                .setType(MediaType.parse("multipart/form-data"))
                .addPart(part)
                .addFormDataPart("createTime", createTime)
                .addFormDataPart("mediaType", "photo").build();
    }

    private Bundle createQueryArgs(int pageSize, int offset) {
        Bundle queryArgs = new Bundle();
        queryArgs.putInt(ContentResolver.QUERY_ARG_OFFSET, offset);
        queryArgs.putInt(ContentResolver.QUERY_ARG_LIMIT, pageSize);
        queryArgs.putInt(ContentResolver.QUERY_ARG_SORT_DIRECTION, ContentResolver.QUERY_SORT_DIRECTION_ASCENDING);
        ArrayList<String> columnList = new ArrayList<>(1);
        columnList.add(MediaStore.Files.FileColumns.DATE_MODIFIED);
        queryArgs.putStringArrayList(ContentResolver.QUERY_ARG_SORT_COLUMNS, columnList);
        return queryArgs;
    }

    private String[] createProjection() {
        return new String[] {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Images.Media.DESCRIPTION
        };

    }

}

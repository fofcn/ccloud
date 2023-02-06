package com.github.ccloud.ui.photo.sync;

import static android.content.ContentValues.TAG;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.github.ccloud.common.sync.Synchronizer;
import com.github.ccloud.constant.HostConstant;
import com.github.ccloud.http.api.FileHttpApi;
import com.github.ccloud.http.client.HttpClient;
import com.github.ccloud.http.entity.Response;
import com.github.ccloud.util.ContextHolder;
import com.github.ccloud.util.SpUtil;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

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

                File photo = new File(path);
                Date photoCreateTime = new Date(photo.lastModified());
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String createTime = dateFormat.format(photoCreateTime);
                upload(createTime, path);
            }
        }
    }

    private void upload(String createTime, String filePath) {
        String token = SpUtil.getInstance().getString(HostConstant.ACCOUNT_TOKEN_KEY, "");
        FileHttpApi fileApi = HttpClient.init(FileHttpApi.class);
        File toUploadFile = new File(filePath);
        RequestBody fileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), toUploadFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", toUploadFile.getName(), fileRequest);
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        MultipartBody body = bodyBuilder.setType(MediaType.parse("multipart/form-data")).addPart(part).addFormDataPart("createTime", createTime).addFormDataPart("mediaType", "photo").build();

        Call<Response<Void>> resp = fileApi.upload(body, token);
        resp.enqueue(new Callback<Response<Void>>() {
            @Override
            public void onResponse(Call<Response<Void>> call, retrofit2.Response<Response<Void>> response) {
                Response resp = response.body();
                if (resp.isSuccess()) {
                    Log.i(TAG, "upload file success. " + filePath);
                }
            }

            @Override
            public void onFailure(Call<Response<Void>> call, Throwable t) {
                Log.e("Network", "网络错误：" + t.getMessage() + "," + t);
                Toast.makeText(ContextHolder.getContext(), "文件上传失败" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

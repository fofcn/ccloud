package com.github.ccloud.ui.index;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.ccloud.R;
import com.github.ccloud.http.entity.dto.PhotoListDto;
import com.github.ccloud.ui.photo.PhotoAdapter;
import com.github.ccloud.util.ContextHolder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PhotoFragment extends Fragment {

    private ListView photoListView;

    private static AtomicInteger photoNameIdx = new AtomicInteger(0);

    public PhotoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.idx_photo, container, false);
        photoListView = view.findViewById(R.id.listViewPhoto);

        List<PhotoListDto> photoListDtos = mockPhotoListDto();
        PhotoAdapter photoAdapter1 = new PhotoAdapter(ContextHolder.getContext());
        photoAdapter1.setDataList(photoListDtos);
        photoListView.setAdapter(photoAdapter1);

        // 处理下拉刷新
        SwipeRefreshLayout photoListRefreshLayout = view.findViewById(R.id.srlPhotoList);
        photoListRefreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"));
        photoListRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor("#0000ff"));
        photoListRefreshLayout.setOnRefreshListener(() -> {
            //判断是否在刷新
            if (photoListRefreshLayout.isRefreshing()) {
                return;
            }

            photoListRefreshLayout.postDelayed(() -> {
                // 从接口获取数据
                photoAdapter1.addAll(mockPhotoListDto());
                photoAdapter1.notifyDataSetChanged();
                photoListRefreshLayout.setRefreshing(false);
            }, 3000);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @NonNull
    private List<PhotoListDto> mockPhotoListDto() {
        List<PhotoListDto> photoListDtos = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            PhotoListDto dto = new PhotoListDto();
            dto.setName("照片-" + photoNameIdx.incrementAndGet());
            dto.setUrl("");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dto.setCreateTime(LocalDateTime.now());
            }
            photoListDtos.add(dto);
        }
        return photoListDtos;
    }
}

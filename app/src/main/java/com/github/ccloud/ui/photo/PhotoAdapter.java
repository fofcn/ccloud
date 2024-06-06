package com.github.ccloud.ui.photo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.ccloud.R;
import com.github.ccloud.entity.dto.PhotoListDto;
import com.github.ccloud.util.ContextHolder;
import com.squareup.picasso.Picasso;

public class PhotoAdapter extends BaseListAdapter<PhotoListDto> {
    public PhotoAdapter(@NonNull Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PhotoListDto photoDto = (PhotoListDto) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(ContextHolder.getContext()).inflate(R.layout.item_photo, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.ivPhoto);
        Picasso.with(ContextHolder.getContext()) //
                .load(photoDto.getUrl()) //加载地址
                .placeholder(R.mipmap.ic_launcher)
                //占位图
                .error(R.mipmap.ic_launcher) //加载失败的图
                .fit() //充满
                .tag(ContextHolder.getContext()) //标记
                .into(imageView);//加载到的ImageView

        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        // Populate the data into the template view using the data object
        tvName.setText(photoDto.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}

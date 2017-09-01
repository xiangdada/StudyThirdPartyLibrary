package com.example.admin.studythirdpartylibrary.uitl;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.example.admin.studythirdpartylibrary.R;

/**
 * Created by xpf on 2017/8/21.
 */

public class GlideUtil {

    public static void setImage(Context context, String string, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerInside()
                .placeholder(R.mipmap.ic_default)
                .error(R.mipmap.ic_default)
                .priority(Priority.HIGH);
        Glide.with(context)
                .load(string)
                .apply(options)
                .into(imageView);
    }
}

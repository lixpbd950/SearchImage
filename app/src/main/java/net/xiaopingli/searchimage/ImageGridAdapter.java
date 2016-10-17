package net.xiaopingli.searchimage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by xiaopingli on 10/17/16.
 */

public class ImageGridAdapter extends ArrayAdapter<ImageObject> {
    public ImageGridAdapter(Context context, List<ImageObject> images) {
        super(context,R.layout.image_result_item ,images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SquaredImageView view = (SquaredImageView) convertView;
        ImageObject imageObject = this.getItem(position);

        if (view == null) {
            view = new SquaredImageView(getContext());
        }
        String url = imageObject.getThumbnailLink();

        Picasso.with(getContext()).load(url).into(view);
        return view;
    }
}

package net.xiaopingli.searchimage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {

    public static final String IMAGEOBJECT_KEY = "imageobject_key";
    private ImageObject imageObject = null;
    private ImageView imageView;

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null){
            imageObject = (ImageObject) savedInstanceState.getSerializable(IMAGEOBJECT_KEY);
        }

        Log.d("tag-=========---","here");

        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.result_image);

        Bundle args = getArguments();
        if (args != null) {
            imageObject = (ImageObject) args.getSerializable(IMAGEOBJECT_KEY);
            String url = imageObject.getLink();
            Log.d("tag-=========---","here,"+url);

            Picasso.with(getContext()).load(url).into(imageView);
        }else{
            Log.d("tag-=========---","here,empty");

        }

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putSerializable(IMAGEOBJECT_KEY, imageObject);
    }

}

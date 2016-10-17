package net.xiaopingli.searchimage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {

    public static final String RESULT_FRAGMENT_TAG = "result_fragment_tag";

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

        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT );
        rootView.setLayoutParams(layoutParams);
        imageView = (ImageView) rootView.findViewById(R.id.result_image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        Bundle args = getArguments();
        if (args != null) {
            imageObject = (ImageObject) args.getSerializable(IMAGEOBJECT_KEY);
            String url = imageObject.getLink();
            Picasso.with(getContext()).load(url).into(imageView);
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

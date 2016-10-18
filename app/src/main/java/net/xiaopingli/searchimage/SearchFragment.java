package net.xiaopingli.searchimage;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;


import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment{

    private static final String GOOGLE_QUERY = "https://www.googleapis.com/customsearch/v1"
            +"?key=AIzaSyD_RO5nT2wmi2u_LrXg3YH7pCjnHaGcRf4&cx=007067661042286930144:7ryhle0ll8c&searchType=image&q=";
    public static final String SEARCH_FRAGMENT_TAG = "search_fragment_tag";

    private ArrayList<ImageObject> imageObjects;
    private EditText editTextSearch;
    private Button buttonSearch;
    private GridView gridViewResult;
    private ImageGridAdapter imageGridAdapter;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        setUpView(rootView);
        imageObjects = ((MainActivity)getActivity()).getImageObjects();
        imageGridAdapter = new ImageGridAdapter(getActivity(),imageObjects);
        gridViewResult.setAdapter(imageGridAdapter);
        return rootView;
    }


    public void notifyAdapter(){
        imageGridAdapter.notifyDataSetChanged();
    }

    public void setUpView(View view){
        editTextSearch = (EditText) view.findViewById(R.id.editTextSearch);
        buttonSearch = (Button) view.findViewById(R.id.buttonSearch);
        gridViewResult = (GridView) view.findViewById(R.id.gridViewResult);


        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = editTextSearch.getText().toString();

                if (query != null){
                    ((MainActivity)getActivity()).startHttpRequestTask(GOOGLE_QUERY+ Uri.encode(query)+"&start=1");
                }

            }
        });


        gridViewResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                ResultFragment resultFragment = new ResultFragment();
                Bundle args = new Bundle();
                args.putSerializable(ResultFragment.IMAGEOBJECT_KEY,imageObjects.get(position));
                resultFragment.setArguments(args);
                fragmentTransaction.replace(R.id.fragment_container,resultFragment,SEARCH_FRAGMENT_TAG);
                fragmentTransaction.addToBackStack(SEARCH_FRAGMENT_TAG);
                fragmentTransaction.commit();
            }
        });

        gridViewResult.setOnScrollListener(new ScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMoreImages(totalItemsCount);
            }
        });
    }

    public void loadMoreImages(int totalItemsCount) {
        String query = editTextSearch.getText().toString();

        if (query != null){
            ((MainActivity)getActivity()).startHttpRequestTask(GOOGLE_QUERY+ Uri.encode(query)+"&start="+totalItemsCount);
        }
    }
}

package net.xiaopingli.searchimage;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {

    private static final String GOOGLE_QUERY = "https://www.googleapis.com/customsearch/v1"
            +"?key=AIzaSyD_RO5nT2wmi2u_LrXg3YH7pCjnHaGcRf4&cx=007067661042286930144:7ryhle0ll8c&searchType=image&q=";
    private static final String GOOGLE_QUERY2 = "https://mwstaticresponses.herokuapp.com/test_json";

    ArrayList<ImageObject> imageObjects;
    EditText editTextSearch;
    Button buttonSearch;
    GridView gridViewResult;
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
                ((MainActivity)getActivity()).startHttpRequestTask(GOOGLE_QUERY2);
            }
        });

        gridViewResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d("====tag","hehe"+position);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                ResultFragment resultFragment = new ResultFragment();
                Bundle args = new Bundle();
                args.putSerializable(ResultFragment.IMAGEOBJECT_KEY,imageObjects.get(position));
                resultFragment.setArguments(args);
                fragmentTransaction.replace(R.id.search_fragment,resultFragment,"this is a tag");
                fragmentTransaction.commit();
            }
        });
    }
}

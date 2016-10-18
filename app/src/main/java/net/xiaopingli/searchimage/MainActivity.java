package net.xiaopingli.searchimage;

import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity{

    private ArrayList<ImageObject> imageObjects = new ArrayList<ImageObject>();
    public static final String DEBUG_TAG = "main_activity_debug_tag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            SearchFragment searchFragment = new SearchFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, searchFragment,SearchFragment.SEARCH_FRAGMENT_TAG).commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startHttpRequestTask(String url){
        new HttpRequestTask().execute(url);
    }

    public ArrayList<ImageObject> getImageObjects(){
        return imageObjects;
    }


    public class HttpRequestTask extends AsyncTask<String, Void, JSONObject> {

        protected JSONObject doInBackground(String... url) {
            JSONParser jsonParser = new JSONParser();
            return jsonParser.getJSONFromUrl(url[0]);
        }

        /** The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground() */
        protected void onPostExecute(JSONObject result) {
            JSONArray imageJsonObjects = null;
            try {
                imageJsonObjects = result.getJSONArray("items");
                imageObjects.addAll(ImageObject.fromJSONArray(imageJsonObjects));
                SearchFragment currentFragment = (SearchFragment)getSupportFragmentManager().findFragmentByTag(SearchFragment.SEARCH_FRAGMENT_TAG);
                if (currentFragment != null && currentFragment.isVisible()){
                    ((SearchFragment) currentFragment).notifyAdapter();
                }
            } catch (JSONException e) {
                Log.e("JSON Error", "Error converting JSON result " + e.toString());
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }
}

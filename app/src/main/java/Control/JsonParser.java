package Control;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import DataBase.DataBaseAdapter;
import Model.Movies;
import Model.URLs;

/**
 * Created by Anwar on 16-Oct-16.
 */

public class JsonParser extends AsyncTask<Void, Void, Void> {
    public static boolean isFailed;
    private final Context context;
    private final DataBaseAdapter dp;
    HashMap<String, String> params;
    private ArrayList<Movies> movies;
    private OnUserRetrieve onUserRetrieve;
    private boolean failed;
    private WebRequest webreq;

    public JsonParser(Context context) {
        dp = new DataBaseAdapter(context);
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        webreq = new WebRequest();
        //String jsonStr = webreq.makeWebServiceCall(url, WebRequest.GETRequest);
        String jsonStr = webreq.makeServiceCall(URLs.URL);
        Log.d("Response: ", "> " + jsonStr);
        ParseJSON(jsonStr);
        return null;
    }

    @Override
    protected void onPostExecute(Void requestresult) {
        super.onPostExecute(requestresult);
        //is failed is static field used by web request if any error occurred while executing the web request for the json
        if (isFailed)
            onUserRetrieve.onFinished(false);
        else
            onUserRetrieve.onFinished(true);
    }

    private void ParseJSON(String json) {
        JSONObject jsonObj;
        if (json != null) {
            try {
                callMoviesRequest(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void setOnUserRetrieve(OnUserRetrieve onUserRetrieve) {
        this.onUserRetrieve = onUserRetrieve;
    }

    /**
     * get the json object and array and find them by tags
     * and save in the data base
     * @param json
     * @throws JSONException
     */
    private void callMoviesRequest(String json) throws JSONException {
        //category request
//        json = json.substring(10);
//        json = json.substring(0, json.length() - 1);
        JSONObject jsonObj = new JSONObject(json);
        movies = new ArrayList<Movies>();
        JSONArray catResults = jsonObj.getJSONArray(URLs.CATEGORIES).getJSONObject(0).getJSONArray(URLs.VIDEOS);
        for (int i = 0; i < catResults.length(); i++) {
            JSONObject c = catResults.getJSONObject(i);
            Movies movies = new Movies();
            movies.setSubtitle(c.getString(URLs.SUBTITLE));
            movies.setTitle(c.getString(URLs.TITLE));
            movies.setStudio(c.getString(URLs.STUDIO));
            movies.setImage(c.getString(URLs.IMAGE));
            movies.setThumb(c.getString(URLs.THUMB));
            movies.setSourceUrl(c.getString(URLs.SOURCES));
            dp.insertInMovies(movies);
        }
        ArrayList<Movies> mm = dp.getAllMovies();
//        mm.get(0).getSourceUrl();
    }

    public interface OnUserRetrieve {
        public void onFinished(boolean successfully);
    }

}

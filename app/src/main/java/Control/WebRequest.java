package Control;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Ravi Tamada on 01/09/16.
 * www.androidhive.info
 */
public class WebRequest {

    private static final String TAG = WebRequest.class.getSimpleName();

    public WebRequest() {
    }

    // an original Http request with a get method
    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            JsonParser.isFailed = true;
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            JsonParser.isFailed = true;
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            JsonParser.isFailed = true;
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            JsonParser.isFailed = true;
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    //get the json response as a string returned
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
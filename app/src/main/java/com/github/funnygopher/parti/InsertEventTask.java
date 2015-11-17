package com.github.funnygopher.parti;

import android.os.AsyncTask;
import android.util.Log;

import com.github.funnygopher.parti.event.Event;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kylhunts on 11/15/2015.
 */
public class InsertEventTask extends AsyncTask<Void, Void, String> {

    private final String address = "http://pumpuptheparti.netne.net/api/insert_event.php";
    private Event event;
    private ResponseCallback responseCallback;

    public InsertEventTask(ResponseCallback responseCallback, Event event) {
        this.responseCallback = responseCallback;
        this.event = event;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            HttpRequest httpRequest = new HttpRequest(HttpRequest.POST, address);
            httpRequest.withString("body=" + event.toJSON());
            String response = httpRequest.send();
            Log.i("InsertEvent#Response", response);
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("Malformed URL Exception", "Malformed URL Exception");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOException", "IOException");
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        responseCallback.onResponseCallback(s);
        super.onPostExecute(s);
    }

    public interface ResponseCallback {
        void onResponseCallback(String response);
    }
}

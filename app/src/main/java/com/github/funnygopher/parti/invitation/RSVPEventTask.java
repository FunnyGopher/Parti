package com.github.funnygopher.parti.invitation;

import android.os.AsyncTask;
import android.util.Log;

import com.github.funnygopher.parti.HttpRequest;
import com.github.funnygopher.parti.event.Event;

import java.io.IOException;

/**
 * Created by kylhunts on 11/17/2015.
 */
public class RSVPEventTask extends AsyncTask<Void, Void, String> {

    private ResponseCallback responseCallback;
    private int eventId;

    public RSVPEventTask(ResponseCallback responseCallback, int eventId) {
        this.responseCallback = responseCallback;
        this.eventId = eventId;
    }

    @Override
    protected String doInBackground(Void... params) {
        String address = "http://pumpuptheparti.netne.net/api/accept_event.php";

        try {
            HttpRequest httpRequest = new HttpRequest(HttpRequest.POST, address);
            httpRequest.withString("id=" + Integer.toString(eventId));
            String response = httpRequest.send();
            Log.i("RSVPEvent#Response", response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
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

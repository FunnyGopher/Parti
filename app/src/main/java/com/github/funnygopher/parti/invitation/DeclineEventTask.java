package com.github.funnygopher.parti.invitation;

import android.os.AsyncTask;
import android.util.Log;

import com.github.funnygopher.gopherhttp.HttpRequest;

import java.io.IOException;

public class DeclineEventTask extends AsyncTask<Void, Void, String> {

    private static final String ADDRESS = "http://pumpuptheparti.netne.net/api/decline_event.php";
    private Long eventId;
    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onResponse(String response) {
            // Do nothing...
        }
    };

    public DeclineEventTask(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            HttpRequest httpRequest = new HttpRequest(HttpRequest.POST, ADDRESS);
            httpRequest.withString("id=" + Long.toString(eventId));
            String response = httpRequest.send();
            Log.i("DeclineEvent#Response", response);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        responseListener.onResponse(s);
        super.onPostExecute(s);
    }

    public void setOnResponseListener(OnResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    public interface OnResponseListener {
        void onResponse(String response);
    }
}

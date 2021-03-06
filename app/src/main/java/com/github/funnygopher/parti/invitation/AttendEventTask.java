package com.github.funnygopher.parti.invitation;

import android.os.AsyncTask;
import android.util.Log;

import com.github.funnygopher.gopherhttp.HttpRequest;

import java.io.IOException;

public class AttendEventTask extends AsyncTask<Void, Void, String> {

    private static final String ADDRESS = "http://pumpuptheparti.netne.net/api/attend_event.php";
    private Long eventId;
    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onResponse(String response) {
            // Do nothing...
        }
    };

    public AttendEventTask(Long eventId) {
        this.eventId = eventId;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            HttpRequest httpRequest = new HttpRequest(HttpRequest.POST, ADDRESS);
            httpRequest.withString("id=" + Long.toString(eventId));
            String response = httpRequest.send();
            Log.i("AttendEvent#Response", response);
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

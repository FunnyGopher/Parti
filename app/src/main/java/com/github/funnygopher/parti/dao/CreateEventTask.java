package com.github.funnygopher.parti.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.github.funnygopher.parti.model.Event;
import com.github.funnygopher.parti.util.HttpRequest;

import java.io.IOException;
import java.net.MalformedURLException;

public class CreateEventTask extends AsyncTask<Void, Void, String> {

    private final String address = "http://pumpuptheparti.netne.net/api/create_event.php";
    private Event event;
    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onResponse(String response) {
            // Do nothing...
        }
    };

    public CreateEventTask(Event event) {
        this.event = event;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            HttpRequest httpRequest = new HttpRequest(HttpRequest.POST, address);
            httpRequest.withString("body=" + event.toJSON());
            String response = httpRequest.send();
            Log.i("CreateEvent#Response", response);
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

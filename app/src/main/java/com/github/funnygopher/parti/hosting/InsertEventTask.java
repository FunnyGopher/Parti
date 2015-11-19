package com.github.funnygopher.parti.hosting;

import android.os.AsyncTask;
import android.util.Log;

import com.github.funnygopher.parti.event.Event;
import com.github.funnygopher.parti.util.HttpRequest;

import java.io.IOException;
import java.net.MalformedURLException;

public class InsertEventTask extends AsyncTask<Void, Void, String> {

    private final String address = "http://pumpuptheparti.netne.net/api/insert_event.php";
    private Event event;
    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onResponse(String response) {

        }
    };

    public InsertEventTask(Event event) {
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

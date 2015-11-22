package com.github.funnygopher.parti.dao;

import android.os.AsyncTask;
import android.util.Log;

import com.github.funnygopher.parti.util.HttpRequest;

import java.io.IOException;
import java.net.MalformedURLException;

public class GetEventTask extends AsyncTask<Void, Void, String> {

    private final String address = "http://pumpuptheparti.netne.net/api/get_event.php";
    private Long id;
    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onResponse(String response) {
            // Do nothing...
        }
    };

    public GetEventTask(Long id) {
        this.id = id;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            HttpRequest httpRequest = new HttpRequest(HttpRequest.GET, address);
            httpRequest.withString("id=" + Long.toString(id));
            String response = httpRequest.send();
            Log.i("GetEvent#Response", response);
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
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

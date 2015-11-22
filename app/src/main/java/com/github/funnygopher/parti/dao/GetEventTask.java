package com.github.funnygopher.parti.dao;

import android.os.AsyncTask;

import com.github.funnygopher.parti.util.HttpRequest;

import java.io.IOException;

public class GetEventTask extends AsyncTask<Void, Void, String> {

    private final String address = "http://pumpuptheparti.netne.net/api/get_event.php";
    private Long id;
    private OnGetEvent responseListener = new OnGetEvent() {
        @Override
        public void onGetEvent(String response) {
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
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        responseListener.onGetEvent(s);
        super.onPostExecute(s);
    }

    public void setOnResponseListener(OnGetEvent responseListener) {
        this.responseListener = responseListener;
    }

    public interface OnGetEvent {
        void onGetEvent(String response);
    }
}
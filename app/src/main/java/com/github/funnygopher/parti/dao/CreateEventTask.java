package com.github.funnygopher.parti.dao;

import android.os.AsyncTask;

import com.github.funnygopher.parti.model.Event;
import com.github.funnygopher.parti.util.HttpRequest;

import java.io.IOException;

public class CreateEventTask extends AsyncTask<Void, Void, String> {

    private final String address = "http://pumpuptheparti.netne.net/api/create_event.php";
    private Event event;
    private OnCreateEventListener listener = new OnCreateEventListener() {
        @Override
        public void onCreateEvent(String response) {
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
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        listener.onCreateEvent(s);
        super.onPostExecute(s);
    }

    public void setOnCreateEventListener(OnCreateEventListener listener) {
        this.listener = listener;
    }

    public interface OnCreateEventListener {
        void onCreateEvent(String response);
    }
}

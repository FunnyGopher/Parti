package com.github.funnygopher.parti.dao.tasks;

import android.os.AsyncTask;

import com.github.funnygopher.gopherhttp.HttpRequest;
import com.github.funnygopher.parti.model.Event;

import java.io.IOException;

public class UpdateEventTask extends AsyncTask<Void, Void, String> {

    private final String address = "http://pumpuptheparti.netne.net/api/update_event.php";
    private Event event;
    private OnUpdateEventListener listener = new OnUpdateEventListener() {
        @Override
        public void onUpdateEvent(String response) {
            // Do nothing...
        }
    };

    public UpdateEventTask(Event event) {
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
        listener.onUpdateEvent(s);
        super.onPostExecute(s);
    }

    public void setOnUpdateEventListener(OnUpdateEventListener listener) {
        this.listener = listener;
    }

    public interface OnUpdateEventListener {
        void onUpdateEvent(String response);
    }
}

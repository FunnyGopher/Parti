package com.github.funnygopher.parti.dao.tasks;

import android.os.AsyncTask;

import com.github.funnygopher.parti.util.HttpRequest;

import java.io.IOException;

public class DeleteEventTask extends AsyncTask<Void, Void, String> {

    private final String address = "http://pumpuptheparti.netne.net/api/delete_event.php";
    private Long id;
    private OnDeleteEventListener listener = new OnDeleteEventListener() {
        @Override
        public void onDeleteEvent(String response) {
            // Do nothing...
        }
    };

    public DeleteEventTask(Long id) {
        this.id = id;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            HttpRequest httpRequest = new HttpRequest(HttpRequest.POST, address);
            httpRequest.withString("id=" + id);
            String response = httpRequest.send();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        listener.onDeleteEvent(s);
        super.onPostExecute(s);
    }

    public void setOnDeleteEventListener(OnDeleteEventListener listener) {
        this.listener = listener;
    }

    public interface OnDeleteEventListener {
        void onDeleteEvent(String response);
    }
}

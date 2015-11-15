package com.github.funnygopher.parti;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.github.funnygopher.parti.event.Event;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by kylhunts on 11/15/2015.
 */
public class CreateEventAsyncTask extends AsyncTask<Void, Void, Boolean> {

    private Event event;

    public CreateEventAsyncTask(Event event) {
        this.event = event;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Pair<String, String> phpParameter = new Pair<>("name", event.getName());
        String result = "";

        try {
            URL url = new URL("http://pumpuptheparti.netne.net/api/create_event.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            String request = "name=" + event.getName();

            // prepare request
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setFixedLengthStreamingMode(request.getBytes("UTF-8").length);

            // upload request
            OutputStream outputStream = urlConnection.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
            writer.write(request);
            writer.flush();
            writer.close();

            // read response
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            result = response.toString();
            Log.d("doInBackground", result);

            // disconnect
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("Malformed URL Exception", "Malformed URL Exception");
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IOException", "IOException");
        }

        return null;
    }
}

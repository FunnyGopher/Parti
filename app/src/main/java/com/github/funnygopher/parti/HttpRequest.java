package com.github.funnygopher.parti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpRequest {

    public static final int GET = 0;
    public static final int POST = 1;

    private int requestType;
    private URL url;
    private HttpURLConnection conn;
    private OutputStream out;

    public HttpRequest(int requestType, String address) throws IOException {
        this.requestType = requestType;
        this.url = new URL(address);

        prepare();
    }

    private void prepare() throws IOException {
        conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(1000);
        if(requestType == 1) {
            conn.setRequestMethod("POST");
        }
        conn.setDoOutput(true);
        conn.setDoInput(true);
        out = conn.getOutputStream();
    }

    public HttpRequest withParameters(Map<String, String> parameters) throws IOException {
        StringBuffer params = new StringBuffer();

        boolean first = true;
        for (String key : parameters.keySet()) {
            if(!first)
                params.append("&");
            params.append(key + "=" + URLEncoder.encode(parameters.get(key), "UTF-8"));

            if(first)
                first = false;
        }

        out.write(params.toString().getBytes());
        out.flush();

        return this;
    }

    public boolean send() throws IOException {
        boolean status = conn.getResponseCode() == HttpURLConnection.HTTP_OK;
        finish();
        return status;
    }

    public String sendAndGetResponse() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder responseBuilder = new StringBuilder();

        while((inputLine = in.readLine()) != null) {
            responseBuilder.append(inputLine);
        }
        finish();

        return responseBuilder.toString();
    }

    private void finish() throws IOException {
        conn.disconnect();
        out.close();
    }
}

package com.github.funnygopher.parti.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
    private String parameters;
    private OutputStream out;

    public HttpRequest(int requestType, String address) throws IOException {
        this.requestType = requestType;
        this.url = new URL(address);

        prepare();
    }

    private void prepare() throws IOException {
        conn = (HttpURLConnection) url.openConnection();
        if(requestType == POST) {
            conn.setRequestMethod("POST");
        }
        if(requestType == GET) {
            conn.setRequestMethod("GET");
        }
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setReadTimeout(1000);
        conn.setConnectTimeout(1000);
    }

    public HttpRequest withParameters(Map<String, String> parameterMap) throws IOException {
        StringBuffer params = new StringBuffer();

        boolean first = true;
        for (String key : parameterMap.keySet()) {
            if(!first)
                params.append("&");
            params.append(key + "=" + URLEncoder.encode(parameterMap.get(key), "UTF-8"));

            if(first)
                first = false;
        }

        parameters = params.toString();
        conn.setFixedLengthStreamingMode(parameters.getBytes("UTF-8").length);

        return this;
    }

    public HttpRequest withString(String string) throws IOException {
        parameters = string;
        conn.setFixedLengthStreamingMode(string.getBytes("UTF-8").length);
        return this;
    }

    public String send() throws IOException {
        out = conn.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
        writer.write(parameters);
        writer.flush();
        writer.close();

        String response = getResponse();

        conn.disconnect();
        return response;
    }

    private String getResponse() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();
        while((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
}

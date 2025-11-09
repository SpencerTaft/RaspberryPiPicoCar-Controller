package com.taftspencer.rccarcontroller;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    private String ipAddress = "";
    private int port = 0;

    public HttpClient() {}

    public void setEndpoint(String ip, int port) {
        this.ipAddress = ip;
        this.port = port;
    }

    public String postJson(String json) throws Exception {
        if (ipAddress.isEmpty() || port == 0) {
            throw new IllegalStateException("IP address and port must be set before making requests.");
        }
        String urlString = "http://" + ipAddress + ":" + port;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes("UTF-8"));
        }

        int responseCode = conn.getResponseCode();
        InputStream is = (responseCode >= 200 && responseCode < 300) ? conn.getInputStream() : conn.getErrorStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        conn.disconnect();
        return response.toString();
    }
}


package com.remswork.project.alice.service.impl;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.remswork.project.alice.model.UserDetail;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserService implements IP {

    private String baseUri = DOMAIN + "/api";
    private String teacherServiceUri = "/teacher";

    public UserDetail getByUsername(final String username) {
        try {
            return (UserDetail) new AsyncTask<String, UserDetail, UserDetail>() {
                @Override
                protected UserDetail doInBackground(String... params) {
                    try {
                        URL url = new URL(baseUri + "/username?" + params[0]);
                        Gson gson = new Gson();
                        HttpURLConnection httpURLConnection =
                                (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                        httpURLConnection.connect();

                        if (httpURLConnection.getResponseCode() == 200) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String stringData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                stringData += (char) data;
                            }
                            return gson.fromJson(stringData, UserDetail.class);
                        } else {
                            return null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }.execute(username).get();
        } catch (Exception e) {
            return null;
        }
    }
}

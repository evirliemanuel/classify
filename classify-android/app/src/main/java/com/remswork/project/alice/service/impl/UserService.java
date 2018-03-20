package com.remswork.project.alice.service.impl;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.remswork.project.alice.exception.SectionException;
import com.remswork.project.alice.exception.StudentException;
import com.remswork.project.alice.model.Section;
import com.remswork.project.alice.model.Student;
import com.remswork.project.alice.model.UserDetail;
import com.remswork.project.alice.model.support.Message;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

@Deprecated
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

    public Student updateSectionById(final long id, final Student newstudent) throws SectionException {
        try{
            return new AsyncTask<Long, Student, Student>() {
                @Override
                protected Student doInBackground(Long... params) {
                    try {
                        String link = ""
                                .concat(baseUri)
                                .concat("userDetail")
                                .concat("/")
                                .concat(params[0]  + "");
                        Gson gson = new Gson();
                        URL url = new URL(link);
                        HttpURLConnection httpURLConnection =
                                (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("PUT");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                        httpURLConnection.setRequestProperty("Accept", "application/json");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);

                        OutputStream os = httpURLConnection.getOutputStream();
                        BufferedWriter writer =
                                new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        writer.write(gson.toJson(newstudent));
                        writer.flush();
                        writer.close();
                        httpURLConnection.connect();

                        if(httpURLConnection.getResponseCode() == 200) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            return gson.fromJson(jsonData, Student.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            return null;
                        }else
                            throw new StudentException("Server Error");

                    } catch (StudentException e) {
                        e.printStackTrace();
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }.execute(id).get();
        }catch (InterruptedException e){
            e.printStackTrace();
            return null;
        }catch (ExecutionException e){
            e.printStackTrace();
            return null;
        }
    }
}

package com.remswork.project.alice.service.impl;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.remswork.project.alice.exception.TeacherException;
import com.remswork.project.alice.model.Teacher;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.service.TeacherService;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TeacherServiceImpl implements TeacherService, IP {

    private String baseUri = DOMAIN + "/api";
    private String teacherServiceUri = "/teacher";

    public TeacherServiceImpl() {
        super();
        baseUri += teacherServiceUri;
    }

    public TeacherServiceImpl(final String baseUri) {
        this.baseUri = baseUri;
        this.baseUri += teacherServiceUri;
    }

    public TeacherServiceImpl(final Context context) {
        //baseUri = context.getString(R.string.app_baseUri);
        baseUri += teacherServiceUri;
    }

    @Override
    public Teacher getTeacherById(final long id) throws TeacherException{
        try {
            return (Teacher) new AsyncTask<Long, Teacher, Teacher>() {
                @Override
                protected Teacher doInBackground(Long... params) {
                    try {
                        URL url = new URL(baseUri + "/" + params[0]);
                        Gson gson = new Gson();
                        HttpURLConnection httpURLConnection =
                                (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                        httpURLConnection.connect();

                        if(httpURLConnection.getResponseCode() == 200) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String stringData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                stringData += (char) data;
                            }
                            return gson.fromJson(stringData, Teacher.class);
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String stringData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                stringData += (char) data;
                            }

                            Message message = gson.fromJson(stringData, Message.class);
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        } else
                            throw new TeacherException("Server Error");

                    } catch (TeacherException e) {
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

    @Override
    public List<Teacher> getTeacherList() throws TeacherException {
        try {
            final List<Teacher> teacherList = new ArrayList<>();
            return new AsyncTask<String, List<Teacher>, List<Teacher>>(){
                @Override
                protected List<Teacher> doInBackground(String... params) {

                    try{
                        Gson gson = new Gson();
                        URL url = new URL(baseUri);
                        HttpURLConnection httpURLConnection =
                                (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.setRequestProperty("Content-Type","application/json");
                        httpURLConnection.setRequestProperty("Accept","application/json");
                        httpURLConnection.connect();
                        if(httpURLConnection.getResponseCode() == 200) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            JSONArray jsonArray = new JSONArray(jsonData);
                            for (int ctr = 0; ctr < jsonArray.length(); ctr++) {
                                teacherList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Teacher.class));
                            }
                            return teacherList;
                        }else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            JSONArray jsonArray = new JSONArray(jsonData);
                            for (int ctr = 0; ctr < jsonArray.length(); ctr++) {
                                teacherList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Teacher.class));
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return teacherList;
                        }else
                            throw new TeacherException("Server Error");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        return teacherList;
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                        return teacherList;
                    } catch (TeacherException e1) {
                        e1.printStackTrace();
                        return teacherList;
                    }
                }
            }.execute("").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ArrayList<Teacher>();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ArrayList<Teacher>();
        }
    }

    @Deprecated
    @Override
    public Teacher addTeacher(final Teacher teacher) throws TeacherException {
        try {
            if(teacher.getDepartment() != null) {
                teacher.getDepartment().setId(0);
            }
            return (Teacher) new AsyncTask<Teacher, Teacher, Teacher>() {
                @Override
                protected Teacher doInBackground(Teacher... params) {
                    try {
                        Gson gson = new Gson();
                        URL url = new URL(baseUri);
                        HttpURLConnection httpURLConnection =
                                (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                        httpURLConnection.setDoOutput(true);

                        OutputStreamWriter writer =
                                new OutputStreamWriter(httpURLConnection.getOutputStream());
                        writer.write(gson.toJson(teacher).toString());
                        writer.flush();
                        writer.close();
                        httpURLConnection.connect();

                        if(httpURLConnection.getResponseCode() == 201) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String stringData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                stringData += (char) data;
                            }
                            return gson.fromJson(stringData, Teacher.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String stringData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                stringData += (char) data;
                            }
                            Message message = gson.fromJson(stringData, Message.class);
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        }else
                            throw new TeacherException("Server Error");

                    } catch (TeacherException e) {
                        e.printStackTrace();
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }.execute(teacher).get();
        }catch (InterruptedException e){
            e.printStackTrace();
            return null;
        }catch (ExecutionException e){
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    @Override
    public Teacher addTeacher(final Teacher teacher, final long departmentId)
            throws TeacherException {
        try {
            return new AsyncTask<Teacher, Teacher, Teacher>() {
                @Override
                protected Teacher doInBackground(Teacher... params) {
                    try {
                        Gson gson = new Gson();
                        URL url = new URL(baseUri);
                        HttpURLConnection httpURLConnection =
                                (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                        httpURLConnection.setDoOutput(true);

                        Uri.Builder builder = new Uri.Builder()
                                .appendQueryParameter("departmentId", String.valueOf(departmentId));
                        String query = builder.build().getEncodedQuery();

                        OutputStreamWriter writer =
                                new OutputStreamWriter(httpURLConnection.getOutputStream());
                        writer.write(query);
                        writer.write(gson.toJson(teacher).toString());
                        writer.flush();
                        writer.close();
                        httpURLConnection.connect();

                        if(httpURLConnection.getResponseCode() == 201) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String stringData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                stringData += (char) data;
                            }
                            return gson.fromJson(stringData, Teacher.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String stringData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                stringData += (char) data;
                            }
                            Message message = gson.fromJson(stringData, Message.class);
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        }else
                            throw new TeacherException("Server Error");

                    } catch (TeacherException e) {
                        e.printStackTrace();
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }.execute(teacher).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    @Override
    public Teacher updateTeacherById(final long id, final Teacher teacher) throws TeacherException{
        return null;
    }

    @Deprecated
    @Override
    public Teacher updateTeacherById(long l, Teacher teacher, long l1) throws TeacherException {
        return null;
    }

    @Deprecated
    @Override
    public Teacher deleteTeacherById(long id) throws TeacherException {
        try {
            return new AsyncTask<Long, Teacher, Teacher>() {
                @Override
                protected Teacher doInBackground(Long... params) {
                    try {
                        Gson gson = new Gson();
                        URL url = new URL(baseUri + "/" + params[0]);
                        HttpURLConnection httpURLConnection =
                                (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("DELETE");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                        httpURLConnection.connect();

                        InputStream inputStream = httpURLConnection.getInputStream();
                        String stringData = "";
                        int data;
                        while ((data = inputStream.read()) != -1) {
                            stringData += (char) data;
                        }
                        return gson.fromJson(stringData, Teacher.class);
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

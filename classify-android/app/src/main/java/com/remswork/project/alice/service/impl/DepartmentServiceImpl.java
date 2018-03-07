package com.remswork.project.alice.service.impl;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.remswork.project.alice.exception.DepartmentException;
import com.remswork.project.alice.model.Department;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.service.DepartmentService;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DepartmentServiceImpl implements DepartmentService, IP {

    private String domain = DOMAIN;
    private String baseUri = "api";
    private String payload = "department";

    public DepartmentServiceImpl() {
        super();
    }

    public DepartmentServiceImpl(final String domain) {
        this.domain = domain;
    }

    @Override
    public Department getDepartmentById(final long id) throws DepartmentException {
        try {
            return new AsyncTask<String, Department, Department>() {
                @Override
                protected Department doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("/")
                                .concat(String.valueOf(id));
                        URL url = new URL(link);
                        Gson gson = new Gson();
                        HttpURLConnection httpURLConnection =
                                (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                        httpURLConnection.setRequestProperty("Accept", "application/json");
                        httpURLConnection.connect();

                        if(httpURLConnection.getResponseCode() == 200) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            return gson.fromJson(jsonData, Department.class);
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Department");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        } else
                            throw new DepartmentException("Server Error");

                    } catch (DepartmentException e) {
                        e.printStackTrace();
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }.execute((String) null).get();
        }catch (InterruptedException e){
            e.printStackTrace();
            return null;
        }catch (ExecutionException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Department> getDepartmentList() throws DepartmentException {
        final List<Department> departmentList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Department>, List<Department>>() {
                @Override
                protected List<Department> doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload);
                        URL url = new URL(link);
                        Gson gson = new Gson();
                        HttpURLConnection httpURLConnection =
                                (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                        httpURLConnection.setRequestProperty("Accept", "application/json");
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
                                departmentList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Department.class));
                            }

                            return departmentList;
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Department");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return departmentList;
                        } else
                            throw new DepartmentException("Server Error");

                    } catch (DepartmentException e) {
                        e.printStackTrace();
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }.execute((String) null).get();
        }catch (InterruptedException e){
            e.printStackTrace();
            return null;
        }catch (ExecutionException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Department addDepartment(final Department department)
            throws DepartmentException {
        try{
            return new AsyncTask<String, Department, Department>() {
                @Override
                protected Department doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload);
                        Gson gson = new Gson();
                        URL url = new URL(link);
                        HttpURLConnection httpURLConnection =
                                (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                        httpURLConnection.setRequestProperty("Accept", "application/json");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);

                        OutputStream os = httpURLConnection.getOutputStream();
                        BufferedWriter writer =
                                new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                        writer.write(gson.toJson(department));
                        writer.flush();
                        writer.close();
                        httpURLConnection.connect();

                        if(httpURLConnection.getResponseCode() == 201) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            return gson.fromJson(jsonData, Department.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Department");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        }else
                            throw new DepartmentException("Server Error");

                    } catch (DepartmentException e) {
                        e.printStackTrace();
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }.execute((String) null).get();
        }catch (InterruptedException e){
            e.printStackTrace();
            return null;
        }catch (ExecutionException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Department updateDepartmentById(final long id, final Department newDepartment) throws DepartmentException {
        try{
            return new AsyncTask<String, Department, Department>() {
                @Override
                protected Department doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("/")
                                .concat(String.valueOf(id))
                                .concat("?departmentId=");
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
                        writer.write(gson.toJson(newDepartment));
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
                            return gson.fromJson(jsonData, Department.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Department");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        }else
                            throw new DepartmentException("Server Error");

                    } catch (DepartmentException e) {
                        e.printStackTrace();
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }.execute((String) null).get();
        }catch (InterruptedException e){
            e.printStackTrace();
            return null;
        }catch (ExecutionException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Department deleteDepartmentById(final long id) throws DepartmentException {
        try {
            return new AsyncTask<String, Department, Department>() {
                @Override
                protected Department doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("/")
                                .concat(String.valueOf(id));
                        URL url = new URL(link);
                        Gson gson = new Gson();
                        HttpURLConnection httpURLConnection =
                                (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("DELETE");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                        httpURLConnection.setRequestProperty("Accept", "application/json");
                        httpURLConnection.connect();

                        if(httpURLConnection.getResponseCode() == 200) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            return gson.fromJson(jsonData, Department.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Department");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        } else
                            throw new DepartmentException("Server Error");

                    } catch (DepartmentException e) {
                        e.printStackTrace();
                        return null;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }.execute((String) null).get();
        }catch (InterruptedException e){
            e.printStackTrace();
            return null;
        }catch (ExecutionException e){
            e.printStackTrace();
            return null;
        }
    }
}
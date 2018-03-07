package com.remswork.project.alice.service.impl;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Project;
import com.remswork.project.alice.model.ProjectResult;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.service.ProjectService;

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

public class ProjectServiceImpl implements ProjectService, IP {

    private String domain = DOMAIN;
    private String baseUri = "api";
    private String payload = "project";

    public ProjectServiceImpl() {
        super();
    }

    public ProjectServiceImpl(final String domain) {
        this.domain = domain;
    }

    @Override
    public Project getProjectById(final long id) throws GradingFactorException {
        try {
            return new AsyncTask<String, Project, Project>() {
                @Override
                protected Project doInBackground(String... args) {
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
                            return gson.fromJson(jsonData, Project.class);
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        } else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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
    public List<Project> getProjectList() throws GradingFactorException {
        final List<Project> projectList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Project>, List<Project>>() {
                @Override
                protected List<Project> doInBackground(String... args) {
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
                                projectList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Project.class));
                            }

                            return projectList;
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return projectList;
                        } else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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
    public List<Project> getProjectListByClassId(final long classId)
            throws GradingFactorException {
        final List<Project> projectList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Project>, List<Project>>() {
                @Override
                protected List<Project> doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("?classId=")
                                .concat(String.valueOf(classId));
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
                                projectList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Project.class));
                            }

                            return projectList;
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return projectList;
                        } else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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
    public List<Project> getProjectListByClassId(final long classId, final long termId)
            throws GradingFactorException {
        final List<Project> projectList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Project>, List<Project>>() {
                @Override
                protected List<Project> doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("?classId=")
                                .concat(String.valueOf(classId))
                                .concat("&termId=")
                                .concat(String.valueOf(termId));
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
                                projectList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Project.class));
                            }

                            return projectList;
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return projectList;
                        } else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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
    public List<Project> getProjectListByStudentId(final long studentId)
            throws GradingFactorException {
        final List<Project> projectList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Project>, List<Project>>() {
                @Override
                protected List<Project> doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("?studentId=")
                                .concat(String.valueOf(studentId));
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
                                projectList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Project.class));
                            }

                            return projectList;
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return projectList;
                        } else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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
    public List<Project> getProjectListByStudentId(final long studentId, final long termId)
            throws GradingFactorException {
        final List<Project> projectList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Project>, List<Project>>() {
                @Override
                protected List<Project> doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("?studentId=")
                                .concat(String.valueOf(studentId))
                                .concat("&termId=")
                                .concat(String.valueOf(termId));
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
                                projectList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Project.class));
                            }

                            return projectList;
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return projectList;
                        } else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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
    @Deprecated
    public ProjectResult getProjectResultById(long resultId) throws GradingFactorException {
        return null;
    }

    @Override
    public ProjectResult getProjectResultByProjectAndStudentId(final long projectId,
                                                               final long studentId)
            throws GradingFactorException {
        try {
            return new AsyncTask<String, ProjectResult, ProjectResult>() {
                @Override
                protected ProjectResult doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("/")
                                .concat(String.valueOf(projectId))
                                .concat("/")
                                .concat("result")
                                .concat("?studentId=")
                                .concat(String.valueOf(studentId));
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
                            return gson.fromJson(jsonData, ProjectResult.class);
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        } else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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
    public Project addProject(final Project project, final long classId)
            throws GradingFactorException {
        try{
            return new AsyncTask<String, Project, Project>() {
                @Override
                protected Project doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("?classId=")
                                .concat(String.valueOf(classId));
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
                        writer.write(gson.toJson(project));
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
                            return gson.fromJson(jsonData, Project.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        }else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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

    @Deprecated
    public List<Project> getProjectListByStudentAndSubjectId(final long studentId,
                                                             final long subjectId)
            throws GradingFactorException {
        final List<Project> projectList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Project>, List<Project>>() {
                @Override
                protected List<Project> doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("?studentId=")
                                .concat(String.valueOf(studentId))
                                .concat("&subjectId=")
                                .concat(String.valueOf(subjectId));
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
                                projectList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Project.class));
                            }

                            return projectList;
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return projectList;
                        } else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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

    @Deprecated
    public List<Project> getProjectListByStudentAndSubjectId(final long studentId,
                                                             final long subjectId,
                                                             final long termId)
            throws GradingFactorException {
        final List<Project> projectList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Project>, List<Project>>() {
                @Override
                protected List<Project> doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("?studentId=")
                                .concat(String.valueOf(studentId))
                                .concat("&subjectId=")
                                .concat(String.valueOf(subjectId))
                                .concat("&termId=")
                                .concat(String.valueOf(termId));
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
                                projectList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Project.class));
                            }

                            return projectList;
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return projectList;
                        } else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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
    public Project addProject(final Project project, final long classId, final long termId)
            throws GradingFactorException {
        try{
            return new AsyncTask<String, Project, Project>() {
                @Override
                protected Project doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("?classId=")
                                .concat(String.valueOf(classId))
                                .concat("&termId=")
                                .concat(String.valueOf(termId));
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
                        writer.write(gson.toJson(project));
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
                            return gson.fromJson(jsonData, Project.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        }else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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
    public ProjectResult addProjectResult(final int score, final long projectId,
                                          final long studentId)
            throws GradingFactorException {
        try{
            return new AsyncTask<String, ProjectResult, ProjectResult>() {
                @Override
                protected ProjectResult doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("/")
                                .concat(String.valueOf(projectId))
                                .concat("/")
                                .concat("result")
                                .concat("?studentId=")
                                .concat(String.valueOf(studentId))
                                .concat("&score=")
                                .concat(String.valueOf(score));
                        Gson gson = new Gson();
                        URL url = new URL(link);
                        HttpURLConnection httpURLConnection =
                                (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                        httpURLConnection.setRequestProperty("Accept", "application/json");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.connect();

                        if(httpURLConnection.getResponseCode() == 201 ||
                                httpURLConnection.getResponseCode() == 200) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            return gson.fromJson(jsonData, ProjectResult.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        }else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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
    public Project updateProjectById(final long id, final Project newProject,
                                     final long classId)
            throws GradingFactorException {
        try{
            return new AsyncTask<String, Project, Project>() {
                @Override
                protected Project doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("/")
                                .concat(String.valueOf(id))
                                .concat("?classId=")
                                .concat(String.valueOf(classId));
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
                        writer.write(gson.toJson(newProject));
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
                            return gson.fromJson(jsonData, Project.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        }else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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

    @Deprecated
    public Project addProject(final Project project, final long studentId, final long subjectId,
                              final long termId)  throws GradingFactorException {
        try{
            return new AsyncTask<String, Project, Project>() {
                @Override
                protected Project doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("?studentId=")
                                .concat(String.valueOf(studentId))
                                .concat("&subjectId=")
                                .concat(String.valueOf(subjectId))
                                .concat("&termId=")
                                .concat(String.valueOf(termId));
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
                        writer.write(gson.toJson(project));
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
                            return gson.fromJson(jsonData, Project.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        }else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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
    public Project updateProjectById(final long id, final Project newProject,
                                     final long classId, final long termId)
            throws GradingFactorException {
        try{
            return new AsyncTask<String, Project, Project>() {
                @Override
                protected Project doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("/")
                                .concat(String.valueOf(id))
                                .concat("?classId=")
                                .concat(String.valueOf(classId))
                                .concat("&termId=")
                                .concat(String.valueOf(termId));
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
                        writer.write(gson.toJson(newProject));
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
                            return gson.fromJson(jsonData, Project.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        }else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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
    public ProjectResult updateProjectResultByProjectAndStudentId(final int score,
                                                                  final long projectId,
                                                                  final long studentId)
            throws GradingFactorException {
        try{
            return new AsyncTask<String, ProjectResult, ProjectResult>() {
                @Override
                protected ProjectResult doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("/")
                                .concat(String.valueOf(projectId))
                                .concat("/")
                                .concat("result")
                                .concat("?studentId=")
                                .concat(String.valueOf(studentId))
                                .concat("&score=")
                                .concat(String.valueOf(score));
                        Gson gson = new Gson();
                        URL url = new URL(link);
                        HttpURLConnection httpURLConnection =
                                (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("PUT");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                        httpURLConnection.setRequestProperty("Accept", "application/json");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        httpURLConnection.connect();

                        if(httpURLConnection.getResponseCode() == 200) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            return gson.fromJson(jsonData, ProjectResult.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        }else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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

    @Deprecated
    public Project updateProjectById(final long id, final Project newProject,
                                     final long studentId, final long subjectId, final long termId)
            throws GradingFactorException {
        try{
            return new AsyncTask<String, Project, Project>() {
                @Override
                protected Project doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("/")
                                .concat(String.valueOf(id))
                                .concat("?studentId=")
                                .concat(String.valueOf(studentId))
                                .concat("&subjectId=")
                                .concat(String.valueOf(subjectId))
                                .concat("&termId=")
                                .concat(String.valueOf(termId));
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
                        writer.write(gson.toJson(newProject));
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
                            return gson.fromJson(jsonData, Project.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        }else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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
    public Project deleteProjectById(final long id) throws GradingFactorException {
        try {
            return new AsyncTask<String, Project, Project>() {
                @Override
                protected Project doInBackground(String... args) {
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
                            return gson.fromJson(jsonData, Project.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        } else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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
    public ProjectResult deleteProjectResultByProjectAndStudentId(final long projectId,
                                                                  final long studentId)
            throws GradingFactorException {
        try {
            return new AsyncTask<String, ProjectResult, ProjectResult>() {
                @Override
                protected ProjectResult doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("/")
                                .concat(String.valueOf(projectId))
                                .concat("/")
                                .concat("result")
                                .concat("?studentId=")
                                .concat(String.valueOf(studentId));
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
                            return gson.fromJson(jsonData, ProjectResult.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Project");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return null;
                        } else
                            throw new GradingFactorException("Server Error");

                    } catch (GradingFactorException e) {
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
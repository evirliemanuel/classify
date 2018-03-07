package com.remswork.project.alice.service.impl;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Quiz;
import com.remswork.project.alice.model.QuizResult;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.service.QuizService;

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

public class QuizServiceImpl implements QuizService, IP {

    private String domain = DOMAIN;
    private String baseUri = "api";
    private String payload = "quiz";

    public QuizServiceImpl() {
        super();
    }

    public QuizServiceImpl(final String domain) {
        this.domain = domain;
    }

    @Override
    public Quiz getQuizById(final long id) throws GradingFactorException {
        try {
            return new AsyncTask<String, Quiz, Quiz>() {
                @Override
                protected Quiz doInBackground(String... args) {
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
                            return gson.fromJson(jsonData, Quiz.class);
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
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
    public List<Quiz> getQuizList() throws GradingFactorException {
        final List<Quiz> quizList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Quiz>, List<Quiz>>() {
                @Override
                protected List<Quiz> doInBackground(String... args) {
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
                                quizList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Quiz.class));
                            }

                            return quizList;
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return quizList;
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
    public List<Quiz> getQuizListByClassId(final long classId)
            throws GradingFactorException {
        final List<Quiz> quizList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Quiz>, List<Quiz>>() {
                @Override
                protected List<Quiz> doInBackground(String... args) {
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
                                quizList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Quiz.class));
                            }

                            return quizList;
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return quizList;
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
    public List<Quiz> getQuizListByClassId(final long classId, final long termId)
            throws GradingFactorException {
        final List<Quiz> quizList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Quiz>, List<Quiz>>() {
                @Override
                protected List<Quiz> doInBackground(String... args) {
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
                                quizList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Quiz.class));
                            }

                            return quizList;
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return quizList;
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
    public List<Quiz> getQuizListByStudentId(final long studentId)
            throws GradingFactorException {
        final List<Quiz> quizList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Quiz>, List<Quiz>>() {
                @Override
                protected List<Quiz> doInBackground(String... args) {
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
                                quizList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Quiz.class));
                            }

                            return quizList;
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return quizList;
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
    public List<Quiz> getQuizListByStudentId(final long studentId, final long termId)
            throws GradingFactorException {
        final List<Quiz> quizList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Quiz>, List<Quiz>>() {
                @Override
                protected List<Quiz> doInBackground(String... args) {
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
                                quizList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Quiz.class));
                            }

                            return quizList;
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return quizList;
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
    public QuizResult getQuizResultById(long resultId) throws GradingFactorException {
        return null;
    }

    @Override
    public QuizResult getQuizResultByQuizAndStudentId(final long quizId,
                                                      final long studentId)
            throws GradingFactorException {
        try {
            return new AsyncTask<String, QuizResult, QuizResult>() {
                @Override
                protected QuizResult doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("/")
                                .concat(String.valueOf(quizId))
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
                            return gson.fromJson(jsonData, QuizResult.class);
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
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
    public Quiz addQuiz(final Quiz quiz, final long classId)
            throws GradingFactorException {
        try{
            return new AsyncTask<String, Quiz, Quiz>() {
                @Override
                protected Quiz doInBackground(String... args) {
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
                        writer.write(gson.toJson(quiz));
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
                            return gson.fromJson(jsonData, Quiz.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
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
    public List<Quiz> getQuizListByStudentAndSubjectId(final long studentId,
                                                       final long subjectId)
            throws GradingFactorException {
        final List<Quiz> quizList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Quiz>, List<Quiz>>() {
                @Override
                protected List<Quiz> doInBackground(String... args) {
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
                                quizList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Quiz.class));
                            }

                            return quizList;
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return quizList;
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
    public List<Quiz> getQuizListByStudentAndSubjectId(final long studentId,
                                                       final long subjectId,
                                                       final long termId)
            throws GradingFactorException {
        final List<Quiz> quizList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Quiz>, List<Quiz>>() {
                @Override
                protected List<Quiz> doInBackground(String... args) {
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
                                quizList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Quiz.class));
                            }

                            return quizList;
                        } else if(httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return quizList;
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
    public Quiz addQuiz(final Quiz quiz, final long classId, final long termId)
            throws GradingFactorException {
        try{
            return new AsyncTask<String, Quiz, Quiz>() {
                @Override
                protected Quiz doInBackground(String... args) {
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
                        writer.write(gson.toJson(quiz));
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
                            return gson.fromJson(jsonData, Quiz.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
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
    public QuizResult addQuizResult(final int score, final long quizId,
                                    final long studentId)
            throws GradingFactorException {
        try{
            return new AsyncTask<String, QuizResult, QuizResult>() {
                @Override
                protected QuizResult doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("/")
                                .concat(String.valueOf(quizId))
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
                            return gson.fromJson(jsonData, QuizResult.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
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
    public Quiz updateQuizById(final long id, final Quiz newQuiz,
                               final long classId)
            throws GradingFactorException {
        try{
            return new AsyncTask<String, Quiz, Quiz>() {
                @Override
                protected Quiz doInBackground(String... args) {
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
                        writer.write(gson.toJson(newQuiz));
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
                            return gson.fromJson(jsonData, Quiz.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
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
    public Quiz addQuiz(final Quiz quiz, final long studentId, final long subjectId,
                        final long termId)  throws GradingFactorException {
        try{
            return new AsyncTask<String, Quiz, Quiz>() {
                @Override
                protected Quiz doInBackground(String... args) {
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
                        writer.write(gson.toJson(quiz));
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
                            return gson.fromJson(jsonData, Quiz.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
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
    public Quiz updateQuizById(final long id, final Quiz newQuiz,
                               final long classId, final long termId)
            throws GradingFactorException {
        try{
            return new AsyncTask<String, Quiz, Quiz>() {
                @Override
                protected Quiz doInBackground(String... args) {
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
                        writer.write(gson.toJson(newQuiz));
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
                            return gson.fromJson(jsonData, Quiz.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
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
    public QuizResult updateQuizResultByQuizAndStudentId(final int score,
                                                         final long quizId,
                                                         final long studentId)
            throws GradingFactorException {
        try{
            return new AsyncTask<String, QuizResult, QuizResult>() {
                @Override
                protected QuizResult doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("/")
                                .concat(String.valueOf(quizId))
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
                            return gson.fromJson(jsonData, QuizResult.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
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
    public Quiz updateQuizById(final long id, final Quiz newQuiz,
                               final long studentId, final long subjectId, final long termId)
            throws GradingFactorException {
        try{
            return new AsyncTask<String, Quiz, Quiz>() {
                @Override
                protected Quiz doInBackground(String... args) {
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
                        writer.write(gson.toJson(newQuiz));
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
                            return gson.fromJson(jsonData, Quiz.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
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
    public Quiz deleteQuizById(final long id) throws GradingFactorException {
        try {
            return new AsyncTask<String, Quiz, Quiz>() {
                @Override
                protected Quiz doInBackground(String... args) {
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
                            return gson.fromJson(jsonData, Quiz.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
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
    public QuizResult deleteQuizResultByQuizAndStudentId(final long quizId,
                                                         final long studentId)
            throws GradingFactorException {
        try {
            return new AsyncTask<String, QuizResult, QuizResult>() {
                @Override
                protected QuizResult doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("/")
                                .concat(String.valueOf(quizId))
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
                            return gson.fromJson(jsonData, QuizResult.class);
                        } else if(httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Quiz");
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
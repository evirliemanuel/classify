package com.remswork.project.alice.service.impl;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.remswork.project.alice.exception.GradingFactorException;
import com.remswork.project.alice.model.Formula;
import com.remswork.project.alice.model.support.Message;
import com.remswork.project.alice.service.FormulaService;

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

public class FormulaServiceImpl implements FormulaService, IP {

    private String domain = DOMAIN;
    private String baseUri = "api";
    private String payload = "formula";

    public FormulaServiceImpl() {
        super();
    }

    public FormulaServiceImpl(final String domain) {
        this.domain = domain;
    }

    @Override
    public Formula getFormulaById(final long id) throws GradingFactorException {
        try {
            return new AsyncTask<String, Formula, Formula>() {
                @Override
                protected Formula doInBackground(String... args) {
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

                        if (httpURLConnection.getResponseCode() == 200) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            return gson.fromJson(jsonData, Formula.class);
                        } else if (httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Formula");
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
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Formula getFormulaBySubjectAndTeacherId(final long subjectId, final long teacherId,
                                                   final long termId)
            throws GradingFactorException {
        try {
            return new AsyncTask<String, Formula, Formula>() {
                @Override
                protected Formula doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("/")
                                .concat("0")
                                .concat("?subjectId=")
                                .concat(String.valueOf(subjectId))
                                .concat("&teacherId=")
                                .concat(String.valueOf(teacherId))
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

                        if (httpURLConnection.getResponseCode() == 200) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            return gson.fromJson(jsonData, Formula.class);
                        } else if (httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Formula");
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
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Formula> getFormulaList() throws GradingFactorException {
        final List<Formula> formulaList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Formula>, List<Formula>>() {
                @Override
                protected List<Formula> doInBackground(String... args) {
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

                        if (httpURLConnection.getResponseCode() == 200) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            JSONArray jsonArray = new JSONArray(jsonData);
                            for (int ctr = 0; ctr < jsonArray.length(); ctr++) {
                                formulaList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Formula.class));
                            }

                            return formulaList;
                        } else if (httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Formula");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return formulaList;
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
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Formula> getFormulaListByTeacherId(final long teacherId)
            throws GradingFactorException {
        final List<Formula> formulaList = new ArrayList<>();
        try {
            return new AsyncTask<String, List<Formula>, List<Formula>>() {
                @Override
                protected List<Formula> doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("?teacherId=")
                                .concat(String.valueOf(teacherId));
                        URL url = new URL(link);
                        Gson gson = new Gson();
                        HttpURLConnection httpURLConnection =
                                (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.setRequestProperty("Content-Type", "application/json");
                        httpURLConnection.setRequestProperty("Accept", "application/json");
                        httpURLConnection.connect();

                        if (httpURLConnection.getResponseCode() == 200) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            JSONArray jsonArray = new JSONArray(jsonData);
                            for (int ctr = 0; ctr < jsonArray.length(); ctr++) {
                                formulaList.add(gson.fromJson(
                                        jsonArray.get(ctr).toString(), Formula.class));
                            }

                            return formulaList;
                        } else if (httpURLConnection.getResponseCode() == 404) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Formula");
                            Log.i("ServiceTAG", "Status : " + message.getStatus());
                            Log.i("ServiceTAG", "Type : " + message.getType());
                            Log.i("ServiceTAG", "Message : " + message.getMessage());
                            return formulaList;
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
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Formula addFormula(final Formula formula, final long subjectId, final  long teacherId)
            throws GradingFactorException {
        try {
            return new AsyncTask<String, Formula, Formula>() {
                @Override
                protected Formula doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("?subjectId=")
                                .concat(String.valueOf(subjectId))
                                .concat("&teacherId=")
                                .concat(String.valueOf(teacherId));
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
                        writer.write(gson.toJson(formula));
                        writer.flush();
                        writer.close();
                        httpURLConnection.connect();

                        if (httpURLConnection.getResponseCode() == 201) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            return gson.fromJson(jsonData, Formula.class);
                        } else if (httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Formula");
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
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Formula addFormula(final Formula formula, final long subjectId, final  long teacherId,
                              final long termId) throws GradingFactorException {
        try {
            return new AsyncTask<String, Formula, Formula>() {
                @Override
                protected Formula doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("?subjectId=")
                                .concat(String.valueOf(subjectId))
                                .concat("&teacherId=")
                                .concat(String.valueOf(teacherId))
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
                        writer.write(gson.toJson(formula));
                        writer.flush();
                        writer.close();
                        httpURLConnection.connect();

                        if (httpURLConnection.getResponseCode() == 201) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            return gson.fromJson(jsonData, Formula.class);
                        } else if (httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Formula");
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
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Formula updateFormulaById(final long id, final Formula newFormula, final long subjectId,
                                     final long teacherId, final long termId)
            throws GradingFactorException {
        try {
            return new AsyncTask<String, Formula, Formula>() {
                @Override
                protected Formula doInBackground(String... args) {
                    try {
                        String link = ""
                                .concat(domain)
                                .concat("/")
                                .concat(baseUri)
                                .concat("/")
                                .concat(payload)
                                .concat("/")
                                .concat(String.valueOf(id))
                                .concat("?subjectId=")
                                .concat(String.valueOf(subjectId))
                                .concat("&teacherId=")
                                .concat(String.valueOf(teacherId))
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
                        writer.write(gson.toJson(newFormula));
                        writer.flush();
                        writer.close();
                        httpURLConnection.connect();

                        if (httpURLConnection.getResponseCode() == 200) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            return gson.fromJson(jsonData, Formula.class);
                        } else if (httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Formula");
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
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Formula deleteFormulaById(final long id) throws GradingFactorException {
        try {
            return new AsyncTask<String, Formula, Formula>() {
                @Override
                protected Formula doInBackground(String... args) {
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

                        if (httpURLConnection.getResponseCode() == 200) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }
                            return gson.fromJson(jsonData, Formula.class);
                        } else if (httpURLConnection.getResponseCode() == 400) {
                            InputStream inputStream = httpURLConnection.getInputStream();
                            String jsonData = "";
                            int data;
                            while ((data = inputStream.read()) != -1) {
                                jsonData += (char) data;
                            }

                            Message message = gson.fromJson(jsonData, Message.class);
                            Log.i("ServiceTAG", "Service : Formula");
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
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }
}
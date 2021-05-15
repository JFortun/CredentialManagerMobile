package com.fortun.credmanmob.httpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HTTPClientCredentials {

    static ArrayList<String> credentials;
    static JSONArray jsonArray;
    static JSONObject jsonObject;
    static String nameCredential, userCredential, passwordCredential, idUserFK;
    static int idCredential, position;


    public static void create(String nameCredential, String userCredential, String passwordCredential, String idUserFK) {
        try {
            URL url = new URL("http://localhost:8080/api/credentials");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("nameCredential", nameCredential);
            postDataParams.put("userCredential", userCredential);
            postDataParams.put("passwordCredential", passwordCredential);
            postDataParams.put("idUserFK", idUserFK);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write(getDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            connection.getResponseCode();
            if (connection.getResponseCode() == 201) {
                System.out.println("Created");
                connection.disconnect();
            } else {
                System.out.println("Error");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static ArrayList<String> read(String type, String parameter) {
        credentials = new ArrayList<>();
        try {
            URL url = null;
            switch (type) {
                case "findAll":
                    url = new URL("http://192.168.1.35:8080/api/credentials");
                    break;
                case "findById":
                    url = new URL("http://192.168.1.35:8080/api/credentials/id/" + parameter);
                    break;
                case "findByUser":
                    url = new URL("http://192.168.1.35:8080/api/credentials/user/" + parameter);
                    break;
            }
            assert url != null;
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == 200) {
                InputStream responseBody = connection.getInputStream();
                InputStreamReader responseBodyReader = new InputStreamReader(responseBody, StandardCharsets.UTF_8);
                BufferedReader bR = new BufferedReader(responseBodyReader);
                String line;
                StringBuilder responseStrBuilder = new StringBuilder();
                while ((line = bR.readLine()) != null) {
                    responseStrBuilder.append(line);
                }
                if (type.equals("findAll") || type.equals("findByUser")) {
                    jsonArray = new JSONArray(responseStrBuilder.toString());
                    position = 0;
                    jsonObject = jsonArray.getJSONObject(position);
                } else {
                    jsonObject = new JSONObject(responseStrBuilder.toString());
                }
                idCredential = jsonObject.getInt("idCredential");
                nameCredential = jsonObject.getString("nameCredential");
                userCredential = jsonObject.getString("userCredential");
                passwordCredential = jsonObject.getString("passwordCredential");
                idUserFK = String.valueOf(jsonObject.getInt("idUserFK"));
                responseBody.close();
                responseBodyReader.close();
                connection.disconnect();
            } else {
                System.out.println("Error");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            credentials.clear();
            switch (type) {
                case "findAll":
                case "findByUser":
                    if (jsonArray != null && jsonArray.length() >= 1) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            credentials.add(jsonObject.getInt("idCredential") + "     " +
                                    jsonObject.getString("nameCredential") + "     " +
                                    jsonObject.getString("userCredential") + "     " +
                                    jsonObject.getString("passwordCredential"));
                        }
                    }
                    break;
                case "findById":
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return credentials;
    }

    public static void update(int idCredential, String nameCredential, String userCredential, String passwordCredential, String idUserFK) {
        try {
            URL url = new URL("http://192.168.1.35:8080/api/credentials/id/" + idCredential);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setRequestMethod("PUT");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            HashMap<String, String> postDataParams = new HashMap<>();
            postDataParams.put("nameCredential", nameCredential);
            postDataParams.put("userCredential", userCredential);
            postDataParams.put("passwordCredential", passwordCredential);
            postDataParams.put("idUserFK", idUserFK);

            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write(getDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();

            if (connection.getResponseCode() == 201) {
                System.out.println("Updated");
                connection.disconnect();
            } else {
                System.out.println("Error");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void delete(int idCredential) {
        try {
            URL url = new URL("http://192.168.1.35:8080/api/credentials/id/" + idCredential);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            if (connection.getResponseCode() == 204) {
                System.out.println("Deleted");
                connection.disconnect();
            } else {
                System.out.println("Error");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String getDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first) {
                first = false;
            } else {
                result.append("&");
            }
            result.append(URLEncoder.encode(entry.getKey(), String.valueOf(StandardCharsets.UTF_8)));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), String.valueOf(StandardCharsets.UTF_8)));
        }
        return result.toString();
    }
}

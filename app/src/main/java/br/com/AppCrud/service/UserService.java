package br.com.AppCrud.service;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class UserService {

    public String returnDataUser(String name, String password) {
        try {
            URL url = new URL("https://api-mongodb-crud.herokuapp.com/pages/authenticate?retornoJson=true"); // here is your URL path

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("name", name);
            postDataParams.put("password", password);

            Log.e("params", postDataParams.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuilder sb = new StringBuilder();

                for (String line = in.readLine(); line != null; line = in.readLine()) {
                    sb.append(line);
                }

                in.close();
                return sb.toString();
            } else {
                return "{authentication : false}";
            }
        } catch (Exception e) {
            return "{authentication: false}";
        }
    }

    public String createUser(String name, String password, String email, String administrator) {
        try {
            URL url = new URL("https://api-mongodb-crud.herokuapp.com/createNewUser?retornoJson=true"); // here is your URL path

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("name", name);
            postDataParams.put("password", password);
            postDataParams.put("email", email);
            postDataParams.put("admin", administrator.equals("true") ? "ON" : "OFF");

            Log.e("params", postDataParams.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuilder sb;
                sb = new StringBuilder();

                for (String line = in.readLine(); line != null; line = in.readLine()) {
                    sb.append(line);
                }

                in.close();
                return sb.toString();
            } else {
                //return new String("{message : " + responseMessage + ", retorno: false}");
                return "{message : " + responseMessage + ", retorno: false}";
            }
        } catch (Exception e) {
            //return new String("{message : " + e.getMessage() + ", retorno: false}");
            return "{message : " + e.getMessage() + ", retorno: false}";
        }
    }

    private String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
}

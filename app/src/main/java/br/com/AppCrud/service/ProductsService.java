package br.com.AppCrud.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ProductsService {

    public String getAllProducts(String token) {
        URL url;
        StringBuilder response = new StringBuilder();
        try {
            url = new URL("https://api-mongodb-crud.herokuapp.com/pages/getProducts?retornoJson=true");
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url");
        }

        HttpURLConnection conn = null;
        String responseJSON;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(false);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("x-access-token", token);

            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            //Here is your json in string format
            responseJSON = response.toString();
        }
        return responseJSON;
    }

    public String deleteProduct(String idProduct, String token) {
        try {
            URL url = new URL("https://api-mongodb-crud.herokuapp.com/pages/deleteProduct/" + idProduct + "?retornoJson=true"); // here is your URL path

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("x-access-token", token);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();
            String responseMessage = conn.getResponseMessage();

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
            }
            else {
                return "{message : " + responseMessage + ", retorno: false}";
            }
        }
        catch(Exception e){
            return "{message : " + e.getMessage() + ", retorno: false}";
        }
    }
}

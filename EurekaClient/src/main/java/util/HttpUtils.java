package util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {


    public static void postJson(String body,String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");


        con.setDoOutput(true);
        con.setConnectTimeout(3000);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(body);
        wr.flush();
        wr.close();
        try {
            int responseCode = con.getResponseCode();
        }
        catch (Exception ex){
            postJson(body,url);
        }
    }

    public static void put(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("PUT");
        con.setDoOutput(true);
        con.setConnectTimeout(3000);
        try {
            int responseCode = con.getResponseCode();
        }
        catch (Exception ex){
            put(url);
        }
    }

    public static void delete(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("DELETE");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        con.setConnectTimeout(3000);
        try {
            int responseCode = con.getResponseCode();
        }
        catch (Exception ex){
            delete(url);
        }
    }

    public static String get(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);
        con.setConnectTimeout(3000);
        StringBuilder builder = null;
        try {
            BufferedReader br;
            builder = new StringBuilder();
            if (200 <= con.getResponseCode() && con.getResponseCode() <= 299) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            for (String s = br.readLine(); s != null; s = br.readLine()){
                builder.append(s);
            }
        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
        }
        return builder.toString();
    }




}

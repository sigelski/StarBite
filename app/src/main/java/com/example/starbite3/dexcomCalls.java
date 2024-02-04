package com.example.starbite3;

import android.icu.util.Output;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.Object;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class dexcomCalls{

    public String session;
    static OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json");

    data dataList = MainActivity.getDataList();
//Attempts to generate a session key using provided username and password. appId is a constant (should be at least).

    public static String generateSession(String userId, String password, String appId) throws IOException{

        //this session key represents the null/error key. In the event of a server issue, the server will return this
        //same zeroed out string.
        String session = "00000000-0000-0000-0000-000000000000";

        String jsonInputString = "{\"accountName\":\"" + userId + "\",\"password\":\"" + password + "\",\"applicationId\":\"" + appId + "\"}";
        RequestBody requestBody = RequestBody.create(jsonInputString, JSON);
        Log.d("network", "3");
        Request request = new Request.Builder()
                .url("https://share2.dexcom.com/ShareWebServices/Services/General/LoginPublisherAccountByName")
                .post(requestBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    Log.d("huahh", responseBody.string());
                }
            }
        });

        /*
        String session = "ffuckk";
            String jsonInputString = "{\"accountName\":\"" + userId + "\",\"password\":\"" + password + "\",\"applicationId\":\"" + appId + "\"}";
            RequestBody requestBody = RequestBody.create(jsonInputString, JSON);
        Log.d("network", "3");
            Request request = new Request.Builder()
                    .url("https://share2.dexcom.com/ShareWebServices/Services/General/LoginPublisherAccountByName")
                    .post(requestBody)
                    .build();
            Log.d("network", "5");
            try (Response response = client.newCall(request).execute()) {
                Log.d("important", "get fucked mate");
                return response.body().string();
            }


            */



         //   finally {
          //      urlConnection.disconnect();
         //   }
            /*
            URL url = new URL ("https://share2.dexcom.com/ShareWebServices/Services/General/LoginPublisherAccountByName");
            Log.d("network", "1");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            int code = con.getResponseCode();
            Log.d("network", String.valueOf(code));
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String jsonInputString = "{\"accountName\":\"" + userId + "\",\"password\":\"" + password + "\",\"applicationId\":\"" + appId + "\"}";
            Log.d("network", "3");
            //try (OutputStream os = con.getOutputStream()){
            OutputStreamWriter os = new OutputStreamWriter(con.getOutputStream());
                Log.d("network", "4");
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                Log.d("network", "5");
                os.write(Arrays.toString(input), 0, input.length);
                Log.d("network", "6");
            //}

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8)
            )){
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                Log.d("network", "7");
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                Log.d("this is the actual response", responseLine);
                session = responseLine.toString();
            }

             */
        //    session = session.substring(1, session.length()-1);

       // }

    //    catch(Exception e){
     //       if (e instanceof IOException){
     //           System.out.println("uh oh spaghettio");
     //       }
     //   }

        return "";
    }





    //get current glucose reading
    public static String getCurrentGlucose(String sessionId) throws IOException{

        String glucose = "";
        String trend = "";

        try{
           /*
            String[] bits = response.body().split(",");
            glucose = bits[3];
            glucose = glucose.substring(8,11);
            trend = bits[4];
            trend = trend.substring(9,19);
            glucose = glucose + " " + trend;
            */

        }

        catch(Exception e){
            if (e instanceof IOException){
                System.out.println("uh oh spaghettio");
            }
        }


        return glucose;

    }
}

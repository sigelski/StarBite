package com.example.starbite3;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.starbite3.databinding.FragmentFirstBinding;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    public TextView tv1;
    public ImageView trend1;

    public String session;
    static OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json");
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.get("text/x-markdown; charset=utf-8");



    data dataList = MainActivity.getDataList();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        tv1 = (TextView) view.findViewById(R.id.textview_first);
        trend1 = (ImageView) view.findViewById(R.id.trend);
        trend1.setBackgroundResource(R.drawable.rocketanimation);
        AnimationDrawable rocket = (AnimationDrawable) trend1.getBackground();
        rocket.start();



        String jsonInputString = "{\"accountName\":\"" + dataList.getUsername() + "\",\"password\":\"" + dataList.getPassword() + "\",\"applicationId\":\"" + dataList.getAppId() + "\"}";
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
                    String body = responseBody.string();
                    body = body.substring(1, body.length()-1);
                    final String body2 = body;
                    Log.d("huahh", body);
                    dataList.setSessionId(body);


                    Request request = new Request.Builder()
                            .url("https://share2.dexcom.com/ShareWebServices/Services/Publisher/ReadPublisherLatestGlucoseValues?sessionId=" + body2 + "&minutes=1440&maxCount=1")
                            .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, ""))
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
                                String glucose = responseBody.string();
                                String trend = "";
                                glucose = glucose.substring(1, glucose.length()-1);
                                String[] bits = glucose.split(",");
                                glucose = bits[3];
                                glucose = glucose.substring(8,11);
                                trend = bits[4];
                                trend = trend.substring(9, trend.length()-2);
                                final String glucose2 = glucose;
                                Log.d("huahh", glucose2);
                                dataList.setSessionId(glucose2);
                                dataList.setTrend(trend);
                                final int trend2 = dataList.getTrend();

                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv1.setText(glucose2);
                                        trend1.setRotation(trend2);
                                        // Stuff that updates the UI

                                    }
                                });


                            }
                        }
                    });







                }
            }
        });


        int MINUTES = 5; // The delay in minutes
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() { // Function runs every MINUTES minutes.
                String jsonInputString = "{\"accountName\":\"" + dataList.getUsername() + "\",\"password\":\"" + dataList.getPassword() + "\",\"applicationId\":\"" + dataList.getAppId() + "\"}";
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
                            String body = responseBody.string();
                            body = body.substring(1, body.length()-1);
                            final String body2 = body;
                            Log.d("huahh", body);
                            dataList.setSessionId(body);
                            /*
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv1.setText(body2);
                                    // Stuff that updates the UI

                                }
                            });
                             */

                            Request request = new Request.Builder()
                                    .url("https://share2.dexcom.com/ShareWebServices/Services/Publisher/ReadPublisherLatestGlucoseValues?sessionId=" + body2 + "&minutes=1440&maxCount=1")
                                    .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, ""))
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
                                        String glucose = responseBody.string();
                                        String trend = "";
                                        glucose = glucose.substring(1, glucose.length()-1);
                                        String[] bits = glucose.split(",");
                                        glucose = bits[3];
                                        glucose = glucose.substring(8,11);
                                        trend = bits[4];
                                        trend = trend.substring(9, trend.length()-2);
                                        final String glucose2 = glucose;
                                        Log.d("huahh", glucose2);
                                        dataList.setSessionId(glucose2);
                                        dataList.setTrend(trend);
                                        final int trend2 = dataList.getTrend();

                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                tv1.setText(glucose2);
                                                trend1.setRotation(trend2);
                                                // Stuff that updates the UI

                                            }
                                        });


                                    }
                                }
                            });







                        }
                    }
                });
            }
        }, 0, 1000 * 60 * MINUTES);
        // 1000 milliseconds in a second * 60 per minute * the MINUTES variable.



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
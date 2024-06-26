package com.example.stayyoung;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class weather extends AppCompatActivity {
    class Weather extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... address) {
            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"utf-8");
                int data = isr.read();
                String content = "";
                char ch;
                while(data!=-1){
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();
                }
                return content;
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        String content;
        Weather weather = new Weather();
        String c = "Xinzhuang";
        try {
            content = weather.execute("https://openweathermap.org/data/2.5/weather?q="+c+"&appid=439d4b804bc8187953eb36d2a8c26a02").get();
            Log.i("天氣資料", content);
            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            Log.i("簡易天氣資料", weatherData);
            JSONArray array = new JSONArray(weatherData);
            String main = "";
            String description = "";
            for (int i=0;i<array.length();i++){
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");
            }
            Log.i("main", main);
            Log.i("description", description);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
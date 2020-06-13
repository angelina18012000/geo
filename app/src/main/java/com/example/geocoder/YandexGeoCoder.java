package com.example.geocoder;

import android.app.Activity;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class YandexGeoCoder extends Activity {

    final String apikey = "a817921e-3bf3-4517-8504-37fc2b1d334a";
    private String answer;
    private MapView mapView;
    private Coords coords;
    private String request;

    public YandexGeoCoder(MapView mapView, String request)  {
        this.mapView = mapView;
        this.request = ConvertRequest(request);
    }

    private String ConvertRequest(String request) {
        return request.replace(' ', '+');
    }

    public String GET() throws IOException {
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("https://geocode-maps.yandex.ru/1.x/?apikey="+apikey+"&format=xml&geocode="+this.request)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    answer = response.body().string();

                    Pattern pattern = Pattern.compile("<pos>(.+?)</pos>");
                    Matcher matcher = pattern.matcher(answer);
                    if (matcher.find()) {
                        coords = new Coords(answer.substring(matcher.start() + 5, matcher.end() - 6));
                        Thread t = new Thread(new Runnable() {
                            public void run() {
                                runOnUiThread(runn1);
                            }
                        });
                        t.start();
                    }
                }
            }
        });
        return answer;
    }
    Runnable runn1 = new Runnable() {
        public void run() {
            mapView.getMap().move(new CameraPosition(coords.getPoint(), 16.0f, 0.0f, 0.0f));
        }
    };

}

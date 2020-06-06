package com.example.geocoder;

import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class YandexGeoCoder {


    final String apikey = "a817921e-3bf3-4517-8504-37fc2b1d334a";
    private String answer;

    public String POST(String url, Collection<NameValuePair> params) throws IOException {

        /*final Collection<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("title", "foo"));
        params.add(new BasicNameValuePair("body", "bar"));
        params.add(new BasicNameValuePair("userId", "1"));*/

        final Content postResultForm = Request.Post(url)
                .bodyForm(params, Charset.defaultCharset())
                .execute().returnContent();
        System.out.println(postResultForm.asString());
        return "";
    }



    public String GET(String url) throws IOException {

        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("https://petstore.swagger.io/v2/pet/findByStatus?status=available")
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
                }
            }
        });
        return answer;
    }

}

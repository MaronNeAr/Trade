package com.example.service;

import com.example.pojo.CompletionRequest;
import com.squareup.moshi.Moshi;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class ChatService {
    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .build();

    public String completion(String prompt) throws IOException {
        CompletionRequest completionRequest = new CompletionRequest();
        completionRequest.setPrompt(prompt);
        Moshi moshi=new Moshi.Builder().build();
        String reqJson = moshi.adapter(CompletionRequest.class).toJson(completionRequest);

        System.out.println("reqJson: " + reqJson);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                // 将 API_KEY 替换成你自己的 API_KEY
                .header("Authorization", "Bearer " + "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")
                .header("Access-Control-Allow-Credentials", "true")
                .post(RequestBody.create(MediaType.parse("application/json"), reqJson))
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            System.out.println(response.body().string());
            return response.body().string();
        } catch(Exception e) {
            System.out.println(e);
            return "error";
        }
    }
}

package com.todo.util;

import com.todo.model.Todo;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SummaryHelper {
    @Value("${openai.api.key}")
    private String openaiKey;

    @Value("${slack.webhook.url}")
    private String slackUrl;

    private final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json");

    public String summarizeTodos(List<Todo> todos) throws IOException {
        if (todos == null || todos.isEmpty()) {
            return "There are no todos to summarize.";
        }

        String prompt = "Summarize the following todos:\n" + 
            todos.stream().map(t -> "- " + t.getTask()).collect(Collectors.joining("\n"));

        String json = new JSONObject()
            .put("model", "gpt-3.5-turbo")
            .put("messages", new org.json.JSONArray()
                .put(new JSONObject()
                    .put("role", "user")
                    .put("content", prompt)))
            .toString();

        Request request = new Request.Builder()
            .url("https://api.openai.com/v1/chat/completions")
            .header("Authorization", "Bearer " + openaiKey)
            .post(RequestBody.create(json, JSON))
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("OpenAI API error: " + response.code() + " - " + response.message());
            }

            String responseBody = response.body().string();
            return new JSONObject(responseBody)
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
        }
    }

    public void sendToSlack(String summary) throws IOException {
        if (summary == null || summary.isEmpty()) {
            throw new IllegalArgumentException("Summary content is empty");
        }

        String payload = new JSONObject()
            .put("text", summary)
            .toString();

        Request request = new Request.Builder()
            .url(slackUrl)
            .post(RequestBody.create(payload, JSON))
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Slack webhook error: " + response.code() + " - " + response.message());
            }
        }
    }
}

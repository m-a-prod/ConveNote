package com.conve.convenote.view;

import com.conve.convenote.service.ChatGptService;

import java.io.IOException;

public class App {
    private String chatgpt_token = "chatgtp_token";

    public App(String chatgpt_token) {
        this.chatgpt_token = chatgpt_token;
    }


    public String chatGPT(String question) throws IOException, InterruptedException {
        // Создаем исполнительский сервис с одним потоком

        ChatGptService service = new ChatGptService(chatgpt_token);
        return service.search(question);

    }
}


package com.conve.convenote.model;

public class ChatGptRequest {
    private final String model;
    private final String prompt;
    private final int temperature;
    private final int max_tokens;

    public ChatGptRequest(String model, String prompt, int temperature, int max_tokens) {
        this.model = model;
        this.prompt = prompt;
        this.temperature = temperature;
        this.max_tokens = max_tokens;
    }

    // Геттеры, но сеттеры не генерируются, так как поля объявлены с модификатором final
}
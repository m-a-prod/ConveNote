package com.conve.convenote.model;

import com.conve.convenote.view.App;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommandProcessor {
    public String answer;
    public static String chatgpt_token;

    public String Process(String preProcessedText, String chatgpt_token) {
        // текст из editText

        Map<String, CommandHandler> commandHandlers = new HashMap<>();
        this.chatgpt_token = chatgpt_token;
        // здесб добавлять новые команды после создания нового handler для них
        commandHandlers.put("gpt", new ChatGPTCommandHandler());
        commandHandlers.put("translate", new TranslateHandler());
        commandHandlers.put("calcul", new CalculHandler());

        return processCommands(preProcessedText, commandHandlers);
    }

    public static String processCommands(String text, Map<String, CommandHandler> commandHandlers) {
        Pattern pattern = Pattern.compile("(\\w+)\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(text);

        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String commandName = matcher.group(1);
            String commandText = matcher.group(2);
            CommandHandler commandHandler = commandHandlers.get(commandName);
            if (commandHandler != null) {
                String processedCommand = commandHandler.processCommand(commandText);
                matcher.appendReplacement(buffer, processedCommand);
            }
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    // супер интерфейс
    public interface CommandHandler {
        String processCommand(String commandText);
    }

    //здесь начинаются handlerы для команд
    public static class ChatGPTCommandHandler implements CommandHandler {

        private App app = new App(chatgpt_token);

        @Override
        public String processCommand(String commandText) {
            // Обработка команды chatgpt

            try {
                return app.chatGPT(commandText);
            } catch (IOException | InterruptedException e) {
                return "Произошла ошибка, попробуйте снова chatgpt[" + commandText + "]";
            }
        }
    }

    public static class TranslateHandler implements CommandHandler {

        @Override
        public String processCommand(String commandText) {
            return "translate";
        }
    }

    public static class CalculHandler implements CommandHandler {

        @Override
        public String processCommand(String commandText) {
            return MathExpressionEvaluator.evaluateMathExpression(commandText);
        }
    }





    }

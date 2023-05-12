package com.conve.convenote.model;

public class MathExpressionEvaluator {

    public static String evaluateMathExpression(String expression) {
        try {
            double result = evaluate(expression);
            return Double.toString(result);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: Invalid expression calcul[" + expression + "]";
        }
    }

    private static double evaluate(String expression) {
        // Поддерживаемые операции: +, -, *, /, ^ (возведение в степень)

        expression = expression.replaceAll(" ", ""); // Удаляем пробелы

        if (expression.contains("^")) {
            // Обработка возведения в степень
            String[] parts = expression.split("\\^", 2);
            double base = evaluate(parts[0]);
            double exponent = evaluate(parts[1]);
            return Math.pow(base, exponent);
        } else if (expression.startsWith("-")) {
            // Обработка отрицательного числа
            return -evaluate(expression.substring(1));
        } else if (expression.contains("+")) {
            // Обработка сложения
            String[] parts = expression.split("\\+", 2);
            double leftOperand = evaluate(parts[0]);
            double rightOperand = evaluate(parts[1]);
            return leftOperand + rightOperand;
        } else if (expression.contains("-")) {
            // Обработка вычитания
            String[] parts = expression.split("-", 2);
            double leftOperand = evaluate(parts[0]);
            double rightOperand = evaluate(parts[1]);
            return leftOperand - rightOperand;
        } else if (expression.contains("*")) {
            // Обработка умножения
            String[] parts = expression.split("\\*", 2);
            double leftOperand = evaluate(parts[0]);
            double rightOperand = evaluate(parts[1]);
            return leftOperand * rightOperand;
        } else if (expression.contains("/")) {
            // Обработка деления
            String[] parts = expression.split("/", 2);
            double leftOperand = evaluate(parts[0]);
            double rightOperand = evaluate(parts[1]);
            return leftOperand / rightOperand;
        } else {
            // Обработка числовых литералов
            return Double.parseDouble(expression);
        }
    }


}
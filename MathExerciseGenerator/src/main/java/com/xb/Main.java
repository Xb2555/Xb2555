package com.xb;

import java.util.Random;

public class Main {
    // 随机数生成器，用于生成随机数和运算符
    private static final Random random = new Random();
    public static void main(String[] args) {

    }

    // 生成一个随机的四则运算题目
    private static String generateExercise(int range) {
        // 生成两个随机数
        int num1 = random.nextInt(range);
        int num2 = random.nextInt(range);
        // 随机选择一个运算符
        char operator = getRandomOperator();
        // 返回题目字符串，例如 "3 + 5 ="
        return num1 + " " + operator + " " + num2 + " =";
    }

    // 随机选择一个运算符（+、-、*、/）
    private static char getRandomOperator() {
        char[] operators = {'+', '-', '*', '/'};
        return operators[random.nextInt(operators.length)];
    }

    // 简化分数形式
    private static String simplifyFraction(int numerator, int denominator) {
        // 计算最大公约数
        int gcd = gcd(numerator, denominator);
        // 返回简化后的分数，例如 "3/4"
        return (numerator / gcd) + "/" + (denominator / gcd);
    }

    // 计算两个数的最大公约数（GCD）
    private static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    // 计算题目的答案
    private static String calculateAnswer(String exercise) {
        // 将题目字符串拆分为数字和运算符
        String[] parts = exercise.split(" ");
        int num1 = Integer.parseInt(parts[0]);
        char operator = parts[1].charAt(0);
        int num2 = Integer.parseInt(parts[2]);

        // 根据运算符计算结果
        switch (operator) {
            case '+':
                return String.valueOf(num1 + num2);
            case '-':
                return String.valueOf(num1 - num2);
            case '*':
                return String.valueOf(num1 * num2);
            case '/':
                // 如果是除法，返回简化后的分数形式
                return simplifyFraction(num1, num2);
            default:
                return "0";
        }
    }
}
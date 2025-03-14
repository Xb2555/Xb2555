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
}
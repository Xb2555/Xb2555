package com.xb;

import java.io.*;
import java.util.*;

public class Main {
    // 随机数生成器，用于生成随机数和运算符
    private static final Random random = new Random();
    public static void main(String[] args) {
        // 如果没有传入参数，打印帮助信息并退出
        if (args.length == 0) {
            printHelp();
            return;
        }

        // 解析命令行参数
        if (args[0].equals("-n") && args.length == 4 && args[2].equals("-r")) {
            // 生成题目模式：-n <题目数量> -r <数值范围>
            int numberOfExercises = Integer.parseInt(args[1]);
            int range = Integer.parseInt(args[3]);
            generateExercises(numberOfExercises, range);
        }else if (args[0].equals("-e") && args.length == 4 && args[2].equals("-a")) {
            // 判断答案模式：-e <题目文件> -a <答案文件>
            String exerciseFile = args[1];
            String answerFile = args[3];
            gradeAnswers(exerciseFile, answerFile);
        }else{
            printHelp();
        }
    }

    // 生成指定数量的题目
    private static void generateExercises(int numberOfExercises, int range) {
        // 用于存储已生成的题目，避免重复
        Set<String> exercises = new HashSet<>();
        // 存储题目列表
        List<String> exerciseList = new ArrayList<>();
        // 存储答案列表
        List<String> answerList = new ArrayList<>();

        // 生成题目，直到达到指定数量
        while (exerciseList.size() < numberOfExercises) {
            String exercise = generateExercise(range);
            // 检查题目是否重复
            if (!exercises.contains(exercise)) {
                exercises.add(exercise);
                exerciseList.add(exercise);
                // 计算题目的答案并保存
                answerList.add(calculateAnswer(exercise));
            }
        }

        // 将题目和答案保存到文件中
        saveToFile("Exercises.txt", exerciseList);
        saveToFile("Answers.txt", answerList);
    }


    // 打印帮助信息
    private static void printHelp() {
        System.out.println("使用方法:");
        System.out.println("  生成题目: Myapp.jar -n <要生成的题目数> -r <随机数>");
        System.out.println("  检查答案: Myapp.exe -e <题目文件>.txt -a <答案文件>.txt");
    }

    // 生成一个随机的四则运算题目
    private static String generateExercise(int range) {
        // 生成两个随机数
        int num1 = random.nextInt(range);
        int num2 = random.nextInt(range);
        // 随机选择一个运算符
        char operator = getRandomOperator();
        // 如果运算符是除法，确保第二个数不为零
        if (operator == '/' && num2 == 0) {
            num2 = random.nextInt(range - 1) + 1; // 确保 num2 不为零
        }
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
        // 检查分母是否为零
        if (denominator == 0) {
            return "Error: Division by zero";
        }
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
                // 检查除数为零的情况
                if (num2 == 0) {
                    return "Error: Division by zero";
                }
                return simplifyFraction(num1, num2);
            default:
                return "0";
        }
    }

    // 将列表中的内容保存到文件中
    private static void saveToFile(String fileName, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 判断答案的正确性
    private static void gradeAnswers(String exerciseFile, String answerFile) {
        // 读取题目文件和答案文件
        List<String> exercises = readFile(exerciseFile);
        List<String> answers = readFile(answerFile);

        // 存储正确和错误题目的编号
        List<Integer> correctIndices = new ArrayList<>();
        List<Integer> wrongIndices = new ArrayList<>();

        // 遍历每个题目，判断答案是否正确
        for (int i = 0; i < exercises.size(); i++) {
            String exercise = exercises.get(i);
            // 计算题目的正确答案
            String correctAnswer = calculateAnswer(exercise);
            // 获取用户提供的答案
            String userAnswer = answers.get(i);

            // 比较答案是否正确
            if (correctAnswer.equals(userAnswer)) {
                correctIndices.add(i + 1); // 题目编号从1开始
            } else {
                wrongIndices.add(i + 1);
            }
        }

        // 将统计结果保存到文件中
        saveGradeToFile(correctIndices, wrongIndices);
    }

    // 从文件中读取内容
    private static List<String> readFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    // 将统计结果保存到 Grade.txt 文件中
    private static void saveGradeToFile(List<Integer> correctIndices, List<Integer> wrongIndices) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Grade.txt"))) {
            writer.write("Correct: " + correctIndices.size() + " " + correctIndices);
            writer.newLine();
            writer.write("Wrong: " + wrongIndices.size() + " " + wrongIndices);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package com.gg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("请按以下格式输入java -jar main.jar [原文文件] [抄袭版论文的文件] [答案文件]");
            System.exit(1);
        }
        //原文件地址
        String originalPath = args[0];
        //抄袭版论文的文件地址
        String plagiarizedPath = args[1];
        //答案文件地址
        String outputPath = args[2];

        //读取原文
    }

    // 读取文件内容
    private static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString().trim();
    }
}

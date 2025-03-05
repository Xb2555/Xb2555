package com.gg;

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

    }
}

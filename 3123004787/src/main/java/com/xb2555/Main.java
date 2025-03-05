package com.xb2555;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("请按以下格式输入java -jar main.jar [原文文件] [抄袭版论文的文件] [答案文件]");
            System.exit(1);
        }

        String originalPath = args[0];
        String plagiarizedPath = args[1];
        String outputPath = args[2];
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

    // 中文分词
    private static List<String> segmentWords(JiebaSegmenter segmenter, String text) {
        List<SegToken> tokens = segmenter.process(text, JiebaSegmenter.SegMode.SEARCH);
        List<String> words = new ArrayList<>();
        for (SegToken token : tokens) {
            words.add(token.word.toLowerCase());
        }
        return words;
    }
}

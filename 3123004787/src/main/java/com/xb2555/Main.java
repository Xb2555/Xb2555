package com.xb2555;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("请按以下格式输入java -jar main.jar [原文文件] [抄袭版论文的文件] [答案文件]");
            System.exit(1);
        }

        String originalPath = args[0];
        String plagiarizedPath = args[1];
        String outputPath = args[2];

        try {
            // 读取文件内容
            String originalText = readFile(originalPath);
            String plagiarizedText = readFile(plagiarizedPath);

            // 计算相似度
            double similarity = calculateCosineSimilarity(originalText, plagiarizedText);

            // 输出结果
            writeResult(outputPath, similarity);

        } catch (IOException e) {
            System.err.println("文件读写错误: " + e.getMessage());
        }
    }

    // 读取文件内容
    public static String readFile(String filePath) throws IOException {
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
    public static List<String> segmentWords(JiebaSegmenter segmenter, String text) {
        List<SegToken> tokens = segmenter.process(text, JiebaSegmenter.SegMode.SEARCH);
        List<String> words = new ArrayList<>();
        for (SegToken token : tokens) {
            words.add(token.word.toLowerCase());
        }
        return words;
    }

    // 构建词频向量
    public static void buildVector(List<String> words, Map<String, int[]> vectorMap, int index) {
        for (String word : words) {
            if (!vectorMap.containsKey(word)) {
                vectorMap.put(word, new int[2]);
            }
            vectorMap.get(word)[index]++;
        }
    }

    // 计算余弦相似度
    public static double calculateCosineSimilarity(String text1, String text2) {
        // 中文分词
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> words1 = segmentWords(segmenter, text1);
        List<String> words2 = segmentWords(segmenter, text2);

        // 构建词频向量
        Map<String, int[]> vectorMap = new HashMap<>();
        buildVector(words1, vectorMap, 0);
        buildVector(words2, vectorMap, 1);

        // 计算点积和模长
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (Map.Entry<String, int[]> entry : vectorMap.entrySet()) {
            int[] counts = entry.getValue();
            dotProduct += counts[0] * counts[1];
            norm1 += Math.pow(counts[0], 2);
            norm2 += Math.pow(counts[1], 2);
        }

        if (norm1 == 0 || norm2 == 0) {
            return 0.0; // 避免除以零
        }
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    // 写入结果文件
    public static void writeResult(String outputPath, double similarity) throws IOException {
        DecimalFormat df = new DecimalFormat("0.00");
        String result = df.format(similarity * 100) + "%";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write(result);
        }
    }
}

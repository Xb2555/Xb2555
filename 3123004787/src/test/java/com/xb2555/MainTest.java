package com.xb2555;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    //测试readFile方法
    @Test
    public void testReadFile(@TempDir Path tempDir) throws IOException {
        // 创建临时文件
        File tempFile = tempDir.resolve("test.txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("Hello, World!");
        }

        // 调用 readFile 方法
        String content = Main.readFile(tempFile.getAbsolutePath());

        // 验证读取的内容
        assertEquals("Hello, World!", content);
    }

    //测试中文分词函数
    @Test
    public void testSegmentWords() {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        String text = "我爱软件工程";
        List<String> words = Main.segmentWords(segmenter, text);

        // 验证分词结果
        List<String> expected = Arrays.asList("我", "爱", "软件工程");
        assertEquals(expected, words);
    }

    //测试计算余弦相似度函数
    @Test
    public void testBuildVector() {
        List<String> words1 = Arrays.asList("apple", "banana", "apple");
        List<String> words2 = Arrays.asList("banana", "orange", "banana");
        Map<String, int[]> vectorMap = new HashMap<>();
        Main.buildVector(words1, vectorMap, 0);
        Main.buildVector(words2, vectorMap, 1);

        // 验证词频向量
        assertArrayEquals(new int[]{2, 0}, vectorMap.get("apple"));
        assertArrayEquals(new int[]{1, 2}, vectorMap.get("banana"));
        assertArrayEquals(new int[]{0, 1}, vectorMap.get("orange"));
    }


    //测试相似度计算函数
    @Test
    public void testCalculateCosineSimilarity() {
        String text1 = "我爱北京天安门";
        String text2 = "我爱北京天安门";
        double similarity = Main.calculateCosineSimilarity(text1, text2);

        // 验证相似度
        assertEquals(1.0, similarity, 0.0001);
    }



    //测试结果写入函数
    @Test
    public void testWriteResult(@TempDir Path tempDir) throws IOException {
        File tempFile = tempDir.resolve("result.txt").toFile();
        double similarity = 0.85;
        Main.writeResult(tempFile.getAbsolutePath(), similarity);

        // 读取文件内容
        try (BufferedReader reader = new BufferedReader(new FileReader(tempFile))) {
            String result = reader.readLine();
            assertEquals("85.00%", result);
        }
    }

    //测试命令行输入及输出
    @Test
    public void testMain(@TempDir Path tempDir) throws IOException {
        // 创建临时文件
        File originalFile = tempDir.resolve("original.txt").toFile();
        File plagiarizedFile = tempDir.resolve("plagiarized.txt").toFile();
        File outputFile = tempDir.resolve("output.txt").toFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(originalFile))) {
            writer.write("我爱北京天安门");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(plagiarizedFile))) {
            writer.write("我爱北京天安门");
        }

        // 模拟命令行参数
        String[] args = new String[]{
                originalFile.getAbsolutePath(),
                plagiarizedFile.getAbsolutePath(),
                outputFile.getAbsolutePath()
        };

        // 调用 main 方法
        Main.main(args);

        // 验证输出文件内容
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            String result = reader.readLine();
            assertEquals("100.00%", result);
        }
    }

}

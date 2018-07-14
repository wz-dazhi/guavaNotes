package com.wz.guava.io;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.*;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @projectName: Guava
 * @package: com.wz.guava.io
 * @className: FilesTest
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/7/12 22:46
 **/
public class FilesTest {

    private final String SOURCE_FILE = "/Users/wangzhi/work/project/Guava/guava_programming/src/test/resources/io/source.txt";

    private final String TARGET_FILE = "/Users/wangzhi/work/project/Guava/guava_programming/src/test/resources/io/target.txt";

    /**
     * 拷贝文件
     *
     * @throws IOException
     */
    @Test
    public void testCopyFile() throws IOException {
        File targetFile = new File(TARGET_FILE);
        File sourceFile = new File(SOURCE_FILE);
        Files.copy(sourceFile, targetFile);
        assertThat(targetFile.exists(), equalTo(true));
        // 取两个文件的hash进行比较
        HashCode sourceHashCode = Files.asByteSource(sourceFile).hash(Hashing.sha256());
        HashCode targetHashCode = Files.asByteSource(targetFile).hash(Hashing.sha256());
        assertThat(sourceHashCode, equalTo(targetHashCode));
    }

    /**
     * 使用JDK中nio2.0 复制文件
     *
     * @throws IOException
     */
    @Test
    public void testCopyFileWithJDKNio2() throws IOException {
        java.nio.file.Files.copy(
                Paths.get("/Users/wangzhi/work/project/Guava/guava_programming/src/test/resources", "io", "source.txt"),
                Paths.get("/Users/wangzhi/work/project/Guava/guava_programming/src/test/resources", "io", "target.txt"),
                StandardCopyOption.REPLACE_EXISTING
        );
        File targetFile = new File(TARGET_FILE);
        assertThat(targetFile.exists(), equalTo(true));
    }

    /**
     * 移动文件,文件重命名
     *
     * @throws IOException
     */
    @Test
    public void testMoveFile() throws IOException {
        try {
            Files.move(new File(SOURCE_FILE), new File(TARGET_FILE));
            assertThat(new File(TARGET_FILE).exists(), equalTo(true));
            assertThat(new File(SOURCE_FILE).exists(), equalTo(false));
        } finally {
            Files.move(new File(TARGET_FILE), new File(SOURCE_FILE));
        }
    }

    /**
     * 读取文件
     *
     * @throws IOException
     */
    @Test
    public void testToString() throws IOException {
        final String string = "hello guava.\n" +
                "this is txt files\n" +
                "\n" +
                "please see guava document or resource code";
        // 读取文件,设置字符集
        List<String> strings = Files.readLines(new File(SOURCE_FILE), Charsets.UTF_8);
        String result = Joiner.on("\n").join(strings);
        assertThat(result, equalTo(string));
    }

    /**
     * 读取文件, 并处理读取的内容
     *
     * @throws IOException
     */
    @Test
    public void testFileProcess() throws IOException {
//        Files.readLines(new File(SOURCE_FILE), Charsets.UTF_8, LineProcessor) // 过时
        // 定义行处理器
        LineProcessor<List<Integer>> lineProcessor = new LineProcessor<List<Integer>>() {

            private List<Integer> lengths = new ArrayList<>();

            /**
             *[12, 17, 0, 42]
             */

            /**
             * 读文件的过程中, 处理读出来的行. 如果行的长度为0, 就不往后接着读了 --> [12, 17]
             * @param line
             * @return
             * @throws IOException
             */
            @Override
            public boolean processLine(String line) throws IOException {
                int length = line.length();
                if (0 == length) return false;
                lengths.add(length);
                return true;
            }

            /**
             * 返回处理后的结果
             * @return
             */
            @Override
            public List<Integer> getResult() {
                return lengths;
            }
        };
        List<Integer> lengths = Files.asCharSource(new File(SOURCE_FILE), Charsets.UTF_8).readLines(lineProcessor);
        System.out.println(lengths);
    }

    /**
     * 计算文件hash值
     *
     * @throws IOException
     */
    @Test
    public void testFileSha() throws IOException {
//        HashCode hashCode = Files.hash(Hashing.goodFastHash(128)); // 过时
        File file = new File(SOURCE_FILE);
        HashCode hashCode = Files.asByteSource(file).hash(Hashing.sha256());
        System.out.println(hashCode);
    }

    /**
     * 写入文件,写入多次会清空文件,然后写入新的内容(并不是append追加到该文件中)
     *
     * @throws IOException
     */
    @Test
    public void testFileWrite() throws IOException {
        String content1 = "content1";
        String testFilePath = "/Users/wangzhi/work/project/Guava/guava_programming/src/test/resources/io/test.txt";
        File testFile = new File(testFilePath);
        testFile.deleteOnExit(); // 表示程序结束后就删除文件
//        Files.write(content1,testFile, Charsets.UTF_8); // 过时
        CharSink sink = Files.asCharSink(testFile, Charsets.UTF_8);
        sink.write(content1);
        // 读出来进行断言
        String readResult = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(content1, equalTo(readResult));

        String content2 = "content2";
        sink.write(content2);
        readResult = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(content2, equalTo(readResult));
    }

    /**
     * 追加写入文件
     *
     * @throws IOException
     */
    @Test
    public void testFileAppend() throws IOException {
        String filePath = "/Users/wangzhi/work/project/Guava/guava_programming/src/test/resources/io/testAppend.txt";
        File file = new File(filePath);
        file.deleteOnExit();
        CharSink sink = Files.asCharSink(file, Charsets.UTF_8, FileWriteMode.APPEND);
        sink.write("content 1");
        CharSource charSource = Files.asCharSource(file, Charsets.UTF_8);
        String result = charSource.read();
        assertThat(result, equalTo("content 1"));

        // 追加
        sink.write("content 2");
        result = charSource.read();
        assertThat(result, equalTo("content 1content 2"));
    }

    /**
     * 创建一个空的文件
     *
     * @throws IOException
     */
    @Test
    public void testFileTouch() throws IOException {
        String filePath = "/Users/wangzhi/work/project/Guava/guava_programming/src/test/resources/io/testTouch.txt";
        File touchFile = new File(filePath);
        touchFile.deleteOnExit();
        Files.touch(touchFile);
        assertThat(touchFile.exists(), equalTo(true));
    }

    /**
     * 自己写递归获取文件
     */
    @Test
    public void testRecursive() {
        String filePath = "/Users/wangzhi/work/project/Guava/guava_programming/src";
        List<File> files = new ArrayList<>();
        this.recursive(new File(filePath), files);
        files.forEach(System.out::println);
    }

    /**
     * 自己写递归遍历文件
     *
     * @param file
     * @param files
     */
    private void recursive(File file, List<File> files) {
        // 文件夹,文件
        /*if (file.isHidden()) return;
        files.add(file);
        if (!file.isFile()) {
            File[] listFiles = file.listFiles();
            for (File f : listFiles) {
                recursive(f, files);
            }
        }*/

        // 只拿文件
        if (file.isHidden()) return;
        if (file.isFile()) {
            files.add(file);
        } else {
            for (File f : file.listFiles()) {
                recursive(f, files);
            }
        }
    }

    /**
     * 使用Files proOrderTraversal,正序循环递归遍历文件夹以及文件
     */
    @Test
    public void testFilesPreOrderTraversal() {
        String filePath = "/Users/wangzhi/work/project/Guava/guava_programming/src";
        FluentIterable<File> files = Files.fileTreeTraverser().preOrderTraversal(new File(filePath));
        files.stream().forEach(System.out::println);
    }

    /**
     * 使用Files postOrderTraversal,倒序循环递归遍历文件夹以及文件
     */
    @Test
    public void testFilesPostOrderTraversal() {
        String filePath = "/Users/wangzhi/work/project/Guava/guava_programming/src";
        FluentIterable<File> files = Files.fileTreeTraverser().postOrderTraversal(new File(filePath));
        files.stream().forEach(System.out::println);
    }

    /**
     * 使用Files breadthFirstTraversal,根据目录阶梯式循环递归遍历文件夹以及文件
     */
    @Test
    public void testFileBreadthFirstTraversal() {
        String filePath = "/Users/wangzhi/work/project/Guava/guava_programming/src";
        FluentIterable<File> files = Files.fileTreeTraverser().breadthFirstTraversal(new File(filePath));
        files.stream().forEach(System.out::println);
    }

    /**
     * 遍历文件夹中的子文件夹
     */
    @Test
    public void testFileChildren() {
        String filePath = "/Users/wangzhi/work/project/Guava/guava_programming/src";
        Iterable<File> children = Files.fileTreeTraverser().children(new File(filePath));
        children.forEach(System.out::println);
    }

    @After
    public void tearDown() {
        File targetFile = new File(TARGET_FILE);
        if (targetFile.exists())
            targetFile.delete();
    }
}

package com.wz.guava.io;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSource;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @projectName: Guava
 * @package: com.wz.guava.io
 * @className: CharSourceTest
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/7/23 15:57
 **/
public class CharSourceTest {

    /**
     * 字符流
     * 1. 字符流读取
     * 2. 读取字符是否为空
     * 3. 字符的长度
     *
     * @throws IOException
     */
    @Test
    public void testCharSource() throws IOException {
        CharSource charSource = CharSource.wrap("i am the char source");
        ImmutableList<String> lines = charSource.readLines();
        assertThat(lines.get(0), equalTo("i am the char source"));
        assertThat(lines.size(), equalTo(1));
        String read = charSource.read();
        assertThat(read, equalTo("i am the char source"));
        boolean empty = charSource.isEmpty();
        assertThat(empty, equalTo(false));
        long length = charSource.length();
        assertThat(length, equalTo(20L));
        Optional<Long> longOptional = charSource.lengthIfKnown();
        assertThat(longOptional.get(), equalTo(20L));
    }

    /**
     * 使用多个字符流
     *
     * @throws IOException
     */
    @Test
    public void testConcat() throws IOException {
        // 内部只使用一个字符流
        CharSource charSource = CharSource.concat(
                CharSource.wrap("i am the char source1\n"),
                CharSource.wrap("i am the char source2")
        );
        charSource.readLines().forEach(System.out::println);
    }
}

package com.wz.guava.utilities;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @projectName: Guava
 * @package: com.wz.guava.utilities
 * @className: SplitterTest
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/7/4 21:55
 **/
public class SplitterTest {

    /**
     * 分割字符串,返回list集合
     */
    @Test
    public void testSplitOnSplit() {
        List<String> result = Splitter.on("|").splitToList("hello|world|java");
        System.out.println(result);
        assertThat(result.size(), equalTo(3));
        assertThat(result.contains("hello"), equalTo(true));
        assertThat(result.contains("world"), equalTo(true));
    }

    /**
     * 分割字符串,并去除空串
     */
    @Test
    public void testSplitOnSplitOmitEmpty() {
        // 不去除空串
        List<String> result = Splitter.on("|").splitToList("hello|world|java|||");
        System.out.println(result); // [hello, world, java, , , ]
        assertThat(result.size(), equalTo(6));

        // 去除空串
        result = Splitter.on("|").omitEmptyStrings().splitToList("hello|world|java|||");
        System.out.println(result); // [hello, world, java]
        assertThat(result.size(), equalTo(3));
    }

    /**
     * 去除字符串空格
     */
    @Test
    public void testSplitOnSplitTrim() {
        // 去除前
        List<String> result = Splitter.on("|").omitEmptyStrings().splitToList("hello | world|java |||");
        System.out.println(result);
        assertThat(result.get(0), equalTo("hello "));
        assertThat(result.get(1), equalTo(" world"));
        assertThat(result.get(2), equalTo("java "));

        // 去除后
        result = Splitter.on("|").omitEmptyStrings().trimResults().splitToList("hello | world|java |||");
        System.out.println(result);
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
        assertThat(result.get(2), equalTo("java"));
    }

    /**
     * 根据长度进行分割, 比如:每隔3个字符分割一次
     */
    @Test
    public void testSplitOnFixedLength() {
        List<String> result = Splitter.fixedLength(3).splitToList("123abcABC");
        System.out.println(result);
        assertThat(result.get(0), equalTo("123"));
        assertThat(result.get(1), equalTo("abc"));
        assertThat(result.get(2), equalTo("ABC"));
    }

    /**
     * 使用limit
     */
    @Test
    public void testSplitOnLimit() {
        List<String> result = Splitter.on("|").limit(4).splitToList("hello|world|java|google|guava|scala");
        System.out.println(result);
        assertThat(result.size(), equalTo(4));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
        assertThat(result.get(2), equalTo("java"));
        assertThat(result.get(3), equalTo("google|guava|scala"));
    }

    /**
     * 使用字符串正则
     */
    @Test
    public void testSplitOnPatternString() {
        List<String> result = Splitter.onPattern("\\|").omitEmptyStrings().trimResults().splitToList("hello | world||");
        System.out.println(result);
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }

    /**
     * 使用正则
     */
    @Test
    public void testSplitOnPattern() {
        List<String> result = Splitter.on(Pattern.compile("\\|")).omitEmptyStrings().trimResults().splitToList("hello | world||");
        System.out.println(result);
        assertThat(result.size(), equalTo(2));
        assertThat(result.get(0), equalTo("hello"));
        assertThat(result.get(1), equalTo("world"));
    }

    /**
     * 操作map
     */
    @Test
    public void testSplitOnSplitToMap(){
        Map<String, String> result = Splitter.on('#').omitEmptyStrings().trimResults().withKeyValueSeparator('=').split("hello=HELLO # world=WORLD#  ##");
        System.out.println(result);
        assertThat(result.size(), equalTo(2));
        assertThat(result.get("hello"), equalTo("HELLO"));
        assertThat(result.get("world"), equalTo("WORLD"));
    }

}

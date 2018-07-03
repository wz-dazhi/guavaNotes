package com.wz.guava.utilities;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.ImmutableSortedMap.of;
import static java.util.stream.Collectors.joining;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.fail;

/**
 * @projectName: Guava
 * @package: com.wz.guava.utilities
 * @className: JoinerTest
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/7/2 23:18
 **/
public class JoinerTest {

    /**
     * 定义字符串集合
     */
    private final List<String> strings = Arrays.asList("java", "guava", "google", "mysql");

    /**
     * 定义带null字符串集合
     */
    private final List<String> stringsWithNull = Arrays.asList("oracle", "sql server", "db2", null);

    /**
     * 定义Map<String, String>
     */
    private final Map<String, String> stringMap = of("key1", "value1", "key2", "value2", "key3", "value3");

    /**
     * stringList文件路径
     */
    private final String targetFileName = "/Users/wangzhi/appedTo-writer.txt";

    /**
     * stringMap文件路径
     */
    private final String targetMapFileName = "/Users/wangzhi/appedTo-map-writer.txt";

    /**
     * 字符串拼接
     */
    @Test
    public void testJoinOnJoin() {
        String joinString = Joiner.on("#").join(strings);
        System.out.println(joinString);
        assertThat(joinString, equalTo("java#guava#google#mysql"));
    }

    /**
     * 带null字符串拼接
     */
    @Test(expected = NullPointerException.class)
    public void testJoinOnJoinWithNull() {
        String joinString = Joiner.on("#").join(stringsWithNull);
        System.out.println(joinString);
        assertThat(joinString, equalTo("oracle#sql server#db2"));
    }

    /**
     * 跳过null
     */
    @Test
    public void testJoinOnJoinWithNullSkip() {
        String joinString = Joiner.on("#").skipNulls().join(stringsWithNull);
        System.out.println(joinString);
        assertThat(joinString, equalTo("oracle#sql server#db2"));
    }

    /**
     * 为null值添加默认值
     */
    @Test
    public void testJoinOnJoinWithNullAddDefault() {
        String defaultResultString = Joiner.on("#").useForNull("DEFAULT_VALUE").join(stringsWithNull);
        System.out.println(defaultResultString);
        assertThat(defaultResultString, equalTo("oracle#sql server#db2#DEFAULT_VALUE"));
    }

    /**
     * appendTo
     */
    @Test
    public void testJoinOnJoinAppendTo() {
        final StringBuilder builder = new StringBuilder();
        StringBuilder stringBuilder = Joiner.on("#").useForNull("DEFAULT_VALUE").appendTo(builder, stringsWithNull);
        System.out.println(stringBuilder);
        // 断言是否同一个实例builder
        assertThat(stringBuilder, sameInstance(builder));
        assertThat(stringBuilder.toString(), equalTo("oracle#sql server#db2#DEFAULT_VALUE"));
        assertThat(builder.toString(), equalTo("oracle#sql server#db2#DEFAULT_VALUE"));
    }

    /**
     * appendTo writer, 将追加的字符串写到文件中
     */
    @Test
    public void testJoinOnJoinAppendToWriter() {
        try (FileWriter writer = new FileWriter(new File(targetFileName))) {
            Joiner.on("#").useForNull("DEFAULT_VALUE").appendTo(writer, stringsWithNull);
            // 断言是否存在该文件
            assertThat(Files.isFile().test(new File(targetFileName)), equalTo(true));
        } catch (IOException e) {
            fail("append to the writer error...");
        }
    }

    /**
     * 使用jdk1.8的方式拼接'#'字符串,并且跳过null
     */
    @Test
    public void testJoiningByStreamSkipNull() {
        String result = stringsWithNull.stream().filter(s -> s != null && !s.isEmpty()).collect(joining("#"));
        System.out.println(result);
        assertThat(result, equalTo("oracle#sql server#db2"));
    }

    /**
     * 使用jdk1.8的方式拼接'#'字符串,如果出现null,则给null添加默认值
     */
    @Test
    public void testJoiningByStreamWithDefault() {
        String result = stringsWithNull.stream().map(this::defaultValue).collect(joining("#"));
        System.out.println(result);
        assertThat(result, equalTo("oracle#sql server#db2#DEFAULT_VALUE"));
    }

    /**
     * 如果为null或为"", 返回默认值
     *
     * @param s
     * @return
     */
    private String defaultValue(String s) {
        return s == null || s.isEmpty() ? "DEFAULT_VALUE" : s;
    }

    /**
     * 操作map, key=value拼接'#'字符串
     */
    @Test
    public void testJoinOnJoinMap() {
        String joinMap = Joiner.on('#').withKeyValueSeparator('=').join(stringMap);
        System.out.println(joinMap);
        assertThat(joinMap, equalTo("key1=value1#key2=value2#key3=value3"));
    }

    /**
     * 操作map, key=value拼接'#'字符串, 并写入文件当中
     */
    @Test
    public void testJoinOnJoinAppendToMapWriter() {
        try (FileWriter writer = new FileWriter(targetMapFileName)) {
            Joiner.on('#').withKeyValueSeparator('=').appendTo(writer, stringMap);
            assertThat(Files.isFile().test(new File(targetMapFileName)), equalTo(true));
        } catch (IOException e) {
            fail("append to the stringMap writer...");
        }
    }

}

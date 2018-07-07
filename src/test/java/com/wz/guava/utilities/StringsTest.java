package com.wz.guava.utilities;

import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @projectName: Guava
 * @package: com.wz.guava.utilities
 * @className: StringsTest
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/7/6 21:55
 **/
public class StringsTest {

    /**
     * 对字符串的处理
     */
    @Test
    public void testStrings() {
        // 空串变为null
        final String empty = Strings.emptyToNull("");
        assertThat(empty, nullValue());
        // null变为空串
        String nullResult = Strings.nullToEmpty(null);
        assertThat(nullResult, equalTo(""));
        // 公共的前缀
        String commonPrefix = Strings.commonPrefix("hello world", "hi world");
        assertThat(commonPrefix, equalTo("h"));
        // 公共的后缀
        String commonSuffix = Strings.commonSuffix("Guava", "Java");
        assertThat(commonSuffix, equalTo("ava"));
        // 判断是否为null或者空串
        boolean nullOrEmpty = Strings.isNullOrEmpty(null);
        assertThat(nullOrEmpty, equalTo(true));
        // 向前缀填充字符, 设置最小字符串长度, 如果超过长度,则不进行填充. 示列:minLength为4 ava -> java, minLength为5 ava -> jjava
        String padStart = Strings.padStart("ava", 4, 'j');
        assertThat(padStart, equalTo("java"));
        // 向后缀进行填充, 与padStart相反
        String padEnd = Strings.padEnd("Guav", 5, 'a');
        assertThat(padEnd, equalTo("Guava"));
        // 重复字符串
        String repeat = Strings.repeat("java", 3);
        assertThat(repeat, equalTo("javajavajava"));
    }

    /**
     * 使用字符集
     */
    @Test
    public void testCharset() {
        Charset charset = Charset.forName("UTF-8");
        assertThat(charset, equalTo(Charsets.UTF_8));
    }

    /**
     * 字符匹配
     */
    @Test
    public void testCharMatcher() {
        // 是否是数字
        assertThat(CharMatcher.digit().matches('5'), equalTo(true));
        assertThat(CharMatcher.digit().matches('a'), equalTo(false));
        // 所有是否是数字
        assertThat(CharMatcher.digit().matchesAllOf("123"), equalTo(true));
        // 是否存在数字
        assertThat(CharMatcher.digit().matchesAnyOf("hello 12 world"), equalTo(true));
        // 是否不存在数字
        assertThat(CharMatcher.digit().matchesNoneOf("hello"), equalTo(true));

        // 字符出现的次数
        assertThat(CharMatcher.is('a').countIn("java"), equalTo(2));
        // 找到匹配字符串的索引
        assertThat(CharMatcher.is('a').indexIn("Guava"), equalTo(2));
        // 从自定位置开始查找字符所在字符串中的索引
        assertThat(CharMatcher.is('o').indexIn("hello", 2), equalTo(4));

        // 这个类方法挺多的,这里就不再一一演示了...
    }

}

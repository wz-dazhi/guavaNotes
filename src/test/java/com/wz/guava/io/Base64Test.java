package com.wz.guava.io;

import com.google.common.io.BaseEncoding;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @projectName: Guava
 * @package: com.wz.guava.io
 * @className: Base64Test
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/7/24 12:02
 **/
public class Base64Test {

    /**
     * 编码
     */
    @Test
    public void testBase64Encode() {
        BaseEncoding baseEncoding = BaseEncoding.base64();
        String encodeResult = baseEncoding.encode("hello".getBytes());
        System.out.println(encodeResult);
    }

    /**
     * 解码
     */
    @Test
    public void testBase64Decode() {
        BaseEncoding baseEncoding = BaseEncoding.base64();
        byte[] bytes = baseEncoding.decode("aGVsbG8=");
        System.out.println(new String(bytes));
    }

    /**
     * 测试✋写Base64算法编码
     */
    @Test
    public void testMyBase64Encode() {
        String myEncodeResult = Base64.encode("hello");

        BaseEncoding baseEncoding = BaseEncoding.base64();
        String encodeResult = baseEncoding.encode("hello".getBytes());

        assertThat(myEncodeResult, equalTo(encodeResult));

        assertThat(Base64.encode("wangzhi"), equalTo(baseEncoding.encode("wangzhi".getBytes())));
        assertThat(Base64.encode("JAVA"), equalTo(baseEncoding.encode("JAVA".getBytes())));
    }

    /**
     * 测试✋写Base64算法解码
     */
    @Test
    public void testMyBase64Decode() {
        String decodeResult = Base64.decode("aGVsbG8=");
        assertThat(decodeResult, equalTo("hello"));
    }
}

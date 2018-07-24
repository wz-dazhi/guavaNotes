package com.wz.guava.io;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @projectName: Guava
 * @package: com.wz.guava.io
 * @className: ByteSourceTest
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/7/23 18:51
 **/
public class ByteSourceTest {

    private final String filePath = "/Users/wangzhi/work/project/Guava/guava_programming/src/test/resources/io/1.png";

    /**
     * 读取字节文件
     *
     * @throws IOException
     */
    @Test
    public void testByteSource() throws IOException {
        File sourceFile = new File(filePath);
        ByteSource byteSource = Files.asByteSource(sourceFile);
        byte[] readResult = byteSource.read();
        assertThat(readResult, equalTo(Files.toByteArray(sourceFile)));
    }
}

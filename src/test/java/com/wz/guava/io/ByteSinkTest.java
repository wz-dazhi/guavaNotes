package com.wz.guava.io;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @projectName: Guava
 * @package: com.wz.guava.io
 * @className: ByteSinkTest
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/7/23 19:01
 **/
public class ByteSinkTest {

    private final String filePath = "/Users/wangzhi/work/project/Guava/guava_programming/src/test/resources/io/1.png";

    private final String targetPath = "/Users/wangzhi/work/project/Guava/guava_programming/src/test/resources/io/target.png";

    /**
     * 将二进制数据写入字节文件中
     *
     * @throws IOException
     */
    @Test
    public void testByteSink() throws IOException {
        File targetPath = new File(this.targetPath);
        targetPath.deleteOnExit();
        ByteSink byteSink = Files.asByteSink(targetPath);
        byte[] bytes = {0x01, 0x02};
        byteSink.write(bytes);

        // 读取字节文件, 对比数据
        byte[] bytes1 = Files.toByteArray(targetPath);
        assertThat(bytes1, is(bytes));
    }

    /**
     * copy 字节文件
     */
    @Test
    public void testCopyByteFile() throws IOException {
        File sourceFile = new File(filePath);
        File targetFile = new File(targetPath);
        targetFile.deleteOnExit();
        ByteSource byteSource = Files.asByteSource(sourceFile);
        byteSource.copyTo(Files.asByteSink(targetFile));

        HashCode hash1 = Files.asByteSource(sourceFile).hash(Hashing.sha256());
        HashCode hash2 = Files.asByteSource(targetFile).hash(Hashing.sha256());
        assertThat(hash1, equalTo(hash2));
    }

}

package com.wz.guava.io;

import com.google.common.io.Closer;
import org.junit.Test;

import java.io.*;

/**
 * @projectName: Guava
 * @package: com.wz.guava.io
 * @className: CloserTest
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/7/23 22:11
 **/
public class CloserTest {

    /**
     * Closer 用法
     *
     * @throws IOException
     */
    @Test
    public void testCloser() throws IOException {
        Closer closer = Closer.create();
        try {
            InputStream inputStream = closer.register(new FileInputStream(new File("")));
            OutputStream outputStream = closer.register(new FileOutputStream(new File("")));
            // do stuff
        } catch (Throwable e) {
            throw closer.rethrow(e);
        } finally {
            closer.close();
        }
    }

    /**
     * try catch执行顺序
     */
    @Test(expected = RuntimeException.class)
    public void testTryCatchFinally() {
        try {
            System.out.println(">>> try...");
            throw new RuntimeException("1");
        } catch (Exception e) {
            System.out.println(">>> catch...");
            throw new RuntimeException("2");
        } finally {
            System.out.println(">>> finally...");
        }
    }

    /**
     * 模拟Closer捕获异常
     * 当catch到异常的时候, 有可能finally中也会抛出异常(比如当close的时候),
     * finally抛出的异常会压制catch的异常,导致只能看到finally抛出的异常.
     * 所以使用定义一个Throwable,将close的异常添加到异常栈中(addSuppressed(e)方法源码是将所有异常添加List<Throwable>中)
     */
    @Test
    public void testTryCatch() {
        Throwable throwable = null;
        try {
            System.out.println(">>> try1...");
            throw new RuntimeException("try1");
        } catch (Exception e) {
            System.out.println(">>> catch1...");
            //throw new RuntimeException("catch1");
            throwable = e;
            throw e;
        } finally {
            try {
                // close();
                System.out.println(">>> try2...");
                throw new RuntimeException("try2");
            } catch (Exception e) {
                System.out.println(">>> catch2...");
                throwable.addSuppressed(e);
            }
        }
    }
}

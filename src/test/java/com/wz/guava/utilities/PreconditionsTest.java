package com.wz.guava.utilities;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @projectName: Guava
 * @package: com.wz.guava.utilities
 * @className: PreconditionsTest
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/7/5 22:32
 **/
public class PreconditionsTest {

    /**
     * 检查不能为null
     */
    @Test(expected = NullPointerException.class)
    public void testCheckNotNull() {
        List<String> list = null;
        Preconditions.checkNotNull(list);
    }

    /**
     * 检查不能为null,并自定义错误信息
     */
    @Test
    public void testCheckNotNullWithMessage() {
        List<String> list = null;
        try {
            Preconditions.checkNotNull(list, "the list not null");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(e instanceof NullPointerException);
            assertThat(e.getMessage(), equalTo("the list not null"));
        }
    }

    /**
     * 检查不能为null,自定义格式化异常信息
     */
    @Test
    public void testCheckNotNullWithFormatMessage() {
        List<String> list = null;
        try {
            Preconditions.checkNotNull(list, "the list -> %s %s", "not", "null");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(e instanceof NullPointerException);
            assertThat(e.getMessage(), equalTo("the list -> not null"));
        }
    }

    /**
     * 使用Java8中Objects判断不能为null
     */
    @Test(expected = NullPointerException.class)
    public void testObjectsNotNull(){
        String string = null;
        Objects.requireNonNull(string);
    }

    /**
     * 检查状态
     */
    @Test
    public void testCheckState() {
        final String state = "A";
        try {
            Preconditions.checkState("B".equals(state), "the state is illegal..");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(e instanceof IllegalStateException);
            assertThat(e.getMessage(), equalTo("the state is illegal.."));
        }
    }

    /**
     * 检查参数
     */
    @Test
    public void testCheckArguments() {
        String str = "hello";
        try {
            Preconditions.checkArgument("world".equals(str));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    /**
     * 检查参数,并自定义异常消息
     */
    @Test
    public void testCheckArgumentsWithMessage() {
        String str = "hello";
        try {
            Preconditions.checkArgument("world".equals(str), "the str is illegal..");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(e instanceof IllegalArgumentException);
            assertThat(e.getMessage(), equalTo("the str is illegal.."));
        }
    }

    /**
     * 检查集合下标越界
     */
    @Test
    public void testCheckListIsIndexOutOf() {
        List<Object> list = ImmutableList.of();
        try {
            Preconditions.checkElementIndex(10, list.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(e instanceof IndexOutOfBoundsException);
        }
    }

    /**
     * 使用断言的方式检查
     */
    @Test(expected = AssertionError.class)
    public void testAssert() {
        List<Object> list = null;
        assert list != null;
    }

    /**
     * 断言判断,自定义异常信息
     */
    @Test
    public void testAssertWithMessage() {
        List list = null;
        try {
            assert list != null : "the list not null";
        } catch (Error error) {
            System.out.println(error.getMessage());
            assertTrue(error instanceof AssertionError);
            assertThat(error.getMessage(), equalTo("the list not null"));
        }
    }

}

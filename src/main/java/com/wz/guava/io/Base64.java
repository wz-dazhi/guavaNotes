package com.wz.guava.io;

import com.google.common.base.Preconditions;

/**
 * @projectName: Guava
 * @package: com.wz.guava.io
 * @className: Base64
 * @description:
 * @author: Zhi Wang
 * @createDate: 2018/7/24 12:07
 **/
public final class Base64 {

    /**
     * 索引表
     */
    private static final String ENCODE_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    /**
     * 编码字典
     */
    private static final char[] ENCODE_DICT = ENCODE_STRING.toCharArray();


    private Base64() {
    }

    /**
     * 编码
     *
     * @param text
     * @return
     */
    public static String encode(String text) {
        final StringBuilder result = new StringBuilder();
        Preconditions.checkNotNull(text);
        // 将文本转换成二进制字符串
        String binaryStr = toBinary(text);
        // 每6位进行分割,不够6位追加0
        int delta = 6 - binaryStr.length() % 6;
        if (delta != 6) {

            for (int i = 0; i < delta; i++) {
                binaryStr += "0";
            }

            // 遍历二进制字符串
            for (int index = 0; index < binaryStr.length() / 6; index++) {
                // 取出6位字符串
                int begin = index * 6;
                String substr = binaryStr.substring(begin, begin + 6);
                // 将二进制字符串转换成十进制
                Integer indexChar = Integer.valueOf(substr, 2);
                // 从字典中取出对应的字符
                char encodeChar = ENCODE_DICT[indexChar];
                result.append(encodeChar);
            }
            // 追加的0, 每两个0追加为一个=号
            for (int i = 0; i < delta / 2; i++) {
                result.append("=");
            }
        }
        return result.toString();
    }

    /**
     * 将字符串转换成二进制字符串
     *
     * @param source
     * @return
     */
    private static String toBinary(String source) {
        final StringBuilder binaryResult = new StringBuilder();
        // 遍历字符串
        for (int index = 0; index < source.length(); index++) {
            final StringBuilder binary = new StringBuilder();
            char charAt = source.charAt(index);
            // 转换成二进制
            String binaryStr = Integer.toBinaryString(charAt);
            // 追加补全8bit
            int delta = 8 - binaryStr.length();
            for (int i = 0; i < delta; i++) {
                binary.append("0");
            }
            binary.append(binaryStr);
            binaryResult.append(binary);
        }
        return binaryResult.toString();
    }

    /**
     * 解码
     *
     * @param encrypt
     * @return
     */
    public static String decode(String encrypt) {
        Preconditions.checkNotNull(encrypt);
        final StringBuilder result = new StringBuilder();
        String tmp = encrypt;
        // 判断是否包含=号, 去掉=号
        int index = tmp.indexOf("=");
        if (index > 0) {
            tmp = tmp.substring(0, index);
        }
        // 拿到转换后的二进制字符串
        String binaryStr = toBase64Binary(tmp);
        // 每8位循环一次
        for (int i = 0; i < binaryStr.length() / 8; i++) {
            int begin = i * 8;
            // 拿到8位二进制字符串
            String substr = binaryStr.substring(begin, begin + 8);
            // 将二进制转换成十进制
            Integer value = Integer.valueOf(substr, 2);
            // 拿到十进制对应的ASCII
            char[] chars = Character.toChars(value);
            char c = chars[0];
            result.append(c);
        }

        return result.toString();
    }

    /**
     * 转换Base64Binary
     * @param source
     * @return
     */
    private static String toBase64Binary(String source) {
        final StringBuilder binaryResult = new StringBuilder();
        // 循环编码字符串
        for (int i = 0; i < source.length(); i++) {
            StringBuilder builder = new StringBuilder();
            // 根据下标拿到编码字符
            char c = source.charAt(i);
            // 字符所在索引中的下标
            int index = ENCODE_STRING.indexOf(c);
            // 将下标转换成二进制字符串
            String binaryStr = Integer.toBinaryString(index);
            // 判断是否够6位, 不够往前补0
            int delta = 6 - binaryStr.length();
            for (int j = 0; j < delta; j++) {
                builder.append("0");
            }
            builder.append(binaryStr);
            binaryResult.append(builder);
        }
        return binaryResult.toString();
    }

    public static void main(String[] args) {
        /*System.out.println(encode("hello"));
        System.out.println(encode("java"));
        System.out.println(encode("scala"));
        System.out.println("======================");

        System.out.println(decode("aGVsbG8="));
        System.out.println(decode("amF2YQ=="));
        System.out.println(decode("c2NhbGE="));*/
        System.out.println(decode("YQ=="));
    }
}

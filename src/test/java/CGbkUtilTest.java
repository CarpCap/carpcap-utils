import com.carpcap.utils.charset.CGbkUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * CGbkUtil 单元测试
 * @author CarpCap
 */
public class CGbkUtilTest {

    /**
     * 测试正常中文字符串编码后再解码，结果应与原字符串一致
     */
    @Test
    public void testGbkBytesRoundTrip() {
        String original = "你好，世界";
        System.out.println("===== testGbkBytesRoundTrip =====");
        System.out.println("原始字符串: " + original);

        byte[] bytes = CGbkUtil.toGbkBytes(original);
        System.out.println("编码后字节数组: " + Arrays.toString(bytes));
        System.out.println("字节数组长度: " + bytes.length);

        String result = CGbkUtil.toString(bytes);
        System.out.println("解码回字符串: " + result);

        Assert.assertEquals(original, result);
        System.out.println("断言通过：解码结果与原字符串一致\n");
    }

    /**
     * 测试编码后的字节数组末尾应带有结束符0
     */
    @Test
    public void testGbkBytesEndsWithZeroTerminator() {
        String original = "测试";
        System.out.println("===== testGbkBytesEndsWithZeroTerminator =====");
        System.out.println("原始字符串: " + original);

        byte[] bytes = CGbkUtil.toGbkBytes(original);
        System.out.println("编码后字节数组: " + Arrays.toString(bytes));
        System.out.println("最后一个字节: " + bytes[bytes.length - 1]);

        Assert.assertEquals(0, bytes[bytes.length - 1]);
        System.out.println("断言通过：末尾字节为结束符0\n");
    }

    /**
     * 测试空字符串编码后应只剩一个结束符字节
     */
    @Test
    public void testGbkBytesWithEmptyString() {
        System.out.println("===== testGbkBytesWithEmptyString =====");
        System.out.println("原始字符串: \"\"（空字符串）");

        byte[] bytes = CGbkUtil.toGbkBytes("");
        System.out.println("编码后字节数组: " + Arrays.toString(bytes));
        System.out.println("字节数组长度: " + bytes.length);

        Assert.assertEquals(1, bytes.length);
        Assert.assertEquals(0, bytes[0]);
        System.out.println("断言通过：空字符串编码后只剩1个字节且值为0\n");
    }

    /**
     * 测试传入null时不应抛异常，应返回安全的默认字节数组
     */
    @Test
    public void testGbkBytesWithNullInput() {
        System.out.println("===== testGbkBytesWithNullInput =====");
        System.out.println("输入: null");

        byte[] bytes = CGbkUtil.toGbkBytes(null);
        System.out.println("返回结果: " + Arrays.toString(bytes));

        Assert.assertNotNull(bytes);
        System.out.println("断言通过：null输入未抛异常，返回了非空数组\n");
    }

    /**
     * 测试字节数组为null时转字符串不应抛异常，应返回空字符串
     */
    @Test
    public void testToStringWithNullInput() {
        System.out.println("===== testToStringWithNullInput =====");
        System.out.println("输入: null");

        String result = CGbkUtil.toString(null);
        System.out.println("返回结果: \"" + result + "\"");

        Assert.assertNotNull(result);
        Assert.assertEquals("", result);
        System.out.println("断言通过：null输入返回空字符串，未抛异常\n");
    }

    /**
     * 测试结束符0之后的多余数据应被忽略，只解析结束符之前的有效内容
     */
    @Test
    public void testToStringIgnoresTrailingGarbageAfterZero() throws Exception {
        String original = "ABC";
        System.out.println("===== testToStringIgnoresTrailingGarbageAfterZero =====");
        System.out.println("原始内容: " + original);

        byte[] contentBytes = original.getBytes("GBK");
        byte[] withGarbage = new byte[contentBytes.length + 1 + 3];
        System.arraycopy(contentBytes, 0, withGarbage, 0, contentBytes.length);
        withGarbage[contentBytes.length] = 0;
        withGarbage[contentBytes.length + 1] = 1;
        withGarbage[contentBytes.length + 2] = 2;
        withGarbage[contentBytes.length + 3] = 3;

        System.out.println("构造的字节数组(含结束符+垃圾数据): " + Arrays.toString(withGarbage));

        String result = CGbkUtil.toString(withGarbage);
        System.out.println("解析结果: " + result);

        Assert.assertEquals(original, result);
        System.out.println("断言通过：结束符之后的垃圾数据被正确忽略\n");
    }
}
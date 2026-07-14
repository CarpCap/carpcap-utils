import com.carpcap.utils.charset.CUnicodeUtil;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * CUnicodeUtil 单元测试
 * @author CarpCap
 */
public class CUnicodeUtilTest {

    /**
     * 测试基础中文字符串按UTF-16BE字节数组解析，结果应与原字符串一致
     */
    @Test
    public void testUnicodeStringWithChineseText() throws Exception {
        String original = "你好";
        System.out.println("===== testUnicodeStringWithChineseText =====");
        System.out.println("原始字符串: " + original);

        byte[] bytes = original.getBytes("UTF-16BE");
        System.out.println("UTF-16BE字节数组: " + Arrays.toString(bytes));

        String result = CUnicodeUtil.toUnicodeString(bytes);
        System.out.println("解析结果: " + result);

        Assert.assertEquals(original, result);
        System.out.println("断言通过：解析结果与原字符串一致\n");
    }

    /**
     * 测试基础英文字符串按UTF-16BE字节数组解析，结果应与原字符串一致
     */
    @Test
    public void testUnicodeStringWithEnglishText() throws Exception {
        String original = "Hello";
        System.out.println("===== testUnicodeStringWithEnglishText =====");
        System.out.println("原始字符串: " + original);

        byte[] bytes = original.getBytes("UTF-16BE");
        System.out.println("UTF-16BE字节数组: " + Arrays.toString(bytes));

        String result = CUnicodeUtil.toUnicodeString(bytes);
        System.out.println("解析结果: " + result);

        Assert.assertEquals(original, result);
        System.out.println("断言通过：解析结果与原字符串一致\n");
    }

    /**
     * 测试空字节数组应返回空字符串
     */
    @Test
    public void testUnicodeStringWithEmptyBytes() {
        System.out.println("===== testUnicodeStringWithEmptyBytes =====");
        byte[] bytes = new byte[0];
        System.out.println("输入字节数组: " + Arrays.toString(bytes));

        String result = CUnicodeUtil.toUnicodeString(bytes);
        System.out.println("解析结果: \"" + result + "\"");

        Assert.assertEquals("", result);
        System.out.println("断言通过：空数组返回空字符串\n");
    }

    /**
     * 测试传入null应安全返回null，不应抛出未处理异常
     */
    @Test
    public void testUnicodeStringWithNullInput() {
        System.out.println("===== testUnicodeStringWithNullInput =====");
        System.out.println("输入: null");

        String result = CUnicodeUtil.toUnicodeString(null);
        System.out.println("解析结果: " + result);

        Assert.assertNull(result);
        System.out.println("断言通过：null输入被安全处理，返回null\n");
    }

    /**
     * 测试奇数长度字节数组应主动抛出IllegalArgumentException，而不是被静默吞掉
     */
    @Test
    public void testUnicodeStringWithOddLengthBytesShouldThrow() {
        System.out.println("===== testUnicodeStringWithOddLengthBytesShouldThrow =====");
        byte[] bytes = new byte[]{0x00, 0x41, 0x00}; // 长度为3，奇数
        System.out.println("输入字节数组(奇数长度): " + Arrays.toString(bytes));
        System.out.println("说明: 当前实现已改为提前校验长度，奇数长度会主动抛出 IllegalArgumentException");

        try {
            CUnicodeUtil.toUnicodeString(bytes);
            Assert.fail("期望抛出 IllegalArgumentException，但没有抛出");
        } catch (IllegalArgumentException e) {
            System.out.println("捕获到预期异常: " + e.getMessage());
            Assert.assertEquals("bytes长度必须为偶数", e.getMessage());
            System.out.println("断言通过：奇数长度正确抛出了 IllegalArgumentException\n");
        }
    }

    /**
     * 测试中英文混合字符串解析结果应与原字符串一致
     */
    @Test
    public void testUnicodeStringWithMixedChineseAndEnglish() {
        String original = "Hi你好123";
        System.out.println("===== testUnicodeStringWithMixedChineseAndEnglish =====");
        System.out.println("原始字符串: " + original);

        byte[] bytes = original.getBytes(StandardCharsets.UTF_16BE);
        System.out.println("UTF-16BE字节数组: " + Arrays.toString(bytes));

        String result = CUnicodeUtil.toUnicodeString(bytes);
        System.out.println("解析结果: " + result);

        Assert.assertEquals(original, result);
        System.out.println("断言通过：中英文混合字符串解析一致\n");
    }
}
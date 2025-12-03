package com.carpcap.utils.charset;

/**
 * @author CarpCap
 * @since 2025/12/3 18:06
 */
public class CUnicodeUtil {

	 /**
     * 每个字节转成16进制,方法1
     *
     * @param bytes
     */
    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            sb.append(Character.forDigit((aByte & 240) >> 4, 16));
            sb.append(Character.forDigit(aByte & 15, 16));
        }
        return sb.toString();
    }

    private static String unicodeToCn(String unicode) {
        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
            returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return returnStr;
    }

    /**
     * 处理 MsgContent
     * 因为bytes中每2个元素分别对应了高4位和低4位，转成16进制字符 然后将这些字符转成String就是内容了
     *
     * @param bytes
     * @author CarpCap
     * @since 2022/3/28 14:26
     */
    public static String toUnicodeString(byte[] bytes) {
        try {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < bytes.length; i += 2) {
                //unicode 16进制字符
                String unicode = "\\u" + toHex(new byte[]{bytes[i], bytes[i + 1]});
                //将字符转成String
                String s = unicodeToCn(unicode);
                builder.append(s);
            }

            return builder.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
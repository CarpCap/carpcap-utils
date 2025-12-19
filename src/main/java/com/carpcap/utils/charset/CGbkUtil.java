package com.carpcap.utils.charset;

import java.io.UnsupportedEncodingException;

/**
 * @author CarpCap
 * @since 2025/12/3 18:51
 */
public class CGbkUtil {

    //String转byte数组  GBK
    public static byte[] toGbkBytes(String data) {
        int i = 0;
        byte[] s = null;

        try {
            s = data.getBytes("GBK");
            i = s.length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] temp = new byte[i + 1];
        assert s != null;
        System.arraycopy(s, 0, temp, 0, i);
        temp[i] = 0;

        return temp;
    }

    //byte数组 转 String  GBK
    public static String toString(byte[] data) {
        int i;
        String s = "";

        for (i = 0; i < data.length; ++i) {
            if (data[i] == 0) {
                break;
            }
        }

        byte[] temp = new byte[i];
        System.arraycopy(data, 0, temp, 0, temp.length);

        try {
            s = new String(temp, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return s;
    }
}
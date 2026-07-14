package com.carpcap.utils.charset;

import java.io.UnsupportedEncodingException;

/**
 * @author CarpCap
 * @since 2025/12/3 18:51
 */
public class CGbkUtil {

    public static byte[] toGbkBytes(String data) {
        if (data == null) {
            return new byte[]{0};
        }

        byte[] s;
        try {
            s = data.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new byte[]{0};
        }

        byte[] temp = new byte[s.length + 1];
        System.arraycopy(s, 0, temp, 0, s.length);
        temp[s.length] = 0;
        return temp;
    }

    public static String toString(byte[] data) {
        if (data == null) {
            return "";
        }

        int i;
        for (i = 0; i < data.length; ++i) {
            if (data[i] == 0) {
                break;
            }
        }

        byte[] temp = new byte[i];
        System.arraycopy(data, 0, temp, 0, temp.length);

        try {
            return new String(temp, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
package com.github.ccloud.common.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil {

    public static void close(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException ignore) {
            }
        }
    }

    public static void close(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException ignore) {
            }
        }
    }
}

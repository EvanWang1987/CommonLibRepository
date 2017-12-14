package com.github.evan.common_utils.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Evan on 2017/12/14.
 */

public class CommandLineUtil {

    /**
     * 执行命令 & 返回结果
     *
     * @param cmd
     * @return
     */
    public static String execCmd(String cmd) {
        BufferedReader reader = null;
        String content = "";
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuffer output = new StringBuffer();
            int read;
            char[] buffer = new char[4096];
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            content = output.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }


    public static InputStream execCmdFromInputStream(String cmd) {
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            return process.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

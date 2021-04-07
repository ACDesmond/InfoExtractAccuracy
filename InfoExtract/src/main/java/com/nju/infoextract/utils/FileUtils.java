package com.nju.infoextract.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

    public static String getContentByFile(MultipartFile file) throws IOException {
        // 创建StringBuilder缓冲
        StringBuilder sb = new StringBuilder();
        // 创建输入流
        InputStream inputStream = file.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        // 填充缓冲
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
            sb.append("\r\n");
        }

        //返回
        return sb.toString();
    }
}

package com.nju.infoextract.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.lang.model.SourceVersion;
import javax.servlet.http.HttpServletRequest;
import javax.tools.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Controller
public class TestController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @GetMapping("/")
    public String test() {
        return "test";
    }
    
    @PostMapping("/posts")
    @ResponseBody
    public String posts(HttpServletRequest request) throws IOException, InterruptedException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {


        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);

            System.out.println(file.getOriginalFilename());
            System.out.println(file.getBytes().length);

            String fileName = file.getOriginalFilename();
            String filePath = "C:\\Users\\piao\\Desktop\\prz\\temp\\";
            File destination = new File(filePath + fileName);
            file.transferTo(destination);


            Runtime run = Runtime.getRuntime();
            Process process = run.exec("javac " + filePath + fileName);
            InputStream in = process.getInputStream();
            while (in.read() != -1) {
                System.out.println(in.read());
            }
            in.close();
            process.waitFor();

            final String inclass = filePath + "Test.class";
            ClassLoader classLoader = new ClassLoader() {
                @Override
                protected Class<?> findClass(String name) throws ClassNotFoundException {
                    byte[] classData = null;
                    try {
                        FileInputStream in = new FileInputStream(new File(inclass));
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int size = 0;
                        while ((size = in.read(buffer)) != -1) {
                            out.write(buffer, 0, size);
                        }
                        classData = out.toByteArray();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Class log = defineClass(name, classData, 0, classData.length);
                    return log;
                }
            };

            Class<?> Log = classLoader.loadClass("Test");
            Method method = Log.getDeclaredMethod("test", String.class);
            Object object = Log.newInstance();
            Object ret = method.invoke(object, "2");
            System.out.println(ret);





        }



        return "test";
    }

}

package com.nju.infoextract.common.compiler;

import com.nju.infoextract.utils.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.Element;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: songqiang
 * @since: 2020/12/4
 */
public class CodeHelper {
    //byte[] to Byte[]
    public static Byte[] getByteCode(String fileName, String code){
        Map<String, byte[]> byteCode = DynamicLoader.compile(fileName, code);
        byte[] classByte = byteCode.get(fileName.split("\\.")[0]);
        return ArrayUtils.toObject(classByte);
    }

    public static Byte[] getByteCode(String fileName, MultipartFile file) throws IOException {
        return getByteCode(fileName, FileUtils.getContentByFile(file));
    }

    //Byte[] to byte[]
    public static Map<String, byte[]> getByteMap(Byte[] byteObj, String fileName){
        byte[] tmpObj = ArrayUtils.toPrimitive(byteObj);
        Map<String, byte[]> res = new HashMap<>();
        res.put(fileName.split("\\.")[0], tmpObj);
        return res;
    }

    /**
     * <p>
     *     通过反射调用子程序的extract方法
     * </p>
     *
     * @param text
     * @param wjm
     * @param byteObj
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static Element invokeExtract(String text, String wjm, Byte[] byteObj) {
        try {
            Map<String, byte[]> byteCode = CodeHelper.getByteMap(byteObj, wjm);
            DynamicLoader.MemoryClassLoader memoryClassLoader = new DynamicLoader.MemoryClassLoader(byteCode);

            Class clazz = memoryClassLoader.loadClass(wjm.split("\\.")[0]);
            Object object = clazz.newInstance();

            Method method = clazz.getMethod("extract", XWPFDocument.class, String.class);

            Element res = (Element) method.invoke(object, null, text);
            return res;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}


import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 原告/上诉人的具体诉讼请求
 */
public class YGSSQQ {

    public Element extract(XWPFDocument doc, String text) {

        Element YGSSQQ = DocumentHelper.createElement("YGSSQQ");
        YGSSQQ.addAttribute("nameCN", "原告诉讼请求");

        String ssqq = "无此信息";
        Pattern p = Pattern.compile("[法|院|判|决|审|诉]");
        String[] list = text.split("。");
        for (String qwValues : list) {
            Matcher m = p.matcher(qwValues);
            if (m.find()) {
                String s = "";
                if (qwValues.contains("恳请")) {
                    s = qwValues.substring(qwValues.indexOf("恳请"));
                } else if (qwValues.contains("请求")) {
                    s = qwValues.substring(qwValues.indexOf("请求"));

                } else if (qwValues.contains("要求")) {
                    s = qwValues.substring(qwValues.indexOf("要求"));
                } else if (qwValues.contains("判令")) {
                    s = qwValues.substring(qwValues.indexOf("判令"));
                } else if (qwValues.contains("请")) {
                    s = qwValues.substring(qwValues.indexOf("请"));
                }
                if (s.length() > 5) {
                    ssqq = s;
                }
            }

        }
        YGSSQQ.addAttribute("value", ssqq);
        return YGSSQQ;
    }
}

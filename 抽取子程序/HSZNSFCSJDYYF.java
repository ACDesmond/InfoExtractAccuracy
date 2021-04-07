
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断婚生子女是否产生了较多医疗费
 */
public class HSZNSFCSJDYYF {

    public Element extract(XWPFDocument doc, String text) {

        Element HSZNSFCSJDYYF = DocumentHelper.createElement("HSZNSFCSJDYYF");
        HSZNSFCSJDYYF.addAttribute("nameCN", "婚生子女是否产生了较多医疗费");

        String[] allarray = text.split("。|；");
        String nf1 = "无此信息";
        Pattern p1 = Pattern.compile("(儿子)|(女儿)|(孩子)|(男孩)|(女孩)|[(婚生)|(双方(之)?)|(原、被告之)][子|女|(子女)|儿]");
        Pattern p2 = Pattern.compile("[医|药|疗|治]");
        Pattern p3 = Pattern.compile("[费|钱|金|资]");
        Pattern p4 = Pattern.compile("\\d+．?\\d*[元|(人民币)]");
        int flag1 = 0;
        for (String s : allarray) {
            Matcher m1 = p1.matcher(s);
            Matcher m2 = p2.matcher(s);
            Matcher m3 = p3.matcher(s);
            Matcher m4 = p4.matcher(s);
            if (m1.find() && m2.find() && m3.find() && m4.find()) {
                String[] alls = s.split("，");
                for (String str : alls) {
                    Matcher m22 = p2.matcher(str);
                    Matcher m23 = p3.matcher(str);
                    Matcher m24 = p4.matcher(str);
                    if (m22.find() && m23.find() && m24.find()) {
                        nf1 = m4.group(0);
                        flag1 = 1;
                        break;
                    }
                }

                //         }
                if (flag1 == 1) {
                    break;
                }

            }
        }
        HSZNSFCSJDYYF.addAttribute("value", nf1);
        return HSZNSFCSJDYYF;
    }
}

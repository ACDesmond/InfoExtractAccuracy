
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断婚生子女目前的年龄，是否满十周岁
 */
public class HSZNSFMSZS {

    public Element extract(XWPFDocument doc, String text) {

        Element HSZNSFMSZS = DocumentHelper.createElement("HSZNSFMSZS");
        HSZNSFMSZS.addAttribute("nameCN", "婚生子女的年龄是否满十周岁");


        String[] allarray = text.split("。");
        String ifcz = "否";

        String regex1 = "[(年满)|(现年)|(已满)|(年逾)][十|(10)]周?岁";
        String regex2 = "[1-9]+[1-9]+周?岁";
        String regex3 = "[十|(10)]周?岁以上";
        Pattern p1 = Pattern.compile(regex1);
        Pattern p2 = Pattern.compile(regex2);
        Pattern p3 = Pattern.compile(regex3);
        List<Pattern> patternlist = new ArrayList<Pattern>();
        patternlist.add(p1);
        patternlist.add(p2);
        patternlist.add(p3);
        a:
        for (String s : allarray) {
            if (s.contains("子") || s.contains("女") || s.contains("孩")) {
                for (Pattern p : patternlist) {
                    Matcher m = p.matcher(s);
                    if (m.find()) {
                        ifcz = "是";
                        break a;
                    }
                }
            }
        }
        HSZNSFMSZS.addAttribute("value", ifcz);
        return HSZNSFMSZS;
    }
}

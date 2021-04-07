import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 婚生子女的年龄是否在2周岁以内
 */
public class HSZNSFZLZSYN {

    public Element extract(XWPFDocument doc, String text) {

        Element HSZNSFZLZSYN = DocumentHelper.createElement("HSZNSFZLZSYN");
        HSZNSFZLZSYN.addAttribute("nameCN", "婚生子女是否在2周岁以内");


        String[] allarray = text.split("。");
        String ifcz = "否";
        String regex1 = "[不|未|刚]+满[2|两]周?岁";
        String regex2 = "[2|两]周?岁以[下|内]";
        String regex3 = "仅[2|两]周?岁";
        Pattern p1 = Pattern.compile(regex1);
        Pattern p2 = Pattern.compile(regex2);
        Pattern p3 = Pattern.compile(regex3);
        List<Pattern> patternlist = new ArrayList<Pattern>();
        patternlist.add(p1);
        patternlist.add(p2);
        patternlist.add(p3);
        a:
        for (String s : allarray) {
            for (Pattern p : patternlist) {
                Matcher m = p.matcher(s);
                if (m.find()) {
                    ifcz = "是";
                    break a;
                }
            }
        }
        HSZNSFZLZSYN.addAttribute("value", ifcz);
        return HSZNSFZLZSYN;
    }
}

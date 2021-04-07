
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
 * 被告的具体抗辩理由
 */
public class BGKBLY {

    public Element extract(XWPFDocument doc, String text) {

        Element BGKBLY = DocumentHelper.createElement("BGKBLY");
        BGKBLY.addAttribute("nameCN", "被告抗辩理由");

        String ssqq = "无此信息";
        String s = "";
        String[] qw = text.split("。");
        for (String line : qw) {
            if (line.contains("辩称") || line.contains("答辩")) {
                s = line.substring(line.indexOf("辩"));
            }
            if (s.length() > 5) {
                ssqq = s;
                break;
            }
        }

        BGKBLY.addAttribute("value", ssqq);
        return BGKBLY;
    }

}

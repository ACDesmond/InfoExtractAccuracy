import main.KeyCorp;
import main.getBNodeString;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 是否要求增加抚养费
 */
public class SFYQZJFYF {

    public Element extract(XWPFDocument doc, String text) {

        Element SFYQZJFYF = DocumentHelper.createElement("SFYQZJFYF");
        SFYQZJFYF.addAttribute("nameCN", "是否要求增加抚养费");

        String[] strArray = text.split("。");
        String ifcz = "否";
        String regex = "\\d+，?万?．?\\d*元";
        for (String s : strArray) {
            if ((s.contains("请求") || s.contains("要求")) && (s.contains("抚养费") || s.contains("生活费")) && s.contains("增")) {
                String news = KeyCorp.removeQSDH(s, regex);
                String[] newsArray = news.split("，|；");
                for (String newsp : newsArray) {
                    if ((newsp.contains("抚养费") || newsp.contains("生活费")) && newsp.contains("增")) {
                        ifcz = "是";
                    }
                }

            }
        }
        SFYQZJFYF.addAttribute("value", ifcz);
        return SFYQZJFYF;
    }
}

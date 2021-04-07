
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 原告住房状况
 */
public class YGZFQK {

    public Element extract(XWPFDocument doc, String text) {
        Element YGZFQK = DocumentHelper.createElement("YGZFQK");
        YGZFQK.addAttribute("nameCN", "原告住房状况");

        String yg = "原告";
        String bg = "被告";
        String[] allsj = text.split("。|；|，");
        String zf = "无此信息";
        for (String s : allsj) {
            if (s.contains(yg) && (s.contains("住") || s.contains("房")) && !s.contains("医院") && !s.contains("住院")
                    && !s.contains("无") && !s.contains("没有")) {
                String trues = "";
                if (s.contains(bg)) {
                    String news = s.substring(s.indexOf(yg));

                    int end = 0;
                    if (news.contains("住")) {
                        end = news.indexOf("住");
                    }
                    if (news.contains("房")) {
                        end = news.indexOf("房");
                    }
                    if (end != 0) {
                        if (news.contains(bg) && news.indexOf(bg) < end) {
                            if (news.contains(yg)) {
                                if (news.indexOf(yg) > news.indexOf(bg) && news.indexOf(yg) < end) {//属于原告
                                    trues += news;
                                }
                            } else {
                                ;//属于被告
                            }
                        } else {//属于原告
                            trues += news;
                        }
                    }
                } else {
                    trues = s;
                }
                if (!trues.equals("")) {
                    if (trues.contains("租")) {
                        zf = "租赁房";
                    } else if (trues.contains("父") || trues.contains("母")) {
                        zf = "与父母同同住";
                    } else {
                        zf = "自住房";
                    }

                }
            }
        }

        YGZFQK.addAttribute("value", zf);
        return YGZFQK;
    }
}

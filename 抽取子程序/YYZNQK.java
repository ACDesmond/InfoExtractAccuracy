
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 育有子女情况
 */
public class YYZNQK {

    public Element extract(XWPFDocument doc, String text) {

        Element YYZNQK = DocumentHelper.createElement("YYZNQK");
        YYZNQK.addAttribute("nameCN", "育有子女情况");

        String[] allarray = text.split("。");
        int flag1 = 0;
        String allStr = "";
        for (String qw : allarray) {
            String[] qwlist = qw.split("，");
            for (String qwlistsp : qwlist) {
                if ((qwlistsp.contains("婚生") || qwlistsp.contains("原、被告") || qwlistsp.contains("原告之") || qwlistsp.contains("被告之") || qwlistsp.contains("双方") || qwlistsp.contains("生"))
                        && (qwlistsp.contains("子") || qwlistsp.contains("女") || qwlistsp.contains("男") || qwlistsp.contains("孩"))) {
                    flag1 = 1;
                    allStr += qwlistsp + ";";

                } else if (qwlistsp.contains("现")) {
                    if (flag1 == 1) {
                        allStr += qwlistsp + ";";
                        flag1 = 0;
                    }
                }
            }

        }
        YYZNQK.addAttribute("value", allStr);
        return YYZNQK;
    }
}

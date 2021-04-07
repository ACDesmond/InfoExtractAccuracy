
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BGYSR {

    public Element extract(XWPFDocument doc, String text) {

        Element BGYSR = DocumentHelper.createElement("BGYSR");
        BGYSR.addAttribute("nameCN", "被告月收入");

        String[] allsj = text.split("。|；");
        String ysr = "无此信息";
        String regex = "\\d+，?．?\\d*元";
        Pattern p = Pattern.compile(regex);

        for (String s : allsj) {
            Matcher m = p.matcher(s);
            if (m.find()) {
                if (s.contains("被告")) {
                    if (s.contains("月收入") || s.contains("月工资") || s.contains("月薪") || s.contains("平均工资") || s.contains("平均收入")) {
                        String news = s.substring(s.indexOf("被告"));
                        Matcher m2 = p.matcher(news);
                        if (m2.find()) {
                            String gzs = "";
                            gzs = m2.group(0);
                            if (news.contains("原告") && news.indexOf("原告") < news.indexOf(gzs)) {
                                ;
                            } else {
                                ysr = gzs;
                                break;
                            }

                        }
                    }
                }
            }
        }
        BGYSR.addAttribute("value", ysr);
        return BGYSR;
    }
}

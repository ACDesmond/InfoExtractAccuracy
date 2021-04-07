import main.KeyCorp;
import main.getBNodeString;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;

/**
 * 依据已有信息，判断抚养权应该归属哪一方
 */
public class YGGSF {

    public Element extract(XWPFDocument doc, String text) {

        Element YGGSF = DocumentHelper.createElement("YGGSF");
        YGGSF.addAttribute("nameCN", "根据已有信息，抚养权应该归属哪一方");

        String[] strArray = text.split("。|；|：|，");
        int ffy = 0;//判断是否找到变更抚养关键词
        int fffy = 0;//找到法院的决定关键词
        int fq = 0;//是否存在”由其抚养“这样的表达
        String ifcz = "无此信息";
        ArrayList<String> news = new ArrayList<String>();
        for (String s : strArray) {
            if (s.contains("变更") && s.contains("抚养")) {
                ffy = 1;
            }
            if (ffy == 1) {
                if (s.contains("予") && (s.contains("支持") || s.contains("准许") || s.contains("驳回") || s.contains("准予"))) {
                    fffy = 1;
                }
            }
            news.add(s);
            if (fffy == 1) {
                break;
            }
        }
        if (news.size() > 0) {//如果news里面有字符串，从后往前找
            for (int i = news.size() - 1; i >= 0; i--) {
                String s = news.get(i);
                if (fq == 1) {
                    int indexyg = s.lastIndexOf("原告");
                    int indexbg = s.lastIndexOf("被告");
                    if (indexyg < indexbg) {
                        ifcz = "由被告抚养";
                        break;
                    }
                    if (indexyg > indexbg) {
                        ifcz = "由原告抚养";
                        break;
                    }
                } else {

                    int start = 0;
                    int end = 0;
                    if ((s.contains("随") || s.contains("与") || s.contains("同") || s.contains("归")) && s.contains("生活")) {
                        String[] keyWords = {"随", "与", "同", "归"};
                        start = KeyCorp.getFirstKeyIndex(s, keyWords);
                        end = s.indexOf("生活") + 2;

                    } else if ((s.contains("由") || s.contains("归")) && s.contains("抚养")) {
                        String[] keyWords = {"由", "归"};
                        start = KeyCorp.getFirstKeyIndex(s, keyWords);
                        end = s.indexOf("抚养") + 2;
                    }
                    if (end - start > 3) {
                        String newsp = s.substring(start, end);
                        if (!newsp.contains("由其") && !newsp.contains("随其") && !newsp.contains("与其") && !newsp.contains("归其")) {
                            ifcz = newsp;
                            break;
                        } else {
                            fq = 1;
                        }
                    }

                }
            }
        }
        YGGSF.addAttribute("value", ifcz);
        return YGGSF;
    }
}

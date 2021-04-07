import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 子女随哪一方生活时间较长，改变生活环境对子女健康成长明显不利的
 */
public class ZNSNYFSSSJJC {

    public Element extract(XWPFDocument doc, String text) {
        Element ZNSNYFSSSJJC = DocumentHelper.createElement("ZNSNYFSSSJJC");
        ZNSNYFSSSJJC.addAttribute("nameCN", "子女随哪一方生活时间较长，改变生活环境对子女健康成长明显不利的");

        String shhj = "无此信息";//生活环境因素
        String[] qwStrarray = text.split("。|；");
        for (String qw : qwStrarray) {
            if (qw.contains("改变") && qw.contains("生活环境")
                    && (qw.contains("不利"))) {
                shhj = qw;
                String[] qwarrayList = qw.split("，");
                for (String qwarray : qwarrayList) {
                    int start = 0;
                    int end = 0;
                    if (qwarray.contains("随") && qwarray.contains("生活")) {
                        start = qwarray.indexOf("随");
                        end = qwarray.indexOf("生活");

                    } else if (qwarray.contains("由") && qwarray.contains("抚养")) {
                        start = qwarray.indexOf("由");
                        end = qwarray.indexOf("抚养");
                    }
                    if (start < end) {
                        shhj = qwarray.substring(start, end + 2);
                    }
                }
                if (!shhj.equals("无此信息") && !shhj.equals("")) {
                    break;
                }
            }
        }

        ZNSNYFSSSJJC.addAttribute("value", shhj);
        return ZNSNYFSSSJJC;
    }
}

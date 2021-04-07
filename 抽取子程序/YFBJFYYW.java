import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 一方不尽扶养义务
 * 一方是否存在有抚养条件不尽抚养义务的情形
 */
public class YFBJFYYW {
    public Element extract(XWPFDocument doc, String text) {

        Element YFBJFYYW = DocumentHelper.createElement("YFBJFYYW");
        YFBJFYYW.addAttribute("nameCN", "一方是否存在有抚养条件不尽抚养义务的情形");
        String ifcc = "否";
        String[] qwArray = text.split("。|，");
        for (String qw : qwArray) {
            if (qw.contains("抚养义务") && (qw.contains("不履行") || qw.contains("未尽") || qw.contains("未履行"))) {
                ifcc = "是";
            }
        }
        YFBJFYYW.addAttribute("value", ifcc);
        return YFBJFYYW;
    }
}

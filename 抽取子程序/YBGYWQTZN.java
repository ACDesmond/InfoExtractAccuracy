import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 与子女共同生活的一方不尽抚养义务或有虐待子女行为，或其与子女共同生活对子女身心健康确有不利影响的
 */
public class YBGYWQTZN {

    public Element extract(XWPFDocument doc, String text) {
        Element YBGYWQTZN = DocumentHelper.createElement("YBGYWQTZN");
        YBGYWQTZN.addAttribute("nameCN", "与子女共同生活的一方是否存在不尽抚养义务或有虐待子女行为，或其与子女共同生活对子女身心健康确有不利影响的");

        String[] allStrArray = text.split("。");
        String ifcz = "否";
        for (String s : allStrArray) {
            if (!(s.contains("未") || s.contains("没有") || s.contains("不存在")) && (s.contains("子") || s.contains("女"))) {
                if (s.contains("抚养义务") && (s.contains("不履行") || s.contains("未尽") || s.contains("未履行"))) {
                    ifcz = "是";
                    break;
                }
                if (s.contains("虐待") || s.contains("殴打") || s.contains("暴力") || s.contains("动手")) {
                    ifcz = "是";
                    break;
                }
                if (s.contains("不利") && s.contains("身心健康")) {
                    ifcz = "是";
                    break;
                }
            }
        }

        YBGYWQTZN.addAttribute("value", ifcz);
        return YBGYWQTZN;

    }
}

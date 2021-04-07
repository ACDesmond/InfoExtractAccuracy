
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 双方对子女抚养是否曾经达成一致意见
 */
public class SFDZNFYSFCJDCYZ {

    public Element extract(XWPFDocument doc, String text) {

        Element SFDZNFYSFCJDCYZ = DocumentHelper.createElement("SFDZNFYSFCJDCYZ");
        SFDZNFYSFCJDCYZ.addAttribute("nameCN", "双方对子女抚养是否曾经达成一致意见");

        String[] allarray = text.split("。");
        String ifcz = "否";
        for (String s : allarray) {
            if (s.contains("抚养") || s.contains("生活")) {
                if (s.contains("一致") || s.contains("统一") || s.contains("同意") || s.contains("协商") || s.contains("协议") || s.contains("约定") || s.contains("调解")) {
                    if (!s.contains("不") && !s.contains("未")) {
                        ifcz = "是";
                        break;
                    }
                }
            }
        }
        SFDZNFYSFCJDCYZ.addAttribute("value", ifcz);
        return SFDZNFYSFCJDCYZ;
    }
}

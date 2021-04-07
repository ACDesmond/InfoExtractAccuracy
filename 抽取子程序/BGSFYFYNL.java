import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 判断被告是否有抚养能力
 */
public class BGSFYFYNL {

    public Element extract(XWPFDocument doc, String text) {

        Element BGSFYFYNL = DocumentHelper.createElement("BGSFYFYNL");
        BGSFYFYNL.addAttribute("nameCN", "被告是否有抚养能力");

        String yb = "被告";
        String uyb = "原告";
        String[] allStrArray = text.split("。");
        String ifcz = "无此信息";
        for (String s : allStrArray) {
            if (s.contains(yb) && (s.contains("抚养能力") || s.contains("抚养条件")) && (s.contains("有") || s.contains("具备"))) {
                if (s.contains(uyb)) {
                    int i1 = s.indexOf(yb);
                    int i2 = s.indexOf(uyb);
                    int end = s.indexOf("抚养");
                    if (Math.abs(i1 - end) < Math.abs(i2 - end)) {
                        if (s.contains("不") || s.contains("没有") || s.contains("无法")) {
                            ifcz = "否";
                            break;
                        } else {
                            ifcz = "是";
                            break;
                        }
                    }
                } else {
                    ifcz = "是";
                    break;
                }
            }

        }
        BGSFYFYNL.addAttribute("value", ifcz);
        return BGSFYFYNL;
    }
}


import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 判断原告在诉讼前是否拥有抚养权
 */
public class YGZLHQSFYYFYQ {

    public Element extract(XWPFDocument doc, String text) {

        Element YGZLHQSFYYFYQ = DocumentHelper.createElement("YGZLHQSFYYFYQ");
        YGZLHQSFYYFYQ.addAttribute("nameCN", "原告在诉讼前是否拥有抚养权");

        String ifcz = "否";
        String[] array2 = text.split("，|；|：|。");
        for (String s2 : array2) {
            if (s2.contains("原告")) {
                if (s2.contains("抚养") && (s2.contains("由") || s2.contains("归"))) {
                    ifcz = "是";
                    System.out.println(s2);
                    break;
                }
                if (s2.contains("生活") && (s2.contains("随") || s2.contains("归"))) {
                    ifcz = "是";
                    System.out.println(s2);
                    break;
                }
            }
            YGZLHQSFYYFYQ.addAttribute("value", ifcz);
        }
        return YGZLHQSFYYFYQ;
    }
}

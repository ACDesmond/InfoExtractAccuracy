import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 离婚协议内容
 * 代表案由：变更抚养关系纠纷
 */
public class LHXYNR {

    public Element extract(XWPFDocument doc, String text) {

        // 创建节点
        Element LHXYNR = DocumentHelper.createElement("LHXYNR");
        LHXYNR.addAttribute("nameCN", "离婚协议内容");

        String[] strarray = text.split("。");
        String sfyxy = "否";
        String xynr = "无此信息";
        //最精准的一种表达
        for (String s : strarray) {
            if (s.contains("离婚") && (!(s.contains("未离婚") || s.contains("没有离婚")))) {
                if (s.contains("约定") || s.contains("协商")) {
                    sfyxy = "是";
                    String str = "";
                    if (s.contains("约定")) {
                        str = s.substring(s.indexOf("约定"));
                    } else {
                        str = s.substring(s.indexOf("协商"));
                    }
                    if (str.length() > 3) {
                        xynr = str;
                        break;
                    }
                }
            }
        }
        //次精准
        if (xynr.equals("无此信息")) {
            for (String s : strarray) {
                if (s.contains("离婚") && (!(s.contains("未离婚") || s.contains("没有离婚")))) {
                    if (s.contains("协议")) {
                        sfyxy = "是";
                        if (s.contains("的协议")) {
                            int start = s.indexOf("离婚");
                            int end = s.indexOf("协议");
                            if (start < end) {
                                String str = s.substring(start, end) + "协议";
                                if (str.length() > 6) {
                                    xynr = str;
                                    break;
                                }
                            }
                        } else {
                            int start = s.indexOf("协议");
                            String str = s.substring(start);
                            if (str.length() > 6) {
                                xynr = str;
                                break;
                            }
                        }
                    }
                }
            }
        }
        LHXYNR.addAttribute("value", xynr);
        return LHXYNR;
    }
}

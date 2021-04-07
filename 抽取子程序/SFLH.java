import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 是否离婚
 * 判断婚生子女的父母是否已经离婚
 * 代表案由：变更抚养关系纠纷
 */
public class SFLH {

    public Element extract(XWPFDocument doc, String text) {
        // 创建节点
        Element SFLH = DocumentHelper.createElement("SFLH");
        SFLH.addAttribute("nameCN", "是否离婚");

        String cmssd = text;
        String[] strarray = cmssd.split("。");
        String iflh = "无此信息";
        for (String s : strarray) {
            if (s.contains("离婚")) {
                if (s.contains("未离婚") || s.contains("没有离婚")) {
                    iflh = "否";
                } else {
                    iflh = "是";
                }
            }
        }
        SFLH.addAttribute("value", iflh);
        return SFLH;
    }
}

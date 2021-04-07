import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 离婚方式
 * 双方离婚的方式
 * 代表案由：变更抚养关系纠纷
 */
public class LHFS {

    public Element extract(XWPFDocument doc, String text) {
        // 创建节点
        Element LHFS = DocumentHelper.createElement("LHFS");
        LHFS.addAttribute("nameCN", "离婚方式");

        String[] strarray = text.split("。");
        String lhfs = "无此信息";
        for (String s : strarray) {
            if (s.contains("离婚") && (!(s.contains("未离婚") || s.contains("没有离婚")))) {
                //协议离婚
                if (s.contains("民政") || s.contains("协议") || s.contains("协商") || s.contains("约定") || s.contains("调解") || s.contains("婚姻登记")) {
                    lhfs = "协议离婚";
                    break;
                }
                if (s.contains("判决") || s.contains("诉讼") || s.contains("起诉")) {
                    lhfs = "诉讼离婚";
                    break;
                }
            }
        }
        LHFS.addAttribute("value", lhfs);

        return LHFS;
    }

}

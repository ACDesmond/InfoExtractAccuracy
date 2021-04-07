import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 离婚时抚养权给谁
 */
public class LHSFYQGS {

    public Element extract(XWPFDocument doc, String text) {
        Element LHSFYQGS = DocumentHelper.createElement("LHSFYQGS");
        LHSFYQGS.addAttribute("nameCN", "离婚时抚养权给谁");

        String fyqgs = "";
        String fyqjdfs = "无此信息";
        if (getSFLH(text).equals("是")) {//判断双方有没有离婚
            String cmssd = text;
            String[] strarray = cmssd.split("。");
            for (String s : strarray) {
                if (s.contains("离婚") && (s.contains("抚养") || s.contains("生活")) && (s.contains("子") || s.contains("女"))) {
                    //提取抚养权决定方式
                    if (s.contains("民政") || s.contains("协议") || s.contains("协商") || s.contains("约定") || s.contains("调解") || s.contains("婚姻登记")) {
                        fyqjdfs = "双方约定";
                    } else if (s.contains("判决") || s.contains("诉讼") || s.contains("起诉")) {
                        fyqjdfs = "法院判决";
                    }
                    //提取抚养权归属
                    String[] array2 = s.split("，|；|：");
                    for (String s2 : array2) {
                        if ((s2.contains("抚养") || s2.contains("抚育")) && (s2.contains("由") || s2.contains("归"))) {
                            fyqgs += s2 + ";";
                        }
                        if (s2.contains("生活") && (s2.contains("随") || s2.contains("归"))) {
                            fyqgs += s2 + ";";
                        }
                        if (s2.contains("抚养权")) {
                            fyqgs += s2 + ";";
                        }
                    }
                    break;
                }
            }
        }

        if (fyqgs.equals("")) {
            fyqgs = "无此信息";
        }

        LHSFYQGS.addAttribute("value", fyqgs);
        return LHSFYQGS;
    }

    private String getSFLH(String text) {
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
        return iflh;
    }
}

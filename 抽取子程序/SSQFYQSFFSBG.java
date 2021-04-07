import main.getBNodeString;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 判断诉讼前抚养权是否发生变更
 */
public class SSQFYQSFFSBG {

    public Element extract(XWPFDocument doc, String text) {

        Element SSQFYQSFFSBG = DocumentHelper.createElement("SSQFYQSFFSBG");
        SSQFYQSFFSBG.addAttribute("nameCN", "诉讼前抚养权是否发生变更");

        String ifcz = "否";
        String  bgfs = "无此信息";
        String[] strarray = text.split("。");
        for (String s : strarray) {
            if (s.contains("抚养") && s.contains("变更") && !s.contains("现")) {
                //	  System.out.println(s);
                if (s.contains("协议") || s.contains("协商") || s.contains("约定") || s.contains("调解")) {
                    ifcz = "是";
                    bgfs = "双方协议";
                    break;
                } else if (s.contains("判决") && !s.contains("要求") && !s.contains("请求")) {
                    ifcz = "是";
                    bgfs = "法院判决";
                    break;
                }
            }
        }
        SSQFYQSFFSBG.addAttribute("value", ifcz);
        return SSQFYQSFFSBG;

    }
}

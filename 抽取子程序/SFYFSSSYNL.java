import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 是否一方丧失生育能力
 * 是否有一方已做绝育手术或因其他原因丧失生育能力的
 */
public class SFYFSSSYNL {
    public Element extract(XWPFDocument doc, String text) {

        Element SFYFSSSYNL = DocumentHelper.createElement("SFYFSSSYNL");
        SFYFSSSYNL.addAttribute("nameCN", "是否有一方已做绝育手术或因其他原因丧失生育能力的");

        String jyys = "否";//绝育因素
        String[] qwStrarray = text.split("。|；|，|：");
        for (String qw : qwStrarray) {
            if (qw.contains("绝育") || qw.contains("结扎") || ((qw.contains("无法") || qw.contains("丧失")) && (qw.contains("生育") || qw.contains("怀孕"))) || qw.contains("不育")) {
                jyys = "是";
            }
        }
        SFYFSSSYNL.addAttribute("value", jyys);
        return SFYFSSSYNL;
    }
}

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 祖父母因素
 * 是否存在父、母抚养未成年子女的条件基本相同，双方均要求子女与其共同生活，但子女单独随祖父母或外祖父母共同生活多年，
 * 且祖父母或外祖父母要求并有能力帮助子女照顾孙子女或外孙子女的，可作为子女随父或母生活的优先条件予以考虑的情形
 */
public class ZFMYS {
    public Element extract(XWPFDocument doc, String text) {
        Element ZFMYS = DocumentHelper.createElement("ZFMYS");
        ZFMYS.addAttribute("nameCN", "祖父母或外祖父母因素");

        String Grandp = "否";//祖父母因素
        String jtqk = "无此信息";

        Element row13_1Node = ZFMYS.addElement("ZFMWAZFYNLFYHZ").addAttribute("nameCN", "是否存在祖父母或外祖父母要求并有能力帮助子女照顾孙子女或外孙子女的情况");
        Element row13_2Node = ZFMYS.addElement("JTQK").addAttribute("nameCN", "具体情况");
        String[] qwStrarray = text.split("。|；");
        for (String qw : qwStrarray) {
            if ((qw.contains("祖母") || qw.contains("祖父") || qw.contains("爷爷") ||
                    qw.contains("奶奶") || qw.contains("外公") || qw.contains("外婆")) && (qw.contains("生活") || qw.contains("居住") || qw.contains("抚养") ||
                    qw.contains("成长")) && (!(qw.contains("房屋") || qw.contains("财产")))) {
                Grandp = "是";
                jtqk = qw;
                break;
            }
        }
        row13_1Node.addAttribute("value", Grandp);
        row13_2Node.addAttribute("value", jtqk);

        return ZFMYS;
    }
}

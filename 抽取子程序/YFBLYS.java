import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 一方不利因素
 * 是否存在子女随其生活，对子女成长有利，而另一方患有久治不愈的传染性疾病或其他严重疾病，
 * 或者有其他不利于子女身心健康的情形，不宜与子女共同生活的情形
 */
public class YFBLYS {

    public Element extract(XWPFDocument doc, String text) {

        Element YFBLYS = DocumentHelper.createElement("YFBLYS");
        YFBLYS.addAttribute("nameCN", "是否存在子女随其生活，对子女成长有利，而另一方患有久治不愈的传染性疾病或其他严重疾病， 或者有其他不利于子女身心健康的情形，不宜与子女共同生活的情形");
        String yfblys = "否";//一方不利因素
        String[] qwStrarray = text.split("。|；");
        for (String qw : qwStrarray) {
            if ((qw.contains("成长有利") || qw.contains("成长不利")
                    || qw.contains("有利于孩子") || qw.contains("不利于孩子")
                    || qw.contains("有利于子女") || qw.contains("有利于小孩")
                    || qw.contains("由原告抚养为宜") || qw.contains("由被告抚养为宜")
                    || (((qw.contains("有利于") || qw.contains("不利于")) && qw.contains("身心健康"))))
                    || ((qw.contains("病") || qw.contains("患"))
                    && (((qw.contains("婚生") || qw.contains("双方")) && (qw.contains("子") || qw.contains("女")))
                    && (qw.contains("由") || qw.contains("跟") || qw.contains("随"))
                    && (qw.contains("被告") || qw.contains("原告") || qw.contains("父亲") || qw.contains("母亲"))
                    && (qw.contains("生活") || qw.contains("抚养") || qw.contains("抚育"))))) {
                yfblys = "是";
                break;
            }
        }
        YFBLYS.addAttribute("value", yfblys);
        return YFBLYS;
    }
}

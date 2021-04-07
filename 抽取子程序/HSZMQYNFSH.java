
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 判断婚生子女目前与原告或者被告哪一方一起生活
 */
public class HSZMQYNFSH {

    public Element extract(XWPFDocument doc, String text) {

        Element HSZMQYNFSH = DocumentHelper.createElement("HSZMQYNFSH");
        HSZMQYNFSH.addAttribute("nameCN", "婚生子女目前与原告或者被告哪一方一起生活");

        String[] allarray = text.split("。");
        String nf1 = "无此信息";
        String nf2 = "";
        String nf = "";
        int f = 0;
        for (String s : allarray) {
            if (!s.contains("前夫") && !s.contains("前妻")) {
                if ((s.contains("子") || s.contains("女") || s.contains("孩")) && (s.contains("生活") || s.contains("抚养") || s.contains("照顾") || s.contains("居住"))) {
                    String[] allsplit = s.split("，|；");
                    for (String str : allsplit) {
                        if (str.contains("生活") || str.contains("抚养") || str.contains("照顾") || str.contains("居住")) {
                            if (str.contains("至今") || (str.contains("现") && str.contains("随"))) {
                                nf2 += str;
                                f = 1;
                                break;
                            } else if (str.contains("一直")) {
                                nf2 += str;
                                f = 1;
                                break;
                            }
                        }
                    }
                    if (f == 1) {
                        break;
                    }
                }
            }
        }
        if (nf2.equals("")) {
            HSZMQYNFSH.addAttribute("value", nf1);
        } else {
            if (nf2.contains("被告") && nf2.contains("父母")) {
                nf += "被告父母";
                if (nf2.contains("及") || nf2.contains("和")) {
                    nf += "被告";
                }
            }
            if (nf2.contains("原告") && nf2.contains("父母")) {
                nf += "原告父母";
                if (nf2.contains("及") || nf2.contains("和")) {
                    nf += "原告";
                }
            }
            if (!nf2.contains("父母")) {
                if (nf2.contains("原告")) {
                    nf += "原告";
                } else if (nf2.contains("被告")) {
                    nf += "被告";
                }
            }
            HSZMQYNFSH.addAttribute("value", nf);
        }

        return HSZMQYNFSH;
    }

}

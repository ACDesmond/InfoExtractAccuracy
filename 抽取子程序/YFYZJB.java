import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 一方严重疾病
 * 一方患有久治不愈的传染性疾病或其他严重疾病，子女不宜与其共同生活的情形
 */
public class YFYZJB {

    public Element extract(XWPFDocument doc, String text) {
        Element YFYZJB = DocumentHelper.createElement("YFYZJB");
        YFYZJB.addAttribute("nameCN", "一方患有久治不愈的传染性疾病或其他严重疾病，子女不宜与其共同生活的情形");

        String ifcc = "否";
        String[] qwStrarray = null;
        int flag1 = 0;
        qwStrarray = text.split("，|。|；|：");
        String roleall = getJHDX(text);
        String role1 = roleall.split(";")[0].trim();
        String role2 = roleall.split(";")[1].trim();
        for (String s : qwStrarray) {
            if (s.contains("病") || s.contains("症")) {
                if ((s.contains("原告") || s.contains("被告") || s.contains("父方") || s.contains("母方") || s.contains(role1) || s.contains(role2))
                        && !(s.contains("未提供证据"))) {
                    flag1 = 1;
                    ifcc = "是";
                    break;
                }
            }
        }
        if (flag1 == 1) {
            qwStrarray = text.split("。");
            for (String qw : qwStrarray) {
                if ((qw.contains("孩子") || qw.contains("子") || qw.contains("女")) &&
                        (qw.contains("抚育") || qw.contains("抚养") || qw.contains("照顾")) && (qw.contains("病") || qw.contains("症")) && (
                        qw.contains("不宜") || qw.contains("不利") || qw.contains("不能") || qw.contains("无力")) && !(qw.contains("未提供证据") || qw.contains("未提交证据"))) {
                    ifcc = "是";
                    break;
                }
            }
        }
        YFYZJB.addAttribute("value", ifcc);

        return YFYZJB;
    }


    private String getJHDX(String text) {
        String[] array = text.split("；|。|，|：");
        String role1 = "  ";
        String role2 = "  ";
        for (String s : array) {
            String[] keyWords = {"夫妻", "结婚", "婚礼", "离婚"};
            int end = 0;
            end = getFirstKeyIndex(s, keyWords);
            if (end > 0) {
                //获取关键词前面的字符串
                String news = s.substring(0, end);
                String[] keyWords2 = {"于", "在", "经", "到", "因", "原系", "曾系", "曾是", "登记", "结婚", "办理", "举行", "离婚"};
                end = getFirstKeyIndex(news, keyWords2);
                if (end > 0) {
                    String newss = news.substring(0, end);
                    String[] role = newss.split("[和|、|与]");
                    if (role.length >= 2) {
                        role1 += role[0];
                        role2 += role[1];
                        break;
                    } else if (newss.contains("原被告")) {
                        role1 += "原告";
                        role2 += "被告";
                    }

                }
            }

        }
        return role1 + ";" + role2;
    }

    private int getFirstKeyIndex(String s, String[] keyWords) {
        int end = 0;
        for (String skey : keyWords) {
            if (s.contains(skey)) {
                end = s.indexOf(skey);
                break;
            }
        }
        return end;
    }
}

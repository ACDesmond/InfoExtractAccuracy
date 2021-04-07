
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 判断原告和婚生子女的关系
 */
public class YGHHSZNGX {

    public Element extract(XWPFDocument doc, String text) {

        Element YGHHSZNGX = DocumentHelper.createElement("YGHHSZNGX");
        YGHHSZNGX.addAttribute("nameCN", "原告和婚生子女的关系");

        String ifcz = "";

        String[] allStr = text.split("。");
        String roleall = getJHDX(text);
        String role1 = roleall.split(";")[0].trim();
        String role2 = roleall.split(";")[1].trim();
        if (role1.contains("原告之子")) {
            ifcz = "亲生父亲亲属";
        } else if (role1.contains("原告之女")) {
            ifcz = "亲生母亲亲属";
        } else if (role1.contains("原")) {
            ifcz = "亲生父母";
        } else if (role2.contains("原告之子")) {
            ifcz = "亲生父亲亲属";
        } else if (role2.contains("原告之女")) {
            ifcz = "亲生母亲亲属";
        } else if (role2.contains("原")) {
            ifcz = "亲生父母";
        }

        if (ifcz.equals("")) {
            ifcz = "其他";
        }

        YGHHSZNGX.addAttribute("value", ifcz);
        return YGHHSZNGX;
    }

    private String getJHDX(String text) {
        String[] array = text.split("；|。|，|：");
        String role1 = "  ";
        String role2 = "  ";
        for (String s : array) {
            String[] keyWords = {"夫妻", "结婚", "婚礼", "离婚"};
            int end = 0;
            end = getFirstKeyIndex(s, keyWords);
            if (end > 0) {//获取关键词前面的字符串
                String news = s.substring(0, end);
                //    System.out.println(news);
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

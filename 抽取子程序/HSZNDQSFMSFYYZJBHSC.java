import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 婚生子女的亲生父母是否有严重疾病或因伤残无力继续抚养子女
 */
public class HSZNDQSFMSFYYZJBHSC {
    public Element extract(XWPFDocument doc, String text) {
        Element HSZNDQSFMSFYYZJBHSC = DocumentHelper.createElement("HSZNDQSFMSFYYZJBHSC");
        HSZNDQSFMSFYYZJBHSC.addAttribute("nameCN", "婚生子女的亲生父母是否有严重疾病或因伤残无力继续抚养子女");


        String[] allStrArray = text.split("。|；|，");
        //首先找出亲生父母
        String roleall = getJHDX(text);
        String role1 = roleall.split(";")[0].trim();
        String role2 = roleall.split(";")[1].trim();

        String ifcz = "否";
        int flag1 = 0;
        int flag2 = 0;
        for (String s : allStrArray) {
            if ((s.contains(role1) || s.contains(role2)) && (s.contains("病") || s.contains("症") || s.contains("残") || s.contains("癌"))) {
                flag1 = 1;
                break;
            }

        }
        String[] allStrArray2 = text.split("。|；|，");
        for (String s : allStrArray2) {
            if ((s.contains(role1) || s.contains(role2)) && (s.contains("无") || s.contains("没有") || s.contains("不能")) && (s.contains("抚养") || s.contains("抚育") || s.contains("照顾"))) {
                flag2 = 1;
                break;
            }

        }
        if (flag1 * flag2 == 1) {
            ifcz = "是";
        }
        HSZNDQSFMSFYYZJBHSC.addAttribute("value", ifcz);

        return HSZNDQSFMSFYYZJBHSC;
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

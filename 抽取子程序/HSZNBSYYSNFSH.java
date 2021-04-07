import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 婚生子女是否表示愿意与随哪一方生活
 */
public class HSZNBSYYSNFSH {

    public Element extract(XWPFDocument doc, String text) {

        Element HSZNBSYYSNFSH = DocumentHelper.createElement("HSZNBSYYSNFSH");
        HSZNBSYYSNFSH.addAttribute("nameCN", "婚生子女是否表示愿意随哪一方生活");

        String zvyj = "无此信息";
        String[] qwStrarray = text.split("。|；|：");
        int flag1 = 0;
        for (String qw : qwStrarray) {
            if ((qw.contains("婚生") || qw.contains("原、被告") || qw.contains("双方")) && (qw.contains("子") || qw.contains("女"))) {
                if (qw.contains("意见") || qw.contains("意愿") || qw.contains("愿意随") || qw.contains("愿随") || qw.contains("愿意跟随") || qw.contains("愿跟随") || qw.contains("愿意由") || qw.contains("愿由")) {
                    {
                        String[] keyWords = {"意见", "意愿", "愿意随", "愿意跟随", "愿随", "愿跟随", "愿由", "愿意由"};
                        int index = getFirstKeyIndex(qw, keyWords);
                        String newqw = "";
                        if (index != -1) {
                            newqw = qw.substring(index);
                        } else {
                            newqw = qw;
                        }
                        String[] qwarrayList = newqw.split("，");
                        for (String qwarray : qwarrayList) {
                            int start = 0;
                            int end = 0;
                            if (qwarray.contains("随") && qwarray.contains("生活")) {
                                start = qwarray.indexOf("随");
                                end = qwarray.indexOf("生活");
                            } else if (qwarray.contains("由") && qwarray.contains("抚养")) {
                                start = qwarray.indexOf("由");
                                end = qwarray.indexOf("抚养");
                            }
                            if (start < end) {
                                zvyj = qwarray.substring(start, end + 2);
                                System.out.println(zvyj);
                                flag1 = 1;
                                break;
                            }
                        }
                    }
                }
            }
            if (flag1 == 1) {
                break;
            }
        }
        HSZNBSYYSNFSH.addAttribute("value", zvyj);
        return HSZNBSYYSNFSH;
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

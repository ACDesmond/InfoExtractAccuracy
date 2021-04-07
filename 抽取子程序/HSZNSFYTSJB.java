
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 判断婚生子女是否有特殊疾病
 */
public class HSZNSFYTSJB {

    public Element extract(XWPFDocument doc, String text) {

        Element HSZNSFYTSJB = DocumentHelper.createElement("HSZNSFYTSJB");
        HSZNSFYTSJB.addAttribute("nameCN", "婚生子女是否有特殊疾病");

        String[] allarray = text.split("。|；|，");
        String nf1 = "无此信息";
        Pattern p1 = Pattern.compile("(儿子)|(女儿)|(孩子)|(男孩)|(女孩)|[(婚生)|(双方(之)?)|(原、被告之)][子|女|(子女)|儿]");
        Pattern p2 = Pattern.compile("[病|症|疹|瘫|癌|残|栓|伤|(癫痫)]");
        Pattern p3 = Pattern.compile("[患|诊|]");
        for (String s : allarray) {
            Matcher m1 = p1.matcher(s);
            Matcher m2 = p2.matcher(s);
            Matcher m3 = p3.matcher(s);
            if (m1.find() && m2.find() && m3.find()) {
                String[] keyWords1 = {"子", "女", "孩"};
                String[] keyWords2 = {"患", "有", "诊"};
                String[] keyWords3 = {"病", "症", "疹", "瘫", "癌", "残", "疾", "栓", "伤", "癫痫"};
                int index1 = getKeyIndex(keyWords1, s);
                int index2 = getKeyIndex(keyWords2, s);
                int index3 = getKeyIndex(keyWords3, s);
                String illtype = getKeyKey(keyWords3, s);
                String[] newKey = {"原告", "被告", "儿", "子", "女", "孩", "男"};
                String ifdis = ifDisMatch(keyWords3, newKey, s);
                if (index2 < index3 &&
                        (ifdis.contains("儿") || ifdis.contains("子") || ifdis.contains("女") || ifdis.contains("孩") || ifdis.contains("男"))) {
                    String ill = s.substring(index2, index3) + illtype;
                    if (ill.length() >= 3) {
                        nf1 = ill;
                        break;
                    }
                }

            }
        }
        HSZNSFYTSJB.addAttribute("value", nf1);

        return HSZNSFYTSJB;
    }

    private int getKeyIndex(String[] keyWords, String s) {
        int index = -1;
        for (String str : keyWords) {
            if (s.contains(str)) {
                index = s.indexOf(str);
                break;
            }
        }
        return index;
    }

    private String ifDisMatch(String[] keyWords1, String[] keyWords2, String s) {
        int d = 10000000;
        String sk = "";
        for (String str : keyWords1) {
            if (s.contains(str)) {    //比如此时str为"病
                int d1 = s.indexOf(str);
                String news1 = s.substring(d1);
                String news2 = s.substring(0, d1);
                for (String str2 : keyWords2) {
                    if (news1.contains(str2)) {
                        int d2 = news1.indexOf(str2);
                        if (d2 < d) {
                            sk = str2;
                            d = d2;
                        }
                    }
                }
                for (String str2 : keyWords2) {
                    if (news2.contains(str2)) {
                        int d2 = news2.lastIndexOf(str2);
                        if (Math.abs(d2 - d1) < d) {
                            d = Math.abs(d2 - d1);
                            sk = str2;
                        }
                    }
                }
                break;
            }
        }
        return sk;
    }

    private String getKeyKey(String[] keyWords, String s) {
        String s1 = "";
        for (String str : keyWords) {
            if (s.contains(str)) {
                s1 = new String(str);
                break;
            }
        }
        return s1;
    }
}

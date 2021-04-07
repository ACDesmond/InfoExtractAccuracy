
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 要求抚养费金额
 */
public class YQFYFJE {

    public Element extract(XWPFDocument doc, String text) {

        Element YQFYFJE = DocumentHelper.createElement("YQFYFJE");
        YQFYFJE.addAttribute("nameCN", "要求抚养费金额");

        String[] strArray = text.split("。");
        String ifcz = "无此信息";
        String allfyf = "";
        String regex = "\\d+，?万?．?\\d*元";
        Pattern p = Pattern.compile(regex);
        int f1 = 0;
        for (String s : strArray) {
            if ((s.contains("请求") || s.contains("要求")) && (s.contains("抚养费") || s.contains("生活费"))) {
                Matcher m = p.matcher(s);
                if (m.find()) {
                    String news = removeQSDH(s, regex);
                    String[] newsArray = news.split("，|；");
                    for (String newsp : newsArray) {
                        Matcher mp = p.matcher(newsp);
                        if (mp.find()) {
                            if ((newsp.contains("抚养费") || newsp.contains("生活费"))) {
                                ArrayList<String> fyfList = new ArrayList<String>();
                                getDate(newsp, fyfList, regex);
                                if (newsp.contains("每月") || newsp.contains("元／月") || newsp.contains("按月")) {
                                    allfyf += fyfList.get(fyfList.size() - 1) + "/月;";
                                } else if (newsp.contains("每年")) {
                                    allfyf += fyfList.get(fyfList.size() - 1) + "/年;";
                                } else {
                                    allfyf += fyfList.get(fyfList.size() - 1) + ";";
                                }
                                f1 = 1;
                            }
                        }
                    }
                }
            }
            if (f1 == 1) {
                break;
            }
        }
        if (!allfyf.equals("")) {
            ifcz = allfyf;
        }
        YQFYFJE.addAttribute("value", ifcz);
        return YQFYFJE;
    }

    private String removeQSDH(String s, String regex) {
        ArrayList<String> all = new ArrayList<String>();
        getDate(s, all, regex);
        StringBuffer news = new StringBuffer(s);
        for (String qs : all) {
            int index = s.indexOf(qs);
            if (qs.contains("，")) {

                String newqs = qs.replace("，", "");
                news.delete(index, index + qs.length());
                news.insert(index, newqs);

            }
        }
        return new String(news);
    }

    private void getDate(String str, ArrayList s, String regx) { //提取出来字符串中所有符合正则表达式regex的字符串，并存进arrayList中

        String date1 = getDateEach(str, regx);

        if (!date1.equals("") && date1 != null) {   //此处有修改
            s.add(date1);
            int index = str.indexOf(date1) + date1.length();
            String newstr = str.substring(index);
            getDate(newstr, s, regx);
        }
    }

    private String getDateEach(String str, String regex) {

        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(str);
        String datestr = "";
        if (m.find()) {
            datestr = m.group();
        }
        return datestr;
    }
}

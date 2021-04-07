
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 不抚养子女一方的工作、收入及财产情况
 */
public class BFYZNYFDGZSRJCC {

    public Element extract(XWPFDocument doc, String text) {

        Element BFYZNYFDGZSRJCC = DocumentHelper.createElement("BFYZNYFDGZSRJCC");
        BFYZNYFDGZSRJCC.addAttribute("nameCN", "不抚养子女一方的工作、收入及财产情况");

        String[] allarray = text.split("。|；");
        String gzqk = "无此信息";
        String srqk = "无此信息";
        String ccqk = "无此信息";
        ArrayList<String> ccqkList = new ArrayList<String>();
        //获取抚养子女方
        String fyznvf = getFYQ(text);
        String bfyznf = "";
        if (fyznvf.contains("原告") && fyznvf.contains("被告")) {
            ;
        } else if (fyznvf.contains("原告")) {
            bfyznf = "被告";
            fyznvf = "原告";
        } else if (fyznvf.contains("被告")) {
            bfyznf = "原告";
            fyznvf = "被告";
        }
        if (!bfyznf.equals("")) {
            String[] gzKeys = {"工作", "从事", "担任", "聘为", "岗位", "工人", "员工", "职业", "职工", "上班", "务农", "农活", "农民", "生意"};
            String regex = "[原|被]告";
            //工作方面**********
            gzqk = getContainKeyStr(allarray, bfyznf, regex, gzKeys);
            //收入情况**********
            String[] srKeys = {"收入", "工资", "薪资", "薪", "奖", "盈利"};
            srqk = getContainKeyStr(allarray, bfyznf, regex, srKeys);
            String[] ccKeys = {"存款", "存折", "房", "车", "财产", "车"};
            allarray = text.split("。|；");
            for (String s : allarray) {
                if (s.contains(bfyznf)) {
                    for (String keygz : ccKeys) {
                        if (s.contains(keygz)) {
                            String jg = getRightDate(s, ccKeys, 0, regex);
                            if (jg.equals(bfyznf)) {
                                String[] sarray = s.split("，");
                                for (String ss : sarray) {
                                    if (ss.contains(fyznvf)) {
                                        break;
                                    }
                                    ArrayList<String> keyList = ifContain(ss, ccKeys);
                                    if (keyList.size() > 0) {
                                        ccqkList.add(ss);

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        removeDuplicateWithOrder(ccqkList);
        if (!ccqkList.isEmpty()) {
            ccqk = new String(ccqkList.toString());
        }
        String allqk = "工作：" + gzqk + ";收入：" + srqk + ";财产：" + ccqk;

        BFYZNYFDGZSRJCC.addAttribute("value", allqk);
        return BFYZNYFDGZSRJCC;
    }

    private String getFYQ(String text) {
        String fyqgs = "";
        if (getSFLH(text).equals("是")) {//判断双方有没有离婚

            String[] strarray = text.split("。");
            for (String s : strarray) {
                if (s.contains("离婚") && (s.contains("抚养") || s.contains("生活")) && (s.contains("子") || s.contains("女"))) {
                    //提取抚养权决定方式
                    //提取抚养权归属
                    String[] array2 = s.split("，|；|：");
                    for (String s2 : array2) {
                        if (s2.contains("抚养") && (s2.contains("由") || s2.contains("归"))) {
                            fyqgs += s2 + ";";
                        }
                        if (s2.contains("生活") && (s2.contains("随") || s2.contains("归"))) {
                            fyqgs += s2 + ";";
                        }
                        if (s2.contains("抚养权")) {
                            fyqgs += s2 + ";";
                        }
                    }
                    break;
                }
            }

        }
        if (fyqgs.equals("")) {
            fyqgs = "无此信息";
        }
        return fyqgs;
    }

    private String getSFLH(String text) {

        String[] strarray = text.split("。");
        String iflh = "无此信息";
        for (String s : strarray) {
            if (s.contains("离婚")) {
                if (s.contains("未离婚") || s.contains("没有离婚")) {
                    iflh = "否";
                } else {
                    iflh = "是";
                }
            }
        }
        return iflh;

    }

    private String getContainKeyStr(String[] allarray, String bfyznf, String regex, String[] gzKeys) {
        int f1 = 0;
        String gzqk = "无此信息";
        for (String s : allarray) {
            if (s.contains(bfyznf)) {
                for (String keygz : gzKeys) {
                    if (s.contains(keygz)) {
                        String jg = getRightDate(s, gzKeys, 0, regex);
                        if (jg.equals(bfyznf)) {
                            String[] sarray = s.split("，");
                            for (String ss : sarray) {
                                ArrayList<String> keyList = ifContain(ss, gzKeys);
                                if (keyList.size() > 0) {
                                    gzqk = ss;
                                    f1 = 1;
                                    break;
                                }
                            }
                        }
                    }
                    if (f1 == 1) {
                        break;
                    }
                }
                if (f1 == 1) {
                    break;
                }
            }
        }
        return gzqk;
    }

    private String getRightDate(String str, String[] keyWords, int fh, String regex) {
        String datef = "";
        String dateh = "";
        int disf = 1000000;
        int dish = 1000000;
        for (int i = 0; i < keyWords.length; i++) {
            if (str.contains(keyWords[i])) {
                //		System.out.println("关键词:"+keyWords[i]);
                int flag = 0;
                ArrayList a = new ArrayList();
                getDate(str, a, regex);
                Iterator it = a.iterator();

                while (it.hasNext()) {

                    String allDate = (String) it.next();
                    //		System.out.println("date"+allDate);


                    int start = str.indexOf(allDate);
                    int end = start + allDate.length() - 1;
                    //	System.out.println(start+"-"+end+":"+str.indexOf(keyWords[i]));
                    if (end < str.indexOf(keyWords[i])) {
                        //				  System.out.println("substring"+str.substring(end, str.indexOf(keyWords[i])));
                        if (!(str.substring(end, str.indexOf(keyWords[i])).contains("，"))) {
                            return allDate;
                        }
                        if (disf > str.indexOf(keyWords[i]) - end) {
                            disf = str.indexOf(keyWords[i]) - end;
                            datef = allDate;
                            //  System.out.println("disf"+disf);
                        }
                    }
                    if (start > str.indexOf(keyWords[i])) {
                        if (!(str.substring(str.indexOf(keyWords[i]), start).contains("，")) && fh != 0) {
                            return allDate;
                        }
                        if (dish > (start - str.indexOf(keyWords[i]))) {
                            dish = start - str.indexOf(keyWords[i]);
                            dateh = allDate;
                            //System.out.println("dish"+dish);
                        }
                    }
                }
                break;
            }
        }
        if (fh == 0) {
            return datef;
        }
        if (fh == 1) {

            return dish > disf ? datef : dateh;
        }
        return dateh;
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

    private ArrayList<String> ifContain(String str, String[] keyWords) { //找到该字符串中包含哪些关键字
        int startindex = -1;
        ArrayList<String> re = new ArrayList();
        for (int i = 0; i < keyWords.length; i++) {
            if (str.contains(keyWords[i])) {
                re.add(keyWords[i]);

            }
        }
        return re;

    }

    private void removeDuplicateWithOrder(List list) {//去掉List中的重复元素，只留一个
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        //  System.out.println( " remove duplicate "   +  list);
    }

}

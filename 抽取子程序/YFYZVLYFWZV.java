import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 是否存在无其他子女，而另一方有其他子女的情形
 */
public class YFYZVLYFWZV {

    public Element extract(XWPFDocument doc, String text) {
        Element YFYZVLYFWZV = DocumentHelper.createElement("YFYZVLYFWZV");
        YFYZVLYFWZV.addAttribute("nameCN", "是否存在无其他子女，而另一方有其他子女的情形");

        String ifcz = "否";//一方有其他子女
        String ygzv = getQTZN(text, "原告");
        String bgzv = getQTZN(text, "被告");
        if (!ygzv.equals(bgzv)) {
            ifcz = "是";
        }
        YFYZVLYFWZV.addAttribute("value", ifcz);
        return YFYZVLYFWZV;

    }


    private String getQTZN(String text, String yb) {
        String ifcz = "否";

        String[] qwStrarray = null;
        qwStrarray = text.split("。");
        String[] zvKeys = {"生育", "育有"};
        String[] zhKeys = {"前妻", "前夫", "组成家庭", "再婚", "现任"};
        String[] ybgKeys = {"原告", "被告", "原被告", "原、被告"};
        String regex = "[原|被]告";
        for (String s : qwStrarray) {
            if ((s.contains(yb) || s.contains("原被告") || s.contains("原、被告")) && (s.contains("子") || s.contains("女")) && !s.contains("婚生") && (s.contains("前妻") || s.contains("前夫")
                    || ((s.contains("组成家庭") || s.contains("再婚") || s.contains("现任")) && !(s.contains("未") || s.contains("没有")))) && (s.contains("生") || s.contains("育"))) {
                String[] sarray = s.split("，");
                int flag1 = 0;
                int flag2 = 0;
                for (String sp : sarray) {
                    if (sp.contains("生育") || sp.contains("育有")) {
                        if (sp.contains("均") || sp.contains("各自")) {
                            flag1 = 1;
                            break;
                        }
                        if ((sp.contains("生育") || sp.contains("育有")) && sp.contains(yb)) {
                            String jg = ifDisMatch(zvKeys, ybgKeys, sp);
                            if (jg.contains(yb.charAt(0) + "")) {
                                flag1 = 1;
                                break;
                            } else if (!jg.equals("")) {
                                flag1 = 2;
                            }

                        }
                    }
                }
                for (String sp : sarray) {
                    if ((sp.contains("前妻") || sp.contains("前夫")
                            || sp.contains("组成家庭") || sp.contains("再婚") || sp.contains("现任") || sp.contains("建立家庭"))) {
                        if (sp.contains("均") || sp.contains("各自")) {
                            flag2 = 1;
                            break;
                        }
                        if (sp.contains(yb)) {
                            String jg = ifDisMatch(zhKeys, ybgKeys, sp);
                            if (jg.contains(yb.charAt(0) + "")) {
                                flag2 = 1;
                                break;
                            } else if (!jg.equals("")) {
                                flag2 = 2;
                            }

                        }
                    }
                }
                if (flag1 * flag2 == 1) {
                    ifcz = "是";
                    break;
                } else if (flag1 == 2 || flag2 == 2) {
                    break;
                } else {
                    String znjg = "";
                    String zhjg = "";
                    if (flag1 == 1) {
                        zhjg = ifDisMatch(zhKeys, ybgKeys, s);
                        if (zhjg.contains(yb.charAt(0) + "")) {
                            ifcz = "是";
                            break;
                        }
                    }
                    if (flag2 == 1) {
                        znjg = ifDisMatch(zvKeys, ybgKeys, s);
                        if (znjg.contains(yb.charAt(0) + "")) {
                            ifcz = "是";
                            break;
                        }
                    } else {
                        zhjg = ifDisMatch(zhKeys, ybgKeys, s);
                        if (zhjg.contains(yb.charAt(0) + "")) {
                            flag1 = 1;
                        }
                        znjg = ifDisMatch(zvKeys, ybgKeys, s);
                        if (znjg.contains(yb.charAt(0) + "")) {
                            flag2 = 1;
                        }
                        if (flag1 * flag2 == 1) {
                            ifcz = "是";

                        }
                        break;
                    }


                }
            }
        }
        return ifcz;
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
}

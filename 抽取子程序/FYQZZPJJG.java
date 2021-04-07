
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 判断最终判决结果抚养权归属于哪一方
 */
public class FYQZZPJJG {

    public Element extract(XWPFDocument doc, String text) {
        Element FYQZZPJJG = DocumentHelper.createElement("FYQZZPJJG");
        FYQZZPJJG.addAttribute("nameCN", "抚养权最终判决结果");

        String gsf = "";
        gsf = getPJJG(text);
        if (gsf.equals("")) {
            gsf = "无此信息";
        }

        FYQZZPJJG.addAttribute("value", gsf);
        return FYQZZPJJG;
    }

    private String getPJJG(String text) {
        String gsf = "";
        String[] strArray = text.split("。|，|；");
        for (String s : strArray) {
            if (s.contains("抚养") || s.contains("抚育") || s.contains("生活") || s.contains("抚 养")) {
                if (s.contains("驳回") || s.contains("不予支持")) {
                    gsf = "驳回，仍：" + getFYQ(text);
                    break;
                } else {
                    String news = "";
                    if (s.contains("变更")) {
                        news += s.substring(s.indexOf("变更"));
                    } else {
                        int start = 0;
                        if (s.contains("由")) {
                            start = s.indexOf("由");
                        } else if (s.contains("随")) {
                            start = s.indexOf("随");
                        }
                        news += s.substring(start);
                    }
                    if (!news.equals("")) {
                        if (news.contains("原告")) {
                            gsf = "原告";
                            break;
                        } else if (news.contains("被告")) {
                            gsf = "被告";
                            break;
                        }
                    }
                }
            }
        }
        return gsf;

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
}

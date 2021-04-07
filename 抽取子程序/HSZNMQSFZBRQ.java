import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 判断婚生子女目前的是否在哺乳期
 */
public class HSZNMQSFZBRQ {

    public Element extract(XWPFDocument doc, String text) {

        Element HSZNMQSFZBRQ = DocumentHelper.createElement("HSZNMQSFZBRQ");
        HSZNMQSFZBRQ.addAttribute("nameCN", "婚生子女目前是否在哺乳期");

        String[] allarray = text.split("。");
        String ifcz = "否";
        for(String s:allarray){
            if(s.contains("哺乳期内")||s.contains("在哺乳期")||s.contains("需哺乳")){
                ifcz = "是";
                break;
            }
        }
        HSZNMQSFZBRQ.addAttribute("value", ifcz);

        return HSZNMQSFZBRQ;
    }
}

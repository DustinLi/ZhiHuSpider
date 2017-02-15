package spider.utils.html;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import zhiHuUserSprider.service.util.JsoupUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 清除html中的空格，ctrl
 * <p>
 * Created by lishilin on 2017/2/15.
 */
public class MyHtmlTool {

    public static String removeNBSP(String html) {

        return commonTool(html, "\\s");
    }

    public static String removeCtrl(String html) {

        return commonTool(html, "\r\n");
    }

    public static String commonTool(String input, String expression) {

        Matcher matcher;
        Pattern pattern;
        pattern = Pattern.compile(expression);
        matcher = pattern.matcher(input);
        String cleanedStr = matcher.replaceAll("");
        return cleanedStr;
    }

    public static String remainTag(String input, String... tags) {

        Whitelist whitelist = Whitelist.none();
        whitelist.addTags(tags);
        String result = Jsoup.clean(input, whitelist);
        return result;
    }

    public static List<String[]> matchByGroup(String input, String expression) {
        ArrayList<String[]> list = new ArrayList<>();
        Matcher matcher = Pattern.compile(expression).matcher(input);
        while (matcher.find()) {
            int count = matcher.groupCount() + 1;
            String[] strs = new String[count];
            for (int i = 1; i < count; i++) {
                strs[i] = matcher.group(i);
            }
            list.add(strs);
        }
        return list;
    }

    public static void main(String[] args) {

        String htmlCleaned = JsoupUtils.getDocument("http://www.rrjc.com/login.do");
        System.out.println(htmlCleaned);
//        String htmlCleaned = MyHtmlTool.remainTag(html, "tr", "td");
        htmlCleaned = MyHtmlTool.removeCtrl(htmlCleaned);
        htmlCleaned = MyHtmlTool.removeNBSP(htmlCleaned);
        System.out.println(htmlCleaned);
        List<String[]> sresult = MyHtmlTool.matchByGroup(htmlCleaned, "<tr><td></td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td></tr>");
        System.out.println(sresult);
    }
}

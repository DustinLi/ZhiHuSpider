package spider;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zhiHuUserSprider.service.util.JsoupUtils;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by lishilin on 2017/1/31.
 */
public class WeiboSpider {

    public static void main(String[] args) throws IOException, InterruptedException {
//        String url = "http://m.weibo.cn/container/getIndex?uid=1415334141&luicode=10000011&lfid=1076031415334141&sudaref=m.weibo.cn&retcode=6102&type=uid&value=1415334141&containerid=1076031415334141";
//        String uid = "5465081672";
        String[] strs = {"1344668552"
                , "1775109112"
                , "2175796227"
                , "1238543194"
                , "1811022885"
                , "1720190012"
                , "1772749943"
                , "1760695383"
                , "3044545740"
                , "1643386091"
                , "3256095983"
                , "1102141664"
                , "1810502982"
                , "3062109527"
                , "5542122841"
                , "5079307616"
                , "5706093987"
                , "2863535571"
                , "3504739901"
                , "1232344650"
                , "2489849513"
                , "1794905844"
                , "5329262329"
                , "1303910221"
                , "1769871202"
                , "3946190706"
                , "5354117442"
                , "5767436200"
                , "1803687851"
                , "5185476532"
                , "3757840312"
                , "3158972347"
                , "1632638914"
                , "1872762823"
                , "1896481783"
                , "1801367203"
                , "5852633043"
                , "2244220912"
                , "1707743230"
                , "3243120642"
                , "2378793094"
                , "5496045720"
        };
        for (int i = 0; i < strs.length; i++) {
            doMain(strs[i]);
        }

    }

    private static void doMain(String uid) throws InterruptedException, IOException {
        String url = "http://m.weibo.cn/container/getIndex?uid=" + uid + "&luicode=10000011&lfid=107603" + uid + "&sudaref=m.spider.cn&retcode=6102&type=uid&value=" + uid + "&containerid=107603" + uid;
        String html = JsoupUtils.getDocument(url);
        JSONObject jsonObject = JSONObject.fromObject(html);
        JSONObject cardlist = jsonObject.getJSONObject("cardlistInfo");
        int total = cardlist.getInt("total");
        StringBuffer stringBuffer = new StringBuffer();
        appendString(stringBuffer, jsonObject.getJSONArray("cards"));
        FileWriter writer;
        int count = (total + 9) / 10;
        int tag = count;
        if (tag > 100) tag = 100;
        for (int i = 1; i <= tag; i++) {
            String result = JsoupUtils.getDocument(url + "&page=" + i);
            JSONObject jsonResult = JSONObject.fromObject(result);
            JSONArray jsonArray = jsonResult.getJSONArray("cards");
            appendString(stringBuffer, jsonArray);
//            Thread.sleep(100);
        }
        String str = stringBuffer.toString();

        writer = new FileWriter("E:\\聂颖杰\\" + uid + ".txt");
        writer.write(str);
        writer.flush();
        writer.close();
        System.out.println(total);
    }

    private static void appendString(StringBuffer stringBuffer, JSONArray jsonArray) {

        for (int j = 0; j < jsonArray.size(); j++) {

            JSONObject item = jsonArray.getJSONObject(j);
            JSONObject mblogJson;
            String text = "";
            try {
                mblogJson = item.getJSONObject("mblog");
                text = mblogJson.getString("text");
            } catch (Exception e) {
                continue;
            }
            if (text.contains("转发微博") || text.contains("轉發微博")) {
                continue;
            }
            text = text.replaceAll("</br>", ",");
            text = text.replaceAll("<.*?/.*?>", "");
            System.out.println(text);
            String createTime = mblogJson.getString("created_at");
            String strCheck = createTime.substring(0, 2);
            if (!"20".equals(strCheck)) {
                createTime = "2017-" + createTime;
            }
            stringBuffer.append(text);
            stringBuffer.append("\n");
            stringBuffer.append(createTime);
            stringBuffer.append("\n");
        }
    }

}

package spider;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import zhiHuUserSprider.service.util.HttpUtils;
import zhiHuUserSprider.service.util.JsoupUtils;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by lishilin on 2017/1/31.
 */
public class WeiboSpider {

    private static Executor executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws IOException, InterruptedException {
//        String url = "http://m.weibo.cn/container/getIndex?uid=1415334141&luicode=10000011&lfid=1076031415334141&sudaref=m.weibo.cn&retcode=6102&type=uid&value=1415334141&containerid=1076031415334141";
//        String uid = "5465081672";
        BufferedReader proxyIpReader = new BufferedReader(new InputStreamReader(HttpUtils.class.getResourceAsStream("/shuju.txt")));

        String ip = "";
        while ((ip = proxyIpReader.readLine()) != null) {
            final String temp = ip;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        doMain(temp);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
//        for (int i = 0; i < strs.length; i++) {
//            doMain(strs[i]);
//        }

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
        if (tag > 60) tag = 60;
        for (int i = 1; i <= tag; i++) {
            String result = JsoupUtils.getDocument(url + "&page=" + i);
            JSONObject jsonResult = JSONObject.fromObject(result);
            JSONArray jsonArray = jsonResult.getJSONArray("cards");
            appendString(stringBuffer, jsonArray);
            Random random = new Random();
            int temp = random.nextInt(10000);
            Thread.sleep(temp + 1000);
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

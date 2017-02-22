package zhiHuUserSprider.service.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class JsoupUtils {

    public static String getDocument(String url) {
        try {
//				String result=Jsoup.connect(url).ignoreContentType(true).execute().body();
            Connection.Response res = Jsoup.connect(url)
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(10000).ignoreContentType(true).execute();//.get();
            String document = res.body();
            if (document == null || document.toString().trim().equals("")) {// 表示ip被拦截或者其他情况
                System.out.println("出现ip被拦截或者其他情况");
                HttpUtils.setProxyIp();
                getDocument(url);
            }
            return document;
        } catch (Exception e) { // 链接超时等其他情况
            System.out.println("出现链接超时等其他情况");
            HttpUtils.setProxyIp();// 换代理ip
            getDocument(url);// 继续爬取网页
        }
        return getDocument(url);
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            String urlWWwwweiboTestSlave = "http://m.weibo.cn/container/getIndex?uid=2351693951&luicode=10000011&lfid=1076032351693951&sudaref=m.spider.cn&retcode=6102&type=uid&value=2351693951&containerid=1076032351693951";
//		String url="https://www.baidu.com/";
//		String url="http://s.weibo.com/user/%25E4%25B8%258D%25E6%258B%2594%25E5%2585%2585%25E7%2594%25B5%25E5%2599%25A8&Refer=index";
            System.out.println(getDocument(urlWWwwweiboTestSlave));
            System.out.println("测试git的删除、修改、添加");
        }

    }
}

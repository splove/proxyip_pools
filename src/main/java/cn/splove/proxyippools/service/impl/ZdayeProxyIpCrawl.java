/*
 * 文件名：CrawlProxyIp.java 版权：Copyright by www.sdhuijin.cn 描述： 修改人：sunp@sdhuijin.cn 修改时间：2019年8月13日
 * 修改内容：
 */

package cn.splove.proxyippools.service.impl;


import cn.splove.proxyippools.component.channel.ChannelPoolSubject;
import cn.splove.proxyippools.service.IpProxyCrawlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;


@Service("zdayeProxyIpCrawl")
public class ZdayeProxyIpCrawl implements IpProxyCrawlService {

    private Logger log = LoggerFactory.getLogger(ZdayeProxyIpCrawl.class);

    @Autowired
    private ChannelPoolSubject subject;

    @Override
    @Async(value = "proxyIpAsyncServiceExecutor")
    public Future<String> crawlAndSave() {
//        Thread t = Thread.currentThread();
//        System.out.println(t.getName() + "\t####  抓取站大人代理  ####");
//        List<Integer> nums = crawlPagesFromZDaYe();
//        Map<String, String> configs = ConfigUtils.readConfig(
//                ResourceUtils.CLASSPATH_URL_PREFIX + "zdaye.txt");
//        for (int num : nums) {
//            List<Map<String, Object>> list = crawlFromZDaYe(num);
//            for (Map<String, Object> m : list) {
//                if (m.containsKey("ip") && m.containsKey("port")) {
//                    String ip = m.get("ip").toString();
//                    int port = Integer.parseInt(m.get("port").toString());
//                    subject.addProxyIp(new ProxyIp(ip, port));
//                }
//            }
//            configs.put("cur_page", String.valueOf(num));
//        }
//        ConfigUtils.writeConfig(ResourceUtils.CLASSPATH_URL_PREFIX + "zdaye.txt", configs);
        return new AsyncResult<String>("站大人代理抓取任务完成");
    }

//    private List<Integer> crawlPagesFromZDaYe() {
//        Map<String, String> header = new HashMap<>();
//        header.put("User-Agent",
//                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
//        int id = 0;
//        HttpRequest request = HttpUtil.createGet("http://ip.zdaye.com/dayProxy.html");
//        request.addHeaders(header);
//        String pageContent = request.execute().body();
//        Document doc = Jsoup.parse(pageContent);
//        if (!doc.select("A[class='thread_theme_type']").isEmpty()) {
//            String href = doc.select("A[class='thread_theme_type']").first().attr("href");
//            String idStr = href.replaceAll("/dayProxy/ip/|.html", "");
//            id = Integer.parseInt(idStr);
//        }
//        Map<String, String> configs = ConfigUtils.readConfig(
//                ResourceUtils.CLASSPATH_URL_PREFIX + "zdaye.txt");
//        int oldNum = 0;
//        if (configs.containsKey("cur_page") && StringUtils.isNotBlank(configs.get("cur_page"))) {
////            oldNum = Integer.valueOf(configs.get("cur_page"));
//        }
//        List<Integer> list = new ArrayList<>();
//        if (oldNum < id) {
//            int start = oldNum + 1;
//            if (id - oldNum > 20) {
//                start = id - 20;
//            }
//            for (int i = start; i <= id; i++) {
//                list.add(i);
//            }
//        }
//        return list;
//    }
//
//    private List<Map<String, Object>> crawlFromZDaYe(int pageNum) {
//        Map<String, String> header = new HashMap<>();
//        header.put("Referer", "http://ip.zdaye.com/dayProxy/2019/8/1.html");
//        header.put("User-Agent",
//                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
//
//        HttpRequest request = HttpUtil.createGet(
//                "http://ip.zdaye.com/dayProxy/ip/" + pageNum + ".html");
//        request.addHeaders(header);
//        String pageContent = request.execute().body();
//
//        List<Map<String, Object>> list = new ArrayList<>();
//        Document doc = Jsoup.parse(pageContent);
//        if (!doc.select("div .cont").isEmpty()) {
//            Pattern pt = Pattern.compile("<br>.*?@HTTP");
//            Matcher mt2 = pt.matcher(doc.select("div .cont").first().html());
//            while (mt2.find()) {
//                Map<String, Object> map = new HashMap<>();
//                String ip = mt2.group().replaceAll("<br>|@HTTP", "");
//                String[] strs = ip.split(":");
//                if (strs.length == 2) {
//                    map.put("ip", strs[0]);
//                    map.put("port", Integer.valueOf(strs[1]));
//                }
//                list.add(map);
//            }
//        }
//        return list;
//    }
}

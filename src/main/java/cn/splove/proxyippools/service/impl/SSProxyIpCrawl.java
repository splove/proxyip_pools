package cn.splove.proxyippools.service.impl;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.splove.proxyippools.component.channel.ChannelPoolSubject;
import cn.splove.proxyippools.domain.ProxyIp;
import cn.splove.proxyippools.service.IpProxyCrawlService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 66 代理 http://www.66ip.cn
 */
@Component("66ProxyIpCrawl")
public class SSProxyIpCrawl implements IpProxyCrawlService {

    @Autowired
    private ChannelPoolSubject subject;

    @Override
    @Async(value = "proxyIpAsyncServiceExecutor")
    public Future<String> crawlAndSave() {
        crawlPagesFrom66();
        return new AsyncResult<String>("66代理抓取任务完成");
    }

    private void crawlPagesFrom66() {
        Thread t = Thread.currentThread();
        System.out.println(t.getName() + "\t####  抓取66代理  ####");
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        for (int i = 1; i <= 5; i++) {
            HttpRequest request = HttpUtil.createGet("http://www.66ip" +
                    ".cn/areaindex_35/" + i + ".html");
            request.addHeaders(header);
            String pageContent = request.execute().body();
            Document doc = Jsoup.parse(pageContent);
            if (!doc.select("table").isEmpty()) {
                Elements elements = doc.select("table").select("tbody tr:not(:first-child)");
                for (Element e : elements) {
                    String ip = e.child(0).text().trim();
                    int port = Integer.parseInt(e.child(1).text().trim());
                    subject.addProxyIp(new ProxyIp(ip, port));
                }
            }
        }
    }
}

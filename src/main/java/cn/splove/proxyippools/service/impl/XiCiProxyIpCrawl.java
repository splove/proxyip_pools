/*
 * 文件名：XiCiProxyIpCrawlService.java 版权：Copyright by www.sdhuijin.cn 描述： 修改人：sunp@sdhuijin.cn
 * 修改时间：2019年8月23日 修改内容：
 */

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


@Component("xiCiProxyIpCrawl")
public class XiCiProxyIpCrawl implements IpProxyCrawlService {

    @Autowired
    private ChannelPoolSubject subject;

    @Override
    @Async(value = "proxyIpAsyncServiceExecutor")
    public Future<String> crawlAndSave() {
        crawlPagesFromXiCi();
        return new AsyncResult<String>("西刺代理抓取任务完成");
    }

    private void crawlPagesFromXiCi() {
        Thread t = Thread.currentThread();
        System.out.println(t.getName() + "\t####  抓取西刺代理  ####");
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        for (int i = 1; i <= 10; i++) {
            HttpRequest request = HttpUtil.createGet("https://www.xicidaili.com/nn/" + i);
            request.addHeaders(header);
            String pageContent = request.execute().body();
            Document doc = Jsoup.parse(pageContent);
            if (!doc.select("#ip_list").isEmpty()) {
                Elements elements = doc.select("#ip_list tbody tr:not(:first-child)");
                for (Element e : elements) {
                    if (e.child(4).text().equals("高匿")) {
                        String ip = e.child(1).text();
                        int port = Integer.parseInt(e.child(2).text());
                        subject.addProxyIp(new ProxyIp(ip, port));
                    }
                }
            }
        }
    }
}

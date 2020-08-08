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


@Component("89ProxyIpCrawl")
public class ENProxyIpCrawl implements IpProxyCrawlService {

    @Autowired
    private ChannelPoolSubject subject;

    @Override
    @Async(value = "proxyIpAsyncServiceExecutor")
    public Future<String> crawlAndSave() {
        crawlPagesFrom89();
        return new AsyncResult<>("89代理抓取任务完成");
    }

    private void crawlPagesFrom89() {
        Thread t = Thread.currentThread();
        System.out.println(t.getName() + "\t####  抓取89代理  ####");
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        for (int i = 1; i <= 10; i++) {
            HttpRequest request = HttpUtil.createGet("http://www.89ip" +
                    ".cn/index_" + i + ".html");
            request.addHeaders(header);
            String pageContent = request.execute().body();
            Document doc = Jsoup.parse(pageContent);
            if (!doc.select(".layui-table").isEmpty()) {
                Elements elements = doc.select(".layui-table tbody tr");
                for (Element e : elements) {
                    String ip = e.child(0).text().trim();
                    int port = Integer.parseInt(e.child(1).text().trim());
                    subject.addProxyIp(new ProxyIp(ip, port));
                }
            }
        }
    }
}

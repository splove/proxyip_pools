package cn.splove.proxyippools.task;

import cn.splove.proxyippools.component.channel.ChannelPool;
import cn.splove.proxyippools.component.channel.ChannelPoolObserver;
import cn.splove.proxyippools.component.channel.ChannelPoolSubject;
import cn.splove.proxyippools.service.IpProxyCrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.Future;

@Component
public class AutoCrawlProxyIp {

    @Resource(name = "zdayeProxyIpCrawl")
    private IpProxyCrawlService zDaRenProxyIpCrawl;

    @Resource(name = "xiCiProxyIpCrawl")
    private IpProxyCrawlService xiCiProxyIpCrawl;

    @Resource(name = "89ProxyIpCrawl")
    private IpProxyCrawlService enProxyIpCrawl;

    @Resource(name = "66ProxyIpCrawl")
    private IpProxyCrawlService ssProxyIpCrawl;

    @Autowired
    private ChannelPoolSubject subject;

    @Resource(name = "wxArticleChannelPool")
    private ChannelPoolObserver wxArticleChannelPoolObserver;

    @Resource(name = "wxReadChannelPool")
    private ChannelPoolObserver wxReadChannelPoolObserver;

    @Resource(name = "wxArticleChannelPool")
    private ChannelPool wxArticleChannelPool;

    @Resource(name = "wxReadChannelPool")
    private ChannelPool wxReadChannelPool;

    @PostConstruct
    public void crawl() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                subject.add(wxArticleChannelPoolObserver);
                subject.add(wxReadChannelPoolObserver);

                boolean xiCiDone = true;
                boolean ssDone = true;
                boolean enDone = true;
                Future<String> xiCiResult = null;
                Future<String> ssResult = null;
                Future<String> enResult = null;
                do {
                    if (xiCiDone) {
                        xiCiResult = xiCiProxyIpCrawl.crawlAndSave();
                    }
                    xiCiDone = xiCiResult.isDone();
                    if (ssDone) {
                        ssResult = ssProxyIpCrawl.crawlAndSave();
                    }
                    ssDone = ssResult.isDone();
                    if (enDone) {
                        enResult = enProxyIpCrawl.crawlAndSave();
                    }
                    enDone = enResult.isDone();
                    try {
                        Thread.sleep(120 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (true);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    wxArticleChannelPool.autoClean();
                    wxReadChannelPool.autoClean();
                    try {
                        Thread.sleep(90 * 60 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (true);
            }
        }).start();
    }
}

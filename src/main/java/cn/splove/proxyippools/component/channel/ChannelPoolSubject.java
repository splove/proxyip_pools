package cn.splove.proxyippools.component.channel;

import cn.splove.proxyippools.domain.ProxyIp;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * ip频道池主题
 */
@Component
public class ChannelPoolSubject {

    /**
     * 频道列表
     */
    private List<ChannelPoolObserver> channelPoolList = new ArrayList<>();

    /**
     * 增加频道池
     *
     * @param channelPool 频道池
     */
    public void add(ChannelPoolObserver channelPool) {
        channelPoolList.add(channelPool);
    }

    /**
     * 删除频道池
     *
     * @param channelPool 频道池
     */
    public void remove(ChannelPoolObserver channelPool) {
        channelPoolList.remove(channelPool);
    }

    /**
     * 通知所有观察者-频道池有新的代理ip
     *
     * @param proxyIp 代理ip
     */
    public void addProxyIp(ProxyIp proxyIp) {
        for (ChannelPoolObserver channelPool : channelPoolList) {
            channelPool.addProxyIp(proxyIp);
        }
    }
}

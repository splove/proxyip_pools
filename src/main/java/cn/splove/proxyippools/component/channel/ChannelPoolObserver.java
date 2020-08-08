package cn.splove.proxyippools.component.channel;

import cn.splove.proxyippools.domain.ProxyIp;

/**
 * 抽象观察者-频道池
 */
public interface ChannelPoolObserver {
    /**
     * 存储代理ip
     *
     * @param proxyIp
     */
    void addProxyIp(ProxyIp proxyIp);
}

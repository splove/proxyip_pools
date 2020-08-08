package cn.splove.proxyippools.component.channel;

import cn.splove.proxyippools.domain.ProxyIp;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

/**
 * 代理ip池
 */
public abstract class ChannelPool {

    @Autowired
    public JedisPool pool;

    /**
     * 同步代理ip池
     */
    public abstract void synchronizedIps();

    /**
     * 自清理
     */
    public abstract void autoClean();

    /**
     * 取出一个代理ip
     *
     * @return 代理ip
     */
    public abstract ProxyIp pull();

    /**
     * 归还一个代理ip
     *
     * @param p 待归还的代理ip
     */
    public abstract void push(ProxyIp p);

}

package cn.splove.proxyippools.component.channel.realPools;

import cn.hutool.core.util.StrUtil;
import cn.splove.proxyippools.component.channel.ChannelPool;
import cn.splove.proxyippools.component.channel.ChannelPoolObserver;
import cn.splove.proxyippools.component.channel.utils.IpProxyTestUtil;
import cn.splove.proxyippools.domain.ProxyIp;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * 请求使用的代理ip池
 */
@Component(value = "wxArticleChannelPool")
public class WXArticleChannelPool extends ChannelPool implements ChannelPoolObserver {

    private static final int POOL_MIN_PROXY_IP_NUM = 20;
    private final String WX_ARTICLE_PROXY_IP_CHANNEL_POOL =
            "articleProxyIpPool";

    @Override
    public void synchronizedIps() {
        // TODO 待开发
    }

    @Override
    public void autoClean() {
        System.out.println("####  start clean wx article pool  ####");
        FastJsonRedisSerializer<ProxyIp> serializer =
                new FastJsonRedisSerializer<>(ProxyIp.class);
        Jedis jedis = pool.getResource();
        List<String> list = jedis.lrange(WX_ARTICLE_PROXY_IP_CHANNEL_POOL, 0,
                jedis.llen(WX_ARTICLE_PROXY_IP_CHANNEL_POOL));
        ProxyIp proxyIp;
        for (String str : list) {
            proxyIp = serializer.deserialize(str.getBytes());
            if (proxyIp != null) {
                long timeConsuming =
                        IpProxyTestUtil.testWxArticleProxyIpEnable(proxyIp.getIp(),
                                proxyIp.getPort());
                if (timeConsuming < 0) {
                    jedis.lrem(WX_ARTICLE_PROXY_IP_CHANNEL_POOL, 0, str);
                }
            }
        }
    }

    @Override
    public ProxyIp pull() {
        ProxyIp proxyIp = null;
        try (Jedis jedis = pool.getResource()) {
            String rpop = jedis.rpop(WX_ARTICLE_PROXY_IP_CHANNEL_POOL);
            if (StrUtil.isNotBlank(rpop)) {
                FastJsonRedisSerializer<ProxyIp> serializer =
                        new FastJsonRedisSerializer<>(ProxyIp.class);
                proxyIp = serializer.deserialize(rpop.getBytes());
            }
        }
        return proxyIp;
    }

    @Override
    public void push(ProxyIp p) {
        try (Jedis jedis = pool.getResource()) {
            if (null != p) {
                FastJsonRedisSerializer<ProxyIp> serializer =
                        new FastJsonRedisSerializer<>(ProxyIp.class);
                jedis.lrem(WX_ARTICLE_PROXY_IP_CHANNEL_POOL.getBytes(), 0,
                        serializer.serialize(p));
                jedis.lpush(WX_ARTICLE_PROXY_IP_CHANNEL_POOL.getBytes(),
                        serializer.serialize(p));
            }
        }
    }

    @Override
    public void addProxyIp(ProxyIp proxyIp) {
        long timeConsuming =
                IpProxyTestUtil.testWxArticleProxyIpEnable(proxyIp.getIp(),
                        proxyIp.getPort());
        if (timeConsuming > 0) {
            this.push(proxyIp);
        }
    }
}

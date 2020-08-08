package cn.splove.proxyippools.component.channel.utils;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class IpProxyTestUtil {
    /**
     * Description:  检测微信读书代理ip是否可用
     *
     * @param ip   代理ip
     * @param port ip端口號
     * @return 响应时间（异常返回-1）
     */
    public static Long testWxReadProxyIpEnable(String ip, int port) {
        Map<String, String> heads = new HashMap<>();
        heads.put("User-Agent", "okhttp/3.12.1");
        Proxy p = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        try {
            LocalDateTime beginTime = LocalDateTime.now();
            int status = HttpUtil.createGet("https://i.weread.qq.com:443").setProxy(p).addHeaders(
                    heads).timeout(10 * 1000).execute(true).getStatus();
            Long timeConsuming = Duration.between(beginTime, LocalDateTime.now()).toMillis();
            if (status == 404) {
                return timeConsuming;
            } else {
                return -1L;
            }
        } catch (Exception e) {
            return -1L;
        }
    }

    /**
     * 检测微信公众号文章代理ip是否可用
     *
     * @param ip   代理ip
     * @param port 代理ip端口号
     * @return 响应时间（异常返回-1）
     */
    public static Long testWxArticleProxyIpEnable(String ip, int port) {
        Map<String, String> heads = new HashMap<>();
        Proxy p = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        try {
            LocalDateTime beginTime = LocalDateTime.now();
            int status = HttpUtil.createGet("https://mp.weixin.qq.com/mp/timereport").setProxy(p).addHeaders(
                    heads).timeout(10 * 1000).execute(true).getStatus();
            Long timeConsuming = Duration.between(beginTime, LocalDateTime.now()).toMillis();
            if (status == 200) {
                return timeConsuming;
            } else {
                return -1L;
            }
        } catch (Exception e) {
            return -1L;
        }
    }

    public static void main(String[] args) {
        Map<String, String> heads = new HashMap<>();
        heads.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        Proxy p = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("218.95.37.252", 3128));
        try {
            HttpResponse response = HttpUtil.createRequest(Method.GET,
                    "http://mp.weixin.qq.com/s?__biz=MjM5NzY2OTE2MQ%3D%3D&mid=2652216531&idx=1&sn=508a9974762b2b2221113cf6bda5a5cd&scene=45").execute();
            String body = HttpUtil.get("https://mp.weixin.qq.com/s?__biz=MjM5NzY2OTE2MQ%3D%3D&mid=2652216531&idx=1&sn=508a9974762b2b2221113cf6bda5a5cd&scene=45#wechat_redirect");
            System.out.println(response.getStatus() + response.body());
            System.out.println("####" + body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

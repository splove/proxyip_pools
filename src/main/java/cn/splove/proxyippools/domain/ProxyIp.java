package cn.splove.proxyippools.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 代理IP
 */
@Data
public class ProxyIp implements Serializable {

    /**
     * ip地址
     */
    private String ip;

    /**
     * ip端口号
     */
    private int port;

    public ProxyIp() {

    }

    public ProxyIp(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
}

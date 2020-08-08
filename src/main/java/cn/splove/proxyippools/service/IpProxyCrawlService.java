/*
 * 文件名：IpProxyCrawlService.java 版权：Copyright by www.sdhuijin.cn 描述： 修改人：sunp@sdhuijin.cn
 * 修改时间：2019年8月13日 修改内容：
 */

package cn.splove.proxyippools.service;

import java.util.concurrent.Future;

public interface IpProxyCrawlService {

    /**
     * Description: <br> 爬虫获取代理ip并保存
     *
     * @return
     * @see
     */
    Future<String> crawlAndSave();

}

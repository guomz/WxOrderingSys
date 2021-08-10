package com.guomz.orderingsys.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.lly835.bestpay.config.AliPayConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "alipay")
@Data
public class AlipayConfiguration {

    private String appid;
    private String gateway;
    private String publicKey;
    private String appPublicKey;
    private String privateKey;
    private String returnUrl;
    private String notifyUrl;

    /**
     * 廖师兄支付系统配置
     * @return
     */
    @Bean
    public AliPayConfig generateAlipayConfig(){
        AliPayConfig aliPayConfig = new AliPayConfig();
        aliPayConfig.setAppId(appid);
        aliPayConfig.setPrivateKey(privateKey);
        aliPayConfig.setAliPayPublicKey(publicKey);
        aliPayConfig.setReturnUrl(returnUrl);
        aliPayConfig.setNotifyUrl(notifyUrl);
        //开启沙箱环境
        aliPayConfig.setSandbox(true);
        return aliPayConfig;
    }

    /**
     * 支付宝官方配置
     * @return
     */
    @Bean
     public Config generateEasySdkConfig(){
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipaydev.com";
        config.signType = "RSA2";

        config.appId = appid;

        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = privateKey;

        //注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
//        config.merchantCertPath = "<-- 请填写您的应用公钥证书文件路径，例如：/foo/appCertPublicKey_2019051064521003.crt -->";
//        config.alipayCertPath = "<-- 请填写您的支付宝公钥证书文件路径，例如：/foo/alipayCertPublicKey_RSA2.crt -->";
//        config.alipayRootCertPath = "<-- 请填写您的支付宝根证书文件路径，例如：/foo/alipayRootCert.crt -->";

        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
         config.alipayPublicKey = publicKey;

        //可设置异步通知接收服务地址（可选）
        config.notifyUrl = notifyUrl;

        //可设置AES密钥，调用AES加解密相关接口时需要（可选）
//        config.encryptKey = "<-- 请填写您的AES密钥，例如：aa4BtZ4tspm2wnXLb1ThQA== -->";
         Factory.setOptions(config);
        return config;
    }
}

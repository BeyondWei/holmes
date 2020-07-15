package com.shuzheng.holmes.demo;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import com.shuzheng.holmes.sdk.flume.common.LoggingAdapter;
import com.shuzheng.holmes.sdk.flume.logback.LogbackAdapterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * @author linbingwen
 * @2015年5月18日9:14:21
 */
public class LogbackTest {
//    private static Logger logger = LoggerFactory.getLogger(LogbackTest.class);
    private static LoggingAdapter logger = new LogbackAdapterFactory().create(LogbackTest.class);

    /**
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        // System.out.println("This is println message.");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int i = 0;
        String msg = "2020-07-08 10:59:58,423 INFO (AspceJService.java:134)- interfaceCode:M3dfkN6d88mWekf4,requestId:4831496ccf3f4d499adc80eb561a0f80,appKey:c7cb11361df94d1889162a6a03f70ab1,total:30ms,server:11ms,gateway:19ms,invokeCacheTime:0ms,proceedTime:10ms,vaildTime:{AccessValid:4ms,CacheRecordData:0ms,CacheRecordAccessValid:1ms,AuthorityValid:4ms,AppRightVaild:1ms,PowerInvokeLog:0ms,CountryPlatformInvokeValid:0ms,DataValid:1ms,ParamsValid:1ms,IdentityVerifiyValid:3ms,InvokeCountValid:2ms,LogHubInvokeValid:0ms,InterfaceRightValid:0ms},resultCode:00,resultMsg:成功,resultData:{\"data\":{\"cityNo\":\"330300\",\"extInfo\":{},\"idcardNo\":\"330324197908135200\",\"idcardType\":\"IDENTITY_CARD\",\"level\":\"green\",\"name\":\"盛陈红\",\"phone\":\"\",\"qrCode\":\"V2bdbc620bfe7e81217d2fcb6862c8a655\",\"qrCodeText\":\"https://h5.dingtalk.com/healthAct/index.html?qrCode=V2bdbc620bfe7e81217d2fcb6862c8a655#/result\",\"temperature\":{\"currentHealth\":[]},\"zjgxsj\":\"2020-02-16 16:17:41\"},\"errCode\":0,\"errMsg\":\"success\",\"success\":true,\"wrapped\":true}";
        Thread.sleep(1000);
        logger.info(msg);
//        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//        StatusPrinter.print(lc);
        while (true) {
            Thread.sleep(1000);
            // 记录debug级别的信息
            logger.info(msg);
            i++;
        }
    }

}
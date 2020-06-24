package com.shuzheng.holmes.server;

import org.apache.dolphinscheduler.remote.NettyRemotingServer;
import org.apache.dolphinscheduler.remote.command.CommandType;
import org.apache.dolphinscheduler.remote.config.NettyServerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

@ComponentScan("com.shuzheng.holmes.server")
public class ServerApplication {

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    /**
     * netty remote server
     */
    private NettyRemotingServer nettyRemotingServer;


    public static void main(String[] args) {
        new SpringApplicationBuilder(ServerApplication.class).web(WebApplicationType.NONE).run(args);
    }


    @PostConstruct
    public void start() {
        //todo zookeeper注册

        //todo 启动服务端开启kafka接收程序
        //init remoting server
        NettyServerConfig serverConfig = new NettyServerConfig();
        serverConfig.setListenPort(8888);
        this.nettyRemotingServer = new NettyRemotingServer(serverConfig);
//        this.nettyRemotingServer.registerProcessor(CommandType.TASK_EXECUTE_REQUEST, new TaskExecuteProcessor());
//        this.nettyRemotingServer.registerProcessor(CommandType.TASK_KILL_REQUEST, new TaskKillProcessor());
        this.nettyRemotingServer.start();

        /**
         *  register hooks, which are called before the process exits
         */
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                //close("shutdownHook");
            }
        }));
    }
}

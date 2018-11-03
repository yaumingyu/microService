package com.imooc.users.thrift;

import com.imooc.thrift.users.UsersService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2018/10/1 0001.
 */
@Configuration
public class ThriftService {

    @Value("${service.port}")
    private int servicePort;

    @Autowired
    private UsersService.Iface usersService;

    @PostConstruct
    public void startThriftService(){
        // 处理器
        TProcessor processor = new UsersService.Processor<>(usersService);

        TNonblockingServerSocket tNonblockingServerSocket = null;
        try{
            tNonblockingServerSocket = new TNonblockingServerSocket(servicePort);
        }catch (Exception e){
           e.printStackTrace();
        }

        TNonblockingServer.Args args = new TNonblockingServer.Args(tNonblockingServerSocket);
        args.processor(processor);
        args.transportFactory(new TFramedTransport.Factory());
        args.protocolFactory(new TBinaryProtocol.Factory());

        TServer server = new TNonblockingServer(args);
        server.serve();

    }
}

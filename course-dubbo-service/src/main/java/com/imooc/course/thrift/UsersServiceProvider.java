package com.imooc.course.thrift;



import com.imooc.thrift.users.UsersService;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * Created by Administrator on 2018/10/1 0001.
 */
@Component
public class UsersServiceProvider {

    @Value("${users.server.ip}")
    private String userServerIp;
    @Value("${users.server.port}")
    private int userServerPort;


    public enum ServerType{
        USER,
        MESSAGE
    }

    public UsersService.Client getUsersService(){
        UsersService.Client client = getService(userServerIp,userServerPort, ServerType.USER);
        System.out.print(client);
        return client;
    }


    private <T>T getService(String serverIp, int serverPort, ServerType serverType){
        TSocket tSocket =  new TSocket(serverIp, serverPort, 10000);
        TFramedTransport tFramedTransport = new TFramedTransport(tSocket);
        TServiceClient client = null;
        try {
            tFramedTransport.open();
        }catch (Exception e){
            e.printStackTrace();
        }
        TProtocol tProtocol = new TBinaryProtocol(tFramedTransport);
        switch(serverType){
            case USER:
                client = new UsersService.Client(tProtocol);
                break;

        }
        System.out.print(client);
        return (T)client;
    }
}

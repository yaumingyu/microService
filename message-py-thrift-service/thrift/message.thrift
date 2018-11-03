namespace java com.imooc.thrift.message
namespace py message.api
service MessageService{
    bool sentMobileMessage(1:string mobile, 2:string message);
    bool sentEmailMessage(1:string email, 2:string message);
}
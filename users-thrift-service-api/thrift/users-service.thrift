namespace java com.imooc.thrift.users

struct UsersInfo {
    1:string username,
    2:string password,
    3:string email,
    4:string mobile,
    5:i32 id,
    6:string realName,
    7:string intro,
    8:i32 stars
}
service UsersService {
        UsersInfo getUsersById(1:i32 id);
        UsersInfo getUsersByUsername(1:string username);
        UsersInfo getTeacherById(1:i32 id);
        void usersRegister(1:UsersInfo usersInfo);
}
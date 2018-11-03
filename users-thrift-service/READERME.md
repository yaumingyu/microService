docker服务化要点
1：制作基本镜像，拉取openjdk:8-jre：docker pull openjdk:8-jre；
验证是否可用：docker run -it --entrypoint bash openjdk:8-jre；查看内核：uname -a;查看jdk版本：java -version;
2：把一些经常变化的环境变量修改成为传递参数的形式，如mysql的地址修改为${mysqladdress}。
3：在pom文件引入spring-boot-package-plugin，版本继承parent。
4：打包users-thrift-service项目成jar包。
5：编写Dockerfile文件。
6：制作镜像：docker build -t users-service:latest .。
7：运行镜像：docker run -it users-service:latest --mysqladdress = 本地的ip。
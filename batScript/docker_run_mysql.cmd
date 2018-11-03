cd c:
cd Users
cd Administrator
docker stop imooc-mysql
docker rm imooc-mysql
docker run --name imooc-mysql -v ~/mysql/conf/my.cnf:/etc/mysql/conf.d/my.cnf -v ~/mysql/data:/var/lib/mysql -v ~/mysql/logs:/logs -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 --restart always -d mysql:5.7


d:
cd myredis
docker stop imooc-redis
docker rm imooc-redis
docker run --name imooc-redis -p 6379:6379 -v redis.conf:/etc/redis/redis_default.conf -v data:/data --restart always -d redis redis-server --appendonly yes
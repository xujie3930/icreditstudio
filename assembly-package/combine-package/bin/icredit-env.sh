deployUser=hadoop ##部署的用户，以上面创建的hadoop为例
ICREDITSTUDIO_HOME=/opt/icreditstudio  ##安装目录
ICREDITSTUDIO_VERSION=0.0.1  ##版本号

##service
PROFILE=dev ##启动的环境，如果需要新的环境，需要手动添加配置文件
NACOS_SERVER_ADDRESS=192.168.0.30:8848 ##nacos的地址
NACOS_DISCOVERY_NAMESPACE=dd8ede24-19c7-4547-8956-a566f800a823
##nacos的namespace
MYSQL_HOST=192.168.0.17 ##mysql数据库ip，如果需要每个服务单独一个数据库，需要手动修改具体服务的数据库配置，采用一键配置将所有的服务共用一个数据库
MYSQL_PORT=3306 ##mysql数据库端口
MYSQL_USER=icdstu ##mysql数据库用户名
MYSQL_PASSWORD=icdstu@0902 ##mysql数据库账号

REDIS_HOST=192.168.0.201 ##redis地址
REDIS_PORT=6379 ##redis端口

GATEWAY_PORT=13249 ##网关的监听端口

HIVE_HIVESERVER_USER=root ##hive的hiveserver2用户名
HIVE_HIVESERVER_PWSSWORD=bd@0414##hive的hiveserver2密码
HIVE_HIVESERVER_NODES=192.168.0.174:10000 ##hive的hiveserver2地址
HIVE_WAREHOUSE=/user/hive/warehouse/ ##hive的存储地址
defaultFS=192.168.0.174:8020 ##namenode地址

##scheduler
HDFS_ROOT_USER=root

##Zookeeper的地址
ZOOKEEPER_QUORUM=192.168.0.30:2181

#Actively load user env
source ~/.bash_profile
shellDir=`dirname $0`
workDir=`cd ${shellDir}/..;pwd`

#To be compatible with MacOS and Linux
txt=""
if [[ "$OSTYPE" == "darwin"* ]]; then
    txt="''"
elif [[ "$OSTYPE" == "linux-gnu" ]]; then
    # linux
    txt=""
elif [[ "$OSTYPE" == "cygwin" ]]; then
    echo "icreditstudio not support Windows operating system"
    exit 1
elif [[ "$OSTYPE" == "msys" ]]; then
    echo "icreditstudio not support Windows operating system"
    exit 1
elif [[ "$OSTYPE" == "win32" ]]; then
    echo "icreditstudio not support Windows operating system"
    exit 1
elif [[ "$OSTYPE" == "freebsd"* ]]; then

    txt=""
else
    echo "Operating system unknown, please tell us(submit issue) for better service"
    exit 1
fi

source ~/.bash_profile

export local_host="`hostname --fqdn`"

ipaddr=$(ip addr | awk '/^[0-9]+: / {}; /inet.*global/ {print gensub(/(.*)\/(.*)/, "\\1", "g", $2)}')

function checkPythonAndJava(){
    python --version
    isSuccess "execute python --version"
    java -version
    isSuccess "execute java --version"
}

function checkHadoopAndHive(){
    hadoopVersion="`hdfs version`"
    defaultHadoopVersion="2.7"
    checkversion "$hadoopVersion" $defaultHadoopVersion hadoop
    checkversion "$(whereis hive)" "2.3" hive
}

function checkversion(){
versionStr=$1
defaultVersion=$2
module=$3

result=$(echo $versionStr | grep "$defaultVersion")
if [ -n "$result" ]; then
    echo "$module version match"
else
   echo "WARN: Your $module version is not $defaultVersion, there may be compatibility issues:"
   echo " 1: Continue installation, there may be compatibility issues"
   echo " 2: Exit installation"
   echo ""
   read -p "Please input the choice:"  idx
   if [[ '2' = "$idx" ]];then
    echo "You chose  Exit installation"
    exit 1
   fi
fi
}

say() {
    printf 'check command fail \n %s\n' "$1"
}

err() {
    say "$1" >&2
    exit 1
}

check_cmd() {
    command -v "$1" > /dev/null 2>&1
}

need_cmd() {
    if ! check_cmd "$1"; then
        err "need '$1' (command not found)"
    fi
}

##load config
echo "step1:load config "
export ICREDIT_CONFIG_PATH=${ICREDIT_CONFIG_PATH:-"${workDir}/bin/icreidt-env.sh"}
source ${ICREDIT_CONFIG_PATH}


isSuccess "load config"

##env check
echo "Do you want to clear icreditstudio table information in the database?"
echo " 1: Do not execute table-building statements"
echo " 2: Dangerous! Clear all data and rebuild the tables"
echo " other: exit"
echo ""

MYSQL_INSTALL_MODE=1

read -p "Please input the choice:"  idx
if [[ '2' = "$idx" ]];then
  MYSQL_INSTALL_MODE=2
  echo "You chose Rebuild the table"
elif [[ '1' = "$idx" ]];then
  MYSQL_INSTALL_MODE=1
  echo "You chose not execute table-building statements"
else
  echo "no choice,exit!"
  exit 1
fi

MYSQL_PASSWORD=$(echo ${MYSQL_PASSWORD//'#'/'\#'})

#init db
if [[ '2' = "$MYSQL_INSTALL_MODE" ]];then
  mysql -h$MYSQL_HOST -P$MYSQL_PORT -u$MYSQL_USER -p$MYSQL_PASSWORD   --default-character-set=utf8 -e "source ${ICREDITSTUDIO_HOME}/db/${ICREDITSTUDIO_VERSION}/icreditstudio_service_ddl.sql"
  mysql -h$MYSQL_HOST -P$MYSQL_PORT -u$MYSQL_USER -p$MYSQL_PASSWORD   --default-character-set=utf8 -e "source ${ICREDITSTUDIO_HOME}/db/${ICREDITSTUDIO_VERSION}/icreditstudio_service_dml.sql"
  mysql -h$MYSQL_HOST -P$MYSQL_PORT -u$MYSQL_USER -p$MYSQL_PASSWORD   --default-character-set=utf8 -e "source ${ICREDITSTUDIO_HOME}/db/${ICREDITSTUDIO_VERSION}/icreditstudio_scheduler_ddl.sql"
  mysql -h$MYSQL_HOST -P$MYSQL_PORT -u$MYSQL_USER -p$MYSQL_PASSWORD   --default-character-set=utf8 -e "source ${ICREDITSTUDIO_HOME}/db/${ICREDITSTUDIO_VERSION}/icreditstudio_service_dml.sql"
  echo "Rebuild the table"
fi
###########################################################################


#Deal common config
echo "Update config..."

##dolphinscheduler api
sed -i ${txt}  "s#server-addr:.*#server-addr: $NACOS_SERVER_ADDRESS#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-api/conf/application-$PROFILE.yml
sed -i ${txt}  "s#host:.*#host: $REDIS_HOST#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-api/conf/application-$PROFILE.yml
sed -i ${txt}  "s#port:.*#port: $REDIS_PORT#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-api/conf/application-$PROFILE.yml
sed -i ${txt}  "s#fs.defaultFS=.*#fs.defaultFS=$defaultFS#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-api/conf/common.properties
sed -i ${txt}  "s#hdfs.root.user=.*#hdfs.root.user=$HDFS_ROOT_USER#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-api/conf/common.properties
sed -i ${txt}  "s#spring.datasource.url=.*#spring.datasource.url=$SCHEDULER_DATASOURCE_URL#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-api/conf/datasource.properties
sed -i ${txt}  "s#spring.datasource.username=.*#spring.datasource.username=$SCHEDULER_DATASOURCE_USER#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-api/conf/datasource.properties
sed -i ${txt}  "s#spring.datasource.password=.*#spring.datasource.password=$SCHEDULER_DATASOURCE_PASSWORD#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-api/conf/datasource.properties
sed -i ${txt}  "s#task.datasource.url=.*#task.datasource.url=$TASK_DATASOURCE_URL#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-api/conf/task.properties
sed -i ${txt}  "s#task.datasource.username=.*#task.datasource.username=$TASK_DATASOURCE_USER#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-api/conf/task.properties
sed -i ${txt}  "s#task.datasource.password=.*#task.datasource.password=$TASK_DATASOURCE_PASSWORD#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-api/conf/task.properties
sed -i ${txt}  "s#zookeeper.quorum=.*#zookeeper.quorum=$ZOOKEEPER_QUORUM#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-api/conf/zookeeper.properties

##dolphinscheduler master
sed -i ${txt}  "s#server-addr:.*#server-addr: $NACOS_SERVER_ADDRESS#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-master-server/conf/application-$PROFILE.yml
sed -i ${txt}  "s#host:.*#host: $REDIS_HOST#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-master-server/conf/application-$PROFILE.yml
sed -i ${txt}  "s#port:.*#port: $REDIS_PORT#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-master-server/conf/application-$PROFILE.yml
sed -i ${txt}  "s#fs.defaultFS=.*#fs.defaultFS=$defaultFS#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-master-server/conf/common.properties
sed -i ${txt}  "s#hdfs.root.user=.*#hdfs.root.user=$HDFS_ROOT_USER#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-master-server/conf/common.properties
sed -i ${txt}  "s#spring.datasource.url=.*#spring.datasource.url=$SCHEDULER_DATASOURCE_URL#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-master-server/conf/datasource.properties
sed -i ${txt}  "s#spring.datasource.username=.*#spring.datasource.username=$SCHEDULER_DATASOURCE_USER#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-master-server/conf/datasource.properties
sed -i ${txt}  "s#spring.datasource.password=.*#spring.datasource.password=$SCHEDULER_DATASOURCE_PASSWORD#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-master-server/conf/datasource.properties
sed -i ${txt}  "s#task.datasource.url=.*#task.datasource.url=$TASK_DATASOURCE_URL#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-master-server/conf/task.properties
sed -i ${txt}  "s#task.datasource.username=.*#task.datasource.username=$TASK_DATASOURCE_USER#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-master-server/conf/task.properties
sed -i ${txt}  "s#task.datasource.password=.*#task.datasource.password=$TASK_DATASOURCE_PASSWORD#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-master-server/conf/task.properties
sed -i ${txt}  "s#zookeeper.quorum=.*#zookeeper.quorum=$ZOOKEEPER_QUORUM#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-master-server/conf/zookeeper.properties

##dolphinscheduler worker
sed -i ${txt}  "s#fs.defaultFS=.*#fs.defaultFS=$defaultFS#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-worker-bin/icreditstudio-scheduler-worker-server/conf/common.properties
sed -i ${txt}  "s#hdfs.root.user=.*#hdfs.root.user=$HDFS_ROOT_USER#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-worker-bin/icreditstudio-scheduler-worker-server/conf/common.properties
sed -i ${txt}  "s#spring.datasource.url=.*#spring.datasource.url=$SCHEDULER_DATASOURCE_URL#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-worker-bin/icreditstudio-scheduler-worker-server/conf/datasource.properties
sed -i ${txt}  "s#spring.datasource.username=.*#spring.datasource.username=$SCHEDULER_DATASOURCE_USER#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-worker-bin/icreditstudio-scheduler-worker-server/conf/datasource.properties
sed -i ${txt}  "s#spring.datasource.password=.*#spring.datasource.password=$SCHEDULER_DATASOURCE_PASSWORD#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-worker-bin/icreditstudio-scheduler-worker-server/conf/datasource.properties
sed -i ${txt}  "s#task.datasource.url=.*#task.datasource.url=$TASK_DATASOURCE_URL#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-worker-bin/icreditstudio-scheduler-worker-server/conf/task.properties
sed -i ${txt}  "s#task.datasource.username=.*#task.datasource.username=$TASK_DATASOURCE_USER#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-worker-bin/icreditstudio-scheduler-worker-server/conf/task.properties
sed -i ${txt}  "s#task.datasource.password=.*#task.datasource.password=$TASK_DATASOURCE_PASSWORD#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-worker-bin/icreditstudio-scheduler-worker-server/conf/task.properties
sed -i ${txt}  "s#zookeeper.quorum=.*#zookeeper.quorum=$ZOOKEEPER_QUORUM#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-worker-bin/icreditstudio-scheduler-worker-server/conf/zookeeper.properties
##dolphinscheduler log
sed -i ${txt}  "s#fs.defaultFS=.*#fs.defaultFS=$defaultFS#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-worker-bin/icreditstudio-scheduler-log-server/conf/common.properties

##SERVICE CONFIG
## nacos addr
sed -i ${txt}  "s#server-addr:.*#server-addr: $NACOS_SERVER_ADDRESS#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-datasource/conf/application-$PROFILE.yml
sed -i ${txt}  "s#server-addr:.*#server-addr: $NACOS_SERVER_ADDRESS#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-datasync/conf/application-$PROFILE.yml
sed -i ${txt}  "s#server-addr:.*#server-addr: $NACOS_SERVER_ADDRESS#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-gateway/conf/application-$PROFILE.yml
sed -i ${txt}  "s#server-addr:.*#server-addr: $NACOS_SERVER_ADDRESS#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-metadata/conf/application-$PROFILE.yml
sed -i ${txt}  "s#server-addr:.*#server-addr: $NACOS_SERVER_ADDRESS#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-system/conf/application-$PROFILE.yml
sed -i ${txt}  "s#server-addr:.*#server-addr: $NACOS_SERVER_ADDRESS#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-workspace/conf/application-$PROFILE.yml
## nacos namespace
sed -i ${txt}  "s#namespace:.*#namespace: $NACOS_DISCOVERY_NAMESPACE#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-datasource/conf/application-$PROFILE.yml
sed -i ${txt}  "s#namespace:.*#namespace: $NACOS_DISCOVERY_NAMESPACE#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-datasync/conf/application-$PROFILE.yml
sed -i ${txt}  "s#namespace:.*#namespace: $NACOS_DISCOVERY_NAMESPACE#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-gateway/conf/application-$PROFILE.yml
sed -i ${txt}  "s#namespace:.*#namespace: $NACOS_DISCOVERY_NAMESPACE#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-metadata/conf/application-$PROFILE.yml
sed -i ${txt}  "s#namespace:.*#namespace: $NACOS_DISCOVERY_NAMESPACE#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-system/conf/application-$PROFILE.yml
sed -i ${txt}  "s#namespace:.*#namespace: $NACOS_DISCOVERY_NAMESPACE#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-workspace/conf/application-$PROFILE.yml
##redis host
sed -i ${txt}  "s#host:.*#host: $REDIS_HOST#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-datasource/conf/application-$PROFILE.yml
sed -i ${txt}  "s#host:.*#host: $REDIS_HOST#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-datasync/conf/application-$PROFILE.yml
sed -i ${txt}  "s#host:.*#host: $REDIS_HOST#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-gateway/conf/application-$PROFILE.yml
sed -i ${txt}  "s#host:.*#host: $REDIS_HOST#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-metadata/conf/application-$PROFILE.yml
sed -i ${txt}  "s#host:.*#host: $REDIS_HOST#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-system/conf/application-$PROFILE.yml
sed -i ${txt}  "s#host:.*#host: $REDIS_HOST#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-workspace/conf/application-$PROFILE.yml
##redis port
sed -i ${txt}  "s#port:.*#port: $REDIS_PORT#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-datasource/conf/application-$PROFILE.yml
sed -i ${txt}  "s#port:.*#port: $REDIS_PORT#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-datasync/conf/application-$PROFILE.yml
sed -i ${txt}  "s#port:.*#port: $REDIS_PORT#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-gateway/conf/application-$PROFILE.yml
sed -i ${txt}  "s#port:.*#port: $REDIS_PORT#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-metadata/conf/application-$PROFILE.yml
sed -i ${txt}  "s#port:.*#port: $REDIS_PORT#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-system/conf/application-$PROFILE.yml
sed -i ${txt}  "s#port:.*#port: $REDIS_PORT#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-workspace/conf/application-$PROFILE.yml
##datasource url
sed -i ${txt}  "s#url: \"jdbc:mysql://.*:.*/#url: \"jdbc:mysql://$MYSQL_HOST:$MYSQL_PORT/#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-datasource/conf/application-$PROFILE.yml
sed -i ${txt}  "s#url: \"jdbc:mysql://.*:.*/#url: \"jdbc:mysql://$MYSQL_HOST:$MYSQL_PORT/#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-datasync/conf/application-$PROFILE.yml
sed -i ${txt}  "s#url: \"jdbc:mysql://.*:.*/#url: \"jdbc:mysql://$MYSQL_HOST:$MYSQL_PORT/#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-metadata/conf/application-$PROFILE.yml
sed -i ${txt}  "s#url: \"jdbc:mysql://.*:.*/#url: \"jdbc:mysql://$MYSQL_HOST:$MYSQL_PORT/#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-system/conf/application-$PROFILE.yml
sed -i ${txt}  "s#url: \"jdbc:mysql://.*:.*/#url: \"jdbc:mysql://$MYSQL_HOST:$MYSQL_PORT/#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-workspace/conf/application-$PROFILE.yml
##datasource user
sed -i ${txt}  "s#password: .*:#username: '$MYSQL_USER'/#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-datasource/conf/application-$PROFILE.yml
sed -i ${txt}  "s#username: .*:#username: '$MYSQL_USER'/#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-datasync/conf/application-$PROFILE.yml
sed -i ${txt}  "s#username: .*:#username: '$MYSQL_USER'/#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-metadata/conf/application-$PROFILE.yml
sed -i ${txt}  "s#username: .*:#username: '$MYSQL_USER'/#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-system/conf/application-$PROFILE.yml
sed -i ${txt}  "s#username: .*:#username: '$MYSQL_USER'/#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-workspace/conf/application-$PROFILE.yml
##datasource password
sed -i ${txt}  "s#password: .*:#password: '$MYSQL_PASSWORD'/#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-datasource/conf/application-$PROFILE.yml
sed -i ${txt}  "s#password: .*:#password: '$MYSQL_PASSWORD'/#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-datasync/conf/application-$PROFILE.yml
sed -i ${txt}  "s#password: .*:#password: '$MYSQL_PASSWORD'/#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-metadata/conf/application-$PROFILE.yml
sed -i ${txt}  "s#password: .*:#password: '$MYSQL_PASSWORD'/#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-system/conf/application-$PROFILE.yml
sed -i ${txt}  "s#password: .*:#password: '$MYSQL_PASSWORD'/#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-workspace/conf/application-$PROFILE.yml
##gateway
sed -i ${txt}  "s#port: 13249#port: $GATEWAY_PORT#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-gateway/conf/application-$PROFILE.yml
##metadata
sed -i ${txt}  "s#username: root#username: $HIVE_HIVESERVER_USER#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-metadata/conf/application-$PROFILE.yml
sed -i ${txt}  "s#password: bd@0414#password: $HIVE_HIVESERVER_PASSWORD#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-metadata/conf/application-$PROFILE.yml
sed -i ${txt}  "s#nodes: 192.168.0.174:10000#nodes: $HIVE_HIVESERVER_NODES#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-metadata/conf/application-$PROFILE.yml
sed -i ${txt}  "s#warehouse: /user/hive/warehouse/#warehouse: $HIVE_WAREHOUSE#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-metadata/conf/application-$PROFILE.yml
sed -i ${txt}  "s#defaultFS: 192.168.0.174:8020#defaultFS: $defaultFS#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/icreditstudio-metadata/conf/application-$PROFILE.yml
##FRONTEND

echo "icreditstudio front-end deployment start"

frontend_path=$ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-frontend-bin

echo "========================================================================配置信息======================================================================="

echo "前端访问端口：${FRONTEND_PORT}"
echo "后端网关的地址：${GATEWAY_HOST}:${GATEWAY_PORT}"
echo "静态文件地址：${frontend_path}/dist"
echo "当前路径：${workDir}"
echo "本机ip：${ipaddr}"
echo "========================================================================配置信息======================================================================="
echo ""
# nginx是否安装
# sudo rpm -Uvh http://nginx.org/packages/centos/7/noarch/RPMS/nginx-release-centos-7-0.el7.ngx.noarch.rpm
sudo yum install -y nginx
echo "nginx 安装成功"

# 创建文件并配置nginx
icreditConf(){

	s_host='$host'
    s_remote_addr='$remote_addr'
    s_proxy_add_x_forwarded_for='$proxy_add_x_forwarded_for'
    s_http_upgrade='$http_upgrade'
    echo "
        server {
            listen       $FRONTEND_PORT;# 访问端口
            server_name  localhost;
            #charset koi8-r;
            #access_log  /var/log/nginx/host.access.log  main;
            location / {
              root   ${frontend_path}/dist; # 静态文件目录
              index  index.html index.html;
            }

            location ^~/api/ {
              proxy_pass http://${GATEWAY_HOST}:${GATEWAY_PORT}/;
            }
            error_page   500 502 503 504  /50x.html;
            location = /50x.html {
              root   /usr/share/nginx/html;
            }
        }
    " > /etc/nginx/conf.d/icreditstudio_frontend.conf

}


# 配置nginx
icreditConf

echo "Congratulations! You have installed icreditstudio $ICREDITSTUDIO_VERSION successfully, please use sbin/start-all.sh to start it!"

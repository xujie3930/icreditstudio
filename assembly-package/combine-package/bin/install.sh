#!/usr/bin/env bash
#Actively load user env
source ~/.bash_profile
shellDir=$(dirname $0)
workDir=$(
  cd ${shellDir}/..
  pwd
)

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

export local_host="$(hostname --fqdn)"

ipaddr=$(ip addr | awk '/^[0-9]+: / {}; /inet.*global/ {print gensub(/(.*)\/(.*)/, "\\1", "g", $2)}')

function checkPythonAndJava() {
  python --version
  isSuccess "execute python --version"
  java -version
  isSuccess "execute java --version"
}

function checkHadoopAndHive() {
  hadoopVersion="$(hdfs version)"
  defaultHadoopVersion="2.7"
  checkversion "$hadoopVersion" $defaultHadoopVersion hadoop
  checkversion "$(whereis hive)" "2.3" hive
}

function isSuccess() {
  if [ $? -ne 0 ]; then
    echo "Failed to " + $1
    exit 1
  else
    echo "Succeed to" + $1
  fi
}

function checkversion() {
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
    read -p "Please input the choice:" idx
    if [[ '2' == "$idx" ]]; then
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
  command -v "$1" >/dev/null 2>&1
}

need_cmd() {
  if ! check_cmd "$1"; then
    err "need '$1' (command not found)"
  fi
}

##load config
echo "step1:load config "
export ICREDIT_CONFIG_PATH=${ICREDIT_CONFIG_PATH:-"${workDir}/bin/icredit-env.sh"}
source ${ICREDIT_CONFIG_PATH}

isSuccess "load config"

##env check
echo "Do you want to clear icreditstudio table information in the database?"
echo " 1: Do not execute table-building statements"
echo " 2: Dangerous! Clear all data and rebuild the tables"
echo " other: exit"
echo ""

MYSQL_INSTALL_MODE=1

read -p "Please input the choice:" idx
if [[ '2' == "$idx" ]]; then
  MYSQL_INSTALL_MODE=2
  echo "You chose Rebuild the table"
elif [[ '1' == "$idx" ]]; then
  MYSQL_INSTALL_MODE=1
  echo "You chose not execute table-building statements"
else
  echo "no choice,exit!"
  exit 1
fi

MYSQL_PASSWORD=$(echo ${MYSQL_PASSWORD//'#'/'\#'})

#init db
if [[ '2' == "$MYSQL_INSTALL_MODE" ]]; then
  mysql -h$MYSQL_HOST -P$MYSQL_PORT -u$MYSQL_USER -p$MYSQL_PASSWORD --default-character-set=utf8 -e "source ${ICREDITSTUDIO_HOME}/db/${ICREDITSTUDIO_VERSION}/icreditstudio_service_ddl.sql"
  mysql -h$MYSQL_HOST -P$MYSQL_PORT -u$MYSQL_USER -p$MYSQL_PASSWORD --default-character-set=utf8 -e "source ${ICREDITSTUDIO_HOME}/db/${ICREDITSTUDIO_VERSION}/icreditstudio_service_dml.sql"
  mysql -h$MYSQL_HOST -P$MYSQL_PORT -u$MYSQL_USER -p$MYSQL_PASSWORD --default-character-set=utf8 -e "source ${ICREDITSTUDIO_HOME}/db/${ICREDITSTUDIO_VERSION}/icreditstudio_scheduler_ddl.sql"
  mysql -h$MYSQL_HOST -P$MYSQL_PORT -u$MYSQL_USER -p$MYSQL_PASSWORD --default-character-set=utf8 -e "source ${ICREDITSTUDIO_HOME}/db/${ICREDITSTUDIO_VERSION}/icreditstudio_service_dml.sql"
  echo "Rebuild the table"
fi
###########################################################################

#Deal common config
echo "Update config..."

##dolphinscheduler api

SCHEDULER_API_CONF=$ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-api/conf

sed -i ${txt} "s#spring.cloud.nacos.discovery.server-addr.*#spring.cloud.nacos.discovery.server-addr: $NACOS_SERVER_ADDRESS#g" $SCHEDULER_API_CONF/application-$PROFILE.yml
sed -i ${txt} "s#spring.cloud.nacos.discovery.namespace.*#spring.cloud.nacos.discovery.namespace: $NACOS_DISCOVERY_NAMESPACE#g" $SCHEDULER_API_CONF/application-$PROFILE.yml
sed -i ${txt} "s#spring.redis.host.*#spring.redis.host: $REDIS_HOST#g" $SCHEDULER_API_CONF/application-$PROFILE.yml
sed -i ${txt} "s#spring.redis.port.*#spring.redis.port: $REDIS_PORT#g" $SCHEDULER_API_CONF/application-$PROFILE.yml
sed -i ${txt} "s#fs.defaultFS.*#fs.defaultFS=$defaultFS#g" $SCHEDULER_API_CONF/common.properties
sed -i ${txt} "s#hdfs.root.user.*#hdfs.root.user=$HDFS_ROOT_USER#g" $SCHEDULER_API_CONF/common.properties
sed -i ${txt} "s#spring.datasource.url.*#spring.datasource.url=jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/icreditdolphinscheduler?characterEncoding=UTF-8#g" $SCHEDULER_API_CONF/datasource.properties
sed -i ${txt} "s#spring.datasource.username.*#spring.datasource.username=$MYSQL_USER#g" $SCHEDULER_API_CONF/datasource.properties
sed -i ${txt} "s#spring.datasource.password.*#spring.datasource.password=$MYSQL_PASSWORD#g" $SCHEDULER_API_CONF/datasource.properties
sed -i ${txt} "s#task.datasource.url.*#task.datasource.url=jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/datasync?characterEncoding=utf8#g" $SCHEDULER_API_CONF/task.properties
sed -i ${txt} "s#task.datasource.username.*#task.datasource.username=$MYSQL_USER#g" $SCHEDULER_API_CONF/task.properties
sed -i ${txt} "s#task.datasource.password.*#task.datasource.password=$MYSQL_PASSWORD#g" $SCHEDULER_API_CONF/task.properties
sed -i ${txt} "s#zookeeper.quorum.*#zookeeper.quorum=$ZOOKEEPER_QUORUM#g" $SCHEDULER_API_CONF/zookeeper.properties

##dolphinscheduler master
SCHEDULER_MASTER_CONF=$ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-scheduler-bin/icreditstudio-scheduler-master-server/conf

sed -i ${txt} "s#fs.defaultFS.*#fs.defaultFS=$defaultFS#g" $SCHEDULER_MASTER_CONF/common.properties
sed -i ${txt} "s#hdfs.root.user.*#hdfs.root.user=$HDFS_ROOT_USER#g" $SCHEDULER_MASTER_CONF/common.properties
sed -i ${txt} "s#spring.datasource.url.*#spring.datasource.url=jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/icreditdolphinscheduler?characterEncoding=UTF-8#g" $SCHEDULER_MASTER_CONF/datasource.properties
sed -i ${txt} "s#spring.datasource.username.*#spring.datasource.username=$MYSQL_USER#g" $SCHEDULER_MASTER_CONF/datasource.properties
sed -i ${txt} "s#spring.datasource.password.*#spring.datasource.password=$MYSQL_PASSWORD#g" $SCHEDULER_MASTER_CONF/datasource.properties
sed -i ${txt} "s#task.datasource.url.*#task.datasource.url=jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/datasync?characterEncoding=utf8#g" $SCHEDULER_MASTER_CONF/task.properties
sed -i ${txt} "s#task.datasource.username.*#task.datasource.username=$MYSQL_USER#g" $SCHEDULER_MASTER_CONF/task.properties
sed -i ${txt} "s#task.datasource.password.*#task.datasource.password=$MYSQL_PASSWORD#g" $SCHEDULER_MASTER_CONF/task.properties
sed -i ${txt} "s#zookeeper.quorum.*#zookeeper.quorum=$ZOOKEEPER_QUORUM#g" $SCHEDULER_MASTER_CONF/zookeeper.properties

##dolphinscheduler worker

SCHEDULER_WORKER_CONF=$ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-worker-bin/icreditstudio-scheduler-worker-server/conf

sed -i ${txt} "s#fs.defaultFS.*#fs.defaultFS=$defaultFS#g" $SCHEDULER_WORKER_CONF/common.properties
sed -i ${txt} "s#hdfs.root.user.*#hdfs.root.user=$HDFS_ROOT_USER#g" $SCHEDULER_WORKER_CONF/common.properties
sed -i ${txt} "s#spring.datasource.url.*#spring.datasource.url=jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/icreditdolphinscheduler?characterEncoding=UTF-8#g" $SCHEDULER_WORKER_CONF/datasource.properties
sed -i ${txt} "s#spring.datasource.username.*#spring.datasource.username=$MYSQL_USER#g" $SCHEDULER_WORKER_CONF/datasource.properties
sed -i ${txt} "s#spring.datasource.password.*#spring.datasource.password=$MYSQL_PASSWORD#g" $SCHEDULER_WORKER_CONF/datasource.properties
sed -i ${txt} "s#task.datasource.url.*#task.datasource.url=jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/datasync?characterEncoding=utf8#g" $SCHEDULER_WORKER_CONF/task.properties
sed -i ${txt} "s#task.datasource.username.*#task.datasource.username=$MYSQL_USER#g" $SCHEDULER_WORKER_CONF/task.properties
sed -i ${txt} "s#task.datasource.password.*#task.datasource.password=$MYSQL_PASSWORD#g" $SCHEDULER_WORKER_CONF/task.properties
sed -i ${txt} "s#zookeeper.quorum.*#zookeeper.quorum=$ZOOKEEPER_QUORUM#g" $SCHEDULER_WORKER_CONF/zookeeper.properties
##dolphinscheduler log
sed -i ${txt} "s#fs.defaultFS.*#fs.defaultFS=$defaultFS#g" $ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-worker-bin/icreditstudio-scheduler-log-server/conf/common.properties

##SERVICE CONFIG
## nacos addr

SERVICE_CONF_BASE=$ICREDITSTUDIO_HOME/jnh-datasphere-icreditstudio-services-bin/servers

sed -i ${txt} "s#spring.cloud.nacos.discovery.server-addr.*#spring.cloud.nacos.discovery.server-addr: $NACOS_SERVER_ADDRESS#g" $SERVICE_CONF_BASE/icreditstudio-datasource/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.cloud.nacos.discovery.server-addr.*#spring.cloud.nacos.discovery.server-addr: $NACOS_SERVER_ADDRESS#g" $SERVICE_CONF_BASE/icreditstudio-datasync/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.cloud.nacos.discovery.server-addr.*#spring.cloud.nacos.discovery.server-addr: $NACOS_SERVER_ADDRESS#g" $SERVICE_CONF_BASE/icreditstudio-gateway/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.cloud.nacos.discovery.server-addr.*#spring.cloud.nacos.discovery.server-addr: $NACOS_SERVER_ADDRESS#g" $SERVICE_CONF_BASE/icreditstudio-metadata/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.cloud.nacos.discovery.server-addr.*#spring.cloud.nacos.discovery.server-addr: $NACOS_SERVER_ADDRESS#g" $SERVICE_CONF_BASE/icreditstudio-system/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.cloud.nacos.discovery.server-addr.*#spring.cloud.nacos.discovery.server-addr: $NACOS_SERVER_ADDRESS#g" $SERVICE_CONF_BASE/icreditstudio-workspace/conf/bootstrap-$PROFILE.yml
## nacos namespace
sed -i ${txt} "s#spring.cloud.nacos.discovery.namespace.*#spring.cloud.nacos.discovery.namespace: $NACOS_DISCOVERY_NAMESPACE#g" $SERVICE_CONF_BASE/icreditstudio-datasource/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.cloud.nacos.discovery.namespace.*#spring.cloud.nacos.discovery.namespace: $NACOS_DISCOVERY_NAMESPACE#g" $SERVICE_CONF_BASE/icreditstudio-datasync/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.cloud.nacos.discovery.namespace.*#spring.cloud.nacos.discovery.namespace: $NACOS_DISCOVERY_NAMESPACE#g" $SERVICE_CONF_BASE/icreditstudio-gateway/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.cloud.nacos.discovery.namespace.*#spring.cloud.nacos.discovery.namespace: $NACOS_DISCOVERY_NAMESPACE#g" $SERVICE_CONF_BASE/icreditstudio-metadata/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.cloud.nacos.discovery.namespace.*#spring.cloud.nacos.discovery.namespace: $NACOS_DISCOVERY_NAMESPACE#g" $SERVICE_CONF_BASE/icreditstudio-system/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.cloud.nacos.discovery.namespace.*#spring.cloud.nacos.discovery.namespace: $NACOS_DISCOVERY_NAMESPACE#g" $SERVICE_CONF_BASE/icreditstudio-workspace/conf/bootstrap-$PROFILE.yml
##redis host
sed -i ${txt} "s#spring.redis.host.*#spring.redis.host: $REDIS_HOST#g" $SERVICE_CONF_BASE/icreditstudio-datasource/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.redis.host.*#spring.redis.host: $REDIS_HOST#g" $SERVICE_CONF_BASE/icreditstudio-datasync/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.redis.host.*#spring.redis.host: $REDIS_HOST#g" $SERVICE_CONF_BASE/icreditstudio-gateway/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.redis.host.*#spring.redis.host: $REDIS_HOST#g" $SERVICE_CONF_BASE/icreditstudio-metadata/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.redis.host.*#spring.redis.host: $REDIS_HOST#g" $SERVICE_CONF_BASE/icreditstudio-system/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.redis.host.*#spring.redis.host: $REDIS_HOST#g" $SERVICE_CONF_BASE/icreditstudio-workspace/conf/bootstrap-$PROFILE.yml
##redis port
sed -i ${txt} "s#spring.redis.port.*#spring.redis.port: $REDIS_PORT#g" $SERVICE_CONF_BASE/icreditstudio-datasource/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.redis.port.*#spring.redis.port: $REDIS_PORT#g" $SERVICE_CONF_BASE/icreditstudio-datasync/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.redis.port.*#spring.redis.port: $REDIS_PORT#g" $SERVICE_CONF_BASE/icreditstudio-gateway/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.redis.port.*#spring.redis.port: $REDIS_PORT#g" $SERVICE_CONF_BASE/icreditstudio-metadata/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.redis.port.*#spring.redis.port: $REDIS_PORT#g" $SERVICE_CONF_BASE/icreditstudio-system/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.redis.port.*#spring.redis.port: $REDIS_PORT#g" $SERVICE_CONF_BASE/icreditstudio-workspace/conf/bootstrap-$PROFILE.yml
##datasource url
sed -i ${txt} "s#spring.datasource.url.*#spring.datasource.url: jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/datasource?characterEncoding=utf8#g" $SERVICE_CONF_BASE/icreditstudio-datasource/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.datasource.url.*#spring.datasource.url: jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/datasync?characterEncoding=utf8#g" $SERVICE_CONF_BASE/icreditstudio-datasync/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.datasource.url.*#spring.datasource.url: jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/datasync?characterEncoding=utf8#g" $SERVICE_CONF_BASE/icreditstudio-metadata/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.datasource.url.*#spring.datasource.url: jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/icdstuiframe?characterEncoding=utf8#g" $SERVICE_CONF_BASE/icreditstudio-system/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.datasource.url.*#spring.datasource.url: jdbc:mysql://${MYSQL_HOST}:${MYSQL_PORT}/workspace?characterEncoding=utf8#g" $SERVICE_CONF_BASE/icreditstudio-workspace/conf/bootstrap-$PROFILE.yml
##datasource user
sed -i ${txt} "s#spring.datasource.username.*#spring.datasource.username: $MYSQL_USER#g" $SERVICE_CONF_BASE/icreditstudio-datasource/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.datasource.username.*#spring.datasource.username: $MYSQL_USER#g" $SERVICE_CONF_BASE/icreditstudio-datasync/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.datasource.username.*#spring.datasource.username: $MYSQL_USER#g" $SERVICE_CONF_BASE/icreditstudio-metadata/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.datasource.username.*#spring.datasource.username: $MYSQL_USER#g" $SERVICE_CONF_BASE/icreditstudio-system/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.datasource.username.*#spring.datasource.username: $MYSQL_USER#g" $SERVICE_CONF_BASE/icreditstudio-workspace/conf/bootstrap-$PROFILE.yml
##datasource password
sed -i ${txt} "s#spring.datasource.password.*#spring.datasource.password: $MYSQL_PASSWORD#g" $SERVICE_CONF_BASE/icreditstudio-datasource/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.datasource.password.*#spring.datasource.password: $MYSQL_PASSWORD#g" $SERVICE_CONF_BASE/icreditstudio-datasync/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.datasource.password.*#spring.datasource.password: $MYSQL_PASSWORD#g" $SERVICE_CONF_BASE/icreditstudio-metadata/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.datasource.password.*#spring.datasource.password: $MYSQL_PASSWORD#g" $SERVICE_CONF_BASE/icreditstudio-system/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#spring.datasource.password.*#spring.datasource.password: $MYSQL_PASSWORD#g" $SERVICE_CONF_BASE/icreditstudio-workspace/conf/bootstrap-$PROFILE.yml
##gateway
sed -i ${txt} "s#server.port.*#server.port: $GATEWAY_PORT#g" $SERVICE_CONF_BASE/icreditstudio-gateway/conf/bootstrap-$PROFILE.yml
##metadata
sed -i ${txt} "s#hive.username.*#hive.username: $HIVE_HIVESERVER_USER#g" $SERVICE_CONF_BASE/icreditstudio-metadata/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#hive.password.*#hive.password: $HIVE_HIVESERVER_PASSWORD#g" $SERVICE_CONF_BASE/icreditstudio-metadata/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#hive.nodes.*#hive.nodes: $HIVE_HIVESERVER_NODES#g" $SERVICE_CONF_BASE/icreditstudio-metadata/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#hive.warehouse.*#hive.warehouse: $HIVE_WAREHOUSE#g" $SERVICE_CONF_BASE/icreditstudio-metadata/conf/bootstrap-$PROFILE.yml
sed -i ${txt} "s#hive.defaultFS.*#hive.defaultFS: $defaultFS#g" $SERVICE_CONF_BASE/icreditstudio-metadata/conf/bootstrap-$PROFILE.yml

echo "Congratulations! You have installed icreditstudio $ICREDITSTUDIO_VERSION successfully, please use sbin/start-all.sh to start it!"

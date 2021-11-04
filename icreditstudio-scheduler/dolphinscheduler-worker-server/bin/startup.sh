#!/bin/bash
cd `dirname $0`
cd ..
HOME=`pwd`

export ICREDIT_STUDIO_LOG_PATH=$HOME/logs
export SERVER_CLASS=org.apache.dolphinscheduler.server.worker.WorkerServer

if test -z "$SERVER_HEAP_SIZE"
then
  export SERVER_HEAP_SIZE="512M"
fi

if test -z "$SERVER_JAVA_OPTS"
then
  export SERVER_JAVA_OPTS=" -Xmx$SERVER_HEAP_SIZE -XX:+UseG1GC -Dlogging.config=classpath:logback-worker.xml -Ddruid.mysql.usePingMethod=false"
fi

export SERVER_PID=$HOME/bin/icreditstudio-scheduler-worker.pid

if [[ -f "${SERVER_PID}" ]]; then
    pid=$(cat ${SERVER_PID})
    if kill -0 ${pid} >/dev/null 2>&1; then
      echo "Server is already running."
      exit 1
    fi
fi

nohup java $SERVER_JAVA_OPTS  -Duser.timezone=Asia/Shanghai -cp $HOME/conf:$HOME/lib/* $SERVER_CLASS  2>&1 > $HOME/bin/nohup.out &

pid=$!
if [[ -z "${pid}" ]]; then
    echo "server $SERVER_NAME start failed with profile $PROFILE !"
    exit 1
else
    echo "server $SERVER_NAME start succeeded with profile $PROFILE !"
    echo $pid > $SERVER_PID
    sleep 1
fi

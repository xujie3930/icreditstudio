#!/usr/bin/env bash

info="We will start  icreditstudio frontend, it will take some time, please wait"
echo ${info}

startNginx(){

    # 启动nginx
    systemctl restart nginx
}

startNginx

echo "请浏览器访问：http://${FRONTEND_HOST}:${FRONTEND_PORT}"

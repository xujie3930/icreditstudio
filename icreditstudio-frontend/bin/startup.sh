#Actively load user env
source ~/.bash_profile
shellDir=$(dirname $0)
workDir=$(
  cd ${shellDir}/..
  pwd
)
info="We will start  icreditstudio frontend, it will take some time, please wait"
echo ${info}

##FRONTEND

ipaddr=$(ip addr | awk '/^[0-9]+: / {}; /inet.*global/ {print gensub(/(.*)\/(.*)/, "\\1", "g", $2)}')

export ICREDIT_CONFIG_PATH=${ICREDIT_CONFIG_PATH:-"${workDir}/bin/config.sh"}
source ${ICREDIT_CONFIG_PATH}

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
icreditConf() {

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
    " >/etc/nginx/conf.d/icreditstudio_frontend.conf

}

# 配置nginx
icreditConf

startNginx(){

    # 启动nginx
    systemctl restart nginx
}

echo "icreditstudio front-end deployment start"

startNginx

echo "请浏览器访问：http://${ipaddr}:${FRONTEND_PORT}"

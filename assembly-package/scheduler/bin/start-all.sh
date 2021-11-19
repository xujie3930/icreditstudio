#!/usr/bin/env bash


info="We will start all icreditstudio applications, it will take some time, please wait"
echo ${info}

#Actively load user env
source /etc/profile
source ~/.bash_profile
profile=$1
shellDir=`dirname $0`
workDir=`cd ${shellDir}/..;pwd`


function isSuccess(){
if [ $? -ne 0 ]; then
    echo "ERROR:  " + $1
    exit 1
else
    echo "INFO:" + $1
fi
}



function startApp(){
echo "<-------------------------------->"
echo "Begin to start $SERVER_NAME"
SERVER_BIN=${workDir}/scheduler/icreditstudio-${SERVER_NAME}/bin
SERVER_LOCAL_START_CMD="sh ${SERVER_BIN}/startup.sh $profile"

eval $SERVER_LOCAL_START_CMD

isSuccess "End to start $SERVER_NAME"
echo "<-------------------------------->"
}

SERVER_NAME=scheduler-api
startApp

SERVER_NAME=scheduler-master-server
startApp

echo "icreditstudio started successfully"

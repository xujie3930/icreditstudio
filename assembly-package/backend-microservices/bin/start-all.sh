#!/usr/bin/env bash


info="We will start all icreditstudio applications, it will take some time, please wait"
echo ${info}

#Actively load user env
source /etc/profile
source ~/.bash_profile

shellDir=`dirname $0`
profile=$1
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
SERVER_BIN=${workDir}/servers/icreditstudio-${SERVER_NAME}/bin
SERVER_LOCAL_START_CMD="sh ${SERVER_BIN}/startup.sh $profile"

eval $SERVER_LOCAL_START_CMD

isSuccess "End to start $SERVER_NAME"
echo "<-------------------------------->"
}

SERVER_NAME=gateway
startApp

SERVER_NAME=system
startApp

SERVER_NAME=workspace
startApp

SERVER_NAME=datasync
startApp

SERVER_NAME=datasource
startApp

SERVER_NAME=metadata
startApp

echo "icreditstudio started successfully"

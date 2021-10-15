#!/usr/bin/env bash


info="We will stop all icreditstudio applications, it will take some time, please wait"
echo ${info}

#Actively load user env
source /etc/profile
source ~/.bash_profile

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



function stopApp(){
echo "<-------------------------------->"
echo "Begin to stop $SERVER_NAME"
SERVER_BIN=${workDir}/servers/icreditstudio-${SERVER_NAME}/bin
SERVER_LOCAL_START_CMD="sh ${SERVER_BIN}/stop.sh"

eval $SERVER_LOCAL_START_CMD

isSuccess "End to stop $SERVER_NAME"
echo "<-------------------------------->"
}

SERVER_NAME=gateway
stopApp

SERVER_NAME=system
stopApp

SERVER_NAME=workspace
stopApp

SERVER_NAME=datasync
stopApp

SERVER_NAME=datasource
stopApp

SERVER_NAME=metadata
stopApp

echo "icreditstudio stop successfully"

package com.jinninghui.datasphere.icreditstudio.sparkx.test;

import com.jinninghui.datasphere.icreditstudio.sparkx.executor.Main;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.SparkEngineSession;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.SparkScalaExecutor;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.response.ExecuteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SparkScalaExecutorTest {

    private static Logger logger = LoggerFactory.getLogger(SparkScalaExecutorTest.class);

    static String code =
            "    val configFile1 = \"full-batch.yaml\"\n" +
            "    val date = \"20191211\"\n" +
            "    App.main(Array(\"-d\", date, \"-c\", configFile1, \"--debug\"))";

    public static void main(String[] args) {


        SparkEngineSession sparkEngineSession = Main.createEngineConnSession();
        SparkScalaExecutor sparkScalaExecutor = new SparkScalaExecutor(sparkEngineSession);
        sparkScalaExecutor.init();
//        sparkScalaExecutor.runCode("sc.getConf.getAll");
//        ExecuteResponse executeResponse = sparkScalaExecutor.runCode("sql(\"show databases\").show");

        ExecuteResponse executeResponse = sparkScalaExecutor.runCode(code);
        logger.info(executeResponse.code().name());
        logger.info("" + executeResponse.success());
        logger.info("" + executeResponse.data());
//        sparkScalaExecutor.runCode("sql(\"drop database hive_test\")");
//
//        sparkScalaExecutor.runCode("sql(\"create database hive_test\")");
//        sparkScalaExecutor.runCode("sql(\"show databases\").show");

    }
}

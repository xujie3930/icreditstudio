package com.jinninghui.datasphere.icreditstudio.sparkx.test;

import com.jinninghui.datasphere.icreditstudio.sparkx.executor.ExecuteResponse;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.Main;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.SparkEngineSession;
import com.jinninghui.datasphere.icreditstudio.sparkx.executor.SparkScalaExecutor;
import org.apache.spark.SparkContext;

public class SparkScalaExecutorTest {

    public static void main(String[] args) {


        SparkEngineSession sparkEngineSession = Main.createEngineConnSession();
        SparkScalaExecutor sparkScalaExecutor = new SparkScalaExecutor(sparkEngineSession);
        sparkScalaExecutor.init();
        sparkScalaExecutor.runCode("sc.getConf.getAll");

        sparkScalaExecutor.runCode("sql(\"show databases\").show");
        sparkScalaExecutor.runCode("sql(\"drop database hive_test\")");

        sparkScalaExecutor.runCode("sql(\"create database hive_test\")");
        sparkScalaExecutor.runCode("sql(\"show databases\").show");

    }
}
